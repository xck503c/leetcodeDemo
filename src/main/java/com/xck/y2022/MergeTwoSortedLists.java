package com.xck.y2022;

import java.util.ArrayList;
import java.util.List;

/**
 * 合并两个有序链表
 *
 * @author xuchengkun
 * @date 2022/02/17 17:47
 **/
public class MergeTwoSortedLists {

    public static void main(String[] args) {
        printListNode(mergeTwoLists1(create(new int[]{1,2,4}), create(new int[]{1,3,4})));
        printListNode(mergeTwoLists1(create(new int[]{}), create(new int[]{})));
        printListNode(mergeTwoLists1(create(new int[]{}), create(new int[]{0})));
    }

    public static ListNode create(int[] arr) {
        if (arr.length == 0) return null;

        ListNode head = new ListNode(arr[0]);
        ListNode cur = head;
        for (int i = 1; i < arr.length; i++) {
            cur.next = new ListNode(arr[i]);
            cur = cur.next;
        }
        return head;
    }

    public static void printListNode(ListNode node) {
        List<Integer> list = new ArrayList<Integer>();
        while (node != null) {
            list.add(node.val);
            node = node.next;
        }
        System.out.println(list);
    }

    public static ListNode mergeTwoLists1(ListNode list1, ListNode list2) {
        if (list1 == null && list2 == null) {
            return null;
        }

        if (list1 == null) return list2;
        if (list2 == null) return list1;

        ListNode merge = null, cur = null;
        if (list1.val > list2.val) {
            cur = merge = new ListNode(list2.val);
            list2 = list2.next;
        } else {
            cur = merge = new ListNode(list1.val);
            list1 = list1.next;
        }

        while (list1 != null && list2 != null) {
            if (list1.val > list2.val) {
                cur.next = list2;
//                cur.next = new ListNode(list2.val);
                list2 = list2.next;
            } else {
                cur.next = list1;
//                cur.next = new ListNode(list1.val);
                list1 = list1.next;
            }
            cur = cur.next;
        }

//        while (list1 != null) {
//            cur.next = new ListNode(list1.val);
//            cur = cur.next;
//            list1 = list1.next;
//        }
//
//        while (list2 != null) {
//            cur.next = new ListNode(list2.val);
//            cur = cur.next;
//            list2 = list2.next;
//        }

        //换成下面这个
        cur.next = list1 != null ? list1 : list2;
        return merge;
    }

    public static ListNode mergeTwoLists2(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        } else if (list2 == null) {
            return list1;
        } else if (list1.val > list2.val){
            list2.next = mergeTwoLists2(list1, list2.next);
            return list2;
        } else {
            list1.next = mergeTwoLists2(list1.next, list2);
            return list1;
        }
    }

    public static class ListNode {
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
