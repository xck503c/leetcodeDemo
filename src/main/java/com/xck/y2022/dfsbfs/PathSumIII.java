package com.xck.y2022.dfsbfs;

/**
 * 路径和III
 *
 * @author xuchengkun
 * @date 2022/06/22 10:35
 **/
public class PathSumIII {

    public static void main(String[] args) {

    }

    /**
     * 32ms 19.35%
     * 40.9MB 58.24%
     * @param root
     * @param targetSum
     * @return
     */
    public static int pathSum_dfs(TreeNode root, int targetSum) {
        if (root == null) return 0;

        int total = rootSum(root, targetSum);
        //递归遍历所有
        total += pathSum_dfs(root.left, targetSum);
        total += pathSum_dfs(root.right, targetSum);

        return total;
    }

    /**
     * 当前节点，所有等于targetSum的数量
     *
     * @param root
     * @param targetSum
     * @return
     */
    public static int rootSum(TreeNode root, int targetSum) {
        if (root == null) return 0;

        int isRight = 0;
        targetSum -= root.val;
        // 当前如果满足只能算所有路径之一，后面可能还有，需要继续递归
        if (targetSum == 0) {
            isRight = 1;
        }

        return isRight + rootSum(root.left, targetSum) + rootSum(root.right, targetSum);
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
