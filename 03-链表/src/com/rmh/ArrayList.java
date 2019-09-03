package com.rmh;

public class ArrayList<E> extends AbstractList<E>{

	
	/**
	 * 所有的元素
	 */
	private E[] elements;
	
	private static final int DEFAULT_CAPACITY = 10;
	private static final int ELEMENT_NOT_FOUND = -1;
	
	@SuppressWarnings("unchecked")
	public ArrayList(int capaticy) {
		capaticy = (capaticy < DEFAULT_CAPACITY) ? DEFAULT_CAPACITY:capaticy;
		elements = (E[])new Object[capaticy];
	}
	
	public ArrayList() {
		this(DEFAULT_CAPACITY);
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
	
	/**
	 * 清空数组
	 */
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}
	

	
	/**
	 * index位置插入一个元素
	 * @param index
	 * @param element
	 */
	public void add(int index,E element) {
		rangeCheckForAdd(index);
		
		ensureCapacity(size + 1);
		
		for (int i = size ; i > index; i--) {
			elements[i] = elements[i-1];
		}
		elements[index] = element;
		size++;
	}
	
	/**
	 * 删除元素
	 * @param index
	 */
	public void remove(int index) {
		rangeCheck(index);
		for (int i = index + 1; i < size; i++) {
			elements[i - 1] = elements[i];
		}
		elements[--size] = null;
	}
	
//	/**
//	 * 删除元素
//	 * @param element
//	 */
//	public void remove(E element) {
//		remove(indexOf(element));
//	}
//	
	
	

	
	/**
	 * 获取index位置元素
	 * @param index
	 * @return
	 */
	public E get(int index) {
		rangeCheck(index);
		return elements[index];
	}
	
	/**
	 * 设置index位置的元素
	 * @param index
	 * @param element
	 * @return
	 */
	public E set(int index ,E element) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index:" + index + ",size" + size);
		}
		E old = elements[index];
		elements[index] = element;
		return old;
	}
	
	/**
	 * 查看元素索引
	 * @param element
	 * @return
	 */
	public int indexOf(E element) {
		
		if (element == null) {
			for (int i = 0; i < size; i++) {
				if (elements[i] == null) return i;
			}
		}else {
			for (int i = 0; i < size; i++) {
				if (elements[i].equals(element)) return i;
			}
		}
		
		
		return ELEMENT_NOT_FOUND;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder string = new StringBuilder();
		string.append("size=").append(size).append(",[");
		for (int i = 0; i < size; i++) {
			if (i != 0) {
				string.append(",");
			}
			string.append(elements[i]);
		}
		string.append("]");
		return string.toString();
	}

}
