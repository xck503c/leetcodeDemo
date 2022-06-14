package com.xck.y2022.dynamic;

/**
 * 买卖股票的最佳时机IV
 *
 * @author xuchengkun
 * @date 2022/06/13 10:23
 **/
public class BestTimeToBuyAndSellStockIV {

    public static void main(String[] args) {
        System.out.println(new BestTimeToBuyAndSellStockIV().maxProfit_dp(2, new int[]{3, 3, 5, 0, 0, 3, 1, 4}));
    }

    /**
     * 1ms，击败99.50%%
     * 39.5MB，击败74.69%
     *
     * @param prices
     * @return
     */
    public static int maxProfit_dp(int k, int[] prices) {
        if (k == 0) return 0;
        if (prices.length == 0) return 0;

        //前面预留一个位置
        int[] buy = new int[k + 1];
        for (int j = 1; j <= k; j++) {
            buy[j] = -prices[0];
        }
        int[] sell = new int[k + 1];
        for (int i = 1; i < prices.length; i++) {
            //从第1次开始
            for (int j = 1; j <= k; j++) {
                //第j次买=max(第j次跳过，第j-1次卖 - 当前价格)
                buy[j] = Math.max(buy[j], sell[j - 1] - prices[i]);
                //第j次卖=max(第j次卖，第j次买 + 当前价格)
                sell[j] = Math.max(sell[j], buy[j] + prices[i]);
            }
        }

        int max = 0;
        for (int j = 1; j <= k; j++) {
            max = Math.max(sell[j], max);
        }
        return max;
    }
}
