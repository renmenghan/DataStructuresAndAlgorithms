package com.rmh;

import java.util.Deque;

import com.rmh.circle.CircleDeque;
import com.rmh.circle.CircleQueue;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Deque<Integer> queue = new Deque<Integer>();
//		queue.enQueueFront(11);
//		queue.enQueueFront(22);
//		queue.enQueueRear(33);
//		queue.enQueueRear(44);
		
		
//		
//		CircleQueue<Integer> circleQueue = new CircleQueue<Integer>();
//		for (int i = 0; i < 10; i++) {
//			circleQueue.enQueue(i);
//		}
//		
//		for (int i = 0; i < 5; i++) {
//			circleQueue.deQueue();
//		}
//		for (int i = 15; i < 20; i++) {
//			circleQueue.enQueue(i);
//		}
//		
//		
//		System.out.println(circleQueue);
//		while (!circleQueue.isEmpty()) {
//			System.out.println(circleQueue.deQueue());
//			
//		}
//		
		Deque<Integer> deque;
		
		CircleDeque<Integer> queue = new CircleDeque<Integer>();
		for (int i = 0; i < 10; i++) {
			queue.enQueueFront(i + 1);
			queue.enQueueRear(i + 100);
		}
		
		for (int i = 0; i < 3; i++) {
			queue.deQueueFront();
			queue.deQueueRear();
		}
		
		queue.enQueueFront(11);
		queue.enQueueFront(12);
		System.out.println(queue);
		
	}

}
