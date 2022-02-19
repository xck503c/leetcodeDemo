package com.xck.y2022;

import java.util.*;

/**
 * 括号生成
 *
 * 动态规划，回溯法，bfs，理解手动栈和系统栈区别
 *
 * @author xuchengkun
 * @date 2022/02/18 10:33
 **/
public class GenerateParentheses {

    public static void main(String[] args) {
        System.out.println(generateParenthesisDFS1(3));
        System.out.println(generateParenthesisDFS1(4));

        System.out.println(generateParenthesisStack1(3));
        System.out.println(generateParenthesisStack1(4));

        System.out.println(generateParenthesisStack2(3));
        System.out.println(generateParenthesisStack2(4));

        System.out.println(generateParenthesisDp(3));
        System.out.println(generateParenthesisDp(4));
    }

    public static List<String> generateParenthesisDFS1(int n) {
        List<String> result = new ArrayList<>();

        if (n == 1) {
            result.add("()");
            return result;
        }

        if (n == 2) {
            result.add("()()");
            result.add("(())");
            return result;
        }

        DFSResult dfsResult = new DFSResult();
        dfsResult.result = result;
        dfsResult.n = n;
        dfsResult.singleBracket = 0;

        generateParenthesisDFSInner(dfsResult);

        return result;
    }

    public static void generateParenthesisDFSInner(DFSResult dfsResult) {
        if (dfsResult.pairBracket == dfsResult.n) {
            dfsResult.result.add(dfsResult.bracketStr.toString());
            return;
        }

        //这里如果不计算会更快
        if (dfsResult.singleBracket < dfsResult.n - dfsResult.pairBracket) {
            dfsResult.bracketStr.append("(");
            dfsResult.singleBracket++;
            generateParenthesisDFSInner(dfsResult);
            dfsResult.bracketStr.deleteCharAt(dfsResult.bracketStr.length() - 1);
            dfsResult.singleBracket--;
        }

        //向右
        if (dfsResult.singleBracket > 0) {
            dfsResult.bracketStr.append(")");
            dfsResult.singleBracket--;
            dfsResult.pairBracket++;
            generateParenthesisDFSInner(dfsResult);
            dfsResult.bracketStr.deleteCharAt(dfsResult.bracketStr.length() - 1);
            dfsResult.singleBracket++;
            dfsResult.pairBracket--;
        }
    }

    public static class DFSResult {
        List<String> result;
        int n; //总对数
        int pairBracket; //生成对数
        int singleBracket; //单个左括号数量，不能超过n
        StringBuilder bracketStr = new StringBuilder();
    }

    //显示回溯
    public static List<String> generateParenthesisStack1(int n) {
        List<String> result = new ArrayList<>();

        if (n == 1) {
            result.add("()");
            return result;
        }

        if (n == 2) {
            result.add("()()");
            result.add("(())");
            return result;
        }

        Node[] nodes = new Node[n * 2];
        nodes[0] = new Node(1, 0, false);
        int top = 1;

        boolean isBack = false; //是否处于回溯状态

        //栈不为空
        while (top != 0) {
            //若栈满，这表示已经得到了结果
            if (top == nodes.length) {
                StringBuilder sb = new StringBuilder();
                for (Node node : nodes) {
                    sb.append(node.isRight ? ")" : "(");
                }
                result.add(sb.toString());
                isBack = true;
            }

            if (isBack) {
                //因为顺序是先左后右，所以如果top是右括号则表示要继续退栈
                --top;
                while (top > 0 && nodes[top].isRight) {
                    --top;
                }
                if (top <= 0) {
                    break;
                }
                Node nextNode = nodes[top - 1];
                //保证之前试过必须是左括号，现在试右括号
                if (nextNode.openBracket > nextNode.closeBracket) {
                    nodes[top++] = new Node(nextNode.openBracket
                            , nextNode.closeBracket + 1, true);
                    isBack = false;
                }
            } else {
                Node nextNode = nodes[top - 1];
                if (nextNode.openBracket < n) {
                    nodes[top++] = new Node(nextNode.openBracket + 1
                            , nextNode.closeBracket, false);
                    isBack = false;
                } else if (nextNode.openBracket > nextNode.closeBracket) {
                    nodes[top++] = new Node(nextNode.openBracket
                            , nextNode.closeBracket + 1, true);
                    isBack = false;
                } else {
                    isBack = true;
                }
            }
        }

        return result;
    }

    //隐式回溯
    public static List<String> generateParenthesisStack2(int n) {
        List<String> result = new ArrayList<>();

        if (n == 1) {
            result.add("()");
            return result;
        }

        if (n == 2) {
            result.add("()()");
            result.add("(())");
            return result;
        }

        List<String> res = new ArrayList<>();
        if (n == 0) {
            return res;
        }
        Stack<Node> queue = new Stack<>();
        queue.push(new Node("", n, n));

        while (!queue.isEmpty()) {

            //每次选取最上面的一个进行深度遍历
            Node curNode = queue.pop();
            if (curNode.openBracket == 0 && curNode.closeBracket == 0) {
                res.add(curNode.res);
            }
            //将所有分支进行压栈
            if (curNode.openBracket > 0) {
                queue.push(new Node(curNode.res + "(", curNode.openBracket - 1
                        , curNode.closeBracket));
            }
            if (curNode.closeBracket > 0 && curNode.openBracket < curNode.closeBracket) {
                queue.push(new Node(curNode.res + ")", curNode.openBracket
                        , curNode.closeBracket - 1));
            }
        }
        return res;
    }

    public static class Node {
        private int openBracket;
        private int closeBracket;
        private String res;
        private boolean isRight; //是否是右括号

        public Node(String res, int openBracket, int closeBracket) {
            this.openBracket = openBracket;
            this.closeBracket = closeBracket;
            this.res = res;
        }

        public Node(int openBracket, int closeBracket, boolean isRight) {
            this.openBracket = openBracket;
            this.closeBracket = closeBracket;
            this.isRight = isRight;
        }
    }

    static List<String>[] cache = new ArrayList[9];

    static {
        cache[0] = new ArrayList<>(Arrays.asList(new String[]{""}));
        cache[1] = new ArrayList<>(Arrays.asList(new String[]{"()"}));
        cache[2] = new ArrayList<>(Arrays.asList(new String[]{"()()", "(())"}));
    }

    public static List<String> generateParenthesisDp(int n) {
        if (cache[n] != null) return cache[n];

        for (int i = 3; i <= n; i++) {
            //若已经有缓存则跳过
            if (cache[i] != null) continue;
            List<String> result = new ArrayList<>();
            //根据(a)b的序列进行自底向上求解
            for (int a = 0, b = n - 1; a < n; a++, b--) {
                List<String> aList = cache[a];
                List<String> bList = cache[b];
                for (String aItem : aList) {
                    for (String bItem : bList) {
                        result.add("(" + aItem + ")" + bItem);
                    }
                }

            }
            cache[i] = result;
        }

        return cache[n];
    }
}
