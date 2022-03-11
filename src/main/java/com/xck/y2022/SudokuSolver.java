package com.xck.y2022;

/**
 * 解数独
 *
 * @author xuchengkun
 * @date 2022/03/11 11:09
 **/
public class SudokuSolver {

    boolean isFinish = false;

    public static int[][] orderArr = new int[][]{
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8}
    };

    private int[][] iArr = new int[9][9]; //行
    private int[][] jArr = new int[9][9]; //列
    private int[][] order = new int[9][9]; //序号

    public int isOnly(int i, int j) {
        int number = -1;
        for (int k = 0; k < 9; k++) {
            if (iArr[i][k] == 0 && jArr[j][k] == 0 && order[orderArr[i / 3][j / 3]][k] == 0) {
                if (number != -1) {
                    return -1;
                }
                number = k;

            }
        }
        iArr[i][number] = 1;
        jArr[j][number] = 1;
        order[orderArr[i / 3][j / 3]][number] = 1;
        return number;
    }

    public void init(int i, int j, int number) {
        iArr[i][number] = 1;
        jArr[j][number] = 1;
        order[orderArr[i / 3][j / 3]][number] = 1;
    }

    public void write(int i, int j, int number) {
        iArr[i][number] = 1;
        jArr[j][number] = 1;
        order[orderArr[i / 3][j / 3]][number] = 1;
    }

    public void clear(int i, int j, int number) {
        iArr[i][number] = 0;
        jArr[j][number] = 0;
        order[orderArr[i / 3][j / 3]][number] = 0;
    }

    public void solveSudoku(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                char value = board[i][j];
                if (value == '.') continue;

                int index = value - '1';
                init(i, j, index);
            }
        }

        while (true) { //不断尝试填入
            int count = 0;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (board[i][j] != '.') continue;
                    int number = isOnly(i, j);
                    if (number != -1) {
                        board[i][j] = (char) (number + '1');
                        ++count;
                    }
                }
            }
            if (count == 0) {
                break;
            }
        }

        solveSudokuInner(board, 0, 0); //遍历所有解
    }

    public void solveSudokuInner(char[][] board, int i, int j) {
        if (i == board.length) { //获得解
            isFinish = true;
            return;
        }

        if (i < board.length && j < board.length) {
            if (board[i][j] != '.') { //非空白跳过
                solveSudokuInner(board, i, j + 1);
            } else {
                //深度搜索
                for (int k = 0; k < 9; k++) {
                    if (iArr[i][k] == 0 && jArr[j][k] == 0 && order[orderArr[i / 3][j / 3]][k] == 0) {
                        write(i, j, k); //记录
                        board[i][j] = (char) (k + '1'); //填入
                        solveSudokuInner(board, i, j + 1);
                        if (isFinish) {
                            break;
                        } else {
                            clear(i, j, k); //清除
                            board[i][j] = '.';
                        }
                    }
                }
            }
        } else {
            solveSudokuInner(board, i + 1, 0);
        }
    }

    public static void main(String[] args) {
        String arr = "[[\"5\",\"3\",\".\",\".\",\"7\",\".\",\".\",\".\",\".\"],[\"6\",\".\",\".\",\"1\",\"9\",\"5\",\".\",\".\",\".\"],[\".\",\"9\",\"8\",\".\",\".\",\".\",\".\",\"6\",\".\"],[\"8\",\".\",\".\",\".\",\"6\",\".\",\".\",\".\",\"3\"],[\"4\",\".\",\".\",\"8\",\".\",\"3\",\".\",\".\",\"1\"],[\"7\",\".\",\".\",\".\",\"2\",\".\",\".\",\".\",\"6\"],[\".\",\"6\",\".\",\".\",\".\",\".\",\"2\",\"8\",\".\"],[\".\",\".\",\".\",\"4\",\"1\",\"9\",\".\",\".\",\"5\"],[\".\",\".\",\".\",\".\",\"8\",\".\",\".\",\"7\",\"9\"]]";
        char[][] arr1 = convert(arr);

        SudokuSolver solver = new SudokuSolver();
        solver.solveSudoku(arr1);
        print(arr1);
    }

    public static char[][] convert(String str){
        char[][] c = new char[9][9];
        int count = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                while (count < str.length() && ((str.charAt(count) < '0' || str.charAt(count) > '9')
                        && str.charAt(count) != '.')) {
                    ++count;
                }
                if (count == str.length()) {
                    return c;
                }
                c[i][j] = str.charAt(count);
                ++count;
            }
        }
        return c;
    }

    public static void print(char[][] arr){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(arr[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
