package com.xck.y2022;

import java.util.ArrayList;
import java.util.List;

/**
 * 括号生成
 *
 * @author xuchengkun
 * @date 2022/02/18 10:33
 **/
public class GenerateParentheses {

    public static void main(String[] args) {
        System.out.println(generateParenthesisDFS1(3));
        System.out.println(generateParenthesisDFS1(4));
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

    public static List<String> generateParenthesisDFS2(int n) {
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

        //栈不为空
        while (top != 0) {
            //若栈满，这表示已经得到了结果
            if (top == nodes.length) {
                StringBuilder sb = new StringBuilder();
                for (Node node : nodes) {
                    sb.append(node.isRight ? ")" : "(");
                }
                result.add(sb.toString());
                //弹出栈顶
                Node topNode = nodes[--top];
//                if (topNode.)
            }


        }

        return result;
    }

    public static class Node{
        private int openBracket;
        private int closeBracket;

        private boolean isRight; //是否是右括号

        public Node(int openBracket, int closeBracket, boolean isRight) {
            this.openBracket = openBracket;
            this.closeBracket = closeBracket;
            this.isRight = isRight;
        }
    }
}
