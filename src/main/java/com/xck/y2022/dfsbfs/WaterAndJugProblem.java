package com.xck.y2022.dfsbfs;

import java.util.*;

/**
 * 水壶问题
 *
 * @author xuchengkun
 * @date 2022/06/24 10:03
 **/
public class WaterAndJugProblem {

    public static void main(String[] args) {
        System.out.println(new WaterAndJugProblem()
                .canMeasureWater_bfs(3, 5, 4) == true);
        System.out.println(new WaterAndJugProblem()
                .canMeasureWater_bfs(2, 6, 5) == false);
        System.out.println(new WaterAndJugProblem()
                .canMeasureWater_bfs(1, 2, 3) == true);
    }

    public boolean canMeasureWater(int jug1Capacity, int jug2Capacity, int targetCapacity) {
        if (jug1Capacity == targetCapacity || jug2Capacity == targetCapacity
                || jug1Capacity + jug2Capacity == targetCapacity) {
            return true;
        }

        if (jug1Capacity + jug2Capacity < targetCapacity) {
            return false;
        }

        Set<Long> record = new HashSet<>();

        Deque<int[]> stack = new LinkedList<>();
        stack.push(new int[]{0, 0});
        while (!stack.isEmpty()) {
            int[] curArr = stack.peek();
            int x = curArr[0], y = curArr[1];
            long id = x * 1000000L + y;
            // 如果已经走过，这跳过
            if (record.contains(id)) {
                stack.pop();
                continue;
            }

            curArr = stack.pop();
            x = curArr[0];
            y = curArr[1];
            id = x * 1000000L + y;
            record.add(id);

            if (x == targetCapacity || y == targetCapacity || x + y == targetCapacity) {
                return true;
            }

            // x装满
            stack.push(new int[]{jug1Capacity, y});
            // y装满
            stack.push(new int[]{x, jug2Capacity});
            // x倒空
            stack.push(new int[]{0, y});
            // y倒空
            stack.push(new int[]{x, 0});
            // x倒入y
            int newY = Math.min(jug2Capacity, x + y);
            stack.push(new int[]{x - newY + y, newY});
            // y倒入x
            int newX = Math.min(jug1Capacity, x + y);
            stack.push(new int[]{newX, y - newX + x});
        }

        return false;
    }

    public boolean canMeasureWater_1(int jug1Capacity, int jug2Capacity, int targetCapacity) {
        if (jug1Capacity == targetCapacity || jug2Capacity == targetCapacity
                || jug1Capacity + jug2Capacity == targetCapacity) {
            return true;
        }

        if (jug1Capacity + jug2Capacity < targetCapacity) {
            return false;
        }

        Set<Long> record = new HashSet<>();

        Deque<int[]> stack = new LinkedList<>();
        stack.push(new int[]{0, 0});
        while (!stack.isEmpty()) {
            // 如果已经走过，这跳过
            if (record.contains(hash(stack.peek()))) {
                stack.pop();
                continue;
            }

            int[] curArr = stack.pop();
            int x = curArr[0];
            int y = curArr[1];
            record.add(hash(curArr));

            if (x == targetCapacity || y == targetCapacity || x + y == targetCapacity) {
                return true;
            }

            // x装满
            stack.push(new int[]{jug1Capacity, y});
            // y装满
            stack.push(new int[]{x, jug2Capacity});
            // x倒空
            stack.push(new int[]{0, y});
            // y倒空
            stack.push(new int[]{x, 0});
            int diffx2y = Math.min(x, jug2Capacity - y);
            stack.push(new int[]{x - diffx2y, y + diffx2y});
            int diffy2x = Math.min(y, jug1Capacity - x);
            stack.push(new int[]{x + diffy2x, y - diffy2x});
        }

        return false;
    }

    public long hash(int[] state) {
        return (long) state[0] * 1000000L + state[1];
    }

    /**
     * 316ms 46.01%
     * 99MB 40.96%
     * @param jug1Capacity
     * @param jug2Capacity
     * @param targetCapacity
     * @return
     */
    public boolean canMeasureWater_bfs(int jug1Capacity, int jug2Capacity, int targetCapacity) {
        if (jug1Capacity == targetCapacity || jug2Capacity == targetCapacity
                || jug1Capacity + jug2Capacity == targetCapacity) {
            return true;
        }

        if (jug1Capacity + jug2Capacity < targetCapacity) {
            return false;
        }

        Set<Long> record = new HashSet<>();
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        while (!queue.isEmpty()) {
            int[] curArr = queue.poll();
            if (record.contains(hash(curArr))) {
                continue;
            }
            int x = curArr[0], y = curArr[1];
            record.add(hash(curArr));

            // 终止条件
            if (x == targetCapacity || y == targetCapacity || x + y == targetCapacity) {
                return true;
            }

            List<int[]> statusList = statusList(x, y, jug1Capacity, jug2Capacity);
            for (int[] tmpArr : statusList) {
                if (record.contains(hash(tmpArr))) {
                    continue;
                }
                queue.offer(tmpArr);
            }
        }

        return false;
    }

    public List<int[]> statusList(int x, int y, int jug1Capacity, int jug2Capacity) {
        List<int[]> statusList = new LinkedList<>();
        if (x < jug1Capacity) { //如果x没有满才倒入
            statusList.add(new int[]{jug1Capacity, y});
        }

        if (y < jug2Capacity) { //如果y没有满才倒入
            statusList.add(new int[]{x, jug2Capacity});
        }

        if (x > 0) {
            // 如果x有水，才可以清空
            statusList.add(new int[]{0, y});
            // 如果y没有满，x才能倒过去
            if (y < jug2Capacity) {
                int diff = Math.min(jug2Capacity - y, x);
                statusList.add(new int[]{x - diff, y + diff});
            }
        }

        if (y > 0) {
            statusList.add(new int[]{x, 0});
            // 如果x没有满，y才能倒过去
            if (y < jug2Capacity) {
                int diff = Math.min(jug1Capacity - x, y);
                statusList.add(new int[]{x + diff, y - diff});
            }
        }

        return statusList;
    }
}
