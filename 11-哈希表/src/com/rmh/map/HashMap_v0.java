package com.rmh.map;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import com.rmh.printer.BinaryTreeInfo;
import com.rmh.printer.BinaryTrees;

public class HashMap_v0<K, V> implements Map<K, V> {
	
	private static final boolean RED = false;
	private static final boolean BLACK = true;
	
	private int size;
	private Node<K, V>[] table;
	private static final int DEFAULT_CAPACITY = 1<<4;
	@SuppressWarnings("unchecked")
	public HashMap_v0() {
		// TODO Auto-generated constructor stub
		table = new Node[DEFAULT_CAPACITY];
	}
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size == 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		if (size == 0) {
			return;
		}
		size = 0;
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public V put(K key, V value) {
		// TODO Auto-generated method stub
		int index = index(key);
		// 取出index位置的红黑树根节点
		Node<K, V> root = table[index];
		if (root == null) {
			root = new Node<>(key, value, null);
			table[index]= root;
			size++;
			afterPut(root);
			return null;
		}
		
		Node<K,V> node = root;
		Node<K,V> parent = root;
		int cmp = 0;
		int h1 = key == null?0:key.hashCode();
		Node<K, V>result = null;
		boolean searched = false; // 是否已经搜索过
		K k1 = key;
		do {
			parent = node;
			K k2 = node.key;
			int h2 = node.hash;
			if (h1 > h2) {
				cmp = 1;
			}else if (h1 < h2) {
				cmp = -1; 
			}else if (Objects.equals(k1, k2)) {
				cmp = 0;
			}else if (k1 != null  && k2 != null
					&& k1.getClass() == k2.getClass()
					&& k1 instanceof Comparable
					&& (cmp = ((Comparable) k1).compareTo(k2)) != 0 ) {
				
			}else if(searched) {  // 已经扫描了
				cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
			}else { // 还没扫描 再根据内存地址决定左右
				if ((node.left != null && (result = node(node.left,k1)) != null)
						|| (node.right != null && (result = node(node.right,k1)) != null)) {
					// 已经存在key
					node = result;
					cmp = 0;
				}else { // 不存在key
					searched = true;
					cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
				}
			}
			if (cmp > 0) {
				node = node.right;
			}else if (cmp < 0) {
				node = node.left; 
			}else if (Objects.equals(k1, k2)) {
				node.key = key;
				V oldValue = node.value;
				node.value = value;
				node.hash = h1;
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
	
		return node != null ?node.value : null;
	}

	@Override
	public V remove(K key) {
		// TODO Auto-generated method stub
		return remove(node(key));
	}

	@Override
	public boolean containsKey(K key) {
		// TODO Auto-generated method stub
		return node(key) != null;
	}

	@Override
	public boolean containsValue(V value) {
		// TODO Auto-generated method stub
		if (size == 0) {
			return false;
		}
		Queue<Node<K, V>> queue = new LinkedList<>();
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null) continue;
			queue.offer(table[i]);
			while (!queue.isEmpty()) {
				Node<K, V> node = queue.poll();
				if (Objects.equals(value, node.value)) {
					return true;
				}
				if (node.left != null) {
					queue.offer(node.left);
				}
				if (node.right != null) {
					queue.offer(node.right);
				}
			}
		}
		
		
		return false;
	}

