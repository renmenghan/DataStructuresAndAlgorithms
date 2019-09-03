package com.rmh;

public class Person {

	private int age;
	public Person(int age, String name) {
		super();
		this.age = age;
		this.name = name;
	}
	private String name;
	@Override
	public String toString() {
		return "Person [age=" + age + ", name=" + name + "]";
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		System.out.println("Person finalize");
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == null) {
			return false;
		}
		if (obj instanceof Person) {
			Person person  = (Person)obj;
			return this.age == person.age;
		}
		return false;
	}
}
