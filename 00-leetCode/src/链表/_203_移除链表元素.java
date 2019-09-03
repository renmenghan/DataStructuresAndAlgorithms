package 链表;

public class _203_移除链表元素 {
	public static ListNode removeElements(ListNode head, int val) {
		
		ListNode newHeader = new ListNode(-1);
		newHeader.next = head;
		ListNode cur = newHeader;
		while (cur.next != null) {
			if (cur.next.val == val ) {
				cur.next = cur.next.next;
			}else {
				cur = cur.next;
			}
				
		}
		return newHeader.next;
		
		
    }
	
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
		
		
		ListNode node = removeElements(node5, 1);
		for (int i = 0; i < 4 ; i++) {
			System.out.print(node.val);
			if (node.next != null ) {
				node = node.next;
			}
			
		}

	}
}
