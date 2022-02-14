package com.xck.y2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 3数之和
 * <p>
 * a+b+c=0
 * 找出abc，且三个数构成的元组不重复，就是不能重复出现-1,0,1，任何顺序都不行
 *
 * @author xuchengkun
 * @date 2022/02/14 09:00
 **/
public class ThreeSum {

    public static void main(String[] args) {
        System.out.println(threeSum1(new int[]{-1, 0, 1, 2, -1, -4}));
        System.out.println(threeSum1(new int[]{-1, -1, 0, 2, 2, 2, 2}));
        System.out.println(threeSum1(new int[]{1, -1, -1, 0}));
        System.out.println(threeSum1(new int[]{-2, 0, 0, 2, 2}));
    }

    //这个耗时维持在27ms左右
    public static List<List<Integer>> threeSum1(int[] nums) {
        if (nums.length < 3) {
            return Collections.emptyList();
        }

        List<List<Integer>> list = new ArrayList<List<Integer>>();
        if (nums.length == 3 && nums[0] + nums[1] + nums[2] == 0) {
            List<Integer> inner = new ArrayList<Integer>(3);
            inner.add(nums[0]);
            inner.add(nums[1]);
            inner.add(nums[2]);
            list.add(inner);
            return list;
        }

        Arrays.sort(nums);

        int a;
        for (int i = 0; i < nums.length; i++) {
            //第一个固定不用判断
            if (i == 0 || nums[i - 1] != nums[i]) {
                a = nums[i];
                int target = -a;
                for (int j = i + 1, k = nums.length - 1; j < k; ) {
                    if (j > i + 1 && nums[j] == nums[j - 1]) {
                        ++j;
                        continue;
                    }

                    int sum = nums[j] + nums[k];
                    if (sum > target) {
                        --k;
                    } else if (sum < target) {
                        ++j;
                        continue;
                    } else {
                        List<Integer> inner = new ArrayList<Integer>(3);
                        inner.add(a);
                        inner.add(nums[j]);
                        inner.add(nums[k]);
                        list.add(inner);
                        ++j;
                        --k;
                    }
                    while (j < k && nums[k] == nums[k + 1]) {
                        --k;
                    }
                }
            }

        }
        return list;
    }

    //这个耗时维持在22ms左右
    public static List<List<Integer>> threeSum2(int[] nums) {
        if (nums.length < 3) {
            return Collections.emptyList();
        }

        List<List<Integer>> list = new ArrayList<List<Integer>>();
        if (nums.length == 3 && nums[0] + nums[1] + nums[2] == 0) {
            List<Integer> inner = new ArrayList<Integer>(3);
            inner.add(nums[0]);
            inner.add(nums[1]);
            inner.add(nums[2]);
            list.add(inner);
            return list;
        }

        Arrays.sort(nums);

        int a;
        for (int i = 0; i < nums.length; i++) {
            //第一个固定不用判断
            if (i == 0 || nums[i - 1] != nums[i]) {
                a = nums[i];
                int target = -a;
                for (int j = i + 1, k = nums.length - 1; j < k; ++j) {
                    //如果和上一个一样就跳过
                    if (j > i + 1 && nums[j] == nums[j - 1]) {
                        continue;
                    }

                    while (j < k && nums[j] + nums[k] > target) {
                        --k;
                    }

                    if (j == k) break;

                    if (nums[j] + nums[k] == target) {
                        List<Integer> inner = new ArrayList<Integer>(3);
                        inner.add(a);
                        inner.add(nums[j]);
                        inner.add(nums[k]);
                        list.add(inner);
                        --k;
                    }
                }
            }

        }
        return list;
    }
}
