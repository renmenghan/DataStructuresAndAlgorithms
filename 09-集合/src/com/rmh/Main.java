package com.rmh;

import com.rmh.set.ListSet;
import com.rmh.set.Set;
import com.rmh.set.Set.Visitor;
import com.rmh.set.TreeSet;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Set<Integer> treeSet = new TreeSet<>();
		treeSet.add(20);
		treeSet.add(10);
		treeSet.add(56);
		treeSet.add(30);
		treeSet.add(60);
		treeSet.add(60);
		treeSet.add(70);
		treeSet.add(20);
		treeSet.traversal(new Visitor<Integer>() {
			public boolean visit(Integer element) {
				System.out.println(element);
				return false;
			}
		});
	}

}
