package 链表;

public class _206_反转链表 {
	
	public static void main(String[] args) {
		ListNode node5 = new ListNode(5);
		ListNode node4 = new ListNode(4);
		ListNode node3 = new ListNode(3);
		ListNode node2 = new ListNode(2);
		ListNode node1 = new ListNode(1);
		node5.next = node4;
		node4.next = node3;
		node3.next = node2;
		node2.next = node1;
		node1.next = null;
		reverseList2(node5);
		
		ListNode node = node1;
		for (int i = 0; i < 5 ; i++) {
			if (node.next != null) {
				System.out.print(node.val);
				node = node.next;
			}
			
		}

	}
	//  3 2 1 
	public static ListNode reverseList(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}
        ListNode newNode = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newNode;
    }
	
	
	public static void reverseList2(ListNode head) {
		if (head == null || head.next == null) {
			return;
		}
        ListNode newHead = null;
        while (head!=null) {
			ListNode tmp = head.next;
			head.next = newHead;
			newHead = head;
			head = tmp;
		}
       
    }
	
}
