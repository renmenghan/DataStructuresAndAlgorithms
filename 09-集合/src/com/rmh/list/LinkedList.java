package com.rmh.list;


public class LinkedList<E> extends AbstractList<E>{
	
	private Node<E> first;
	private Node<E> last;
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
			last = new Node<>(oldLaset, element, null);
			if (oldLaset == null) { // 链表添加的第一个元素
				first = last;
			}else {
				oldLaset.next = last;
			}
			
		}else {
			Node<E> next = node(index);
			Node<E> prev = next.prev;
			Node<E> node = new Node<>(prev, element, next);
			next.prev = node;
			
			if (prev == null) {
				first = node;
			}else {
				prev.next = node;
			}
		}
		
		
		
		
		size++;
		
	}

	@Override
	public void remove(int index) {
		rangeCheck(index);
		Node<E> node = node(index);
		Node<E> prev = node.prev;
		Node<E> next = node.next;
		
		if (prev == null) { //inde == 0
			first = next;
		}else {
			prev.next = next;
		}
		
		if (next == null) { // index == size-1
			last = prev;
		}else {
			next.prev = prev;
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
