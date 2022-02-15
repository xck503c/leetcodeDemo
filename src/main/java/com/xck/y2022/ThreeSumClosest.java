package com.xck.y2022;

import java.util.Arrays;

/**
 * 最接近的三数之和，只有一个解
 * <p>
 * 3 <= nums.length <= 1000
 * -1000 <= nums[i] <= 1000
 * -10^4 <= target <= 10^4
 *
 * @author xuchengkun
 * @date 2022/02/14 17:42
 **/
public class ThreeSumClosest {

    public static void main(String[] args) {
        System.out.println(threeSumClosest1(new int[]{-1, 2, 1, -4}, 1));
        System.out.println(threeSumClosest1(new int[]{0, 0, 0, 4}, 1));
        System.out.println(threeSumClosest1(new int[]{1, 1, 1, 1}, 4));
        System.out.println(threeSumClosest1(new int[]{0, 2, 1, -3}, 1));
        System.out.println(threeSumClosest1(new int[]{1, 2, 4, 8, 16, 32, 64, 128}, 82));
    }

    //2022-02-15 6ms 46%
    public static int threeSumClosest1(int[] nums, int target) {

        if (nums.length == 3) {
            return nums[0] + nums[1] + nums[2];
        }

        Arrays.sort(nums);

        int closeNumberDiff = Math.abs(target - (nums[0] + nums[1] + nums[2]));
        int closeNumber = nums[0] + nums[1] + nums[2];
        int a;
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            a = nums[i];
            int j = i + 1, k = nums.length - 1;
            while (j < k) {
                int sum = a + nums[j] + nums[k];
                if (sum == target) {
                    return target;
                }

                if (Math.abs(target - sum) < closeNumberDiff) {
                    closeNumber = sum;
                    closeNumberDiff = Math.abs(target - sum); //这里忘记加上了
                }

                if (sum > target) {
                    --k;
                    while (j < k && nums[k] == nums[k + 1]) { //相同的要跳过
                        --k;
                    }
                } else {
                    ++j;
                    if (j < k && nums[j] == nums[j - 1]) {
                        ++j;
                    }
                }
            }
        }
        return closeNumber;
    }
}
