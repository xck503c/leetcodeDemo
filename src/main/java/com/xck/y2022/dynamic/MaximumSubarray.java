package com.xck.y2022.dynamic;

/**
 * 最大子数组和
 *
 * @author xuchengkun
 * @date 2022/06/12 23:03
 **/
public class MaximumSubarray {

    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[]{-2,1}));
        System.out.println(maxSubArray(new int[]{-1,-2}));
        System.out.println(maxSubArray(new int[]{2, 1, -3, 4, -1, 2, 1, -5, 4}));
        System.out.println(maxSubArray(new int[]{5, 4, -1, 7, 8}));
    }

    public static int maxSubArray(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }

        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = dp[0] ;
        for (int i = 1; i < nums.length; i++) {
            int sum = nums[i] + dp[i - 1];
            if (sum > nums[i]) {
                dp[i] = sum;
            } else {
                dp[i] = nums[i];
            }
            max = Math.max(dp[i], max);
        }

        return max;
    }
}
