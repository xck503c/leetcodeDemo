package com.xck.y2021;

/**
 * leetcode 最长回文字符串：https://leetcode-cn.com/problems/longest-palindromic-substring/
 *
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 *
 * 示例1：
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 *
 * 示例2：
 * 输入：s = "cbbd"
 * 输出："bb"
 *
 * 示例3：
 * 输入：s = "a"
 * 输出："a"
 *
 * 示例4：
 * 输入：s = "ac"
 * 输出："a"
 *
 * 提示：
 * 1. 1 <= s.length <= 1000
 * 2. s 仅由数字和英文字母（大写和/或小写）组成
 *
 * 审题：
 * 1. 字符只有数字和大小写字母
 * 2. 长度为1000，不存在为空的情况，无需判空
 * 3. 回文就是读正反读都一样，所以如果是bbbb那么它也是一个回文字符串，
 * 它可以用2中形式：
 * abba
 * ababa
 * 无中点，x[i] == x[i+1]
 * 有中点，x[i] == x[i+2]
 *
 *
 * @Classname LongestSubstringWithoutRepeatingChar
 * @date 2021/06/22 15:15
 * @Created by xck503c
 **/
public class LongestPalindromicSubstring {

    public static void main(String[] args) {
        longestPalindrome("babad");
        longestPalindrome("cbbd");
        longestPalindrome("a");
        longestPalindrome("ac");
        longestPalindrome("bbaabb");
        longestPalindrome("bbabb");
        longestPalindrome("ccc");
        longestPalindrome("cccc");
        longestPalindrome("ccccfdfcccc");
        longestPalindrome("aacabdkacaa");
    }

    public static void longestPalindrome(String s) {
        String result = longestPalindrome1(s);
        System.out.println(result);

        result = longestPalindrome11(s);
        System.out.println(result);

        result = longestPalindrome2(s);
        System.out.println(result);

        result = longestPalindrome3(s);
        System.out.println(result);
    }

    /**
     * 解法1：老样子，还是暴力先来
     *
     * 有点复杂，虽然通过了leetcode，但是这个方法应该不怎么行
     *
     * 用时：35ms，击败76.84%
     * 内存：38.3MB，击败90.83%
     *
     * @param s
     * @return
     */
    public static String longestPalindrome1(String s) {

        boolean isPalindrome = false; //当前是否找到回文，正在扩展长度

        PalindromeRecord maxRecord = new PalindromeRecord();
        PalindromeRecord curRecord = null, last = null;

        for (int i = 0; i < s.length(); i++) {
            if (!isPalindrome) { //正在寻找回文中心点
                //这里的第一个判断和该判断里面的last主要为了一定对ccc和cccc这种重复的情况
                //last.curPalindromeMid != i 防止重置游标导致死循环
                if (i > 0 && i < s.length()-1 && s.charAt(i-1) == s.charAt(i+1)
                        && (last == null || last.curPalindromeMid != i)) {
                    curRecord = new PalindromeRecord(i, true);
                    i = i+1;
                    isPalindrome = true;
                } else if (i +1 < s.length() && s.charAt(i) == s.charAt(i+1)){
                    curRecord = new PalindromeRecord(i, false);
                    i = i+1;
                    isPalindrome = true;
                } else if (i +2 < s.length() && s.charAt(i) == s.charAt(i+2)){
                    curRecord = new PalindromeRecord(i+1, true);
                    i = i+2; //从i+2开始，还有个i++，所以上i+3开始
                    isPalindrome = true;
                }
            } else if (curRecord.isPalindPart(s, i)){ //当前字符属于回文一部分，累加长度
                ++curRecord.curPalindromeLen;
            } else {
                if (maxRecord.len() < curRecord.len()){
                    maxRecord = curRecord;
                }
                isPalindrome = false;
                i = curRecord.isHasMid ? curRecord.curPalindromeMid-1 : curRecord.curPalindromeMid;
                last = curRecord;
                curRecord = null;
            }
        }
        //最后还需要比较一次，因为可能整个s都是回文
        if (curRecord != null && maxRecord.len() < curRecord.len()){
            maxRecord = curRecord;
        }

        return maxRecord.getString(s);
    }

