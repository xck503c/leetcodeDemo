package com.xck.y2022.monotonicStack;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 去除重复字母
 *
 * 去重，并保证相对顺序，同时保证最小字典序
 *
 * @author xuchengkun
 * @date 2022/06/20 08:51
 **/
public class RemoveDuplicateLetters {

    public static void main(String[] args) {
        System.out.println(removeDuplicateLetters("bcabc").equals("abc"));
        System.out.println(removeDuplicateLetters("cbacdcbc").equals("acdb"));
    }

    /**
     * 2ms 81.33%
     * 41.6MB 12.42%
     * @param s
     * @return
     */
    public static String removeDuplicateLetters(String s) {
        if (s.length() == 1) {
            return s;
        }

        //ASCII字符出现计数数组
        int[] count = new int[256];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i)]++;
        }

        //判断该字母是否在栈中存在
        boolean[] isExists = new boolean[256];
        //单调递增栈
        Deque<Character> stack = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            //1. 如果当前不存在，那肯定要push
            //2. 如果当前已经存在，有2种情况
            //(1) 当前<top: 本来需要往前推，不过之前已经是最小就没必要再来一次
            //(2) 当前>top: 不可能出现
            if (!isExists[c]) {
                //1. 如果top > 当前，说明可以往前推，但是如果top后面不会再出现则不能动
                //2. 如果top < 当前，则直接push即可
                while (!stack.isEmpty() && stack.peek() > c) {
                    if (count[stack.peek()] > 0) {
                        isExists[stack.pop()] = false;
                    } else {
                        break;
                    }
                }
                stack.push(c);
                isExists[c] = true;
            }
            //无论在不在都要--count
            count[c]--;
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.removeLast());
        }
        return sb.toString();
    }
}
