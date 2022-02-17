package com.xck.y2022;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 电话号码的字母组合
 *
 * 1. 分治法
 * 2. DFS回溯
 * 3. BFS搜索
 *
 * @author xuchengkun
 * @date 2022/02/15 16:46
 **/
public class LetterCombinationsOfAPhoneNumber {

    public static void main(String[] args) {
        System.out.println(letterCombinations("23"));
        System.out.println(letterCombinations("234"));
        System.out.println(letterCombinations("3649"));

        System.out.println(letterCombinationsBFS("23"));
        System.out.println(letterCombinationsBFS("234"));
        System.out.println(letterCombinationsBFS("3649"));
    }

    public static Map<Character, String[]> phoneNumber2LettersMap = new HashMap<Character, String[]>() {
        {
            put('2', new String[]{"a", "b", "c"});
            put('3', new String[]{"d", "e", "f"});
            put('4', new String[]{"g", "h", "i"});
            put('5', new String[]{"j", "k", "l"});
            put('6', new String[]{"m", "n", "o"});
            put('7', new String[]{"p", "q", "r", "s"});
            put('8', new String[]{"t", "u", "v"});
            put('9', new String[]{"w", "x", "y", "z"});
        }
    };

    //分治法，递归解决
    public static List<String> letterCombinations(String digits) {
        if (digits.length() == 0) {
            return new ArrayList<String>();
        }

        if (digits.length() == 1) {
            String[] arr = phoneNumber2LettersMap.get(digits.charAt(0));
            return new ArrayList<String>(Arrays.asList(arr));
        }

        int mid = digits.length() / 2;
        String left = digits.substring(0, mid);
        String right = digits.substring(mid, digits.length());
        return mergeTwoSet(letterCombinations(left), letterCombinations(right));
    }

    public static List<String> mergeTwoSet(List<String> left, List<String> right) {
        if (left.isEmpty()) {
            return right;
        }

        if (right.isEmpty()) {
            return left;
        }

        List<String> result = new ArrayList<String>(left.size() * right.size());

        for (String l : left) {
            for (String r : right) {
                StringBuilder sb = new StringBuilder();
                result.add(sb.append(l).append(r).toString());
            }
        }

        return result;
    }

    public static List<String> letterCombinationsDFS(String digits) {
        if (digits.length() == 0) {
            return new ArrayList<String>();
        }

        if (digits.length() == 1) {
            String[] arr = phoneNumber2LettersMap.get(digits.charAt(0));
            return new ArrayList<String>(Arrays.asList(arr));
        }

        List<String> result = new ArrayList<String>();

        backtrack(result, 0, digits, new StringBuilder());

        return result;
    }

    /**
     * 回溯递归子函数
     * @param result 结果列表
     * @param index 当前遍历到哪个数字集合
     * @param digits 输入字符串
     * @param sb 回溯用的
     */
    public static void backtrack(List<String> result, int index, String digits, StringBuilder sb) {
        if (digits.length() == index) {
            result.add(sb.toString());
            return;
        }

        String[] arr = phoneNumber2LettersMap.get(digits.charAt(index));
        for (int i = 0; i < arr.length; i++) {
            //选择一个，继续往下
            sb.append(arr[i]);
            backtrack(result, index + 1, digits, sb);
            //回溯回来，先去掉上个拼接的，然后继续循环
            sb.deleteCharAt(index);
        }
    }

    public static List<String> letterCombinationsBFS(String digits) {
        if (digits.length() == 0) {
            return new ArrayList<String>();
        }

        String[] arr = phoneNumber2LettersMap.get(digits.charAt(0));
        if (digits.length() == 1) {
            return new ArrayList<String>(Arrays.asList(arr));
        }

        List<String> result = new ArrayList<String>();
        Queue<String> queue = new LinkedBlockingQueue<String>();
        for (int i = 0; i < arr.length; i++) {
            queue.offer(arr[i]);
        }

        for (int i = 1; i < digits.length(); i++) {
            arr = phoneNumber2LettersMap.get(digits.charAt(i));
            int len = queue.size(); //当前有多少节点需要进一步搜索
            //取出叠加
            for (int j = 0; j < len; j++) {
                String str = queue.poll();
                for (int k = 0; k < arr.length; k++) {
                    String s = str + arr[k];
                    if (s.length() == digits.length()) {
                        result.add(s);
                    } else {
                        queue.offer(str + arr[k]);
                    }
                }
            }
        }

        return result;
    }


}