    /**
     * 解法1的代码优化
     *
     * 用时：37ms，击败74.75%
     * 内存：38.3MB，击败91.88%
     *
     * char[] arr = s.toCharArray();
     * 用时：11ms，击败93.88%
     * 内存：38.5MB，击败76.63%
     *
     * @param s
     * @return
     */
    public static String longestPalindrome11(String s) {
        if (s.length() < 2){
            return s;
        }

        char[] arr = s.toCharArray();

        int max = 1, begin = 0;
        for (int i=0; i < s.length(); i++){
            int len1 = expandAroundCenter(arr, i, i);
            int len2 = expandAroundCenter(arr, i, i+1);
            int len = Math.max(len1, len2);
            if (len > max){
                max = len;
                begin = len1 == len ? i - len /2 : i-len/2 + 1;
            }
        }
        return s.substring(begin, begin+max);
    }

    /**
     * 中心扩展
     * @param arr
     * @param left
     * @param right
     * @return 返回回文串长度
     */
    public static int expandAroundCenter(char[] arr, int left, int right){

        while (left >= 0 && right < arr.length && arr[left] == arr[right]) {
            --left;
            ++right;
        }

        return right - left - 1; //left和right是多余的，本来r-l+1是总长度，现在r-l-1，刚刚好-2
    }

