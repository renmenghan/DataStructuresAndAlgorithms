package com.rmh.set;

import com.rmh.list.LinkedList;
import com.rmh.list.List;

public class ListSet<E> implements Set<E> {

	private List<E> list = new LinkedList<>();
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return list.isEmpty();
	}

	@Override
	public void clear() {
		list.clear();
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(E element) {
		// TODO Auto-generated method stub;
		return list.contains(element);
	}

	@Override
	public void add(E element) {
//		if (list.contains(element)) {
//			return;
//		}
//		list.add(element);
		
		int index = list.indexOf(element);
		if (index != list.ELEMENT_NOT_FOUND) {
			list.set(index, element);
		}else
		{
			list.add(element);
		}

	}

	@Override
	public void remove(E element) {
		// TODO Auto-generated method stub
		int index = list.indexOf(element);
		if (index != list.ELEMENT_NOT_FOUND) {
			list.remove(index);
		}
	}

	@Override
	public void traversal(com.rmh.set.Set.Visitor<E> visitor) {
		// TODO Auto-generated method stub
		if (visitor == null) {
			return;
		}
		int size = list.size();
		for (int i = 0 ; i < size ;i++){
			if (visitor.visit(list.get(i))) {
				return;
			}
		}
	}

}
