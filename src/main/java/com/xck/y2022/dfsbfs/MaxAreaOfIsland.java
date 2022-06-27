package com.xck.y2022.dfsbfs;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 最大岛屿面积
 *
 * @author xuchengkun
 * @date 2022/06/22 19:51
 **/
public class MaxAreaOfIsland {

    public static void main(String[] args) {
        System.out.println(maxAreaOfIsland_bfs(new int[][]{
                {1, 1, 0, 0, 0},
                {1, 1, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 1, 1},
        }) == 4);
    }

    /**
     * 2ms 56.96%
     * 41.8MB 11.29%
     *
     * @param grid
     * @return
     */
    public static int maxAreaOfIsland_dfs(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        if (rows == 1 && cols == 1) {
            return grid[0][0] == 1 ? 1 : 0;
        }

        int maxArea = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    maxArea = Math.max(maxArea, dfs(grid, i, j));
                }
            }
        }
        return maxArea;
    }

    public static int dfs(int[][] grid, int i, int j) {
        int rows = grid.length;
        int cols = grid[0].length;

        boolean isInArea = i >= 0 && i < rows
                && j >= 0 && j < cols;
        if (!isInArea) {
            return 0;
        }

        if (grid[i][j] != 1) {
            return 0;
        }
        grid[i][j] = 2;

        //自己也算一块陆地
        int total = 1;
        total += dfs(grid, i - 1, j);
        total += dfs(grid, i + 1, j);
        total += dfs(grid, i, j - 1);
        total += dfs(grid, i, j + 1);
        return total;
    }

    /**
     * 5ms 7.5%
     * 41.8MB 12.56%
     *
     * @param grid
     * @return
     */
    public static int maxAreaOfIsland_stack(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        if (rows == 1 && cols == 1) {
            return grid[0][0] == 1 ? 1 : 0;
        }

        //方向
        int[] di = {0, 0, -1, 1};
        int[] dj = {-1, 1, 0, 0};

        Deque<Integer> stacki = new LinkedList<>();
        Deque<Integer> stackj = new LinkedList<>();
        int maxArea = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    stacki.push(i);
                    stackj.push(j);
                    int area = 0;
                    while (!stacki.isEmpty()) {
                        //每次取出都是左后一个压栈的方块
                        int curi = stacki.pop();
                        int curj = stackj.pop();
                        // 过滤base case 超过范围或者走过的直接过滤
                        if (curi < 0 || curi > rows - 1 || curj < 0 || curj > cols - 1 || grid[curi][curj] != 1) {
                            continue;
                        }

                        grid[curi][curj] = 2; // 标记为走过
                        ++area;
                        for (int k = 0; k < 4; k++) {
                            int nexti = curi + di[k];
                            int nextj = curj + dj[k];
                            stacki.push(nexti);
                            stackj.push(nextj);//不断压栈
                        }
                    }

                    maxArea = Math.max(maxArea, area);
                }
            }
        }
        return maxArea;
    }

    public static int maxAreaOfIsland_bfs(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        if (rows == 1 && cols == 1) {
            return grid[0][0] == 1 ? 1 : 0;
        }

        //方向
        int[] di = {0, 0, -1, 1};
        int[] dj = {-1, 1, 0, 0};

        Queue<Integer> queuei = new LinkedList<>();
        Queue<Integer> queuej = new LinkedList<>();
        int maxAea = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    int area = 0;
                    queuei.offer(i);
                    queuej.offer(j);
                    while (!queuei.isEmpty()) {
                        //取出x，y坐标
                        int curi = queuei.poll();
                        int curj = queuej.poll();

                        if (curi < 0 || curj < 0 || curi > rows - 1 || curj > cols - 1 || grid[curi][curj] != 1) {
                            continue;
                        }
                        ++area;
                        grid[curi][curj] = 2;
                        for (int k = 0; k < 4; k++) {
                            int nexti = curi + di[k];
                            int nextj = curj + dj[k];
                            queuei.offer(nexti);
                            queuej.offer(nextj);
                        }
                    }
                    maxAea = Math.max(maxAea, area);
                }
            }
        }

        return maxAea;
    }
}
