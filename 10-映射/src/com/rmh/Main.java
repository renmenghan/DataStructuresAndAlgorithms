package com.rmh;


import com.rmh.map.Map;
import com.rmh.map.Map.Visitor;
import com.rmh.map.TreeMap;
import com.rmh.set.Set;
import com.rmh.set.TreeSet;

public class Main {

	static void test1(){
		Map<String, Integer> map = new TreeMap<>();
		map.put("c", 2);
		map.put("a", 5);
		map.put("b", 6);
		map.put("a", 8);
		
		map.traversal(new Visitor<String, Integer>() {
			public boolean visit(String key,Integer value){
				System.out.println(key + "_" + value);
				return false;
			}
		});
		
	}
	
	static void test3(){
		Set<String> map  = new TreeSet<>();
		map.add("c");
		map.add("a");
		map.add("b");
		map.add("a");
		
		map.traversal(new Set.Visitor<String>() {
			public boolean visit(String element){
				System.out.println(element);
				return false;
			}
		});
		
	}
	
	public static void main(String[] args) {
	
		test3();
	}

}
