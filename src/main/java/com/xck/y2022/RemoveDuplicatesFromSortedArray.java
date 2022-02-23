package com.xck.y2022;

/**
 * 删除有序数组中的重复项
 *
 * 关键词，已经排序，将重复项放到后面就行了
 *
 * @author xuchengkun
 * @date 2022/02/23 09:16
 **/
public class RemoveDuplicatesFromSortedArray {

    public static void main(String[] args) {
        int[] arr1 = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        System.out.println(removeDuplicates1(arr1) + " " + print(arr1));
    }

    public static String print(int[] arr){
        String s = "";
        for (int i = 0; i < arr.length - 1; i++) {
            s += arr[i];
            s += ",";
        }
        s += arr[arr.length-1];
        return s;
    }

    public static int removeDuplicates1(int[] nums) {
        if (nums.length < 2) {
            return nums.length;
        }

        int noRepeatEnd = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[noRepeatEnd]) {
                continue;
            }

            ++noRepeatEnd;
            if (noRepeatEnd < i) {
                int tmp = nums[noRepeatEnd];
                nums[noRepeatEnd] = nums[i];
                nums[i] = tmp;
            }
        }

        return noRepeatEnd + 1;
    }
}
