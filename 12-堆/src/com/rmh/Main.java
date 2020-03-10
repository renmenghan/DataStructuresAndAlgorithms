package com.rmh;

import java.util.Comparator;

import com.rmh.heap.BinaryHeap;
import com.rmh.printer.BinaryTrees;

public class Main {
	static void test1() {
		BinaryHeap<Integer> heap = new BinaryHeap<Integer>();
		
		heap.add(68);
		heap.add(72);
		heap.add(43);
		heap.add(50);
		heap.add(38);
		heap.add(10);
		heap.add(90); 
		heap.add(65);
		
		BinaryTrees.println(heap);
		heap.replace(70);
//		heap.remove();
		BinaryTrees.println(heap);
		
	}
	public void test2() {
		Integer[] data =  {88,44,22,15,99,54,23,75};
		BinaryHeap<Integer> heap = new BinaryHeap<>(data);
		BinaryTrees.println(heap);
	}
	
	
	public void test3() {
		// TODO Auto-generated method stub
				Integer[] data =  {88,44,22,15,99,54,23,75};
				BinaryHeap<Integer> heap = new BinaryHeap<>(data,new Comparator<Integer>() {

					@Override
					public int compare(Integer o1, Integer o2) {
						
						return o2- o1;
					}
					
				});
				BinaryTrees.println(heap);
	}

	public static void main(String[] args) {
		Integer[] data =  {15, 47, 97, 13, 46, 65, 89, 72, 71, 32, 26, 43, 85, 17, 7, 48, 22, 11};
		BinaryHeap<Integer> heap = new BinaryHeap<>(null,new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				
				return o2- o1;
			}
			
		});
		int k = 3;
		for (int i = 0; i < data.length; i++) { //
			if (heap.size() < k) {
				heap.add(data[i]); // log k
			}else if(heap.get() < data[i]) {
				heap.replace(data[i]); // log k
			}
		}
		// nlogk
		BinaryTrees.println(heap);
	}
	
}
