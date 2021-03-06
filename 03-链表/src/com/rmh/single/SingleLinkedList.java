package com.rmh.single;

import com.rmh.AbstractList;

public class SingleLinkedList<E> extends AbstractList<E>{
	
//	private int size;
	private Node<E> first;
	
	private static class Node<E> {
		E element;
		Node<E> next;
		public Node(E element,Node<E> next) {
			this.element  = element;
			this.next = next;
		}
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		size = 0;
		first = null;
		
	}

	@Override
	public E get(int index) {
		// TODO Auto-generated method stub
		return node(index).element;
	}

	@Override
	public E set(int index, E element) {
		// TODO Auto-generated method stub
		Node<E> node = node(index);
		E oldE = node.element;
		node.element = element;
		return oldE;
	}

	@Override
	public void add(int index, E element) {
		rangeCheckForAdd(index);
		if (index == 0) {
			 first = new Node<E>(element,first);
		}else {
			Node<E>prev = node(index - 1);
			prev.next = new Node<E>(element,prev.next);
			
		}
		size++;
		
	}

	@Override
	public void remove(int index) {
		rangeCheck(index);
		if (index == 0) {
			first = first.next;
		}else {
			Node<E> prev = node(index - 1);
			prev.next = prev.next.next;
		}
		size--;
	}

	@Override
	public int indexOf(E element) {
		if (element == null) {
			Node<E> node = first;
			for (int i = 0; i < size; i++) {
				if (node.element == null) return i;
				node = node.next;
			}
		}else {
			Node<E> node = first;
			for (int i = 0; i < size; i++) {
				if (node.element.equals(element)) return i;
				node = node.next;
			}
		}
		
		
		return ELEMENT_NOT_FOUND;
	}
	
	/**
	 * 获取index节点对象
	 * @param index
	 * @return
	 */
	private Node<E> node(int index) {
		rangeCheck(index);
		
		Node<E> node = first;
		
		for (int i = 0; i < index; i++) {
			node = node.next;
		}
		
		return node;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
				StringBuilder string = new StringBuilder();
				string.append("size=").append(size).append(",[");
				Node<E> node = first;
				for (int i = 0; i < size; i++) {
					if (i != 0) {
						string.append(",");
					}
					string.append(node.element);
					node = node.next;
				}
				string.append("]");
				return string.toString();
	}


}
