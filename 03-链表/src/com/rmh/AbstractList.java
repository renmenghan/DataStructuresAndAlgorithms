package com.rmh;

public abstract class AbstractList<E> implements List<E> {

	/**
	 * 元素的数量
	 */
	protected int size;
	
	/**
	 * 元素的数量
	 * @return
	 */
	public int size() {
		return size;
	}
	
	/**
	 * 是否为空
	 * @return
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * 添加元素
	 * @param element
	 */
	public void add( E element) {
		add(size,element);
	}
	
	/**
	 * 是否包含某个元素
	 * @param element
	 * @return
	 */
	public boolean contains(E element) {
		return indexOf(element) != ELEMENT_NOT_FOUND;
	}
	
	protected void outOfBounds(int index) {
		
		throw new IndexOutOfBoundsException("Index:" + index + ",size" + size);
	}
	
	protected void rangeCheck(int index) {
		if (index < 0 || index >= size) {
			outOfBounds(index);
		}
	}
	
	protected void rangeCheckForAdd(int index) {
		if (index < 0 || index > size) {
			outOfBounds(index);
		}
	}
}
