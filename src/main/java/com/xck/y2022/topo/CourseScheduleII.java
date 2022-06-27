package com.xck.y2022.topo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 课程表II
 *
 * @Classname CourseScheduleII
 * @Description TODO
 * @Date 2022/6/26 23:00
 * @Created by xck503c
 */
public class CourseScheduleII {

    public static void main(String[] args) {
        System.out.println(new SchedualII_DFS().findOrder(2, new int[][]{
                {1, 0}
        })); //[0,1]
    }

    /**
     * 3ms 93.90%
     * 42.5MB 44.37MB
     */
    private static class SchedualII_DFS {
        // 存储有向图的邻接表
        List<List<Integer>> edges;
        // 标记节点搜索状态0-未搜索，1-搜索种，2-正在搜索
        int[] visited;
        // 用数组来模拟栈，0-为栈顶，n-1为栈底
        int[] result;
        int index; // 栈顶指针

        public int[] findOrder(int numCourses, int[][] prerequisites) {

            // 创建邻接表数组
            edges = new ArrayList<>(numCourses);
            for (int i = 0; i < numCourses; i++) {
                edges.add(new LinkedList<Integer>());
            }
            for (int i = 0; i < prerequisites.length; i++) {
                edges.get(prerequisites[i][1]).add(prerequisites[i][0]);
            }

            visited = new int[numCourses];
            result = new int[numCourses];
            index = numCourses - 1;

            for (int i = 0; i < numCourses; i++) {
                if (!dfs(i)) {
                    return new int[0];
                }
            }

            return result;
        }

        public boolean dfs(int id) {
            if (visited[id] == 1) { //出现环
                return false;
            }
            if (visited[id] == 2) { // 搜索完成
                return true;
            }

            visited[id] = 1; // 标记为搜索中

            List<Integer> edgeList = edges.get(id);
            for (Integer edge : edgeList) {
                if (!dfs(edge)) {
                    return false;
                }
            }

            //搜索完成，入栈
            visited[id] = 2;
            result[index--] = id;
            return true;
        }
    }

    /**
     * 5ms 44.56%
     * 42.8MB 18.72%
     */
    private static class SchedualII_BFS {
        // 存储有向图的邻接表
        List<List<Integer>> edges;
        // 结果集合
        int[] result;
        int index; // 指针
        // 入度边
        int[] inEdges;

        public int[] findOrder(int numCourses, int[][] prerequisites) {

            inEdges = new int[numCourses];

            // 创建邻接表数组
            edges = new ArrayList<>(numCourses);
            for (int i = 0; i < numCourses; i++) {
                edges.add(new LinkedList<Integer>());
            }
            for (int i = 0; i < prerequisites.length; i++) {
                edges.get(prerequisites[i][1]).add(prerequisites[i][0]);
                inEdges[prerequisites[i][0]]++;
            }

            result = new int[numCourses];
            index = 0;

            Queue<Integer> queue = new LinkedList<>();
            // 将所有入度入队列
            for (int i = 0; i < numCourses; i++) {
                if (inEdges[i] == 0) {
                    queue.offer(i);
                }
            }

            while (!queue.isEmpty()) {
                // 队首取出节点，放入结果集合中
                Integer id = queue.poll();
                result[index++] = id;
                for (Integer tmp : edges.get(id)) {
                    --inEdges[tmp]; // 将相邻节点入度-1，直到入度为0
                    if (inEdges[tmp] == 0) {
                        queue.offer(tmp);
                    }
                }
            }

            if (index != numCourses) {
                return new int[0];
            }

            return result;
        }
    }
}