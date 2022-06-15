package com.xck.y2022.monotonicStack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Stack;

/**
 * 下一个更大的元素
 *
 * @author xuchengkun
 * @date 2022/06/15 16:19
 **/
public class NextGreaterElementI {

    public static void main(String[] args) {
        nextGreaterElement_lr(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2});
        nextGreaterElement_rl(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2});
    }

    /**
     * 从左往右遍历
     * 3ms 89.65%
     * 41.7MB 9.44%
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] nextGreaterElement_lr(int[] nums1, int[] nums2) {
        if (nums2.length == 1) return new int[]{-1};

        HashMap<Integer, Integer> map = new HashMap<>(nums2.length);
        //栈单调递减，出栈条件是当前元素比栈顶元素大
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < nums2.length; i++) {

            if (!stack.isEmpty()) {
                Integer top = stack.peek();
                //如果当前元素比栈顶大，说明当前元素是栈顶元素的右边第一个
                while (nums2[i] > top) {
                    map.put(top, nums2[i]);
                    stack.pop(); //出栈
                    if (stack.isEmpty()) break;

                    top = stack.peek(); //如果重复满足条件，则重复出栈
                }
            }

            stack.push(nums2[i]); //最后将当前元素放入
            map.put(nums2[i], -1); //默认-1
        }

        int[] result = new int[nums1.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = map.get(nums1[i]);
        }
        return result;
    }

    /**
     * 从右往左遍历
     * 3ms 89.65%
     * 41.7MB 9.44%
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] nextGreaterElement_rl(int[] nums1, int[] nums2) {
        if (nums2.length == 1) return new int[]{-1};

        HashMap<Integer, Integer> map = new HashMap<>(nums2.length);
        //栈单调递减，出栈条件是当前元素比栈顶元素大
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = nums2.length - 1; i >= 0; i--) {
            int cur = nums2[i];
            // 当前元素如果比栈顶大，说明没有右侧第一个大的元素，直接出栈
            while (!stack.isEmpty() && cur >= stack.peek()) {
                stack.pop();
            }

            map.put(cur, stack.isEmpty() ? -1 : stack.peek()); //默认-1
            stack.push(cur); //最后将当前元素放入
        }

        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            result[i] = map.get(nums1[i]);
        }
        return result;
    }
}
