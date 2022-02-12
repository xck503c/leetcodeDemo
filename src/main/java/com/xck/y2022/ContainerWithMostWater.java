package com.xck.y2022;

/**
 * 盛最多水的容器
 * <p>
 * 题目：给定一个数组，里面元素表示板的高度
 * 找出2个板使得装水最多。板之间的举例就是数组索引距离
 * <p>
 * 如：[1,8,6,2,5,4,8,3,7]，输出49
 * 如：[4,3,2,1,4]，输出16
 * <p>
 * 假设数组长度位n
 * 2 <= n <= 10^5
 * 0 <= height[i] <= 10^4
 *
 * @author xuchengkun
 * @date 2022/02/11 21:26
 **/
public class ContainerWithMostWater {

    public static void main(String[] args) {
        System.out.println(maxArea2(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
        System.out.println(maxArea2(new int[]{1, 1}));
        System.out.println(maxArea2(new int[]{2, 1, 2}));
        System.out.println(maxArea2(new int[]{4, 3, 2, 1, 4}));
    }

    //暴力破解(这个会超出时间限制)
    public static int maxArea1(int[] height) {
        int maxArea = 0;
        for (int i = 0; i < height.length; i++) {
            int leftHeight = height[i];
            for (int j = i + 1; j < height.length; j++) {
                int minHeight = Math.min(leftHeight, height[j]);
                maxArea = Math.max(maxArea, (j - i) * minHeight);
            }
        }
        return maxArea;
    }

    //双指针
    public static int maxArea2(int[] height) {
        int maxArea = 0;
        int left = 0, right = height.length - 1;
        while (left < right) {
            int leftH = height[left];
            int rightH = height[right];
            maxArea = Math.max((right - left) * Math.min(leftH, rightH), maxArea);
            if (leftH >= rightH) {
                --right; //右边向左移动1
            } else {
                ++left; //左边向右移动1
            }
        }
        return maxArea;
    }
}
