package com.rmh;

import com.rmh.printer.BinaryTrees;
import com.rmh.tree.AVLTree;
import com.rmh.tree.BST;
import com.rmh.tree.RBTree;

public class Main {

	public static void main(String[] args) {
		
		test();
		
//		Integer data[] = new Integer[] {
//				73, 72, 39, 77, 82, 3,55,22		
//		};
//		RBTree<Integer> rbTree = new RBTree<>();
//		for (int i = 0; i < data.length; i++) {
//			rbTree.add(data[i]);
//		}
//		
//		BinaryTrees.println(rbTree);
		
//		
//		AVLTree<Integer> bst = new AVLTree<>();
//		
//		for (int i = 0; i < data.length; i++) {
//			bst.add(data[i]);
//		}
//		
		
		
//		BinaryTrees.println(bst);
//		bst.remove(9);

//		BinaryTrees.println(bst);
		
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
	static void test(){
		Integer data[] = new Integer[] {
				73, 72, 39, 77, 82, 3,55,22		
		};
		RBTree<Integer> rbTree = new RBTree<>();
		for (int i = 0; i < data.length; i++) {
			rbTree.add(data[i]);
		}
		
		BinaryTrees.println(rbTree);
		
		for (int i = 0; i < data.length; i++) {
			rbTree.remove(data[i]);
			System.out.println("-----------------------");
			System.out.println("【"+data[i]+"】");
			BinaryTrees.println(rbTree);
		}
	}

}
