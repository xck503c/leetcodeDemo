package com.xck.y2022;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 有效的数独
 *
 * @author xuchengkun
 * @date 2022/03/07 09:51
 **/
public class ValidSudoku {

    public static void main(String[] args) {
        System.out.println(isValidSudoku2(converCharArr((new String[][]{
                {"8", "3", ".", ".", "7", ".", ".", ".", "."}
                , {"6", ".", ".", "1", "9", "5", ".", ".", "."}
                , {".", "9", "8", ".", ".", ".", ".", "6", "."}
                , {"8", ".", ".", ".", "6", ".", ".", ".", "3"}
                , {"4", ".", ".", "8", ".", "3", ".", ".", "1"}
                , {"7", ".", ".", ".", "2", ".", ".", ".", "6"}
                , {".", "6", ".", ".", ".", ".", "2", "8", "."}
                , {".", ".", ".", "4", "1", "9", ".", ".", "5"}
                , {".", ".", ".", ".", "8", ".", ".", "7", "9"}
        })))); //false

//        System.out.println(isValidSudoku1(new String[][]
//                {{"5", "3", ".", ".", "7", ".", ".", ".", "."}
//                        , {"6", ".", ".", "1", "9", "5", ".", ".", "."}
//                        , {".", "9", "8", ".", ".", ".", ".", "6", "."}
//                        , {"8", ".", ".", ".", "6", ".", ".", ".", "3"}
//                        , {"4", ".", ".", "8", ".", "3", ".", ".", "1"}
//                        , {"7", ".", ".", ".", "2", ".", ".", ".", "6"}
//                        , {".", "6", ".", ".", ".", ".", "2", "8", "."}
//                        , {".", ".", ".", "4", "1", "9", ".", ".", "5"}
//                        , {".", ".", ".", ".", "8", ".", ".", "7", "9"}}
//        )); //false

        System.out.println(isValidSudoku1(new String[][]{
                {"5", "3", ".", ".", "7", ".", ".", ".", "."},
                {"6", ".", ".", "1", "9", "5", ".", ".", "."},
                {".", "9", "8", ".", ".", ".", ".", "6", "."},
                {"8", ".", ".", ".", "6", ".", ".", ".", "3"},
                {"4", ".", ".", "8", ".", "3", ".", ".", "1"},
                {"7", ".", ".", ".", "2", ".", ".", ".", "6"},
                {".", "6", ".", ".", ".", ".", "2", "8", "."},
                {".", ".", ".", "4", "1", "9", ".", ".", "5"},
                {".", ".", ".", ".", "8", ".", ".", "7", "9"}
        })); // true
    }

    public static char[][] converCharArr(String[][] board){
        char[][] arr = new char[9][9];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                arr[i][j] = board[i][j].charAt(0);
            }
        }
        return arr;
    }

    public static boolean isValidSudoku1(String[][] board) {
        Set<SudokuLocation> set = new HashSet<>(81);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                String value = board[i][j];
                if (value.equals(".")) continue;

                if (!set.add(new SudokuLocation(i, j, value))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int[][] orderArr = new int[][]{
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8}
    };

    public static boolean isValidSudoku2(char[][] board) {
        int[][] orderNumberArr = new int[9][9]; //存储序号
        char[][] horizontally = new char[9][9]; //存储横的部分
        char[][] vertically = new char[9][9]; //存储竖的部分
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                char value = board[i][j];
                if (value == '.') continue;

                int index = value - '1';
                if (orderNumberArr[orderArr[i/3][j/3]][index] != 0) {
                    return false;
                }
                orderNumberArr[orderArr[i/3][j/3]][index] = 1;

                if (horizontally[i][index] != 0) {
                    return false;
                }

                horizontally[i][index] = 1;

                if (vertically[j][index] != 0) {
                    return false;
                }

                vertically[j][index] = 1;
            }
        }

        return true;
    }

    private static class SudokuLocation {

        private int i;
        private int j;
        private String number;
        private int order;

        public SudokuLocation(int i, int j, String number) {
            this.i = i;
            this.j = j;
            this.number = number;
            this.order = orderArr[i / 3][j / 3];
        }

        @Override
        public boolean equals(Object o) {
            SudokuLocation that = (SudokuLocation) o;
            //如果i一样说明同一行，j一样说明同一列，order一样说明同个方块
            return number == that.number && (i == that.i ||
                    j == that.j || order == that.order);
        }

        @Override
        public int hashCode() {
            return Objects.hash(number);
        }
    }
}
