package com.xck.y2022.monotonicStack;

import java.util.*;

/**
 * 下一个更大的元素II
 *
 * @author xuchengkun
 * @date 2022/06/15 16:19
 **/
public class NextGreaterElementII {

    public static void main(String[] args) {
        nextGreaterElement_lr(new int[]{8, 7, 6, 5, 4});
        nextGreaterElement_lr(new int[]{1, 2, 1});
        nextGreaterElement_lr(new int[]{1, 2, 3, 4, 3});
        nextGreaterElement_lr(new int[]{100, 1, 11, 1, 120, 111, 123, 1, -1, -100});
    }

    /**
     * 从左往右遍历
     * 3ms 98.136%%
     * 43.1MB 15.69%
     *
     * @param nums
     * @return
     */
    public static int[] nextGreaterElement_lr(int[] nums) {
        if (nums.length == 1) return new int[]{-1};

        int[] result = new int[nums.length];
        //栈单调递减，出栈条件是当前元素比栈顶元素大
        //这里由Stack换成LinkedList，耗时从38ms变成4ms
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < nums.length * 2 - 1; i++) {

            int j = i % nums.length;
            //如果当前元素比栈顶大，说明当前元素是栈顶元素的右边第一个
            //如果重复满足条件，则重复出栈
            while (!stack.isEmpty() && nums[j] > nums[stack.peek()]) {
                result[stack.pop()] = nums[j];
            }
            if (i < nums.length) {
                stack.push(j); //最后将当前索引放入，由于数字可能会重复
                result[i] = -1;
            }
        }

        return result;
    }
}
