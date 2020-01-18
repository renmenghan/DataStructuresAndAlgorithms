package com.rmh.tree;

import java.util.Comparator;

public class AVLTree<E> extends BST<E> {
	
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
	
	private void rotate(
			Node<E> r,
			Node<E> a,Node<E> b,Node<E> c,
			Node<E> d,
			Node<E> e,Node<E> f,Node<E> g
			) 
	{
		// 让d成为这颗子树的根节点
		d.parent = r.parent;
		if (r.isLeftChild()) {
			r.parent.left = d;
		}else if (r.isrightChild()) {
			r.parent.right = d;
		}else {
			root = d;
		}
		// a b c
		b.left = a;
		b.right = c;
		if (a!=null) {
			a.parent =  b;
		} 
		if (c!=null) {
			c.parent = b;
		}
		
		updateHeight(b);
		
		// e f g
		f.left = e;
		f.right = g;
		if (e!=null) {
			e.parent =  f;
		} 
		if (g!=null) {
			g.parent = f;
		}
		
		updateHeight(f);
		
		// b d f
		d.left = b;
		d.right =f;
		b.parent = d;
		f.parent = d;
		updateHeight(d);
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
	
	private void rotateLeft(Node<E> grand) {
		Node<E> p = grand.right;
		Node<E> child = p.left;
		grand.right = child;
		p.left = grand;
		
		afterRotate(grand, p, child);
	}
	
	private void rotateRight(Node<E> grand) {
		Node<E> p = grand.left;
		Node<E> child = p.right;
		grand.left = child;
		p.right = grand;
		
		afterRotate(grand, p, child);
		
	}
	private void afterRotate(Node<E> grand,Node<E> p,Node<E> child) {
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
		
		updateHeight(grand);
		updateHeight(p);
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
