package com.xck.y2022.dynamic;

/**
 * 最大矩形
 *
 * @author xuchengkun
 * @date 2022/06/14 17:23
 **/
public class MaximalRectangle {

    public static void main(String[] args) {
        char[][] matrix1 = {{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}
                , {'1', '1', '1', '1', '1'}, {'1', '0', '0', '1', '0'}};

        System.out.println(maximalRectangle_dp(matrix1) == 6);
    }

    /**
     * 17ms 46.47%
     * 45.8MB 25.48%
     * @param matrix
     * @return
     */
    public static int maximalRectangle_dp(char[][] matrix) {

        if (matrix.length == 0) return 0;

        //left数组表示该点包括它自己有多少个连续的1
        int[][] left = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == '1') {
                left[i][0] = 1;
            }
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == '1') {
                    left[i][j] = left[i][j - 1] + 1;
                }
            }
        }

        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                //如果该点没有连续1，跳过
                if (left[i][j] == 0) {
                    continue;
                }

                int area = left[i][j];
                int width = area;
                //向上遍历
                for (int c = 2, k = i - 1; k >= 0; k--, c++) {
                    // 每次宽度取最小
                    width = Math.min(width, left[k][j]);
                    area = Math.max(area, width * c);
                }
                // 记录最大值
                max = Math.max(area, max);
            }
        }

        return max;
    }
}
