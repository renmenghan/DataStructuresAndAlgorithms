package com.rmh;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import com.rmh.printer.BinaryTreeInfo;

public class BinarySearchTree<E> implements BinaryTreeInfo {

	private int size;
	private Node<E> root;
	private Comparator<E> comparator;
	public BinarySearchTree(Comparator<E> comparator){
		this.comparator = comparator;
	}
	
	public BinarySearchTree(){
		this(null);
	}
	
	public int size() {
		return 0;
	}
	
	public boolean isEmpty() {
		return false;
	}
	
	public void clear() {
		
	}
	
	public void add(E element) {
		elementNotNullCheck(element);
		
		if (root == null) {// 第一个节点
			root = new Node<>(element, null);
			size++;
			return;
		}
		// 找到父节点
		Node<E> node = root;
		Node<E> parent = root;
		int cmp = 0;
		while (node != null) {
			
			cmp = compare(element, node.element);
			parent = node;
			if (cmp > 0) {
				node = node.right;
			}else if (cmp < 0) {
				node = node.left; 
			}else {
				node.element = element;
				return;
			}
		}
		//看插入到哪个位置
		Node<E> newNode = new Node<>(element, parent);
		if (cmp > 0) {
			parent.right = newNode;
		}else {
			parent.left = newNode;
		}
		size++;

	}
	
	public void remove(E element) {
		remove(node(element));
	}
	
	public boolean contains(E element) {
		return false;
	}
	
	private void remove(Node<E> node) {
		if (node == null) {
			return;
		}
		
		size--;
		if (node.hasTwoChildren()) { // 度为2的节点
			// 后继节点
			Node<E> s = successor(node);
			node.element = s.element;
			node = s;// 删除后继节点
		}
		// 删除node节点 node的度必然为1或者0
		Node<E> repleacement = node.left!=null ? node.left:node.right;
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
			
		}else if(node.parent == null){ // node是叶子节点并且根结点
			root = null;
		}else {///不是根结点
			if (node == node.parent.right) {
				node.parent.right = null;
			}else {
				node.parent.left = null;
			}
		}
		
	}
	
	private Node<E> node(E element) {
		Node<E> node = root;
		while (node != null) {
			int cmp  = compare(element, node.element);
			if (cmp == 0) return node;
			if ( cmp > 0 ) {
				node = node.right;
			}else {
				node = node.left;
			}
		}
		return null;
		
	}
	
	/**
	 * 前序
	 */
	public void preorderTraversal() {
		preorderTraversal(root);
	}
	
	private void preorderTraversal(Node<E> node) {
		if (node == null) {
			return;
		}
		System.out.println(node.element);
		preorderTraversal(node.left);
		preorderTraversal(node.right);
	}
	
	/**
	 * 中序遍历
	 */
	public void inorderTraversal() {
		inorderTraversal(root);
	}
	
	private void inorderTraversal(Node<E> node) {
		if (node == null) {
			return;
		}
		inorderTraversal(node.left);
		System.out.println(node.element);
		inorderTraversal(node.right);
	}
	
	/**
	 * 后序遍历
	 */
	public void postorderTraversal() {
		postorderTraversal(root);
	}
	
	private void postorderTraversal(Node<E> node) {
		if (node == null) {
			return;
		}
		postorderTraversal(node.left);
		postorderTraversal(node.right);
		System.out.println(node.element);
		
	}
	
	public void postorderTraversal(Visitor<E> vistor) {
		if (vistor == null) {
			return;
		}
		postorderTraversal(root,vistor);
	}
	
	private void postorderTraversal(Node<E> node,Visitor<E> vistor) {
		if (node == null || vistor.stop) {
			return;
		}
		postorderTraversal(node.left,vistor);
		postorderTraversal(node.right,vistor);
		if (vistor.stop) {
			return;
		}
		vistor.stop = vistor.visit(node.element);
//		System.out.println(node.element);
		
	}

	/**
	 * 中序遍历
	 */
	public void leverOrderTranversal() {
		if (root == null) {
			return;
		}
		Queue<Node<E>> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			Node<E> node =  queue.poll();
			System.out.println(node.element);
			
			if (node.left != null) {
				queue.offer(node.left);
			}
			if (node.right != null) {
				queue.offer(node.right);
			}
		}
		
	}
	
	
	/**
	 * 中序遍历
	 */
	public void leverOrderTranversal(Visitor<E> visitor) {
		if (root == null) {
			return;
		}
		Queue<Node<E>> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			Node<E> node =  queue.poll();
			if(visitor.visit(node.element)) return;
			
			if (node.left != null) {
				queue.offer(node.left);
			}
			if (node.right != null) {
				queue.offer(node.right);
			}
		}
		
	}
	
	public int height() {
		if (root == null) {
			return 0;
		}
		int height = 0;
		int levelSize = 1;
		Queue<Node<E>> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			Node<E> node =  queue.poll();
			levelSize--;
			if (node.left != null) {
				queue.offer(node.left);
			}
			if (node.right != null) {
				queue.offer(node.right);
			}
			
			if (levelSize == 0) {// 即将要访问西一层
				levelSize = queue.size();
				height++;
			}
		}
		
		
		return height;
		
