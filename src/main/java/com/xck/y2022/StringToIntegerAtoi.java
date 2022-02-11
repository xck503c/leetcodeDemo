package com.xck.y2022;

/**
 * 字符串转换整数 (atoi函数)
 *
 * @author xuchengkun
 * @date 2022/02/10 14:17
 **/
public class StringToIntegerAtoi {

    public static void main(String[] args) {
        System.out.println(myAtoi("  fdsd-6"));
        System.out.println(myAtoi("2147483648"));
        System.out.println(myAtoi("-91283472332"));
        System.out.println(myAtoi("words and 987"));
        System.out.println(myAtoi("   -42"));
        System.out.println(myAtoi("  "));
        System.out.println(myAtoi("+"));

        System.out.println(myAtoiDFA("  fdsd-6"));
        System.out.println(myAtoiDFA("2147483648"));
        System.out.println(myAtoiDFA("-91283472332"));
        System.out.println(myAtoiDFA("words and 987"));
        System.out.println(myAtoiDFA("   -42"));
        System.out.println(myAtoiDFA("  "));
        System.out.println(myAtoiDFA("+"));
    }

    public static int myAtoi(String s) {

        char[] arr = s.toCharArray();

        //跳过前导空格
        int index = 0;
        while (index < s.length()) {
            if (arr[index] == ' ') {
                ++index;
                continue;
            } else {
                break;
            }
        }

        //判断全部都是空格的情况
        if (index >= s.length()) {
            return 0;
        }

        char signChar = arr[index];
        boolean isNegative = false;
        if (signChar == '-' || signChar == '+') {
            ++index;
            if (signChar == '-') {
                isNegative = true;
            }
        }

        //判断只有一个符号位的情况
        if (index >= s.length()) {
            return 0;
        }

        char firstChar = arr[index];
        if (!('0' <= firstChar && firstChar <= '9')) {
            return 0;
        }

        int number = 0;
        int min = Integer.MIN_VALUE / 10, max = Integer.MAX_VALUE / 10;
        //最后一位
        int minDig = Integer.MIN_VALUE % 10, maxDig = Integer.MAX_VALUE % 10;
        for (int i = index; i < arr.length; i++) {
            char c = arr[i];
            if (!('0' <= c && c <= '9')) {
                break;
            }

            int dig = c - '0';

            //溢出判断
            if (number < min || (number == min && -dig < minDig)) {
                return Integer.MIN_VALUE;
            } else if (number > max || (number == max && dig > maxDig)) {
                return Integer.MAX_VALUE;
            }
            if (!isNegative) {
                number = number * 10 + dig;
            } else {
                number = number * 10 - dig;
            }
        }
        return number;
    }

    /**
     * 限自动机实现
     * <p>
     * 状态：0-开始，1-结束，2-数字，3-符号
     * <p>
     * 字符：0-空格，1-数字，2,-符号，3-其他字符
     *
     * @param s
     * @return
     */
    public static int myAtoiDFA(String s) {
        //根据状态填表
        int[][] status = new int[][]{
                {0, 2, 3, 1},
                {1, 1, 1, 1},
                {1, 2, 1, 1},
                {1, 2, 1, 1}
        };

        boolean isNegative = false;
        int number = 0;
        int min = Integer.MIN_VALUE / 10, max = Integer.MAX_VALUE / 10;
        //最后一位
        int minDig = Integer.MIN_VALUE % 10, maxDig = Integer.MAX_VALUE % 10;
        char[] arr = s.toCharArray();
        int curStatus = 0; //当前状态从0开始
        for (char c : arr) {
            //根据字符获取所属字符集合编号
            int charType = charType(c);
            //根据集合编号和状态获取当前状态
            curStatus = status[curStatus][charType];

            switch (curStatus) {
                case 1:
                    return number;
                case 2:
                {
                    int dig = c - '0';

                    //溢出判断
                    if (number < min || (number == min && -dig < minDig)) {
                        return Integer.MIN_VALUE;
                    } else if (number > max || (number == max && dig > maxDig)) {
                        return Integer.MAX_VALUE;
                    }
                    //根据正负号累加
                    if (!isNegative) {
                        number = number * 10 + dig;
                    } else {
                        number = number * 10 - dig;
                    }
                    break;
                }
                case 3:
                    isNegative = c == '+' ? false : true;
            }
        }
        return number;
    }

    public static int charType(char c) {
        if (c == ' ') {
            return 0;
        }

        if ('0' <= c && c <= '9') {
            return 1;
        }

        if (c == '+' || c == '-') {
            return 2;
        }

        return 3;
    }
}
