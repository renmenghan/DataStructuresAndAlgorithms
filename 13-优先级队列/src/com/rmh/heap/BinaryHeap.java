package com.rmh.heap;

import java.util.Comparator;


public class BinaryHeap<E> extends AbstractHeap<E>  {
	private E[] elements;
	private static final int DEFAULT_CAPACITY = 10;
	
	public BinaryHeap(E[] elements,Comparator<E> comparator) {
		super(comparator);
		if (elements == null || elements.length == 0) {
			this.elements = (E[])new Object[DEFAULT_CAPACITY];
		}else {
			size = elements.length;
			int capacity = Math.max(elements.length, DEFAULT_CAPACITY);
			this.elements = (E[]) new Object[capacity];
			for (int i = 0; i < elements.length; i++) {
				this.elements[i] = elements[i];
			}
			heapify();
		}
	}
	
	public BinaryHeap(E[] elements) {
		this(elements, null);
	}
	
	public BinaryHeap(Comparator<E> comparator) {
		this(null,comparator);
	}
	
	public BinaryHeap() {
		this(null,null);
	}



	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		
		size = 0;
		

	}

	@Override
	public void add(E element) {
		elementNotNullCheck(element);
		ensureCapacity(size + 1);
		elements[size ++] = element;
		siftUp(size - 1);
	}

	@Override
	public E get() {
		// TODO Auto-generated method stub
		emptyCheck();
		return elements[0];
	}

	@Override
	public E remove() {
		emptyCheck();
		int lastSize = --size ;
		E root = elements[0];
		elements[0] = elements[lastSize];
		elements[lastSize] = null;
		siftDown(0);
		return root;
	}

	@Override
	public E replace(E element) {
		elementNotNullCheck(element);
		E root = null;
		if (size == 0) {
			elements[0] = element;
			size++;
		}else {
			root = elements[0];
			elements[0] = element;
			siftDown(0);
		}
		return root;
	}
	private void siftDown(int index) {
		int half = size >> 1;
		E e = elements[index];
		// 第一个叶子节点的索引等于非叶子节点数量
		while ( index < half ) { //保证index位置是非叶子节点
			// index的节点有2中情况  1只有左子节点 2同时左右
			
			// 默认为左子节点的索引
			int childIndex = (index << 1) + 1;
			E child = elements[childIndex];
			
			// 右子节点
			int rightIndex = childIndex + 1;
			
			if (rightIndex < size && compare(elements[rightIndex], child) > 0) {
				childIndex = rightIndex;
				child = elements[rightIndex];
			}
			
			if (compare(e, child) >= 0) {
				break;
			}
			// 将子节点存放到index位置
			elements[index] = child;
			index = childIndex;
		}
		elements[index] = e;
	}
	
	private void siftUp(int index) {
//		E e = elements[index];
//		while (index > 0 ) {
//			int pindex = (index - 1) >> 1;
//			E p = elements[pindex];
//			if (compare(e, p) <= 0) {
//				return;
//			}
//			// change p e
//			E tmp = elements[index];
//			elements[index] = elements[pindex];
//			elements[pindex] = tmp;
//			
//			pindex = index;
//		}
		
		E e = elements[index];
		while (index > 0 ) {
			int pindex = (index - 1) >> 1;
			E p = elements[pindex];
			if (compare(e, p) <= 0) {
				break;
			}
			elements[index] = p;
			
			index = pindex;
		}
		elements[index] = e;
		
	}
	
	private void heapify() {
		// 自上而下的上滤
//		for (int i = 1; i < size; i++) {
//			siftUp(i);
//		}
		// 自下而上的下滤
		for (int i = (size >>1) - 1; i >= 0; i--) {
			siftDown(i);
		}
		
	}
	

	
	private void emptyCheck() {
		if (size == 0) {
			throw new IndexOutOfBoundsException("heap is empty");
		}
	}
	private void elementNotNullCheck(E element) {
		if (element == null) {
			throw new IllegalArgumentException("element not null");
		}
	}
	
	/**
	 * 保证有capacity容量
	 * @param capacity
	 */
	@SuppressWarnings("unchecked")
	private void ensureCapacity(int capacity) {
		int oldCapacity = elements.length;
		if (oldCapacity >= capacity) return;
		// 旧容量1.5
		int newCapacity = oldCapacity + (oldCapacity >> 1); 
		E []newElements = (E[])new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[i];
		}
		elements = newElements;
		System.out.println(oldCapacity+"扩容为"+newCapacity);
	}



}
