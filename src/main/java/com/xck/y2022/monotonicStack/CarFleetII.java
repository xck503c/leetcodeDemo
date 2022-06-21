package com.xck.y2022.monotonicStack;

import java.util.*;

/**
 * 车队II
 *
 * @author xuchengkun
 * @date 2022/06/19 16:37
 */
public class CarFleetII {

    public static void main(String[] args) {
        System.out.println(getCollisionTimes(new int[][]{
                {1, 2},
                {2, 1},
                {4, 3},
                {7, 2}
        }));
    }

    /**
     * 36ms 77.42%
     * 96.2MB 87.10%
     *
     * @param cars
     * @return
     */
    public static double[] getCollisionTimes(int[][] cars) {
        if (cars.length == 1) {
            return new double[]{-1};
        }

        double[] timeArr = new double[cars.length];
        Deque<Integer> stack = new LinkedList<>();
        for (int i = cars.length - 1; i >= 0; i--) {

            //这里循环出栈，1. 当前时间比前面时间大，永远追不上；2. 当前速度比前面速度小，永远追不上
            while (!stack.isEmpty()) {
                if (timeArr[stack.peek()] != -1 && (((double) (cars[stack.peek()][0] - cars[i][0])) / (cars[i][1] - cars[stack.peek()][1]) > timeArr[stack.peek()])
                        || cars[i][1] <= cars[stack.peek()][1]) {
                    stack.pop();
                } else {
                    break;
                }
            }

            if (stack.isEmpty()) {
                timeArr[i] = -1;
            } else {
                timeArr[i] = ((double) (cars[stack.peek()][0] - cars[i][0])) / (cars[i][1] - cars[stack.peek()][1]);
            }
            stack.push(i);
        }

        return timeArr;
    }
}
