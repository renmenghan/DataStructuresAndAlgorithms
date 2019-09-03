package com.rmh;

import com.rmh.list.ArrayList;
import com.rmh.list.List;

public class Stack<E> {
	private List<E> list =  new ArrayList<E>();
	
	public int size() {
		return list.size();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public void push(E element) {
		list.add(element);
	}
	
	public void pop() {
		list.remove(list.size() - 1);
	}
	
	public E top() {
		return list.get(list.size() - 1);
	}
}
