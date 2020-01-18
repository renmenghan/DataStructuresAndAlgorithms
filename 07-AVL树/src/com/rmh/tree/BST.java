package com.rmh.tree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import com.rmh.printer.BinaryTreeInfo;

public class BST<E> extends BinaryTree<E> {


	private Comparator<E> comparator;
	public BST(Comparator<E> comparator){
		this.comparator = comparator;
	}
	
	public BST(){
		this(null);
	}
	

	
	public void add(E element) {
		elementNotNullCheck(element);
		
		if (root == null) {// 第一个节点
			root = createNode(element, null);
			size++;
			
			afterAdd(root);
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
		Node<E> newNode = createNode(element,parent);
		if (cmp > 0) {
			parent.right = newNode;
		}else {
			parent.left = newNode;
		}
		size++;
		// 新添加节点之后的处理
		afterAdd(newNode);

	}
	
	protected void afterAdd(Node<E> node) {}
	protected void afterRemove(Node<E> node) {}
	
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
			// 删除节点之后的处理
			afterRemove(node);
			
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



}
