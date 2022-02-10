package com.xck.y2022;

import java.util.ArrayList;
import java.util.List;

/**
 * z字形变化（来自：https://leetcode-cn.com/problems/zigzag-conversion/）
 * <p>
 * 题目：将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。
 * <p>
 * 如：PAYPALISHIRING，当行数为3的时候如下所示：
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："PAHNAPLSIIGYIR"
 * <p>
 * 若是2行的时候
 * P Y A...
 * A P L...
 * 输出的时候就是间隔输出
 * <p>
 * 1 <= s.length <= 1000
 * s 由英文字母（小写和大写）、',' 和 '.' 组成
 * 1 <= numRows <= 1000
 * <p>
 * 分析：
 * 第一行从0开始，间隔4，分别是0,4,8,12
 * 第二行从1开始，分别是1,3,5,7
 * 第三行从2开始，间隔4
 * <p>
 * 同理如果是4行,
 * <p>
 * P     I    N
 * A   L S  I G
 * Y A   H R
 * P     I
 * <p>
 * 第一行从0开始，间隔6，0,6,12，组内间隔6
 * 第二行从1开始，分别是1,5,7,11,13，组内间隔4
 * 第三行从2开始，分别是2,4,8,10，组内间隔2
 * 第四行从3开始，间隔6
 * <p>
 * 我们可以知道中间部分是2个一组，间隔一个步长，比如说4行
 * 情况下步长是6，那1,5是一个，下一个从1+6=7开始
 *
 * @author xuchengkun
 * @date 2022/02/09 16:46
 **/
public class ZigzagConversion {

    public static void main(String[] args) {
        System.out.println(convert("PAYPALISHIRING", 4));
        System.out.println(convert("PAYPALISHIRING", 3));
        System.out.println(convert("PAYPALISHIRING", 2));
        System.out.println(convert("A", 1));;

        System.out.println(convertZ("PAYPALISHIRING", 4));
        System.out.println(convertZ("PAYPALISHIRING", 3));
        System.out.println(convertZ("PAYPALISHIRING", 2));
        System.out.println(convertZ("A", 1));;
    }

    public static String convert(String s, int numRows) {
        //如果就一行或者只有一个字符，直接返回
        if (s.length() == 1 || numRows == 1 || s.length() <= numRows) {
            return s;
        }

        StringBuilder sb = new StringBuilder(s.length());

        //总步长
        int step = 2 + (numRows - 2) * 2;

        //第一行
        for (int i = 0; i < s.length(); i = i + step) {
            sb.append(s.charAt(i));
        }

        //中间部分
        if (numRows > 2) {
            for (int i = 1; i < numRows - 1; ++i) {
                //
                int leftStep = step - i * 2;
                for (int j = i; j < s.length(); j = j + step) {
                    //组内
                    sb.append(s.charAt(j));
                    if (j + leftStep < s.length()) {
                        sb.append(s.charAt(j + leftStep));
                    }
                }
            }
        }

        //最后一行
        for (int i = numRows - 1; i < s.length(); i = i + step) {
            sb.append(s.charAt(i));
        }

        return sb.toString();
    }

    //好像还更慢了
    public static String convertZ(String s, int numRows) {
        if (s.length() == 1 || numRows == 1 || s.length() <= numRows) {
            return s;
        }

        //创建一个行列表，每行用StringBuilder表示
        List<StringBuilder> list = new ArrayList<StringBuilder>(numRows);

        for (int i = 0; i < numRows; i++) {
            list.add(new StringBuilder());
        }

        int curRow = 0; //当前行位置
        boolean isGoingDown = false; //是否往下
        for (char c : s.toCharArray()) {
            list.get(curRow).append(c);
            if (curRow == 0 || curRow == numRows - 1) {
                isGoingDown = !isGoingDown; //转向
            }
            curRow += (isGoingDown ? 1 : -1);
        }

        StringBuilder oneRow = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            oneRow.append(list.get(i));
        }
        return oneRow.toString();
    }
}
