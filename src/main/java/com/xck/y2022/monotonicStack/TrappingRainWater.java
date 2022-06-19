package com.xck.y2022.monotonicStack;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 接雨水
 *
 * 只有2侧柱子之间有凹槽才可以留住雨水
 */
public class TrappingRainWater {

    public static void main(String[] args) {
        System.out.println(trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}) == 6);
    }

    /**
     * 3ms 38.46%
     * 41.9MB 59.68%
     * @param height
     * @return
     */
    public static int trap(int[] height) {
        if (height.length == 1 || height.length == 2) {
            return 0;
        }

        int total = 0;
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < height.length; i++) {
            int curHeight = height[i];
            // 只有右侧>左侧才可能出现槽
            while (!stack.isEmpty() && height[stack.peek()] < curHeight) {
                Integer top = stack.pop();
                //已经出栈一个，所以是>0
                if (stack.size() > 0) {
                    //只计算top可以留住的雨水，一层层算
                    int h = Math.min(height[stack.peek()], height[i]) - height[top];
                    //需要扣除柱子宽度
                    int w = i - stack.peek() - 1;
                    total += (h * w);
                }
            }

            stack.push(i);
        }

        return total;
    }
}
