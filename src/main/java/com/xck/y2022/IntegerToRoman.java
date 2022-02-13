package com.xck.y2022;

import java.util.HashMap;
import java.util.Map;

/**
 * 数字转罗马数
 *
 * DFA敏感词算法构造查找
 *
 * @author xuchengkun
 * @date 2022/02/12 09:01
 **/
public class IntegerToRoman {

    public static void main(String[] args) {
        System.out.println(intToRoman1(2));
        System.out.println(intToRoman1(12));
        System.out.println(intToRoman1(27));
        System.out.println(intToRoman1(1994));
    }

    static Map<Integer, String> three2RomanMap = new HashMap<Integer, String>();
    static Map<Integer, String> two2RomanMap = new HashMap<Integer, String>();
    static Map<Integer, String> one2RomanMap = new HashMap<Integer, String>();
    static Map<Integer, String>[] number2RomanArr = new Map[]{
            three2RomanMap, two2RomanMap, one2RomanMap
    };

    static {
        three2RomanMap.put(9, "CM");
        three2RomanMap.put(5, "D");
        three2RomanMap.put(4, "CD");
        three2RomanMap.put(1, "C");

        two2RomanMap.put(9, "XC");
        two2RomanMap.put(5, "L");
        two2RomanMap.put(4, "XL");
        two2RomanMap.put(1, "X");

        one2RomanMap.put(9, "IX");
        one2RomanMap.put(5, "V");
        one2RomanMap.put(4, "IV");
        one2RomanMap.put(1, "I");
    }

    public static String intToRoman1(int num) {
        StringBuilder sb = new StringBuilder();

        //预处理
        int n = num;
        int[] iArr = new int[4];
        for (int i = 3; i >= 0; i--) {
            iArr[i] = n % 10;
            n = n / 10;
            if (n == 0) {
                break;
            }
        }

        while (iArr[0] != 0) {
            iArr[0] = iArr[0] - 1;
            sb.append("M");
        }

        for (int i = 1; i < iArr.length; i++) {
            if (iArr[i] == 0) continue;
            Map<Integer, String> map = number2RomanArr[i - 1];
            if (iArr[i] == 9) {
                sb.append(map.get(9));
                continue;
            }
            while (iArr[i] != 0) {
                int t = 1;
                if (iArr[i] >= 5) {
                    t = 5;
                    iArr[i] = iArr[i] - t;
                    sb.append(map.get(t));
                } else if (iArr[i] >= 4) {
                    t = 4;
                    iArr[i] = iArr[i] - t;
                    sb.append(map.get(t));
                } else {
                    for (int j = 0; j < iArr[i]; j++) {
                        sb.append(map.get(t));
                    }
                    break;
                }

            }
        }
        return sb.toString();
    }
}
