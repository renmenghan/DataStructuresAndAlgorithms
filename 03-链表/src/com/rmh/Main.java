package com.rmh;

import com.rmh.circle.CircleLinkedList;
import com.rmh.single.SingleLinkedList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		List<Integer> list = new LinkedList<Integer>();
//		list.add(20);
//		list.add(0, 10);
//		list.add(30);
//		list.add(list.size(),40);
//		list.remove(0);
//		System.out.println(list);
		josephus();
	 
	}
	
	static void josephus() {
		CircleLinkedList<Integer> list = new CircleLinkedList<Integer>();
		for (int i = 1; i <= 8; i++) {
			list.add(i);
		}
		System.out.println(list);
		// 指向头结点
		list.reset();
		while (list.size != 1) {
			list.next();
			list.next();
			list.remove();
		}
		System.out.println(list);
	}

}
