package com.rmh.tree;

import java.util.LinkedList;
import java.util.Queue;

import com.rmh.printer.BinaryTreeInfo;

public class BinaryTree<E> implements BinaryTreeInfo{
	protected int size;
	protected Node<E> root;
	
	public int size() {
		return 0;
	}
	
	public boolean isEmpty() {
		return false;
	}
	
	public void clear() {
		
	}
	
	
	protected static class Node<E> {
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
		
		public boolean isLeftChild() {
			return parent != null && this == parent.left;
		}
		
		public boolean isrightChild() {
			return parent != null && this == parent.right;
		}
		
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
	/**
	 * 后序遍历
	 */
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
	
	protected Node<E> predecessor(Node<E> node){
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
	
	
	protected Node<E> successor(Node<E> node){
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
	
	protected Node<E> createNode(E element, Node<E> parent) {
		return new Node<>(element,parent);
	}

	
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
