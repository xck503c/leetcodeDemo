package com.xck.y2022.monotonicStack;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 每日温度
 *
 * @author xuchengkun
 * @date 2022/06/19 15:36
 */
public class DailyTemperatures {

    public static void main(String[] args) {
        System.out.println(dailyTemperatures(new int[] {
                73,74,75,71,69,72,76,73
        }));
    }

    /**
     * 22ms 89.58%
     * 57MB 13.58%
     * @param temperatures
     * @return
     */
    public static int[] dailyTemperatures(int[] temperatures) {
        if (temperatures.length == 1) {
            return new int[]{0};
        }

        int[] answer = new int[temperatures.length];
        //栈单调递减
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < temperatures.length; i++) {
            //如果当前温度比top大，则出栈
            while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
                int top = stack.pop();
                answer[top] = i - top; //赋值差值天数后递增
            }
            stack.push(i);
        }

        return answer;
    }
}
