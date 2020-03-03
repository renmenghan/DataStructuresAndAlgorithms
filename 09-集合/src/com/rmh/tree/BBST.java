package com.rmh.tree;

import java.util.Comparator;

import com.rmh.tree.BinaryTree.Node;

public class BBST<E> extends BST<E> {
	
	public BBST() {
		this(null);
	}
	
	
	public BBST(Comparator<E> comparator){
		
		super(comparator);
	}
	
	protected void rotateLeft(Node<E> grand) {
		Node<E> p = grand.right;
		Node<E> child = p.left;
		grand.right = child;
		p.left = grand;
		
		afterRotate(grand, p, child);
	}
	
	protected void rotateRight(Node<E> grand) {
		Node<E> p = grand.left;
		Node<E> child = p.right;
		grand.left = child;
		p.right = grand;
		
		afterRotate(grand, p, child);
		
	}
	protected void afterRotate(Node<E> grand,Node<E> p,Node<E> child) {
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
	
	protected void rotate(
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
		
		
		// e f g
		f.left = e;
		f.right = g;
		if (e!=null) {
			e.parent =  f;
		} 
		if (g!=null) {
			g.parent = f;
		}
		
		
		// b d f
		d.left = b;
		d.right =f;
		b.parent = d;
		f.parent = d;
		
	}
	
	
	
	
}
