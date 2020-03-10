
public class Person implements Comparable<Person> {

	private String name;
	private int boneBreak;
	
	public Person(String name,int count) {
		this.name = name;
		this.boneBreak = count;
	}
	
	@Override
	public int compareTo(Person o) {
		// TODO Auto-generated method stub
		return this.boneBreak - o.boneBreak;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name;
	}
	
}
