package com.xck.y2022;

/**
 * 两两交换链表中的节点
 *
 * @author xuchengkun
 * @date 2022/02/19
 **/
public class SwapNodesInPair {

    public static void main(String[] args) {

    }


    public static ListNode swapPairs1(ListNode head) {
        if (head == null) return null;

        if (head.next == null) return head;

        ListNode left = head;
        ListNode right = head.next;

        left.next = right.next;
        right.next = left;
        //重新链接后面的，每次只交换2个
        left.next = swapPairs1(left.next);

        return right;
    }



    private static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
