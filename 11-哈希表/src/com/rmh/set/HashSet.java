package com.rmh.set;

import com.rmh.map.HashMap;
import com.rmh.map.Map;
import com.rmh.set.Set.Visitor;

public class HashSet<E> implements Set<E> {

	private HashMap<E, Object> map = new HashMap<>();
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return map.isEmpty();
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		map.clear();
	}

	@Override
	public boolean contains(E element) {
		// TODO Auto-generated method stub
		return map.containsKey(element);
	}

	@Override
	public void add(E element) {
		// TODO Auto-generated method stub
		map.put(element, null);
	}

	@Override
	public void remove(E element) {
		// TODO Auto-generated method stub
		map.remove(element);
	}

	@Override
	public void traversal(Visitor<E> visitor) {
		map.traversal(new Map.Visitor<E, Object>() {

			public boolean visit(E key, Object value) {
				// TODO Auto-generated method stub
				return visitor.visit(key);
			}
		});
		
	}
}
