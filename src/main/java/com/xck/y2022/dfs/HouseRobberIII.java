package com.xck.y2022.dfs;

import java.util.HashMap;
import java.util.Map;

/**
 * 打家劫舍III
 *
 * @author xuchengkun
 * @date 2022/06/23 14:17
 **/
public class HouseRobberIII {

    public static void main(String[] args) {

    }

    /**
     * 2ms 39.89%
     * 41.4MB 5.26%
     */
    Map<TreeNode, Integer> record = new HashMap<>();
    public int rob_dfs(TreeNode root) {
        if (root == null) return 0;

        Integer robMax = record.get(root);
        if (robMax != null) {
            return robMax;
        }

        //1. 当前节点需要打劫
        int robRoot = root.val;
        //左子树的左右子节点
        if (root.left != null) {
            robRoot += (rob_dfs(root.left.left) + rob_dfs(root.left.right));
        }

        //右子树的左右子节点
        if (root.right != null) {
            robRoot += (rob_dfs(root.right.left) + rob_dfs(root.right.right));
        }

        //2. 当前节点不需要打劫
        int robNoRoot = rob_dfs(root.left) + rob_dfs(root.right);

        //取最大值
        robMax = Math.max(robRoot, robNoRoot);
        //记录结果避免重复计算
        record.put(root, robMax);
        return robMax;
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
