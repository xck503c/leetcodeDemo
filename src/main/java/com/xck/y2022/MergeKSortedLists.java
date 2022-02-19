package com.xck.y2022;

/**
 * 合并k个升序链表
 *
 * @author xuchengkun
 * @date 2022/02/19 17:04
 **/
public class MergeKSortedLists {

    public static void main(String[] args) {

    }

    public static ListNode mergeKLists1(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }

        if (lists.length == 1) {
            return lists[0];
        }

        int mid = lists.length / 2;

        ListNode[] left = new ListNode[mid];
        System.arraycopy(lists, 0, left, 0, left.length);

        ListNode[] right = new ListNode[lists.length - mid];
        System.arraycopy(lists, mid, right, 0, right.length);

        return merge2ListsIt(mergeKLists1(left), mergeKLists1(right));
    }

    //110ms
    public static ListNode mergeKLists2(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }

        if (lists.length == 1) {
            return lists[0];
        }

        ListNode merge = lists[0];
        for (int i = 1; i < lists.length; i++) {
            if (lists[i] == null) continue;
            merge = merge2ListsIt(merge, lists[i]);
        }
        return merge;
    }

    //迭代遍历
    public static ListNode merge2ListsIt(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        ListNode merge, cur;
        if (list1.val > list2.val) {
            cur = merge = list2;
            list2 = list2.next;
        } else {
            cur = merge = list1;
            list1 = list1.next;
        }

        while (list1 != null && list2 != null) {
            if (list1.val > list2.val) {
                cur.next = list2;
                list2 = list2.next;
            } else {
                cur.next = list1;
                list1 = list1.next;
            }
            cur = cur.next;
        }

        cur.next = list1 == null ? list2 : list1;

        return merge;
    }

    //递归遍历
    public static ListNode merge2ListsRecursion(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        if (list1.val > list2.val) {
            //重新链接list2
            list2.next = merge2ListsRecursion(list2.next, list1);
            return list2;
        } else {
            list1.next = merge2ListsRecursion(list2, list1.next);
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
