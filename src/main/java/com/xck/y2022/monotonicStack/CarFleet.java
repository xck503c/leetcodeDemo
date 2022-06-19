package com.xck.y2022.monotonicStack;

import java.util.*;

/**
 * 车队
 *
 * @author xuchengkun
 * @date 2022/06/19 16:37
 */
public class CarFleet {

    public static void main(String[] args) {
        System.out.println(carFleet(12
                , new int[]{10, 8, 0, 5, 3}
                , new int[]{2, 4, 1, 1, 3}) == 3);
        System.out.println(carFleet(10
                , new int[]{3}
                , new int[]{3}) == 1);
        System.out.println(carFleet(100
                , new int[]{0, 2, 4}
                , new int[]{4, 2, 1}) == 1);
        System.out.println(carFleet(10
                , new int[]{6, 8}
                , new int[]{3, 2}) == 2);
    }

    /**
     * 53ms 94.40%
     * 56.2MB 23.44%
     * @param target
     * @param position
     * @param speed
     * @return
     */
    public static int carFleet(int target, int[] position, int[] speed) {
        if (position.length == 1 || target == 0) {
            return 1;
        }

        Map<Integer, Integer> pos2Speed = new HashMap<>(position.length);
        for (int i = 0; i < position.length; i++) {
            pos2Speed.put(position[i], speed[i]);
        }

        //升序
        Arrays.sort(position);
        //时间花费，单调递减
        Deque<Double> stack = new LinkedList<>();
        for (int i = 0; i < position.length; i++) {
            Double time = ((double) (target - position[i])) / pos2Speed.get(position[i]);
            // 如果cur时间 >= 栈顶时间，说明属于一个车队
            while (!stack.isEmpty() && time >= stack.peek()) {
                stack.pop();
            }

            stack.push(time);
        }

        return stack.size();
    }
}
