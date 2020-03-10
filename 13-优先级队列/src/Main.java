import com.rmh.queue.PriorityQueue;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		PriorityQueue<Person> queue =  new PriorityQueue<>();
		queue.enQueue(new Person("jack",1));
		queue.enQueue(new Person("rose",12));
		queue.enQueue(new Person("jame",5));
		queue.enQueue(new Person("nick",4));
		queue.enQueue(new Person("eadi",18));
		
		while (!queue.isEmpty()) {
			System.out.println(queue.deQueue());
			
		}
		
	}

}
