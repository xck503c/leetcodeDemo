package com.xck.y2022.dynamic;

/**
 * 买卖股票的最佳时机
 *
 * @author xuchengkun
 * @date 2022/06/13 10:23
 **/
public class BestTimeToBuyAndSellStockII {

    public static void main(String[] args) {
        System.out.println(new BestTimeToBuyAndSellStockII().maxProfit_dp(new int[]{7, 1, 5, 3, 6, 4}));
        System.out.println(new BestTimeToBuyAndSellStockII().maxProfit_dp(new int[]{1, 2, 3, 4, 5}));
        System.out.println(new BestTimeToBuyAndSellStockII().maxProfit_dp(new int[]{7, 6, 4, 3, 1}));
        System.out.println(new BestTimeToBuyAndSellStockII().maxProfit_dp(new int[]{7, 6, 4, 3, 1}));
    }

    public static int maxProfit_all(int[] prices) {
        if (prices.length == 1) {
            return 0;
        }

        int max = Math.max(maxProfit_all_dfs(prices, 0, -prices[0], 1)
                , maxProfit_all_dfs(prices, 1, 0, 1));
        return Math.max(max, 0);
    }

    /**
     * dfs搜索
     *
     * @param prices
     * @param status   0-买入，1-卖出
     * @param income   收益
     * @param dayIndex 当前天数
     * @return
     */
    public static int maxProfit_all_dfs(int[] prices, int status, int income, int dayIndex) {
        if (dayIndex == prices.length) {
            return income;
        }

        if (status == 0) { //持有状态，要么跳过，要么卖出
            return Math.max(maxProfit_all_dfs(prices, 0, income, dayIndex + 1)
                    , maxProfit_all_dfs(prices, 1, income + prices[dayIndex], dayIndex + 1));
        } else { //不持有状态，要么跳过，要么买入
            return Math.max(maxProfit_all_dfs(prices, 1, income, dayIndex + 1)
                    , maxProfit_all_dfs(prices, 0, income - prices[dayIndex], dayIndex + 1));
        }
    }

    /**
     * 3ms，击败24.96%
     * 41.2MB，击败61.78%
     * @param prices
     * @return
     */
    public static int maxProfit_dp(int[] prices) {
        if (prices.length == 1) {
            return 0;
        }

        int[][] dp = new int[prices.length][2];
        dp[0][0] = -prices[0];
        dp[0][1] = 0;

        for (int i = 1; i < prices.length; i++) {
            //持股：今天买进，前面不持股；前面持股，今天跳过
            dp[i][0] = Math.max(dp[i - 1][1] - prices[i], dp[i - 1][0]);
            //不持股：今天卖出，签名持股；前面不持股，今天跳过
            dp[i][1] = Math.max(dp[i - 1][0] + prices[i], dp[i - 1][1]);
        }

        return dp[prices.length - 1][1];
    }

    public static int maxProfit_dp_1(int[] prices) {
        if (prices.length == 1) {
            return 0;
        }

        int hold = -prices[0];
        int noHold = 0;

        for (int i = 1; i < prices.length; i++) {
            //持股：今天买进，前面不持股；前面持股，今天跳过
            hold = Math.max(noHold - prices[i], hold);
            //不持股：今天卖出，前面持股；前面不持股，今天跳过
            noHold = Math.max(hold + prices[i], noHold);
        }

        return noHold;
    }
}
