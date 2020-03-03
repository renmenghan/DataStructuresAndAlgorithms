package com.rmh.map;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;


public class TreeMap<K, V> implements Map<K, V> {
	private static final boolean RED = false;
	private static final boolean BLACK = true;
	
	private Comparator<K> comparator;
	
	public TreeMap() {
		this(null);
	}
	
	public TreeMap(Comparator<K> comparator){
		this.comparator = comparator;
	}
	
	private int size;
	private Node<K,V> root;
	
	public int size() {
		return 0;
	}
	
	public boolean isEmpty() {
		return false;
	}
	
	public void clear() {
		root = null;
		size = 0;
	}
	@Override
	public V put(K key, V value) {
		keyNotNullCheck(key);
		if (root == null) {// 第一个节点
			root = new Node<>(key, value, null);
			size++;
			afterPut(root);
			return null;
		}
		// 找到父节点
		Node<K,V> node = root;
		Node<K,V> parent = root;
		int cmp = 0;
		do {
			
			cmp = compare(key, node.key);
			parent = node;
			if (cmp > 0) {
				node = node.right;
			}else if (cmp < 0) {
				node = node.left; 
			}else {
				node.key = key;
				V oldValue = node.value;
				node.value = value;
				return oldValue;
			}
		} while (node != null);
		
		//看插入到哪个位置
		Node<K,V> newNode = new Node<>(key, value, parent);
		if (cmp > 0) {
			parent.right = newNode;
		}else {
			parent.left = newNode;
		}
		size++;
		// 新添加节点之后的处理
		afterPut(newNode);

		return null;
	}

	@Override
	public V get(K key) {
		Node<K, V> node = node(key);
		return node != null ? node.value:null;
	}

	@Override
	public V remove(K key) {
		return remove(node(key));
	}

	@Override
	public boolean containsKey(K key) {
		return node(key)!=null;
	}

	private boolean valEquals(V v1, V v2){
		return v1==null ? v2==null :v1.equals(v2);
	}
	@Override
	public boolean containsValue(V value) {
		// TODO Auto-generated method stub
		if (root == null ) {
			return false;
		}
		Queue<Node<K, V>> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			Node<K, V> node  = queue.poll();
			if (valEquals(value, node.value)) {
				return true;
			}
			if (node.left != null) {
				queue.offer(node.left);
			}
			if (node.right != null) {
				queue.offer(node.right);
			}
			
		}
		
