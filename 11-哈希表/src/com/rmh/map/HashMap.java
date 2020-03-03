package com.rmh.map;

public class HashMap<K, V> implements Map<K, V> {
	
	private static final boolean RED = false;
	private static final boolean BLACK = true;
	
	private int size;
	private Node<K, V>[] table;
	private static final int DEFAULT_CAPACITY = 1<<4;
	@SuppressWarnings("unchecked")
	public HashMap() {
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
		do {
			cmp = compare(key, node.key ,h1,node.hash);
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
		return null;
	}

	@Override
	public V remove(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsKey(K key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(V value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void traversal(com.rmh.map.Map.Visitor<K, V> visitor) {
		// TODO Auto-generated method stub

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
		
		return hash ^ (hash >>> 16) & (table.length - 1);
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
		
		return 0;
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
			root = p;
		}
		// 更新child parent
		if (child != null) {
			child.parent = grand;
		}
		
		// 更新grand parent
		grand.parent = p;
		

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
		
		
	}
	

}
