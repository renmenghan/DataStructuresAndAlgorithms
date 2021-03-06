package com.rmh.circle;

import com.rmh.AbstractList;

public class CircleLinkedList<E> extends AbstractList<E>{
	
	private Node<E> first;
	private Node<E> last;
	public Node<E> current;
	private static class Node<E> {
		E element;
		Node<E> next;
		Node<E> prev;
		public Node(Node<E> prev,E element,Node<E> next) {
			this.element  = element;
			this.next = next;
			this.prev = prev;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			
			if (prev!=null) {
				sb.append(prev.element);
			}else {
				sb.append("null");
			}
			
			sb.append("_").append(element).append("_");
			if (next!=null) {
				sb.append(next.element);
			}else {
				sb.append("null");
			}
			return sb.toString();
		}
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		size = 0;
		first = null;
		last = null;
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
		
		if (index == size) {
			Node<E> oldLaset = last;
			last = new Node<>(oldLaset, element, first);
			if (oldLaset == null) { // 链表添加的第一个元素
				first = last;
				first.next = first;
				first.prev = first;
				
			}else {
				oldLaset.next = last;
				first.prev = last;
			}
			
		}else {
			Node<E> next = node(index);
			Node<E> prev = next.prev;
			Node<E> node = new Node<>(prev, element, next);
			next.prev = node;
			prev.next = node;
			if (next == first) {// index == 0
				first = node;
			}
		}
		
		
		
		
		size++;
		
	}

	@Override
	public void remove(int index) {
		rangeCheck(index);
		remove(node(index));
	}
	
	public void remove(Node<E> node) {
		if (size == 1) {
			first = null;
			last = null;
		}else {
			Node<E> prev = node.prev;
			Node<E> next = node.next;
			prev.next = next;
			next.prev = prev;
			if (node == first) { //inde == 0
				first = next;
			}
			
			if (node == last) { // index == size-1
				last = prev;
			}
			
			
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
		
		if (size < (size >> 1)) {
			Node<E> node = first;
			
			for (int i = 0; i < index; i++) {
				node = node.next;
			}
			
			return node;
		}else {
			Node<E> node = last;
			
			for (int i = size-1; i > index; i--) {
				node = node.prev;
			}
			
			return node;
		}
		
		
	}
	public void reset() {
		current = first;
	}
	public E next() {
		if (current == null) {
			return null;
		}
		current = current.next;
		return current.element;
	}
	
	public void remove() {
		if (current == null) {
			return;
		}
		Node<E> next =  current.next;
		remove(current);
		if (size == 0) {
			current = null;
		}else {
			current = next;
		}
		
		
	}
	
	@Override
	public String toString() {
				StringBuilder string = new StringBuilder();
				string.append("size=").append(size).append(",[");
				Node<E> node = first;
				for (int i = 0; i < size; i++) {
					if (i != 0) {
						string.append(",");
					}
					string.append(node);
					node = node.next;
				}
				string.append("]");
				return string.toString();
	}


}