	@Override
	public void traversal(com.rmh.map.Map.Visitor<K, V> visitor) {
		if (size == 0 || visitor == null) {
			return;
		}
		Queue<Node<K, V>> queue = new LinkedList<>();
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null) continue;
			queue.offer(table[i]);
			while (!queue.isEmpty()) {
				Node<K, V> node = queue.poll();
				if (visitor.visit(node.key, node.value)) {
					return;
				}
				if (node.left != null) {
					queue.offer(node.left);
				}
				if (node.right != null) {
					queue.offer(node.right);
				}
			}
		}
	}
	
	private V remove(Node<K, V> node) {
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
			node.hash = s.hash;
			node = s;// 删除后继节点
		}		
		// 删除node节点 node的度必然为1或者0
		Node<K,V> repleacement = node.left!=null ? node.left:node.right;
		
		int index= index(node);
		
		if (repleacement!=null) { // node是度为1的情况
			// 更改parent
			repleacement.parent = node.parent;
			// 更改 parent right/left指向
			if (node.parent == null) { // node是度为1的节点并且是根结点
				table[index] = repleacement;
			}else if (node == node.parent.left) {
				node.parent.left = repleacement;
			}else {
				node.parent.right = repleacement;
			}
			// 删除节点之后的处理
			afterRemove(repleacement);
			
		}else if(node.parent == null){ // node是叶子节点并且根结点
			table[index] = null;
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
	
	public void print() {
		if (size == 0 ) {
			return;
		}
		for (int i = 0; i < table.length; i++) {
			final Node<K, V> root = table[i];
			BinaryTrees.println(new BinaryTreeInfo() {
				
				@Override
				public Object string(Object node) {
					// TODO Auto-generated method stub
					return node;
				}
				
				@Override
				public Object root() {
					// TODO Auto-generated method stub
					return root;
				}
				
				@Override
				public Object right(Object node) {
					// TODO Auto-generated method stub
					return ((Node<K, V>)node).right;
				}
				
				@Override
				public Object left(Object node) {
					// TODO Auto-generated method stub
					return ((Node<K, V>)node).left;
				}
			});
		}
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
	
	
	private Node<K, V> node(K key){
		Node<K, V> root = table[index(key)];
	
		return root == null ? null :node(root,key);
	}
	
	
	private Node<K, V> node(Node<K, V> node, K k1){
		int h1 = k1 == null ? 0 : k1.hashCode();
		Node<K, V> result = null;
		int cmp = 0;
		while (node != null) {
			int h2 = node.hash;
			K k2 = node.key;
			// 先比较哈希值
			if (h1 > h2) {
				node = node.right;
			}else if(h1 < h2) {
				node = node.left;
			}else if (Objects.equals(k1, k2)) {
				return node;
			}else if(k1 != null && k2 != null 
					&& k1.getClass() == k2.getClass()
					&& k1 instanceof Comparable
					&& (cmp = ((Comparable) k1).compareTo(k2)) != 0
					) {
				node = cmp > 0 ? node.right : node.left;
				
			}else if(node.right != null && (result = node(node.right,k1)) != null){ // 哈希值相等不具备可比较性也不equals
				return result;
			}else {// 只能往左边找
				node = node.left;
			}
//			else if(node.left != null && (result = node(node.left,k1)) != null){ // 哈希值相等不具备可比较性也不equals
//				return result;
//			}else {
//				return null;
//			}
			
		}
		return null;
	}
	
	
	
	
	/**
	 * 根据key生成对应的索引（在桶数组中的位置）
	 * @param key
	 * @return
	 */
	private int index (K key){
		if (key == null) {
			return 0;
		}
		int hash = key.hashCode();

		return (hash ^ (hash >>> 16))  & (table.length - 1);
	}
	
	private int index (Node<K, V> node){
		return (node.hash ^ (node.hash >>> 16)) & (table.length - 1);
	}
	
	
	/**
	 * 比较key大小
	 * @param k1
	 * @param k2
	 * @param h1 k1hashcode
	 * @param h2	 k2hashcode
	 * @return
	 */
	private int compare(K k1 ,K k2 ,int h1 , int h2){
		// 比较哈希值
		int result = h1 - h2;
		if (result != 0) {
			return result;
		}
		// 比较equals
		if(Objects.equals(k1, k2)) return 0;
		// 哈希值相等 不equals
		// 比较类名
		if (k1 != null  && k2 != null
				&& k1.getClass() == k2.getClass()
				&& k1 instanceof Comparable) {
			
			// 同一种类型 并且具备可比较性
			if (k1 instanceof Comparable) {
				return ((Comparable) k1).compareTo(k2);
			}
			
		}
		// 同一种类型，哈希值 但是不equals，但是不具备可比较性
		// k1不为null k2为null
		// k1为null k2不为null
				
		return System.identityHashCode(k1) - System.identityHashCode(k2);
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
			table[index(grand)] = p;
		}
		// 更新child parent
		if (child != null) {
			child.parent = grand;
		}
		
		// 更新grand parent
		grand.parent = p;
		

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
	
	
	
	
	private static class Node<K,V>{
		int hash;
		K key;
		V value;
		boolean color = RED;
		Node<K,V> left;
		Node<K,V> right;
		Node<K,V> parent;
		public Node (K key,V value,Node<K,V> parent) {
			this.key = key;
			this.hash = key == null ? 0 : key.hashCode();
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
		
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			
			return "node_" + key + "_value" + value;
			
			
		}
		
		
	}
	

}
