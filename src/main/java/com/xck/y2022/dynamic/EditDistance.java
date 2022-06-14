package com.xck.y2022.dynamic;

/**
 * 编辑距离
 *
 * @author xuchengkun
 * @date 2022/06/14 16:09
 **/
public class EditDistance {

    public static void main(String[] args) {
        System.out.println(minDistance("a", "b") == 1);
        System.out.println(minDistance("horse", "ros") == 3);
        System.out.println(minDistance("intention", "execution") == 5);
    }

    public static int minDistance(String word1, String word2) {
        if (word1.length() == 0) {
            return word2.length();
        }

        if (word2.length() == 0) {
            return word1.length();
        }

        // dp[i][j]表示word的第i个字符和word的第j个字符最短编辑距离
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];
        for (int i = 1; i < word1.length() + 1; i++) {
            dp[i][0] = i;
        }
        for (int i = 1; i < word2.length() + 1; i++) {
            dp[0][i] = i;
        }

        for (int i = 1; i < word1.length() + 1; i++) {
            char c = word1.charAt(i - 1);
            for (int j = 1; j < word2.length() + 1; j++) {
                // 如果word[i] == word[j]，这表示这一步无需操作，直接参考 dp[i - 1][j - 1]
                if (c == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 取最小值
                    // word1的第i个和word2的第j-1个编辑距离，最后插入word2的第j个字符即可
                    // word1的第i-1个和word2的第j个编辑距离，最后插入word1的第j个字符即可
                    // word1的第i-1个和word2的第j-1个编辑距离，最后一个字符修改即可
                    dp[i][j] = Math.min(Math.min(dp[i][j - 1], dp[i - 1][j]), dp[i - 1][j - 1]) + 1;
                }
            }
        }

        return dp[word1.length()][word2.length()];
    }
}
