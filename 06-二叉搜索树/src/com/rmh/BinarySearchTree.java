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
		
	}
	
	public boolean contains(E element) {
		return false;
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
