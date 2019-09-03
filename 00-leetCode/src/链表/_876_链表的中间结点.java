package 链表;

import java.util.ArrayList;
import java.util.List;

public class _876_链表的中间结点 {
	public ListNode middleNode(ListNode head) {
        if (head == null) {
			return head;
		}
        List<ListNode> list = new ArrayList<ListNode>();
        while (head != null) {
			list.add(head);
			head = head.next;
		}
        return list.get(list.size()/2);
    }
}
