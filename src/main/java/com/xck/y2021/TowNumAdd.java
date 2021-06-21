package com.xck.y2021;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname TowNumAdd
 *
 * leetcode 上两数之和：https://leetcode-cn.com/problems/two-sum
 *
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 * 你可以按任意顺序返回答案。
 *
 * 示例1：
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 *
 * 示例2：
 * 输入：nums = [3,2,4], target = 6
 * 输出：[1,2]
 *
 * 示例3：
 * 输入：nums = [3,3], target = 6
 * 输出：[0,1]
 *
 * @Description TODO
 * @Date 2021/6/21 21:23
 * @Created by xck503c
 */
public class TowNumAdd {

    public static void main(String[] args) {
        int[] nums1 = new int[]{2,7,11,15};
        int target1 = 9;
        twoSum(nums1, target1);

        int[] nums2 = new int[]{3,2,4};
        int target2 = 6;
        twoSum(nums2, target2);

        int[] nums3 = new int[]{3, 3};
        int target3 = 6;
        twoSum(nums3, target3);

        //该示例运行错误：虽然找到2个求和数字，但是在找索引的时候未判断是否获取过，导致第二个未能找到
        int[] nums4 = new int[]{2, 5, 5, 11};
        int target4 = 10;
        twoSum(nums4, target4);

        //该示例运行错误：还是因为找索引过程判断不严谨，
        int[] nums5 = new int[]{-1, -2, -3, -4, -5};
        int target5 = -8;
        twoSum(nums5, target5);
    }

    public static void twoSum(int[] nums, int target){
        int[] result1 = twoSum1(nums, target);
        int[] result2 = twoSum2(nums, target);

        print(result1);
        print(result2);
    }

    /**
     * 解法1：双指针法，先将数组进行排序，然后头尾指针向中间移动：
     * 1. 若x[start]+x[end]>target，则end--
     * 2. 若x[start]+x[end]<target，则start++
     * 3. 若x[start]+x[end]==target，则记录x[start]，x[end]值
     * 当start < end，表示未能找到满足题意的结果
     * 否则，表示找到，根据x[start]，x[end]值去寻找索引，寻找的时候我们只需要遍历一次
     *
     * 用时4ms，31.77%；38.6 MB，45.39%
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum1(int[] nums, int target) {
        int[] copyArr = new int[nums.length];
        System.arraycopy(nums, 0, copyArr, 0, nums.length);
        Arrays.sort(copyArr);
        int start = 0, end = copyArr.length-1;
        int value1 = 0, value2 = 0;

        while (start < end) {
            if (copyArr[start] + copyArr[end] > target) {
                end--;
                continue;
            }
            if (copyArr[start] + copyArr[end] < target) {
                start++;
                continue;
            }
            if (copyArr[start] + copyArr[end] == target) {
                value1 = copyArr[start];
                value2 = copyArr[end];
                break;
            }
        }

        if (start >= end){
            return null;
        }

        int[] result = new int[2];
        boolean firstFlag = false, secondFlag = false;
        for (int i=0, count = 0; i<nums.length && count<2; i++){
            if (!firstFlag && value1 == nums[i]){
                result[count++] = i;
                firstFlag = true;
                continue;
            }
            if (!secondFlag && value2 == nums[i]){
                result[count++] = i;
                secondFlag = true;
            }
        }

        return result;
    }

    /**
     * 解法2：利用Map映射记录遍历过的值，便于后续查找目标值(target-cur[i])
     *
     * 用时2ms，49.19%；38.5 MB，78.24%
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> tmp = new HashMap<Integer, Integer>();
        for (int i=0; i<nums.length; i++){
            Integer num1 = target - nums[i]; //计算目标值
            Integer num2 = tmp.get(num1); //查找是否已经遍历过
            if (num2 != null){ //遍历过
                int[] result = new int[2];
                result[0] = i;
                result[1] = tmp.get(num1);
                return result;
            }
            tmp.put(nums[i], i);
        }
        return null;
    }

    public static void print(int[] num){
        if (num == null || num.length == 0) {
            System.out.println("result is null");
            return;
        }
        System.out.println(num[0] + " " + num[1]);
    }
}
