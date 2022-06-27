package com.xck.y2022.dfsbfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 岛屿的数量I
 *
 * @author xuchengkun
 * @date 2022/06/22 15:55
 **/
public class NumberOfIslands {

    public static void main(String[] args) {
        System.out.println(numIslands_bfs(new char[][]{
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'},
        }) == 3);
        System.out.println(numIslands_bfs(new char[][]{
                {'1', '1', '1'},
                {'0', '1', '0'},
                {'1', '1', '1'},
        }) == 1);
    }

    /**
     * 深度搜索，遍历1，针对每个1，去递归寻找，寻找到的全部置为0
     * <p>
     * 2ms 100%
     * 50.1MB 10.35%
     *
     * @param grid
     * @return
     */
    public static int numIslands_dfs(char[][] grid) {
        if (grid.length == 1 && grid[0].length == 1) {
            return grid[0][0] == '1' ? 1 : 0;
        }

        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    dfs(grid, i, j);
                    ++count;
                }
            }
        }
        return count;
    }

    /**
     * 搜索所有相连的1，并置为0
     */
    public static void dfs(char[][] grid, int i, int j) {
        int rows = grid.length;
        int cols = grid[0].length;

        if (i < 0 || i >= rows || j < 0 || j >= cols || grid[i][j] == '0') {
            return;
        }

        grid[i][j] = '0';

        // 上下左右
        dfs(grid, i - 1, j);
        dfs(grid, i + 1, j);
        dfs(grid, i, j - 1);
        dfs(grid, i, j + 1);
    }

    /**
     * 7ms 12.21%
     * 49MB 89.39%
     * @param grid
     * @return
     */
    public static int numIslands_bfs(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        if (rows == 1 && cols == 1) {
            return grid[0][0] == '1' ? 1 : 0;
        }

        Queue<Integer> neighbors = new LinkedList<>();
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    ++count;
                    grid[i][j] = '0';
                    neighbors.offer(i * cols + j);
                    while (!neighbors.isEmpty()) {
                        // 获得x，y坐标
                        Integer location = neighbors.poll();
                        int locationI = location / cols;
                        int locationJ = location % cols;

                        if (locationI > 0 && grid[locationI - 1][locationJ] == '1') {
                            grid[locationI - 1][locationJ] = '0';
                            neighbors.offer((locationI - 1) * cols + locationJ);
                        }

                        if (locationI < rows - 1 && grid[locationI + 1][locationJ] == '1') {
                            grid[locationI + 1][locationJ] = '0';
                            neighbors.offer((locationI + 1) * cols + locationJ);
                        }

                        if (locationJ > 0 && grid[locationI][locationJ - 1] == '1') {
                            grid[locationI][locationJ - 1] = '0';
                            neighbors.offer(locationI * cols + locationJ - 1);
                        }

                        if (locationJ < cols - 1 && grid[locationI][locationJ + 1] == '1') {
                            grid[locationI][locationJ + 1] = '0';
                            neighbors.offer(locationI * cols + locationJ + 1);
                        }
                    }
                }
            }
        }
        return count;
    }
}
