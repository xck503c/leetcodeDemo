package com.xck.y2021;

/**
 * @Classname AddTwoNumbers
 * leetcode 上两数相加：https://leetcode-cn.com/problems/add-two-numbers/
 *
 * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * 审题：
 * 1. 整数非负，所以不需要考虑相减的情况；
 * 2. 逆序存储，说明开头是最低位，符合我们平时低位计算的规则
 * 3. 非空链表，也就是说我们不需要空判断，一定是有数字可以计算
 * 4. 最后返回的结果格式和输入格式一样，说明也要逆序
 *
 * 示例1：
 * 输入：l1 = [2,4,3], l2 = [5,6,4]
 * 输出：[7,0,8]
 * 解释：342 + 465 = 807.
 *
 * 示例2：
 * 输入：l1 = [0], l2 = [0]
 * 输出：[0]
 *
 * 示例3：
 * 输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * 输出：[8,9,9,9,0,0,0,1]
 *
 * 提示：
 * 1. 每个链表中的节点数在范围 [1, 100] 内
 * 2. 0 <= Node.val <= 9
 * 3. 题目数据保证列表表示的数字不含前导零
 *
 * Definition for singly-linked list.
 * public class ListNode {
 *  int val;
 *  ListNode next;
 *  ListNode() {}
 *  ListNode(int val) { this.val = val; }
 *  ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 *
 *
 * @Description TODO
 * @Date 2021/6/22 15:00
 * @Created by xck503c
 **/
public class AddTwoNumbers {

    public static void main(String[] args) {
        int[] l1 = {2, 4, 3};
        int[] l2 = {5, 6, 4};
        addTwoNumbers(l1, l2);

        l1 = new int[]{0};
        l2 = new int[]{0};
        addTwoNumbers(l1, l2);

        l1 = new int[]{9,9,9,9,9,9,9};
        l2 = new int[]{9,9,9,9};
        addTwoNumbers(l1, l2);

        l1 = new int[]{1,6,1,6,5,1,6,5,1,6,5,1,6};
        l2 = new int[]{8,7,9,4,1,5,3,1,6,5,4,9,6,4,6,1};
        addTwoNumbers(l1, l2);
    }

    public static void addTwoNumbers(int[] li1, int[] li2){
        ListNode l1 = create(li1);
        ListNode l2 = create(li2);

        ListNode result = addTwoNumbers1(l1, l2);

        print(result);
    }

    /**
     * 解法1：存储采用倒置，那就按照正常的进位加法来进行
     *
     * 1. 先同时遍历，每次遍历都会记录一个进位值用于下一次使用
     * 2. 最后长的那个继续遍历，同时也要注意进位问题
     *
     * 用时2ms，击败100%；内存消耗38.4MB，击败94.01%
     *
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        ListNode cur1 = l1, cur2 = l2, curNode = null, head = null;
        int carry = 0;
        while (cur1 != null && cur2 != null){
            int tmp = cur1.val + cur2.val + carry;
            //需要进位，则记录进位值，然后获取非进位值
            if (tmp >= 10){
                carry = tmp / 10;
                tmp = tmp % 10;
            }else {
                carry = 0; //清空进位
            }
            if (curNode == null){ //第一次初始化
                curNode = new ListNode();
                curNode.val = tmp;
                head = curNode; //记录头结点
            }else {
                curNode.next = new ListNode();
                curNode = curNode.next;
                curNode.val = tmp;
            }
            cur1 = cur1.next;
            cur2 = cur2.next;
        }

        while (cur1 != null){
            curNode.next = new ListNode();
            curNode = curNode.next;
            curNode.val = cur1.val + carry;
            if (curNode.val >= 10) {
                carry = curNode.val / 10;
                curNode.val = curNode.val % 10;
            }else {
                carry = 0;
            }
            cur1 = cur1.next;
        }

        while (cur2 != null){
            curNode.next = new ListNode();
            curNode = curNode.next;
            curNode.val = cur2.val + carry;
            if (curNode.val >= 10) {
                carry = curNode.val / 10;
                curNode.val = curNode.val % 10;
            }else {
                carry = 0;
            }
            cur2 = cur2.next;
        }

        if (carry > 0){
            curNode.next = new ListNode();
            curNode.next.val = carry;
        }

        return head;
    }

    /**
     * 根据数组的顺序创建ListNode，返回头结点
     * @param arr
     * @return
     */
    public static ListNode create(int[] arr){
        ListNode head = null, cur = null;
        for (int i = 0; i < arr.length; i++) {
            if (head == null){
                head = new ListNode();
                head.val = arr[i];
                cur = head;
            }else {
                cur.next = new ListNode();
                cur.next.val = arr[i];
                cur = cur.next;
            }
        }
        return head;
    }

    public static void print(ListNode listNode){
        ListNode cur = listNode;
        while (cur != null){
            System.out.print(cur.val);
            cur = cur.next;
        }
        System.out.println();
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {}
        ListNode(int val) {
            this.val = val;
        }
        ListNode(int val, ListNode next) {
            this.val = val; this.next = next;
        }
    }
}
