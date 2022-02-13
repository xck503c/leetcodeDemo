package com.xck.y2022;

/**
 * 最长公共前缀
 * <p>
 * 简单来说就是给定一组字符串，数组表示。找出最长公共前缀，找不到就返回空字符串
 * <p>
 * 正常人第一个反应就是横向和纵向比较2种，但这题还有另外2种解法：分治法；二分查找
 *
 * @author xuchengkun
 * @date 2022/02/12 11:10
 **/
public class LongestCommonPrefix {

    public static void main(String[] args) {
        System.out.println(longestCommonPrefix3(new String[]{
                "flower", "flow", "flight"
        }));

        System.out.println(longestCommonPrefix3(new String[]{
                "ab", "a"
        }));
    }

    //纵向比较，复杂度O(mn)
    //m - 字符串平均长度，n-字符串数量，最坏情况下每个字符串但字符都会比较
    //空间复杂度O(1)
    public static String longestCommonPrefix1(String[] strs) {
        if (strs.length == 1) return strs[0];

        String first = strs[0];
        int index = 0; //指向比较的第index字符
        for (int i = 0; i < first.length(); i++) {
            char c = first.charAt(i);
            for (int j = 1; j < strs.length; j++) {
                String s = strs[j];
                if (index >= s.length()) {
                    return s;
                }
                //不相同说明比较完了
                if (s.charAt(index) != c) {
                    if (index == 0) return "";
                    return first.substring(0, index);
                }
            }
            ++index;
        }

        return first;
    }

    //分治法
    public static String longestCommonPrefix2(String[] strs) {
        if (strs.length == 1) return strs[0];

        if (strs.length == 2) return commonPrefix(strs[0], strs[1]);

        int mid = strs.length / 2;

        String[] leftArr = new String[mid];
        System.arraycopy(strs, 0, leftArr, 0, mid);

        String[] rightArr = new String[strs.length - mid];
        System.arraycopy(strs, mid, rightArr, 0, rightArr.length);

        return commonPrefix(
                longestCommonPrefix2(leftArr),
                longestCommonPrefix2(rightArr)
        );
    }

    public static String commonPrefix(String s1, String s2) {
        if ("".equals(s1) || "".equals(s2)) return "";

        String maxStr = s1, minStr = s2;
        if (s1.length() < s2.length()) {
            maxStr = s2;
            minStr = s1;
        }
        for (int i = 0; i < minStr.length(); i++) {
            if (minStr.charAt(i) != maxStr.charAt(i)) {
                return minStr.substring(0, i);
            }
        }
        return minStr;
    }

    public static String longestCommonPrefix3(String[] strs) {

        if (strs.length == 1) return strs[0];

        //找出最小长度
        int minLen = strs[0].length();
        for (int i = 1; i < strs.length; i++) {
            minLen = Math.min(minLen, strs[i].length());
            if (minLen == 0) {
                return "";
            }
        }

        //这里如果minLen-1就会有问题
        int low = 0, high = minLen;
        while (low < high) {
            //求出对应索引,1开始
            int mid = (high + low + 1) / 2;
            if (isSameChar(strs, mid)) {
                low = mid;
            } else {
                high = mid - 1;
            }
        }

        return strs[0].substring(0, high);
    }

    //每次判断都要比较mn次
    public static boolean isSameChar(String[] strs, int mid) {
        String first = strs[0];
        for (int i = 1; i < strs.length; i++) {
            String s = strs[i];
            for (int j = 0; j < mid; j++) {
                if (first.charAt(j) != s.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }
}
