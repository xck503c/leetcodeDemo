package com.xck.y2022.monotonicStack;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 柱状图中最大的矩形
 *
 * @author xuchengkun
 * @date 2022/06/16 08:49
 **/
public class LargestRectangleInHistogram {

    public static void main(String[] args) {
        System.out.println(largestRectangleArea2(new int[]{2, 1, 5, 6, 2, 3}) == 10);
        System.out.println(largestRectangleArea2(new int[]{2, 4}) == 4);
        System.out.println(largestRectangleArea2(new int[]{2, 1, 3}) == 3);
        System.out.println(largestRectangleArea2(new int[]{1, 2, 3}) == 4);
        System.out.println(largestRectangleArea2(new int[]{2, 1, 2}) == 3);
        System.out.println(largestRectangleArea2(new int[]{3, 2, 1, 2}) == 4);
        System.out.println(largestRectangleArea(new int[]{3, 6, 5, 7, 4, 8, 1, 0}) == 20);
    }

    /**
     * 单调栈
     * 30 ms 46.22%
     * 56.8 MB 38.74%
     *
     * @param heights
     * @return
     */
    public static int largestRectangleArea1(int[] heights) {
        if (heights.length == 1) {
            return heights[0];
        }

        //单调递增栈
        Deque<Integer> stack = new LinkedList<>();

        //从左往右遍历，求右侧最后一个最大
        int[] right = new int[heights.length];
        for (int i = 0; i < heights.length; i++) {
            right[i] = heights.length - 1; //初始化默认值
            int curHeight = heights[i]; //当前柱子高度
            while (!stack.isEmpty() && curHeight < heights[stack.peek()]) {
                right[stack.pop()] = i - 1; //找到第一个最小，那-1就是最后一个最大
            }

            stack.push(i);
        }
        stack.clear();

        //从右往左遍历，求左侧最后一个最大
        int[] left = new int[heights.length];
        for (int i = heights.length - 1; i >= 0; i--) {
            left[i] = 0; //初始化默认值
            int curHeight = heights[i]; //当前柱子高度
            while (!stack.isEmpty() && curHeight < heights[stack.peek()]) {
                left[stack.pop()] = i + 1; //
            }

            stack.push(i);
        }

        int maxArea = heights[0];
        for (int i = 0; i < heights.length; i++) {
            maxArea = Math.max(maxArea, (right[i] - left[i] + 1) * heights[i]);
        }

        return maxArea;
    }

    /**
     * 单调栈 优化
     * 21 ms 76.27%%
     * 57.3 MB 31.56%
     *
     * @param heights
     * @return
     */
    public static int largestRectangleArea2(int[] heights) {
        if (heights.length == 1) {
            return heights[0];
        }

        //单调递增栈
        Deque<Integer> stack = new LinkedList<>();

        //从左往右遍历，求右侧最后一个最大
        int[] right = new int[heights.length];
        int[] left = new int[heights.length];
        for (int i = 0; i < heights.length; i++) {
            right[i] = heights.length - 1; //初始化默认值
            left[i] = i; //如果找不到默认自己
            int curHeight = heights[i]; //当前柱子高度
            while (!stack.isEmpty() && curHeight < heights[stack.peek()]) {
                Integer topIndex = stack.pop();
                right[topIndex] = i - 1; //找到第一个最小，那-1就是最后一个最大
                left[i] = topIndex; //出栈就意味着栈顶比当前大
            }

            stack.push(i);
        }

        int maxArea = heights[0];
        for (int i = 0; i < heights.length; i++) {
            // 递推，left[leftIndex]侧比当前大，那么可能left[left[leftIndex]]也比当前大
            int leftIndex = i;
            while (left[leftIndex] != leftIndex && heights[leftIndex] < heights[left[leftIndex]]) {
                leftIndex = left[left[leftIndex]];

            }
            maxArea = Math.max(maxArea, (right[i] - left[leftIndex] + 1) * heights[i]);
        }

        return maxArea;
    }

    /**
     * 官方答案，效率和我写的第二种差不多，不过感觉更好
     * @param heights
     * @return
     */
    public static int largestRectangleArea(int[] heights) {
        if (heights.length == 1) {
            return heights[0];
        }

        //单调递增栈
        Deque<Integer> stack = new LinkedList<>();

        //从左往右遍历，求右侧最后一个最大
        int[] right = new int[heights.length];
        Arrays.fill(right, heights.length - 1);
        int[] left = new int[heights.length];
        for (int i = 0; i < heights.length; i++) {
            while (!stack.isEmpty() && heights[i] <= heights[stack.peek()]) {
                right[stack.peek()] = i - 1; //找到第一个最小，那-1就是最后一个最大
                stack.pop();
            }

            //这是精髓，不太理解
            left[i] = stack.isEmpty() ? 0 : stack.peek() + 1;
            stack.push(i);
        }

        int maxArea = heights[0];
        for (int i = 0; i < heights.length; i++) {
            maxArea = Math.max(maxArea, (right[i] - left[i] + 1) * heights[i]);
        }

        return maxArea;
    }
}
