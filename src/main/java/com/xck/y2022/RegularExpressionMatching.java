package com.xck.y2022;

/**
 * 正则表达式匹配
 * <p>
 * .-匹配单个字符
 * *匹配0个或多个字符
 * <p>
 * 字符只有小写a～z
 *
 * @author xuchengkun
 * @date 2022/02/19 20:47
 **/
public class RegularExpressionMatching {

    public static void main(String[] args) {
        System.out.println(isMatch4("ab", "a.*b") == true);
        System.out.println(isMatch4("abb", "a.*bb") == true);
        System.out.println(isMatch4("abbb", "ab*b") == true);
        System.out.println(isMatch4("aa", "a") == false);
        System.out.println(isMatch4("aa", "a*") == true);
        System.out.println(isMatch4("ab", "a.b") == false);
        System.out.println(isMatch4("aab", "c*a*b*") == true);
        System.out.println(isMatch4("aab", "aab") == true);
        System.out.println(isMatch4("aab", ".*c") == false);
        System.out.println(isMatch4("aabdfdfs", ".*dfs") == true);
        System.out.println(isMatch4("aabcbcbcaccbcaabc"
                , ".*a*aa*.*b*.c*.*a*") == true);
    }

    //未能通过
    public static boolean isMatch1(String s, String p) {
        if (p.equals(".*")) return true;

        if (p.equals(".")) {
            if (s.length() == 1) {
                return true;
            }
            return false;
        }

        Node root = new RootNode(' ');
        Node lastNode = root;

        for (int i = 0; i < p.length(); i++) {
            char c = p.charAt(i);
            if ('a' <= c && c <= 'z') {
                Node normal = new NormalNode(c);
                if (lastNode.isMulti && lastNode.p == c) {
                    continue;
                }
                lastNode.next = normal;
                lastNode = lastNode.next;
            } else if ('*' == c) {
                //因为题目保证前面一定有，可以匹配，所以这里不判断
                //若是.* 则后面也不需要看
                lastNode.setMulti();
            } else {
                lastNode.next = new SingleNode(c);
                lastNode = lastNode.next;
            }
        }

        lastNode.next = new EndNode(' ');

        Node next = root.next;
        for (int i = 0; i < s.length(); ++i) {
            Node newNext = next.isNext(s.charAt(i));
            if (newNext == null) {
                if (next.isMulti && next.next != null) {
                    newNext = next.next;
                    --i;
                } else {
                    return false;
                }
            } else if (newNext instanceof EndNode) {
                if (i == s.length() - 1) {
                    return true;
                }
                return false;
            }
            next = newNext;
        }

        if (next instanceof NormalNode && !next.isMulti) {
            return false;
        }

        return true;
    }

    private static abstract class Node {
        protected char p;
        protected Node next;
        protected boolean isMulti;

        public Node(char p) {
            this.p = p;
        }

        abstract Node isNext(char c);

        public void setMulti() {
            isMulti = true;
        }
    }

    private static class RootNode extends Node {
        public RootNode(char p) {
            super(p);
        }

        public Node isNext(char c) {
            return next;
        }
    }

    private static class NormalNode extends Node {
        public NormalNode(char p) {
            super(p);
        }

        public Node isNext(char c) {
            boolean isMatch = c == p;
            if (isMatch) {
                if (isMulti) {
                    return this;
                }
                return next;
            } else {
                return null;
            }
        }
    }

    private static class SingleNode extends Node {
        public SingleNode(char p) {
            super(p);
        }

        public Node isNext(char c) {
            if (isMulti) {
                return this;
            }
            return next;
        }
    }

    private static class EndNode extends Node {
        public EndNode(char p) {
            super(p);
        }

        public Node isNext(char c) {
            return null;
        }
    }

    public static boolean isMatch2(String s, String p) {

        //如果模式p到头了，那就看s是否到头了
        if (p.length() == 0) {
            return s.length() == 0;
        }

        char p1 = p.charAt(0);
        boolean isMatch = s.length() > 0
                && (s.charAt(0) == p1 || p1 == '.');

        if (p.length() > 1 && p.charAt(1) == '*') {
            //2个选择
            //1. 0次匹配 2. 1次匹配
            //此时s可能是空字符串，不过因为带*，所以也可能匹配
            return isMatch2(s, p.substring(2))
                    || (isMatch && isMatch2(s.substring(1), p));
        } else {
            return isMatch && isMatch2(s.substring(1), p.substring(1));
        }
    }

    //带备忘的自上而下的递归方式
    public static boolean isMatch3(String s, String p) {
        int[][] mem = new int[s.length()][p.length()];
        return isMatch3Inner(s, p, 0, 0, mem);
    }

    public static boolean isMatch3Inner(String s, String p, int i, int j, int[][] mem) {

        //提前返回
        if (mem[i][j] != 0) {
            return mem[i][j] == 1;
        }

        boolean result = false;

        //如果模式p到头了，那就看s是否到头了
        try {
            if (p.length() == 0) {
                result = s.length() == 0;
                return result;
            }

            char p1 = p.charAt(0);
            boolean isMatch = s.length() > 0
                    && (s.charAt(0) == p1 || p1 == '.');

            if (p.length() > 1 && p.charAt(1) == '*') {
                //2个选择
                //1. 0次匹配 2. 1次匹配
                //此时s可能是空字符串，不过因为带*，所以也可能匹配
                return result = (isMatch3Inner(s, p.substring(2), i, j + 2, mem)
                        || (isMatch && isMatch3Inner(s.substring(1), p, i + 1, j, mem)));
            } else {
                return result = (isMatch && isMatch3Inner(s.substring(1), p.substring(1), i + 1, j + 1, mem));
            }
        } finally {
            mem[i][j] = result ? 1 : 2;
        }
    }

    //动态规划
    public static boolean isMatch4(String s, String p) {
        //0-表示空字符串
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];

        char[] sArr = s.toCharArray();
        char[] pArr = p.toCharArray();

        dp[0][0] = true; //空字符串和空字符串匹配，可以匹配成功
        //空字符p和s匹配，总是失败，所以dp[1...n][0]无需初始化

        boolean result = false;
        //s: 0 1... i
        //p: 0 1... j
        for (int i = 0; i <= sArr.length; i++) {
            for (int j = 1; j <= pArr.length; j++) {
                //若当前是星号
                //1. 1次匹配：dp[i-1][j]是s[1...i-1]和p[1...j]是否能够匹配，若能够匹配，而且当前也可以匹配则表示匹配
                //2. 0次匹配：dp[i][j-2]是s[1...i]和p[1...j-2]跳过星号
                if (pArr[j - 1] == '*') {
                    if (i == 0) {
                        dp[i][j] = dp[i][j - 2]; //直接跳过星号看之前的结果
                    } else {
                        dp[i][j] = dp[i][j - 2] || ((pArr[j - 2] == '.' || pArr[j - 2] == sArr[i - 1]) && dp[i - 1][j]);
                    }
                } else {
                    dp[i][j] = (i > 0 && (pArr[j - 1] == '.' || pArr[j - 1] == sArr[i - 1]) && dp[i - 1][j - 1]);
                }

                result = dp[i][j];
            }
        }

        return result;
    }
}
