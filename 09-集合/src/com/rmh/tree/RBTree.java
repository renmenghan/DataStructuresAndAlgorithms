package com.rmh.tree;

import java.util.Comparator;
import com.rmh.tree.BinaryTree;;
public class RBTree<E> extends BBST<E> {
	private static final boolean RED = false;
	private static final boolean BLACK = true;
	public RBTree() {
		this(null);
	}
	
	
	public RBTree(Comparator<E> comparator){
		
		super(comparator);
		
	}
	
	private Node<E> color(Node<E> node,boolean color){
		if (node == null) return node;
		((RBNode<E>)node).color = color;
		return node;
	}
	
	private Node<E> red(Node<E> node) {
		return color(node, RED);
	}
	private Node<E> black(Node<E> node) {
		return color(node, BLACK);
	}
	
	private boolean colorIf(Node<E> node) {
		return node == null ? BLACK:((RBNode<E>)node).color;
	}
	
	private boolean isBlack(Node<E> node){
		return colorIf(node) == BLACK;
	}
	
	private boolean isRed(Node<E> node){
		return colorIf(node) == RED;
	}
	
	@Override
	protected void afterAdd(Node<E> node) {
		Node<E> parent = node.parent;
		
		// 如果父节点是空 添加为根节点
		if (parent == null) {
			black(node);
			return;
		}
		
		// 如果父节点是黑色 直接返回
		if (isBlack(parent)) return;
		
		
		// uncle节点
		Node<E> uncle = parent.sibling();
		// 祖父节点
		Node<E> grand = parent.parent;
		if (isRed(uncle)) {
			black(parent);
			black(uncle);
			// 祖父节点当做新添加节点
			afterAdd(red(grand));
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
	protected void afterRemove(Node<E> node) {
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
		Node<E> parent = node.parent ;
		// 删除的是根节点
		if (parent == null) return;
		// 删除的是黑色的叶子节点[下溢]
		//判断被删除的node是左还是右
		boolean left = parent.left == null || node.isLeftChild();
		Node<E> sibling = left?parent.right : parent.left;
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
	
//	@Override
//	protected void afterRemove(Node<E> node,Node<E>repleacement) {
//		// TODO Auto-generated method stub
//		// 如果删除的节点是红色
//		if (isRed(node)) {
//			return;
//		}
//		
//		// 用于取代node的子节点是红色
//		if (isRed(repleacement)) {
//			black(repleacement);
//			return;
//		}
//		Node<E> parent = node.parent ;
//		// 删除的是根节点
//		if (parent == null) return;
//		// 删除的是黑色的叶子节点[下溢]
//		//判断被删除的node是左还是右
//		boolean left = parent.left == null || node.isLeftChild();
//		Node<E> sibling = left?parent.right : parent.left;
//		if (left) {// 被删除的节点在左边 兄弟节点在右边
//			if (isRed(sibling)) {// 兄弟节点是红色
//				black(sibling);
//				red(parent);
//				rotateLeft(parent);
//				// 更换兄弟
//				sibling = parent.right;
//			}
//			// 兄弟节点必然是黑色
//			if (isBlack(sibling.left) && isBlack(sibling.right)) {
//				// 兄弟节点没有1个红色子节点 父节点要向下跟兄弟节点合并
//				boolean parentBlack = isBlack(parent);
//				black(parent);
//				red(sibling);
//				if (parentBlack) {
//					afterRemove(parent, null);
//				}
//			}else {// 兄弟节点至少有一个红子节点
//				// 兄弟节点的左边是黑色，兄弟要先旋转
//				if (isBlack(sibling.right)) {
//					rotateRight(sibling);
//					sibling = parent.right;
//				}
//				color(sibling, colorIf(parent));
//				black(sibling.right);
//				black(parent);
//				rotateLeft(parent);
//			}
//			
//		}else {// 被删除的节点在右边 兄弟节点在左边
//			if (isRed(sibling)) {// 兄弟节点是红色
//				black(sibling);
//				red(parent);
//				rotateRight(parent);
//				// 更换兄弟
//				sibling = parent.left;
//			}
//			// 兄弟节点必然是黑色
//			if (isBlack(sibling.left) && isBlack(sibling.right)) {
//				// 兄弟节点没有1个红色子节点 父节点要向下跟兄弟节点合并
//				boolean parentBlack = isBlack(parent);
//				black(parent);
//				red(sibling);
//				if (parentBlack) {
//					afterRemove(parent, null);
//				}
//			}else {// 兄弟节点至少有一个红子节点
//				// 兄弟节点的左边是黑色，兄弟要先旋转
//				if (isBlack(sibling.left)) {
//					rotateLeft(sibling);
//					sibling = parent.left;
//				}
//				color(sibling, colorIf(parent));
//				black(sibling.left);
//				black(parent);
//				rotateRight(parent);
//			}
//			
//		}
//	}
	
	@Override
	protected com.rmh.tree.BinaryTree.Node<E> createNode(E element, com.rmh.tree.BinaryTree.Node<E> parent) {
		// TODO Auto-generated method stub
		return new RBNode(element, parent);
	}
	
	private static class RBNode<E> extends Node<E> {
		boolean color;
		public RBNode(E element , Node<E> parent) {
			super(element, parent);
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			String str = "";
			if (color == RED) {
				str = "R_";
			}
			return str + element.toString();
		}
	}
	

}
