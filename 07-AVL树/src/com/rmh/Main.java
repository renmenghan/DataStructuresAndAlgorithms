package com.rmh;

import com.rmh.printer.BinaryTrees;
import com.rmh.tree.AVLTree;
import com.rmh.tree.BST;

public class Main {

	public static void main(String[] args) {
		Integer data[] = new Integer[] {
				73, 72, 39, 77, 82, 3		
		};
		
		AVLTree<Integer> bst = new AVLTree<>();
		
		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}
		
		
		
//		BinaryTrees.println(bst);
//		bst.remove(9);

		BinaryTrees.println(bst);
		
//		bst.remove(3);
//		bst.remove(39);
//		
//		BinaryTrees.println(bst);
		
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
