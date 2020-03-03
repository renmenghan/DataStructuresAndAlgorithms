package com.rmh.tree;

import java.util.Comparator;

public class AVLTree<E> extends BBST<E> {
	
	public AVLTree() {
		this(null);
	}
	
	
	public AVLTree(Comparator<E> comparator){
		
		super(comparator);
	}
	
	@Override
	protected void afterAdd(Node<E> node) {
		while ((node = node.parent) != null) {
			if (isBalanced(node)) {
				// 更新高度
				updateHeight(node);
			}else {
				// 回复平衡
				rebalance(node);
				break;
			}
			
		}
	}
	
	@Override
	protected void afterRemove(Node<E> node) {
		while ((node = node.parent) != null) {
			if (isBalanced(node)) {
				// 更新高度
				updateHeight(node);
			}else {
				// 回复平衡
				rebalance(node);
			}
		}
	}
	
	@Override
	protected void afterRotate(Node<E> grand, Node<E> p,
			com.rmh.tree.BinaryTree.Node<E> child) {
		// TODO Auto-generated method stub
		super.afterRotate(grand, p, child);
		
		updateHeight(grand);
		updateHeight(p);
	}
	
	@Override
	protected void rotate(Node<E> r, Node<E> a,
			Node<E> b, Node<E> c, Node<E> d,
			Node<E> e, Node<E> f, Node<E> g) {
		// TODO Auto-generated method stub
		super.rotate(r, a, b, c, d, e, f, g);
		
		updateHeight(b);
		updateHeight(f);
		updateHeight(d);
	}
	
	
	@Override
	protected Node<E> createNode(E element, Node<E> parent) {
		return new AVLNode<>(element,parent);
	}
	
	
	/**
	 * 恢复平衡
	 * @param grand 高度最低的不平衡的节点
	 */
	private void rebalance(Node<E>grand) {
		Node<E> parent  = ((AVLNode<E>)grand).tallerChild();
		Node<E> node  = ((AVLNode<E>)parent).tallerChild();
		if (parent.isLeftChild()) {//L
			if (node.isLeftChild()) {	//ll
				rotate(grand, node.left, node, node.right, parent, parent.right, grand, grand.right);
			}else {			//lr
				rotate(grand, parent.left, parent, node.left, node, node.right, grand, grand.right);
			}
		}else { // R
			if (node.isLeftChild()) { // rl
				rotate(grand, grand.left, grand, node.left, node, node.right, parent, parent.right);
			}else { // rr
				rotate(grand, grand.left, grand, parent.left, parent, node.left, node, node.right);
			}
		}
	}
	
	/**
	 * 恢复平衡
	 * @param grand 高度最低的不平衡的节点
	 */
	private void rebalance2(Node<E>grand) {
		Node<E> parent  = ((AVLNode<E>)grand).tallerChild();
		Node<E> node  = ((AVLNode<E>)parent).tallerChild();
		if (parent.isLeftChild()) {//L
			if (node.isLeftChild()) {	//ll
				rotateRight(grand);
			}else {			//lr
				rotateLeft(parent);
				rotateRight(grand);
			}
		}else { // R
			if (node.isLeftChild()) { // rl
				rotateRight(parent);
				rotateLeft(grand);
			}else { // rr
				rotateLeft(grand);
			}
		}
	}
	

	private boolean isBalanced(Node<E> node) {
		return Math.abs(((AVLNode<E>)node).balanceFactor()) <= 1;
	}
	
	private void updateHeight(Node<E> node) {
		((AVLNode<E>) node).updateHeight();
	}
	
	private static class AVLNode<E> extends Node<E> {
		int height = 1;
		
		public AVLNode(E element, Node<E> parent) {
			super(element, parent);
			// TODO Auto-generated constructor stub
		}
		
		public int balanceFactor() {
			int leftHeight = left == null? 0:((AVLNode<E>)left).height;
			int rightHeight = right == null? 0:((AVLNode<E>)right).height;
			
			return leftHeight - rightHeight;
		}
		
		public void updateHeight() {
			int leftHeight = left == null? 0:((AVLNode<E>)left).height;
			int rightHeight = right == null? 0:((AVLNode<E>)right).height;
			height = 1 + Math.max(leftHeight, rightHeight);
		}

		public Node<E> tallerChild() {
			int leftHeight = left == null? 0:((AVLNode<E>)left).height;
			int rightHeight = right == null? 0:((AVLNode<E>)right).height;
			if (leftHeight > rightHeight) {
				return left;
			}
			if (leftHeight < rightHeight) {
				return right;
			}
			return isLeftChild() ? left : right;
			
		}
	}
}
