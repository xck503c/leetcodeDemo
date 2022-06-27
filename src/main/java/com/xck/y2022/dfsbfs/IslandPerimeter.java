package com.xck.y2022.dfsbfs;

/**
 * 岛屿的周长
 *
 * @author xuchengkun
 * @date 2022/06/22 21:40
 **/
public class IslandPerimeter {

    public static void main(String[] args) {
        System.out.println(islandPerimeter_dfs(new int[][]{
                {0, 1, 0, 0},
                {1, 1, 1, 0},
                {0, 1, 0, 0},
                {1, 1, 0, 0}
        }) == 16);
    }

    /**
     * 6ms 69.01%
     * 42.4MB 16.45%
     *
     * @param grid
     * @return
     */
    public static int islandPerimeter_all(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        if (rows == 1 && cols == 1) {
            return grid[0][0] == 1 ? 4 : 0;
        }

        int[] di = {-1, 1, 0, 0};
        int[] dj = {0, 0, -1, 1};

        int total = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 一一过陆地
                if (grid[i][j] == 1) {
                    for (int k = 0; k < 4; k++) {
                        int curi = i + di[k];
                        int curj = j + dj[k];
                        // 如果超出范围则就是水域 || 未超过但是是水域
                        if (curi < 0 || curi > rows - 1 || curj < 0 || curj > cols - 1 || grid[curi][curj] == 0) {
                            ++total;
                        }
                    }
                }
            }
        }

        return total;
    }


    static int[] di = {-1, 1, 0, 0};
    static int[] dj = {0, 0, -1, 1};

    /**
     * 8ms 24.88%
     * 42.6MB 5.15%
     *
     * @param grid
     * @return
     */
    public static int islandPerimeter_dfs(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        if (rows == 1 && cols == 1) {
            return grid[0][0] == 1 ? 4 : 0;
        }

        int total = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 一一过陆地
                if (grid[i][j] == 1) {
                    total += dfs(grid, i, j);
                }
            }
        }

        return total;
    }

    public static int dfs(int[][] grid, int i, int j) {
        int rows = grid.length;
        int cols = grid[0].length;

        // 如果超出范围则就是水域 || 未超过但是是水域
        if (i < 0 || i > rows - 1 || j < 0 || j > cols - 1 || grid[i][j] == 0) {
            return 1;
        }

        if (grid[i][j] == 2) {
            return 0;
        }
        grid[i][j] = 2;

        int total = 0;
        for (int k = 0; k < 4; k++) {
            int curi = i + di[k];
            int curj = j + dj[k];

            total += dfs(grid, curi, curj);
        }

        return total;
    }
}
