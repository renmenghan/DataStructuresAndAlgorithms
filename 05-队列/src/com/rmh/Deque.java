package com.rmh;

import com.rmh.list.LinkedList;
import com.rmh.list.List;

public class Deque<E> {
	private List<E> list = new LinkedList<>();
	public int size() {
		return list.size();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	
	public void enQueueRear(E element) {
		list.add(element);
	}
	
	public E deQueueFront() {
		E e =list.get(0);
		list.remove(0);
		return e;
	}
	
	public void enQueueFront(E element) {
		list.add(0,element);
	}
	
	
	public E deQueueRear() {
		E e =list.get(list.size() - 1);
		list.remove(list.size() - 1);
		return e;
	}
	
	public E front() {
		return list.get(0);
	}
	
	public E rear() {
		return list.get(list.size() - 1);
	}
}
