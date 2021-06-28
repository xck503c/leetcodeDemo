package com.xck.y2021;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname TowSum
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
 * 一共5种解法：
 * 1. 暴力解法；
 * 2. 重排序+双指针解法；
 * 3. 双指针+二分解法；
 * 4. Map解法；
 * 5. 暴力解法优化版本；速度最快，最优方案
 *
 * @Description TODO
 * @Date 2021/6/21 21:23
 * @Created by xck503c
 */
public class TowSum {

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

        int[] nums6 = new int[]{-1, 0, 2, 3, 8};
        int target6 = -1;
        twoSum(nums6, target6);
    }

    public static void twoSum(int[] nums, int target){
        int[] result1 = twoSum1(nums, target);
        int[] result11 = twoSum11(nums, target);
        int[] result2 = twoSum2(nums, target);
        int[] result3 = twoSum3(nums, target);

        print(result1);
        print(result11);
        print(result2);
        print(result3);
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
     * 用时3ms，35.39%；38.3 MB，93.87%
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
            int sum = copyArr[start] + copyArr[end];
            if (sum > target) {
                end--;
            }else if (sum < target) {
                start++;
            }else {
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
            }else if(!secondFlag && value2 == nums[i]){
                result[count++] = i;
                secondFlag = true;
            }
        }

        return result;
    }

    /**
     * 解法1的优化1：之前采用的是遍历方法，现在改用二分查找：
     * 1. 找到最小的一个end，使得x[start]+x[end]>target
     * 2. 找到最大的一个start，使得x[start]+x[end]<target
     * 3. 以此类推
     *
     * 用时3ms，35.97%；38.5 MB，72.53%
     * 用时2ms，49.72%；38.5 MB，70.54%
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum11(int[] nums, int target){
        int[] copyArr = new int[nums.length];
        System.arraycopy(nums, 0, copyArr, 0, nums.length);
        Arrays.sort(copyArr);
        int start = 0, end = copyArr.length-1;
        int value1 = 0, value2 = 0;

        while (start < end) {
            if (copyArr[start] + copyArr[end] > target) {
                int index = binarySearch(copyArr, start+1, end-1
                        , target - copyArr[start], true);
                if (index == -1 || index == end) end--; //找不到
                else end = index;
            }
            if (copyArr[start] + copyArr[end] < target) {
                int index = binarySearch(copyArr, start+1, end-1
                        , target - copyArr[end], false);
                if (index == -1 || index == start) start++;
                else start = index;
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

    /**
     * 解法3：是否有种不排序就可以的算法(思路惊艳)，看到下面，每次遍历都有2次判断，i+j和len-i-1+j两个
     * 相比暴力破解多了少遍历一半，n^2==>n/2*n
     *
     * 简单来说：取头尾数字，j遍历判断，是否为目标数字
     *
     * 如果加上下面的排序代码，耗时高达3ms，说明耗时并不是最好的选择
     * int[] copyArr = new int[nums.length];
     * System.arraycopy(nums, 0, copyArr, 0, nums.length);
     * Arrays.sort(copyArr);
     *
     * 用时0ms，100%；38.6 MB，56.72%
     *
     * 还可以再优化
     * int j=i+1; j < nums.length-1; j++ 变成
     * int j=i+1; j < nums.length-i; j++
     *
     * if((nums[nums.length-i-1] + nums[i]) == target){
     *  return new int[]{i, nums.length-i-1};
     * }
     *
     * 用时在0ms和1ms之间晃荡，优化反而下降
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum3(int[] nums, int target){
        for (int i = 0; i < nums.length/2; i++) {
            for (int j=i+1; j < nums.length; j++){
                if (nums[i] + nums[j] == target){
                    return new int[]{i, j};
                }
                if((nums[nums.length-i-1] + nums[j]) == target){
                    return new int[]{j, nums.length-i-1};
                }
            }
        }
        return null;
    }

    /**
     * 找到一个符合条件(>=target，<=target)最接近的索引
     *
     * 例如：1, 6, 10, 11找9, low=0, high=3
     * 1. 求出mid=1, 6<9, low = 2, high = 3
     * 2. 求出mid=2, 10>9, low = 2, hight = 1, 停止查找
     * 取low或者hight都可以
     *
     * @param nums 数组
     * @param low 寻找起始点
     * @param high 寻找的最终点
     * @param target 需要对比的数字
     * @param isLarge 是>=target还是<=target
     * @return
     */
    public static int binarySearch(int[] nums, int low, int high, int target, boolean isLarge){
        if (low >= high) return -1;

        int mid = 0;
        while (low < high){
            mid = (low+high)/2;
            if (nums[mid] > target) high = mid -1;
            else if (nums[mid] < target) low = mid+1;
            else return mid;
        }
        //未能找到，若是要找大于，则返回后一个，否则返回前一个
        if (isLarge){
            return nums[low] > target ? low : low+1;
        }
        return nums[low] < target ? low : low-1;
    }

    public static void print(int[] num){
        if (num == null || num.length == 0) {
            System.out.println("result is null");
            return;
        }
        System.out.println(num[0] + " " + num[1]);
    }
}
