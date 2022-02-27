package com.xck.y2022;

/**
 * 两数相除
 *
 * @author xuchengkun
 * @date 2022/02/23 13:39
 **/
public class DivideTwoIntegers {

    public static void main(String[] args) {
//        System.out.println(divide2(3, -1) == (3 / -1));
//        System.out.println(divide2(Integer.MAX_VALUE, Integer.MIN_VALUE) == (Integer.MAX_VALUE / Integer.MIN_VALUE));
//        System.out.println(divide2(Integer.MIN_VALUE, Integer.MAX_VALUE) == (Integer.MIN_VALUE / Integer.MAX_VALUE));
        System.out.println(divide2(1, Integer.MAX_VALUE) == (1 / Integer.MAX_VALUE));
//        System.out.println(divide2(1, Integer.MIN_VALUE) == (1 / Integer.MIN_VALUE));
        System.out.println(divide2(Integer.MAX_VALUE, 10) == (Integer.MAX_VALUE / 10));
        System.out.println(divide2(1000, 10) == (1000 / 10));
//        System.out.println(divide2(-1000, -10) == (-1000 / -10));
//        System.out.println(divide2(7, -3) == (7 / -3));
//        System.out.println(divide2(-2147483648, -1) == (-2147483648 / -1));
        System.out.println(divide2(2147483647, 1) == (2147483647 / 1));
    }

    public static int divide1(int dividend, int divisor) {
        if (dividend == 0) {
            return 0;
        }

        int result = 0;
        if (dividend > 0 && divisor > 0) {
            while (dividend >= divisor) {
                ++result;
                dividend -= divisor;
            }
        } else if (dividend > 0 && divisor < 0) {
            while (dividend >= 0) {
                ++result;
                dividend += divisor;
            }
            --result;
            result = -result;
        } else if (dividend < 0 && divisor > 0) {
            while (dividend <= 0) {
                ++result;
                if (result == Integer.MAX_VALUE) {
                    return Integer.MIN_VALUE;
                }
                dividend += divisor;
            }
            --result;
            result = -result;
        } else {
            while (dividend <= divisor) {
                ++result;
                if (result == Integer.MAX_VALUE) {
                    return Integer.MAX_VALUE;
                }
                dividend -= divisor;
            }
        }

        return result;
    }

    public static int divide2(int dividend, int divisor) {
        //溢出情况处理
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        if (dividend == 0) {
            return 0;
        }

        //记符号
        boolean isNegatvie = true;
        if ((dividend > 0 && divisor > 0) || (dividend < 0 && divisor < 0)) {
            isNegatvie = false;
        }

        //全部转成符号
        dividend = dividend > 0 ? -dividend : dividend;
        divisor = divisor > 0 ? -divisor : divisor;

        if (dividend > divisor) {
            return 0;
        }

        if (dividend == divisor) {
            return isNegatvie ? -1 : 1;
        }

        int result = 0;
        //反复逼近
        while (dividend <= divisor) {

            //利用2^x方式
            int tmpResult = 1;
            int tmpDivisor = divisor;
            //每次都从除数开始倍增
            while (dividend - tmpDivisor <= tmpDivisor) {
                int newDivisor = tmpDivisor << 1;
                //如何判断左移溢出问题？
                tmpDivisor = newDivisor;
                tmpResult += tmpResult;
            }

            dividend -= tmpDivisor; //每次都减去最逼近的那个数
            result += tmpResult;
        }
        return isNegatvie ? -result : result;
    }


}
