package com.xck.y2022.dfsbfs;

import java.util.*;

/**
 * 课程表I
 *
 * @author xuchengkun
 * @date 2022/06/23 16:43
 **/
public class CourseSchedule {

    public static void main(String[] args) {
        System.out.println(new CourseSchedule().canFinish(2, new int[][]{
                {1, 0}
        }) == true);
        System.out.println(new CourseSchedule().canFinish(2, new int[][]{
                {1, 0},
                {0, 1}
        }) == false);
        System.out.println(new CourseSchedule().canFinish(3, new int[][]{
                {1, 0},
                {1, 2},
                {0, 1}
        }) == false);
        System.out.println(new CourseSchedule().canFinish(4, new int[][]{
                {0, 1},
                {3, 1},
                {1, 3},
                {3, 2}
        }) == false);

    }

    // 记录先修课程对关系
    Map<Integer, List<Integer>> prerequisitesMap = new HashMap<>();
    // 记录跑过的路径的结果，避免重复计算
    Set<Integer> courseResultSet = new HashSet<>();

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int rows = prerequisites.length;
        if (rows == 0) {
            return true;
        }

        // 记录先修课程对
        for (int i = 0; i < rows; i++) {
            Integer key = prerequisites[i][0];
            if (!prerequisitesMap.containsKey(key)) {
                prerequisitesMap.put(key, new LinkedList<Integer>());
            }
            prerequisitesMap.get(key).add(prerequisites[i][1]);
        }

        for (int i = 0; i < numCourses; i++) {
            if (!isCourseCanFinish(i, null)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断某个课程是否可以修完
     * @param id 当前要修的课程
     * @param set 记录走过的课程
     * @return
     */
    public boolean isCourseCanFinish(Integer id, Set<Integer> set) {
        // 如果已经有结果直接返回
        if (courseResultSet.contains(id)) {
            return true;
        }

        // 如果不存在需要先修的课程
        if (!prerequisitesMap.containsKey(id)) {
            courseResultSet.add(id);
            return true;
        }

        if (set == null) {
            set = new HashSet<>();
            set.add(id);
        } else if (set.contains(id)) {
            return false; // 到这就到头了，也无需记录访问结果
        } else {
            set.add(id);
        }

        boolean result = true;
        List<Integer> backupCourseList = prerequisitesMap.get(id);
        for (Integer tmpId : backupCourseList) {
            if (!isCourseCanFinish(tmpId, set)) {
                result = false;
                break;
            }
        }

        courseResultSet.add(id);
        return result;
    }
}
