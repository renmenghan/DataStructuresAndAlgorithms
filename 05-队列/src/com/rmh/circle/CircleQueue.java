package com.rmh.circle;

@SuppressWarnings("unchecked")
public class CircleQueue<E> {
	private int front;
	private int size;
	private E[] elements;
	private static final int DEFAULT_CAPACITY = 10;


	public CircleQueue() {
		
		elements = (E[]) new Object[DEFAULT_CAPACITY];
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public void enQueue(E element) {
		ensureCapacity(size + 1);
		elements[index(size)] = element;
		size++;
		
	}
	public E deQueue() {
		E frontElement = elements[front];
		elements[front] = null;
		front = index(1);
		size--;
		return frontElement;
	}
	
	public E front() {
		return elements[front];
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
			newElements[i] = elements[index(i)];
		}
		elements = newElements;
		
		front = 0;
		
		System.out.println(oldCapacity+"扩容为"+newCapacity);
	}
	
	private int index(int index) {
		return (front + index)  %elements.length;
	}

	
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder string = new StringBuilder();
		string.append("capcacity=").append(elements.length)
		.append("size=").append(size)
		.append("front=").append(front)
		.append(",[");
		for (int i = 0; i < elements.length; i++) {
			if (i != 0) {
				string.append(",");
			}
			string.append(elements[i]);
		}
		string.append("]");
		return string.toString();
	}
	
	
	
}
