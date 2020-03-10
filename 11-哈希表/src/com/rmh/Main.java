package com.rmh;

import com.rmh.map.HashMap;
import com.rmh.map.LinkedHashMap;
import com.rmh.map.Map.Visitor;
import com.rmh.model.Key;
import com.rmh.model.SubKey1;
import com.rmh.model.SubKey2;

public class Main {

	public static void main(String[] args) {
//		test2(new HashMap<Object, Integer>());
//		test3(new HashMap<Object, Integer>());
//		test4(new HashMap<Object, Integer>());
//		test5(new HashMap<Object, Integer>());
		
		test4(new LinkedHashMap<Object, Integer>());
		
	}
	static void test1() {
		SubKey1 key1 = new SubKey1(1);
		SubKey2 key2 = new SubKey2(1);
		HashMap<Object, Integer> map =  new HashMap<>();
		map.put(key1, 1);
		map.put(key2, 2);
		System.out.println(map.size());
		
	}
	
	
	static void test2(HashMap<Object, Integer> map) {
		for (int i = 1; i <= 20; i++) {
			map.put(new Key(i), i);
		}
		for (int i = 5; i <= 7; i++) {
			map.put(new Key(i), i + 5);
		}
		Asserts.test(map.size() == 20);
		Asserts.test(map.get(new Key(4)) == 4);
		Asserts.test(map.get(new Key(5)) == 10);
		Asserts.test(map.get(new Key(6)) == 11);
		Asserts.test(map.get(new Key(7)) == 12);
		Asserts.test(map.get(new Key(8)) == 8);
	}
	
	static void test3(HashMap<Object, Integer> map) {
		map.put(null, 1); // 1
		map.put(new Object(), 2); // 2
		map.put("jack", 3); // 3
		map.put(10, 4); // 4
		map.put(new Object(), 5); // 5
		map.put("jack", 6);
		map.put(10, 7);
		map.put(null, 8);
		map.put(10, null);
		Asserts.test(map.size() == 5);
		Asserts.test(map.get(null) == 8);
		Asserts.test(map.get("jack") == 6);
		Asserts.test(map.get(10) == null);
		Asserts.test(map.get(new Object()) == null);
		Asserts.test(map.containsKey(10));
		Asserts.test(map.containsKey(null));
		Asserts.test(map.containsValue(null));
		Asserts.test(map.containsValue(1) == false);
	}
	
	static void test4(HashMap<Object, Integer> map) {
		map.put("jack", 1);
		map.put("rose", 2);
		map.put("jim", 3);
		map.put("jake", 4);		
		map.remove("jack");
		map.remove("jim");
		for (int i = 1; i <= 10; i++) {
			map.put("test" + i, i);
			map.put(new Key(i), i);
		}
		for (int i = 5; i <= 7; i++) {
			Asserts.test(map.remove(new Key(i)) == i);
		}
		for (int i = 1; i <= 3; i++) {
			map.put(new Key(i), i + 5);
		}
		Asserts.test(map.size() == 19);
		Asserts.test(map.get(new Key(1)) == 6);
		Asserts.test(map.get(new Key(2)) == 7);
		Asserts.test(map.get(new Key(3)) == 8);
		Asserts.test(map.get(new Key(4)) == 4);
		Asserts.test(map.get(new Key(5)) == null);
		Asserts.test(map.get(new Key(6)) == null);
		Asserts.test(map.get(new Key(7)) == null);
		Asserts.test(map.get(new Key(8)) == 8);
		map.traversal(new Visitor<Object, Integer>() {
			public boolean visit(Object key, Integer value) {
				System.out.println(key + "_" + value);
				return false;
			}
		});
	}
	
	static void test5(HashMap<Object, Integer> map) {
		for (int i = 1; i <= 20; i++) {
			map.put(new SubKey1(i), i);
		}
		map.put(new SubKey2(1), 5);
		Asserts.test(map.get(new SubKey1(1)) == 5);
		Asserts.test(map.get(new SubKey2(1)) == 5);
		Asserts.test(map.size() == 20);
		map.print();
	}
	
	
	static void test()
	{
		Person p1 = new Person(12, 13.6f, "jack");
		Person p2 = new Person(12, 13.6f, "jack");
		
		HashMap<Object, Integer> map =  new HashMap<>();
//		
		map.put(p1, 1);
		map.put(p2, 2);
		map.put("jack", 2);
		map.put("rose", 2);
		map.put("jack", 3);
		map.put(null, 3);
		
		map.print();
		
//		System.out.println(map.get("jack"));
//		System.out.println(map.get("rose"));
//		System.out.println(map.get(null));
//		System.out.println(map.get(p1));
//		System.out.println(map.size());
//		System.out.println(map.remove("jack"));
//		System.out.println(map.size());
		
//		map.traversal(new Visitor<Object, Integer>() {
//			
//			@Override
//			public boolean visit(Object key, Integer value) {
//				System.out.println(key + "_" + value);
//				return false;
//			}
//		});
		
		System.out.println(map.containsKey(p1));
		System.out.println(map.containsKey(null));
		System.out.println(map.containsKey(1));
		System.out.println(map.containsKey("jack"));
	}

}
