package com.rmh;

import com.rmh.list.LinkedList;
import com.rmh.list.List;

public class Queue<E> {

	private List<E> list = new LinkedList<>();
	public int size() {
		return list.size();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public void enQueue(E element) {
		list.add(element);
	}
	public E deQueue() {
		
		E e = list.get(0);
		list.remove(0);
		
		return e;
	}
	
	public E front() {
		return list.get(0);
	}
}