		return false;
	}

	@Override
	public void traversal(Visitor<K, V> visitor) {
		if (visitor == null) {
			return;
		}
		traversal(root, visitor);
		
	}
	private void traversal(Node<K, V> node,Visitor<K, V> visitor) {
		if (node == null || visitor.stop) {
			return;
		}
		traversal(node.left, visitor);
		if (visitor.stop) {
			return;
		}
		visitor.visit(node.key, node.value);
		traversal(node.right, visitor);
		
	}
	private void afterPut(Node<K, V>node){
		Node<K,V> parent = node.parent;
		
		// 如果父节点是空 添加为根节点
		if (parent == null) {
			black(node);
			return;
		}
		
		// 如果父节点是黑色 直接返回
		if (isBlack(parent)) return;
		
		
		// uncle节点
		Node<K,V> uncle = parent.sibling();
		// 祖父节点
		Node<K,V> grand = parent.parent;
		if (isRed(uncle)) {
			black(parent);
			black(uncle);
			// 祖父节点当做新添加节点
			afterPut(red(grand));
			return;
		}
		
		// 叔父节点不是红色
		if (parent.isLeftChild()) { //l
			red(grand);
			if (node.isLeftChild()) {//ll
				black(parent);
			}else { //lr
				black(node);
				rotateLeft(parent);
			}
			rotateRight(grand);
		} else { // r
			red(grand);
			if (node.isrightChild()) { //rr
				black(parent);
			}else { // rl
				black(node);
				rotateRight(parent);
			}
			rotateLeft(grand);

		}
		

	}
	private int compare(K e1 ,K e2){
		if (comparator!=null) {
			return comparator.compare(e1, e2);
		}
		return ((Comparable<K>)e1).compareTo(e2);
	}
	
	private void keyNotNullCheck(K key) {
		if (key == null) {
			throw new IllegalArgumentException("key must not be null");
		}
	}
	
	private static class Node<K,V>{
		K key;
		V value;
		boolean color = RED;
		Node<K,V> left;
		Node<K,V> right;
		Node<K,V> parent;
		public Node (K key,V value,Node<K,V> parent) {
			this.key = key;
			this.value = value;
			this.parent = parent;
		}
		public boolean isLeaf() {
			return left==null && right ==null;
		}
		public boolean hasTwoChildren() {
			return left!=null && right !=null;
		}
		
		public boolean isLeftChild() {
			return parent != null && this == parent.left;
		}
		
		public boolean isrightChild() {
			return parent != null && this == parent.right;
		}
		
		public Node<K,V> sibling() {
			if (isLeftChild()) {
				return parent.right;
			}
			
			if (isrightChild()) {
				return parent.left;
			}
			return null;
		}
		
		
	}
	

	
	
	private Node<K,V> color(Node<K,V> node,boolean color){
		if (node == null) return node;
		node.color = color;
		return node;
	}
	
	private Node<K,V> red(Node<K,V> node) {
		return color(node, RED);
	}
	private Node<K,V> black(Node<K,V> node) {
		return color(node, BLACK);
	}
	
	private boolean colorIf(Node<K,V> node) {
		return node == null ? BLACK:node.color;
	}
	
	private boolean isBlack(Node<K,V> node){
		return colorIf(node) == BLACK;
	}
	
	private boolean isRed(Node<K,V> node){
		return colorIf(node) == RED;
	}
	
	private void rotateLeft(Node<K,V> grand) {
		Node<K,V> p = grand.right;
		Node<K,V> child = p.left;
		grand.right = child;
		p.left = grand;
		
		afterRotate(grand, p, child);
	}
	
	private void rotateRight(Node<K,V> grand) {

		Node<K,V> p = grand.left;
		Node<K,V> child = p.right;
		grand.left = child;
		p.right = grand;
		
		afterRotate(grand, p, child);
		
	}
	
	private void afterRotate(Node<K,V> grand,Node<K,V> p,Node<K,V> child) {
		// 更新parent parent
		p.parent = grand.parent;
		if (grand.isLeftChild()) {
			grand.parent.left = p;
		}else if (grand.isrightChild()) {
			grand.parent.right = p;
		}else {
			root = p;
		}
		// 更新child parent
		if (child != null) {
			child.parent = grand;
		}
		
		// 更新grand parent
		grand.parent = p;
		

	}
	
	private Node<K,V> node(K key) {
		Node<K,V> node = root;
		while (node != null) {
			int cmp  = compare(key, node.key);
			if (cmp == 0) return node;
			if ( cmp > 0 ) {
				node = node.right;
			}else {
				node = node.left;
			}
		}
		return null;
		
	}
	
	private V remove(Node<K, V> node){
		if (node == null) {
			return null;
		}
		
		size--;
		
		V oldValue = node.value;
		
		if (node.hasTwoChildren()) { // 度为2的节点
			// 后继节点
			Node<K,V> s = successor(node);
			node.key = s.key;
			node.value = s.value;
			node = s;// 删除后继节点
		}
		
		
		
		// 删除node节点 node的度必然为1或者0
		Node<K,V> repleacement = node.left!=null ? node.left:node.right;
		if (repleacement!=null) { // node是度为1的情况
			// 更改parent
			repleacement.parent = node.parent;
			// 更改 parent right/left指向
			if (node.parent == null) { // node是度为1的节点并且是根结点
				root = repleacement;
			}else if (node == node.parent.left) {
				node.parent.left = repleacement;
			}else {
				node.parent.right = repleacement;
			}
			// 删除节点之后的处理
			afterRemove(repleacement);
			
		}else if(node.parent == null){ // node是叶子节点并且根结点
			root = null;
			
			// 删除节点之后的处理
			afterRemove(node);
		}else {///不是根结点
			if (node == node.parent.right) {
				node.parent.right = null;
			}else {
				node.parent.left = null;
			}
			// 删除节点之后的处理
			afterRemove(node);
		}
		
		return oldValue;
	}
	
	private void afterRemove(Node<K,V> node) {
		// TODO Auto-generated method stub
		// 如果删除的节点是红色
//		if (isRed(node)) {
//			return;
//		}
		// 如果删除的节点是红色
		// 或者用于取代删除节点的子节点是红色
		if (isRed(node)) {
			black(node);
			return;
		}
		Node<K,V> parent = node.parent ;
		// 删除的是根节点
		if (parent == null) return;
		// 删除的是黑色的叶子节点[下溢]
		//判断被删除的node是左还是右
		boolean left = parent.left == null || node.isLeftChild();
		Node<K,V> sibling = left?parent.right : parent.left;
		if (left) {// 被删除的节点在左边 兄弟节点在右边
			if (isRed(sibling)) {// 兄弟节点是红色
				black(sibling);
				red(parent);
				rotateLeft(parent);
				// 更换兄弟
				sibling = parent.right;
			}
			// 兄弟节点必然是黑色
			if (isBlack(sibling.left) && isBlack(sibling.right)) {
				// 兄弟节点没有1个红色子节点 父节点要向下跟兄弟节点合并
				boolean parentBlack = isBlack(parent);
				black(parent);
				red(sibling);
				if (parentBlack) {
					afterRemove(parent);
				}
			}else {// 兄弟节点至少有一个红子节点
				// 兄弟节点的左边是黑色，兄弟要先旋转
				if (isBlack(sibling.right)) {
					rotateRight(sibling);
					sibling = parent.right;
				}
				color(sibling, colorIf(parent));
				black(sibling.right);
				black(parent);
				rotateLeft(parent);
			}
			
		}else {// 被删除的节点在右边 兄弟节点在左边
			if (isRed(sibling)) {// 兄弟节点是红色
				black(sibling);
				red(parent);
				rotateRight(parent);
				// 更换兄弟
				sibling = parent.left;
			}
			// 兄弟节点必然是黑色
			if (isBlack(sibling.left) && isBlack(sibling.right)) {
				// 兄弟节点没有1个红色子节点 父节点要向下跟兄弟节点合并
				boolean parentBlack = isBlack(parent);
				black(parent);
				red(sibling);
				if (parentBlack) {
					afterRemove(parent);
				}
			}else {// 兄弟节点至少有一个红子节点
				// 兄弟节点的左边是黑色，兄弟要先旋转
				if (isBlack(sibling.left)) {
					rotateLeft(sibling);
					sibling = parent.left;
				}
				color(sibling, colorIf(parent));
				black(sibling.left);
				black(parent);
				rotateRight(parent);
			}
			
		}
	}
	
	private Node<K,V> predecessor(Node<K,V> node){
		if (node == null) {
			return null;
		}
		Node<K,V> p = node.left;
		if (p != null) {
			// 前驱节点在左子树当中 （left.right.right）
			while (p.right != null) {
				p = p.right;
			}
			return p;
		}
		// 从父节点 祖父节点中寻找前驱节点
		while (node.parent != null && node == node.parent.left) {
			node =  node.parent;
		}
		// parent == null 
		// node == node.parent.right
		return node.parent;
	}
	
	private Node<K,V> successor(Node<K,V> node){
		if (node == null) {
			return null;
		}
		Node<K,V> p = node.right;
		if (p != null) {
			// 前驱节点在右子树当中 （right.left.left）
			while (p.left != null) {
				p = p.left;
			}
			return p;
		}
		// 从父节点 祖父节点中寻找前驱节点
		while (node.parent != null && node == node.parent.right) {
			node =  node.parent;
		}
		// parent == null 
		// node == node.parent.right
		return node.parent;
	}
	
	
	

}
