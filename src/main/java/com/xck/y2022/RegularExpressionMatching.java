package com.xck.y2022;

/**
 * 正则表达式匹配
 * <p>
 * .-匹配单个字符
 * *匹配0个或多个字符
 * <p>
 * 字符只有小写a～z
 *
 *
 * @author xuchengkun
 * @date 2022/02/19 20:47
 **/
public class RegularExpressionMatching {

    public static void main(String[] args) {
        System.out.println(isMatch2("ab", "a.*b") == true);
        System.out.println(isMatch2("abb", "a.*bb") == true);
        System.out.println(isMatch2("abbb", "ab*b") == true);
        System.out.println(isMatch2("aa", "a") == false);
        System.out.println(isMatch2("aa", "a*") == true);
        System.out.println(isMatch2("ab", "a.b") == false);
        System.out.println(isMatch2("aab", "c*a*b*") == true);
        System.out.println(isMatch2("aab", "aab") == true);
        System.out.println(isMatch2("aab", ".*c") == false);
        System.out.println(isMatch2("aabdfdfs", ".*dfs") == true);
        System.out.println(isMatch2("aabcbcbcaccbcaabc"
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
}
