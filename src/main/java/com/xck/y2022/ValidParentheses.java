package com.xck.y2022;

import java.util.*;

/**
 * 有效的括号
 *
 * @author xuchengkun
 * @date 2022/02/17 15:32
 **/
public class ValidParentheses {

    public static void main(String[] args) {
        System.out.println(isValid1("([)]")); //false
        System.out.println(isValid1("[()]")); //true
        System.out.println(isValid1("[()[[[[[")); //false
    }

    public static Map<Character, Character> leftBracket = new HashMap<Character, Character>() {{
        put('(', ')');
        put('{', '}');
        put('[', ']');
    }};

    public static boolean isValid1(String s) {
        if (s.length() == 1) return false;

        try {
            Deque<Character> stack = new LinkedList<>();
            char[] arr = s.toCharArray();
            for (int i = 0; i < arr.length; i++) {
                Character rightBucket = leftBracket.get(arr[i]);
                if (rightBucket != null) {
                    stack.push(rightBucket);
                } else {
                    //若栈空的，说明不配对 ，这里加上变成2ms，换句话说这个判断对测试造成很大影响
                    rightBucket = stack.pop();
                    if (rightBucket == null || rightBucket != arr[i]) {
                        return false;
                    }
                }
            }

            if (stack.size() > 0) return false;
        } catch (NoSuchElementException e) {
            return false;
        }

        return true;
    }

    //数组实现栈，耗时差不多
    public static boolean isValid2(String s) {
        if (s.length() == 1) return false;

        char[] stack = new char[s.length()];
        int top = 0;

        char[] arr = s.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            Character rightBucket = leftBracket.get(arr[i]);
            if (rightBucket != null) {
                stack[top] = rightBucket;
                ++top;
            } else {
                //若栈空的，说明不配对 ，这里加上变成2ms，换句话说这个判断对测试造成很大影响
                if (top > 0 && stack[top - 1] == arr[i]) {
                    --top;
                } else {
                    return false;
                }
            }
        }

        if (top > 0) return false;

        return true;
    }
}
