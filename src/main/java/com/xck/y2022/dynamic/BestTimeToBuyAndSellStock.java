package com.xck.y2022.dynamic;

/**
 * 买卖股票的最佳时机
 *
 * @author xuchengkun
 * @date 2022/06/13 10:23
 **/
public class BestTimeToBuyAndSellStock {

    public static void main(String[] args) {
        System.out.println(maxProfit_all(new int[]{7, 1, 5, 3, 6, 4}));
    }

    public static int maxProfit_all(int[] prices) {
        int max = 0;

        for (int i = 0; i < prices.length; i++) {
            int curMax = 0;
            int first = prices[i];
            for (int j = i + 1; j < prices.length; j++) {
                int curPrice = prices[j];
                curMax = Math.max(curMax, curPrice - first);
            }
            max = Math.max(curMax, max);
        }

        return max;
    }

    /**
     * 执行用时：4 ms, 在所有 Java 提交中击败了25.44%的用户
     * 内存消耗：52.5 MB, 在所有 Java 提交中击败了93.16%的用户
     * @param prices
     * @return
     */
    public static int maxProfit_dp(int[] prices) {
        int max = 0;

        int[] dp = new int[prices.length + 1];
        dp[0] = prices[0];
        for (int i = 1; i < prices.length; i++) {
            dp[i] = Math.min(dp[i - 1], prices[i]);
            max = Math.max(max, prices[i] - dp[i]);
        }

        return max;
    }

    public static int maxProfit_one(int[] prices) {
        int max = 0, minPrice = prices[0];

        for (int i = 1; i < prices.length; i++) {
            minPrice = Math.min(minPrice, prices[i]);
            max = Math.max(max, prices[i] - minPrice);
        }

        return max;
    }
}
