package com.xck.y2022;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 删除链表的倒数第N个节点
 * <p>
 * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 * <p>
 * 提示：
 * 链表中结点的数目为 sz
 * 1 <= sz <= 30
 * 0 <= Node.val <= 100
 * 1 <= n <= sz
 * <p>
 * 进阶：你能尝试使用一趟扫描实现吗？
 *
 * @author xuchengkun
 * @date 2022/02/17 09:24
 **/
public class RemoveNthNodeFromEndOfList {

    public static void main(String[] args) {
        printListNode(removeNthFromEnd(create(new int[]{1, 2}), 1));
    }

    public static ListNode create(int[] arr) {
        ListNode head = new ListNode(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            head.next = new ListNode(arr[i]);
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

    //快慢指针
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        //如果只有1个节点
        if (n == 1 && head.next == null) {
            return null;
        }

        ListNode cur = head, nthListNode = head;
        //根据题意，这里不存在到头了但是倒数第n个还没找到的情况
        while (n > 1) {
            cur = cur.next;
            --n;
        }

        ListNode nthListNodeLast = nthListNode;
        //这里不能写cur因为这样会导致cur是链表最后一个节点的后面
        while (cur.next != null) {
            cur = cur.next;
            nthListNodeLast = nthListNode; //记录上一个
            nthListNode = nthListNode.next;
        }

        if (nthListNode == head) { //说明要删除头结点，这里投机一下，直接返回
            return head.next;
        } else {
            nthListNodeLast.next = nthListNode.next;
            nthListNode.next = null;
        }

        return head;
    }

    public static ListNode removeNthFromEndStack(ListNode head, int n) {
        if (n == 1 && head.next == null) {
            return null;
        }

        Deque<ListNode> stack = new LinkedList<>();
        ListNode cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        ListNode delNode = null;
        while (n >= 1) {
            delNode = stack.pop();
            --n;
        }

        if (delNode == head) {
            return head.next;
        } else {
            ListNode delLastNode = stack.pop();
            delLastNode.next = delNode.next;
            delNode.next = null;
        }
        return head;
    }

    /**
     *     int cur=0;
     *     ListNode* removeNthFromEnd(ListNode* head, int n) {
     *        if(!head) return NULL;
     *        head->next = removeNthFromEnd(head->next,n);
     *        cur++;
     *        if(n==cur) return head->next;
     *        return head;
     *     }
     *
     *     评论区的代码，这个代码其实可以用压栈理解，
     *     我不断地压栈，直到链表尾部null，然后返回，出栈，然后这个时候开始计算游标
     *     若当前节点是要删除的节点，那么我们返回该节点的next
     *     再返回上一层，这样虽然没有删除cur->next指针，但是断开了上一个节点指向cur的指针
     *     head->next = removeNthFromEnd(head->next,n);
     *
     * @param head
     * @param n
     * @return
     */
    private static int cur = 0;
    public static ListNode removeNthFromEndRecursion(ListNode head, int n) {
        if (head == null) return null;

        head.next = removeNthFromEndRecursion(head.next, n);
        ++cur;
        if (n == cur) {
            return head.next;
        }

        return head;
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


