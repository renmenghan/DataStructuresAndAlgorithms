package com.rmh;

import com.rmh.printer.BinaryTrees;
import com.rmh.tree.BST;

public class Main {

	public static void main(String[] args) {
		Integer data[] = new Integer[] {
				7,4,9,2,5,8,11,3,12,1		
		};
		
		BST<Integer> bst = new BST<>();
		
		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}
		
		BinaryTrees.println(bst);
		bst.remove(9);

		BinaryTrees.println(bst);
//		System.out.println(bst.isComplete());
//		bst.postorderTraversal(new Visitor<Integer>() {
//
//			@Override
//			public boolean visit(Integer element) {
////				System.out.println(element);
//				if (element == 2) {
//					return true;
//				}
//				return false;
//			}
//			
//			
//		});

	}

}
