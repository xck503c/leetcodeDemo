package com.xck.y2021;

import java.util.HashMap;

/**
 * leetcode 无重复字符的最长子串：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
 *
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * 示例1：
 * 输入：s = "abcabcbb"
 * 输出：3
 * 解释：因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 *
 * 示例2：
 * 输入：s = "bbbbb"
 * 输出：1
 * 解释：因为无重复字符的最长子串是 "b"，所以其长度为 1。
 *
 * 示例3：
 * 输入：s = "pwwkew"
 * 输出：3
 * 解释：因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 *
 * 示例4：
 * 输入：s = ""
 * 输出：0
 *
 * 提示：
 * 1. 0 <= s.length <= 5 * 10^4
 * 2. s 由英文字母、数字、符号和空格组成
 *
 * 审题：
 * 1. 子串而不是子序列，必须是连续的
 * 2. 一旦子串出现重复字符，则不能继续计数
 * 3. 子串长度可以为0
 * 4. 问题是我要怎么判断是不是重复的
 *
 * @Classname LongestSubstringWithoutRepeatingChar
 * @Date 2021/06/22 15:15
 * @Created by xck503c
 **/
public class LongestSubstringWithoutRepeatingChar {

    public static void main(String[] args) {
        lengthOfLongestSubstring("abcabcbb");
        lengthOfLongestSubstring("bbbbb");
        lengthOfLongestSubstring("pwwkew");
        lengthOfLongestSubstring("");
        lengthOfLongestSubstring("fsdfas");
    }

    public static void lengthOfLongestSubstring(String s) {
        int result = lengthOfLongestSubstring1(s);
        System.out.println(result);

        result = lengthOfLongestSubstring12(s);
        System.out.println(result);

        result = lengthOfLongestSubstring13(s);
        System.out.println(result);

        result = lengthOfLongestSubstring14(s);
        System.out.println(result);
    }

    /**
     * 解法1：先来暴力破解流试试水
     *
     * 思路采用双循环+回溯法
     * start：子串的开头
     * end：子串的结尾
     * max：最长的子串
     * i：为当前判断的字符，i == end+1
     *
     * 回溯的时候从end开始到start结束，若出现一样的索引为j，
     * 则start = end = j+1，i = end+1
     *
     * 用时: 384ms，击败5.01%
     * 内存消耗：38.6MB，击败48.63%
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring1(String s) {
        if (s.length() == 0) return 0;

        int max = 0;
        int start = 0, end = 0;
        for (int i=1; i < s.length(); ++i){
            char curChar = s.charAt(i);
            int find = -1; //主用于判断是否++end
            for (int j = end; j >= start; --j){
                if (s.charAt(j) == curChar){
                    int count = end - start + 1;
                    max = count > max ? count : max;
                    end = start = j+1;
                    i = end; //还有用++i，所以这里不+1
                    find = j+1;
                    break;
                }
            }
            if (find >= 0){
                continue;
            }
            ++end;
        }
        int count = end - start + 1;
        return count > max ? count : max;
    }

    /**
     * 解法1优化1：优化回溯效率
     *
     * 判断需要采用回溯法，而字符串是有数字，字母，空格构成，所以长度并会无限长，
     * 我们不能采用Set集合来存储，因为我们需要重复字符的索引在哪里。我们可以用Map
     * 来记录。
     *
     * 用时: 123ms，击败9.76%
     * 内存消耗：39.1MB，击败10.2%
     *
     * 耗时缩短3倍
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring12(String s) {
        if (s.length() == 0) return 0;

        int max = 0;
        int start = 0, end = 0;
        HashMap<Character, Integer> repeatMap = new HashMap<Character, Integer>();
        repeatMap.put(s.charAt(0), 0);
        for (int i=1; i < s.length(); ++i){
            char curChar = s.charAt(i);
            Integer index = repeatMap.get(curChar);
            if (index != null){
                repeatMap.clear(); //清空
                int count = end - start + 1;
                max = count > max ? count : max;
                i = end = start = index+1;
                repeatMap.put(s.charAt(i), i); //放入第一个
                continue;
            }
            repeatMap.put(curChar, i);
            ++end;
        }
        int count = end - start + 1;
        return count > max ? count : max;
    }

    /**
     * 解法1优化2：出现重复的时候不重置i，不基于优化1之上
     *
     * 我们其实没有必要重置i，因为如果start~end中的j和i重复了，那么我们将start置为
     * j+1就可以了，++end往后移动一格
     *
     * 用时: 12ms，击败23.07%
     * 内存消耗：38.5MB，击败57.81%
     *
     * 耗时比优化1再缩短10倍
     *
     * 想了想还有优化空间，可以把优化1的思路拿过来，区别就是要多一个判断
     * Integer index = repeatMap.get(curChar);
     * if (index != null && start <= index){}
     * 而且也不需要清空
     *
     * 用时: 5ms，击败87.37%
     * 内存消耗：38.6MB，击败50.70%
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring13(String s) {
        if (s.length() == 0) return 0;

        int max = 0;
        int start = 0, end = 0;
        HashMap<Character, Integer> repeatMap = new HashMap<Character, Integer>();
        repeatMap.put(s.charAt(0), 0);
        for (int i=1; i < s.length(); ++i, ++end){
            char curChar = s.charAt(i);
            Integer index = repeatMap.get(curChar);
            if (index != null && start <= index){ //只判断在范围内的
                int count = end - start + 1;
                max = count > max ? count : max;
                start = index+1; //只要重置start就可以了，跳过重复字符
            }
            repeatMap.put(curChar, i);
        }
        int count = end - start + 1;
        return count > max ? count : max;
    }

    /**
     * 解法1优化3：基于优化2优化，map替换为数组优化
     *
     * 换成数组映射，最长不过128
     *
     * 用时: 2ms，击败100%
     * 内存消耗：38.6MB，击败47.88%%
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring14(String s) {
        if (s.length() == 0) return 0;

        int max = 0;
        int start = 0, end = 0;
        int[] repeatMap = new int[128];
        for (int i = 0; i < repeatMap.length; i++) {
            repeatMap[i] = -1;
        }

        repeatMap[s.charAt(0)] = 0;
        for (int i=1; i < s.length(); ++i, ++end){
            char curChar = s.charAt(i);
            int index = repeatMap[curChar];
            if (start <= index){
                int count = end - start + 1;
                max = count > max ? count : max;
                start = index+1; //只要重置start就可以了，跳过重复字符
            }
            repeatMap[s.charAt(i)] = i;
        }
        int count = end - start + 1;
        return count > max ? count : max;
    }
}
