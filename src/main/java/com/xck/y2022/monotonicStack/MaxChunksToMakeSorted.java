package com.xck.y2022.monotonicStack;

import java.util.Deque;
import java.util.LinkedList;

public class MaxChunksToMakeSorted {

    public static void main(String[] args) {
        System.out.println(maxChunksToSorted(new int[]{2, 0, 1}) == 1);
        System.out.println(maxChunksToSorted(new int[]{1, 2, 0, 3}) == 2);
        System.out.println(maxChunksToSorted(new int[]{0, 2, 1}) == 2);
        System.out.println(maxChunksToSorted(new int[]{5, 4, 3, 2, 1}) == 1);
        System.out.println(maxChunksToSorted(new int[]{2, 1, 3, 4, 4}) == 4);
    }

    /**
     * 0 ms 100%
     * 38.8MB 45.9%
     *
     * @param arr
     * @return
     */
    public static int maxChunksToSorted(int[] arr) {
        if (arr.length == 1) {
            return 1;
        }

        if (arr.length == 2 && arr[0] <= arr[1]) {
            return 2;
        }

        // 单调递增
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < arr.length; i++) {
            if (stack.isEmpty() || arr[stack.peek()] <= arr[i]) {
                stack.push(i);
                continue;
            }

            int chunkMax = stack.peek();
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                stack.pop();
            }

            stack.push(chunkMax);
        }

        return stack.size();
    }
}