//		return height(root);
	}
	
//	public boolean isComplete() {
//		if (root == null) {
//			return false;
//		}
//		Queue<Node<E>> queue = new LinkedList<>();
//		queue.offer(root);
//		boolean leaf = false;
//		while (!queue.isEmpty()) {
//			Node<E> node =  queue.poll();
//			if (leaf && !node.isLeaf()) {
//				return false;
//			}
//			if (node.left != null && node.right != null) {
//				queue.offer(node.left);
//				queue.offer(node.right);
//			}else if (node.left == null && node.right != null) {
//				return false;
//			}else { // 后面遍历的节点都是叶子
//				leaf = true;
//				if (node.left != null) {
//					queue.offer(node.left);
//				}
//			}
//		}
//		return true;
//		
//	}
	
	public boolean isComplete() {
		if (root == null) {
			return false;
		}
		Queue<Node<E>> queue = new LinkedList<>();
		queue.offer(root);
		boolean leaf = false;
		while (!queue.isEmpty()) {
			Node<E> node = queue.poll();
			if (leaf && !node.isLeaf()) {
				return false;
			}
			if (node.left!=null) {
				queue.offer(node.left);
			}else if (node.right != null) {
				return false;
			} 
			if (node.right != null) {
				queue.offer(node.right);
			}else {
				leaf = true;
			}
			
			
		}
		return true;
	}
	
	private int height(Node<E> node) {
		if (node == null) {
			return 0;
		}
		return Math.max(height(node.left), height(node.right)) + 1;
	}
	
	
	public Node<E> predecessor(Node<E> node){
		if (node == null) {
			return null;
		}
		Node<E> p = node.left;
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
	
	
	public Node<E> successor(Node<E> node){
		if (node == null) {
			return null;
		}
		Node<E> p = node.right;
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
	
	
	public static abstract class Visitor<E> {
		boolean stop;
		abstract boolean visit(E element);
	}
	
	
	/**
	 * 返回值等于0 e1=e2 返回值大于0 e1>e2 负1 e1<e2
	 * @param e1
	 * @param e2
	 */
	private int compare(E e1,E e2) {
		if (comparator != null) {
			return comparator.compare(e1, e2);
		}
		return ((Comparable<E>)e1).compareTo(e2);
	}
	
	private void elementNotNullCheck(E element) {
		if (element == null) {
			throw new IllegalArgumentException("element must not be null");
		}
	}
	
	private static class Node<E> {
		E element;
		Node<E> left;
		Node<E> right;
		Node<E> parent;
		public Node (E element,Node<E> parent) {
			this.element = element;
			this.parent = parent;
		}
		public boolean isLeaf() {
			return left==null && right ==null;
		}
		public boolean hasTwoChildren() {
			return left!=null && right !=null;
		}
		
	}

	@Override
	public Object root() {
		// TODO Auto-generated method stub
		return root;
	}

	@Override
	public Object left(Object node) {
		// TODO Auto-generated method stub
		return ((Node<E>)node).left;
	}

	@Override
	public Object right(Object node) {
		// TODO Auto-generated method stub
		return ((Node<E>)node).right;
	}

	@Override
	public Object string(Object node) {
		// TODO Auto-generated method stub
		return ((Node<E>)node).element;
	}
}