package com.xck.y2022;

import java.util.HashMap;
import java.util.Map;

/**
 * 罗马数字转整数
 *
 * @author xuchengkun
 * @date 2022/02/12 23:11
 **/
public class RomanToInteger {

    public static void main(String[] args) {
        System.out.println(romanToInt1("MCMXCIV"));
        System.out.println(romanToInt1("LVIII"));
        System.out.println(romanToInt1("IX"));
        System.out.println(romanToInt1("IV"));
    }

    static Map<Character, Object> romanMap = new HashMap<Character, Object>();

    static {
        String[] romanArr = new String[]{
                "M", "MM", "MMM",
                "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM",
                "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC",
                "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"
        };
        int[] valueArr = new int[]{
                1000, 2000, 3000,
                100, 200, 300, 400, 500, 600, 700, 800, 900,
                10, 20, 30, 40, 50, 60, 70, 80, 90,
                1, 2, 3, 4, 5, 6, 7, 8, 9
        };
        for (int i = 0; i < romanArr.length; i++) {
            initDFA(romanArr[i], valueArr[i]);
        }
    }

    public static void initDFA(String s, int value) {
        Map<Character, Object> tmp = romanMap;
        for (int i = 0; i < s.length(); i++) {
            Object map = tmp.get(s.charAt(i));
            if (map == null) {
                tmp.put(s.charAt(i), map = new HashMap<Character, Object>());
            }
            tmp = (Map<Character, Object>) map;
        }
        tmp.put('A', value);
    }

    public static int romanToInt1(String s) {
        int number = 0;
        char[] arr = s.toCharArray();
        Character c = arr[0];
        int i = 0;
        Map<Character, Object> tmp = romanMap;
        while (true) {
            //查表
            Map<Character, Object> map = (Map<Character, Object>) tmp.get(c);
            //为null说明查不到c这个字符，需要回退j
            if (map == null) {
                number += (Integer) tmp.get('A');
                tmp = romanMap;
                continue;
            }
            //超过长度说明已经找完，可以直接跳出循环
            if (i + 1 >= s.length()) {
                number += (Integer) map.get('A');
                return number;
            }
            c = arr[++i];
            tmp = map; //递推
        }
    }

    //直接copy
    static Map<Character, Integer> symbolValues = new HashMap<Character, Integer>() {{
        put('I', 1);
        put('V', 5);
        put('X', 10);
        put('L', 50);
        put('C', 100);
        put('D', 500);
        put('M', 1000);
    }};

    public static int romanToInt2(String s) {
        int number = 0;
        for (int i = 0; i < s.length(); i++) {
            Character cur = s.charAt(i);
            int curNumber = symbolValues.get(cur);
            Character right = null;
            if (i + 1 < s.length()) {
                right = s.charAt(i+1);
            }
            if (right != null && symbolValues.get(right) > curNumber) {
                ++i;
                number += (symbolValues.get(right) - curNumber);
            } else {
                number += curNumber;
            }
        }
        return number;
    }

    static Map<Character, Integer> symbolValues1 = new HashMap<Character, Integer>() {{
        put('I', 1);
        put('V', 5);
        put('X', 10);
        put('L', 50);
        put('C', 100);
        put('D', 500);
        put('M', 1000);
        put('a', 4);
        put('b', 9);
        put('c', 40);
        put('d', 90);
        put('e', 400);
        put('f', 900);
    }};

    public static int romanToInt3(String s) {
        s = s.replace("IV","a");
        s = s.replace("IX","b");
        s = s.replace("XL","c");
        s = s.replace("XC","d");
        s = s.replace("CD","e");
        s = s.replace("CM","f");

        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            res += symbolValues1.get(s.charAt(i));
        }
        return res;
    }
}
