package com.xck.y2022;

/**
 * 整数反转
 *
 * 题目：简单来说就是反转一个int类型的数字，但是反转后如果超过int
 * 范围就需要返回0，同时要求不能用long类型来完成
 *
 * 这里比较简单的两种方法：数学分析不等式，方向整除验证方法
 *
 * @author xuchengkun
 * @date 2022/02/10 10:13
 **/
public class ReverseInteger {

    public static void main(String[] args) {
        System.out.println(reverse(123));
        System.out.println(reverse(-123));
        System.out.println(reverse(0));
        System.out.println(reverse(-10));
        System.out.println(reverse(468546541));
        System.out.println(reverse(1534236469));
        System.out.println(reverse(Integer.MAX_VALUE));
        System.out.println(reverse(Integer.MIN_VALUE));
    }

    //long的版本
    public static int reverse(int x) {
        if (x > -10 && x < 10) {
            return x;
        }

        if (x == Integer.MIN_VALUE || x == Integer.MAX_VALUE) return 0;

        int tmp = x;
        long reverse = 0;
        while (tmp != 0) {
            reverse = reverse * 10 + tmp % 10;
            tmp /= 10;
        }

        if (reverse > Integer.MAX_VALUE || reverse < Integer.MIN_VALUE) {
            return 0;
        }

        return (int) reverse;
    }

    public static int reverse10(int x) {
        if (x > -10 && x < 10) {
            return x;
        }

        if (x == Integer.MIN_VALUE || x == Integer.MAX_VALUE) return 0;

        int tmp = x;
        int reverse = 0;
        while (tmp != 0) {
            int last = reverse;
            reverse = last * 10 + tmp % 10;
            //反向除10进行验证是否正确
            if (reverse / 10 != last) {
                return 0;
            }
            tmp /= 10;
        }

        return reverse;
    }

    public static int reverseNoEq(int x) {
        if (x > -10 && x < 10) {
            return x;
        }

        if (x == Integer.MIN_VALUE || x == Integer.MAX_VALUE) return 0;

        int min = Integer.MIN_VALUE / 10;
        int max = Integer.MAX_VALUE / 10;

        int tmp = x;
        int reverse = 0;
        while (tmp != 0) {
            if (reverse < min || reverse > max) {
                return 0;
            }
            reverse = reverse * 10 + tmp % 10;
            tmp /= 10;
        }

        return reverse;
    }
}