    /**
     * 解法2：动态规划解法
     *
     * 递推式：
     * 若X1 == Xn && f(2, n-1) == n-3, f(1, n) = f(2, n-1) + 2
     * 若X1 != Xn , f(1, n)= max(f(1, n-1), f(2, n))
     *
     * @param s
     * @return
     */
    public static String longestPalindrome2(String s) {
        int[][] dp = new int[s.length()][s.length()];

        int max = 1;
        int begin = 0;

        //填表
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j + i < s.length(); j++){
                //首尾相同
                boolean isSame = s.charAt(j) == s.charAt(j+i);
                if (isSame && i < 3){ //长度在2之内可以直接判断
                    dp[j][j+i] = i+1;
                    if (i+1 > max){
                        max = i+1;
                        begin = j;
                    }
                    continue;
                }

                //首尾相同，而且内子串是回文子串
                int subPalindromeLen = dp[j+1][j+i-1];
                if (isSame && subPalindromeLen == i-1) {
                    dp[j][j+i] = subPalindromeLen + 2;
                    if (subPalindromeLen + 2 > max){
                        max = subPalindromeLen + 2;
                        begin = j;
                    }
                    continue;
                }

                //否则就看哪个大
                dp[j][j+i] = Math.max(dp[j][j+i-1], dp[j+1][j+i]);
            }
        }

        return s.substring(begin, begin+max);
    }

    /**
     * 动态规划解法2，换成true-false形式
     *
     * 358ms，18.11%
     * 42.2MB，41.31%
     *
     * 加上<2判断
     * 314ms，26.42%
     *
     * charAt换成char[] charArray = s.toCharArray();
     * 214ms,43.46%
     *
     * @param s
     * @return
     */
    public static String longestPalindrome3(String s) {
        int len = s.length();
        if (len < 2) {
            return s;
        }
        boolean[][] dp = new boolean[s.length()][s.length()];

        int max = 1;
        int begin = 0;

        char[] charArray = s.toCharArray();

        //填表
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j + i < s.length(); j++){
                if (!(charArray[j] == charArray[j+i])){
                    dp[j][j+i] = false;

                } else if (i < 3 || dp[j+1][j+i-1]){
                    dp[j][j+i] = true;

                    if (i+1 > max){
                        max = i+1;
                        begin = j;
                    }

                } else {
                    dp[j][j+i] = false;
                }
            }
        }
        return s.substring(begin, begin+max);
    }

    /**
     * 解法2：动态规划解法，降维
     *
     * 递推式：
     * 若X1 == Xn && f(2, n-1) == n-3, f(1, n) = f(2, n-1) + 2
     * 若X1 != Xn , f(1, n)= max(f(1, n-1), f(2, n))
     *
     * f(n)
     *
     * @param s
     * @return
     */
    public static String longestPalindrome21(String s) {
        int[][] dp = new int[s.length()][s.length()];

        int max = 1;
        int begin = 0;

        //填表
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j + i < s.length(); j++){
                //首尾相同
                boolean isSame = s.charAt(j) == s.charAt(j+i);
                if (isSame && i < 3){ //长度在2之内可以直接判断
                    dp[j][j+i] = i+1;
                    if (i+1 > max){
                        max = i+1;
                        begin = j;
                    }
                    continue;
                }

                //首尾相同，而且内子串是回文子串
                int subPalindromeLen = dp[j+1][j+i-1];
                if (isSame && subPalindromeLen == i-1) {
                    dp[j][j+i] = subPalindromeLen + 2;
                    if (subPalindromeLen + 2 > max){
                        max = subPalindromeLen + 2;
                        begin = j;
                    }
                    continue;
                }

                //否则就看哪个大
                dp[j][j+i] = Math.max(dp[j][j+i-1], dp[j+1][j+i]);
            }
        }

        return s.substring(begin, begin+max);
    }

    /**
     * 回文字符串记录
     */
    public static class PalindromeRecord{
        //是否是默认，如果是默认则只取1个字符，如果非默认，可能是有2个字符
        //例如：ac 和 aa，这2个，中心和长度都一样
        boolean isDefault = true;
        boolean isHasMid = true; //回文是否有中点
        //回文字符的长度，如果是aa为中心，bbaabb，那长度就是3
        //如果bbabb为中心，那就是2
        int curPalindromeMid = 0; //回文字符的中心位置
        int curPalindromeLen = 1;

        public PalindromeRecord() {}

        public PalindromeRecord(int curPalindromeMid, boolean isHasMid) {
            this.curPalindromeMid = curPalindromeMid;
            this.isHasMid = isHasMid;
            this.isDefault = false;
        }

        public int len(){
            if (isDefault && curPalindromeMid == 0){
                return 1;
            }

            if (!isHasMid){
                return curPalindromeLen * 2;
            }

            return curPalindromeLen * 2 + 1;
        }

        /**
         * 计算是否是回文
         * bbaabb 中心mid=2，判断i=5，'b'，对位=0=mid-(i-(mid+1))=mid-(i-mid-1)=mid-i+mid+1=2*mid-i+1
         * bbabb 中心mid=2，判断i=4，'b'，对位=0=mid-(i-mid)=2*mid-i
         * @param s
         * @param i
         * @return
         */
        public boolean isPalindPart(String s, int i) {
            int anotherIndex = !isHasMid ? 2*curPalindromeMid-i+1 : 2*curPalindromeMid-i;
            if(anotherIndex < 0){
                return false;
            }

            if (s.charAt(anotherIndex) == s.charAt(i)){
                return true;
            }

            return false;
        }

        /**
         * 获取回文字符串
         * bbaabb 中心mid=2，长度3，start=mid+1-len；end=mid+len
         * bbabb 中心mid=2，长度2，start=mid-len+1
         * @return
         */
        public String getString(String s){
            if (isDefault && curPalindromeMid == 0) return s.substring(0, 1);

            int start = 0, end = 0;
            if (!isHasMid){
                start =  curPalindromeMid+1-curPalindromeLen;
            } else {
                start =  curPalindromeMid-curPalindromeLen;
            }
            end = curPalindromeMid + curPalindromeLen;

            return s.substring(start, end+1);
        }
    }
}
