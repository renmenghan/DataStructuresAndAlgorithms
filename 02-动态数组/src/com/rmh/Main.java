package com.rmh;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		int array[] = new int[] {11,22,33};
		// java.lang.Object
		// new 堆空间里申请内存
		ArrayList<Person> list = new ArrayList<>();
//		list.get(0);
		list.add(new Person(10, "jack"));
		list.add(new Person(12, "nick"));
		list.add(new Person(15, "edison"));
		list.clear();
		// 提醒jvm进行垃圾回收
		System.gc();
//		list.add(88);
//		list.add(77);
//		list.add(0, 11);
//		list.add(list.size(),100);
//		list.set(1, 22);
//		for (int i = 0; i < 30; i++) {
//			list.add(i);
//		}
//		Asserts.test(list.get(1) == 22);
//		Asserts.test(list.get(list.size()-1) == 100);
		System.out.println(list);
	}

}
