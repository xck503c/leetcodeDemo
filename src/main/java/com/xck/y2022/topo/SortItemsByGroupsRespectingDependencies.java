package com.xck.y2022.topo;

import java.util.*;

/**
 * 项目管理
 *
 * @author xuchengkun
 * @date 2022/06/27 10:30
 **/
public class SortItemsByGroupsRespectingDependencies {

    public static void main(String[] args) {
//        List<List<Integer>> list1 = new ArrayList<>();
//        list1.add(new ArrayList<Integer>());
//        list1.add(new ArrayList<Integer>(){{ add(6); }});
//        list1.add(new ArrayList<Integer>(){{ add(5); }});
//        list1.add(new ArrayList<Integer>(){{ add(6); }});
//        list1.add(new ArrayList<Integer>(){{ add(3); add(6); }});
//        list1.add(new ArrayList<Integer>(){{ }});
//        list1.add(new ArrayList<Integer>(){{ }});
//        list1.add(new ArrayList<Integer>(){{ }});
//        new SortItemsByGroupsRespectingDependencies()
//                .sortItems(8, 2, new int[]{-1,-1,1,0,0,1,0,-1}, list1);

        List<List<Integer>> list2 = new ArrayList<>();
        list2.add(new ArrayList<Integer>(){{ add(2); add(1); add(3);}});
        list2.add(new ArrayList<Integer>(){{ add(2); add(4); }});
        list2.add(new ArrayList<Integer>(){{ }});
        list2.add(new ArrayList<Integer>(){{ }});
        list2.add(new ArrayList<Integer>(){{ }});
        new SortItemsByGroupsRespectingDependencies()
                .sortItems(5, 5, new int[]{2,0,-1,3,0}, list2);
    }

    public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
        // 给没有归属的项目分配一个新组
        for (int i = 0; i < group.length; i++) {
            if (group[i] == -1) {
                group[i] = m++;
            }
        }

        // 建立组的邻接表，找出对应组的所有前置组
        List<Integer>[] groupEdge = new LinkedList[m];
        for (int i = 0; i < m; i++) {
            groupEdge[i] = new LinkedList<>();
        }

        int[] groupInEdge = new int[m];
        for (int i = 0; i < group.length; i++) {
            // 获取当前处理的组
            int curGroup = group[i];
            // 获得当前项目的前置项目，将前置项目的出度节点放进curGroup
            for (Integer beforeItem : beforeItems.get(i)) {
                int beforeGroup = group[beforeItem];
                // 因为不同项目可能属于同个组，存在重复处理可能
                if (beforeGroup != curGroup) {
                    groupEdge[beforeGroup].add(curGroup);
                    groupInEdge[curGroup]++;
                }
            }
        }

        // 建立项目的邻接表
        List<Integer>[] itemEdge = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            itemEdge[i] = new LinkedList<>();
        }
        int[] itemInEdge = new int[n];
        for (int i = 0; i < beforeItems.size(); i++) {
            for (Integer item : beforeItems.get(i)) {
                if (itemEdge[item] == null) {
                    itemEdge[item] = new LinkedList<>();
                }
                itemEdge[item].add(i);
                itemInEdge[i]++;
            }
        }

        // 组和项目的拓扑排序
        int[] groupTopo = topoSort(groupEdge, groupInEdge);
        if (groupTopo.length == 0) {
            return groupTopo;
        }

        int[] itemTopo = topoSort(itemEdge, itemInEdge);
        if (itemTopo.length == 0) {
            return itemTopo;
        }

        // 建立组到项目的映射
        // 遍历项目拓扑，按照分类
        Map<Integer, List<Integer>> group2ItemMap = new HashMap<>(m);
        for (int i = 0; i < itemTopo.length; i++) {
            Integer groupKey = group[itemTopo[i]];
            List<Integer> items = group2ItemMap.get(groupKey);
            if (items == null) {
                group2ItemMap.put(groupKey, items = new LinkedList<>());
            }
            items.add(itemTopo[i]);
        }

        // 遍历组拓扑，将映射的项目拓扑顺序依次放入
        int[] result = new int[n];
        int index = 0;
        for (int i = 0; i < groupTopo.length; i++) {
            int groupId = groupTopo[i];
            List<Integer> list = group2ItemMap.get(groupId);
            if (list != null) {
                for (Integer item : list) {
                    result[index++] = item;
                }
            }
        }
        return result;
    }

    //广度优先进行排序
    public int[] topoSort(List<Integer>[] edge, int[] inEdge) {
        Queue<Integer> queue = new LinkedList<>();
        // 将入度为0的放入队列中
        for (int i = 0; i < inEdge.length; i++) {
            if (inEdge[i] == 0) {
                queue.offer(i);
            }
        }

        int[] result = new int[inEdge.length];
        int index = 0;
        while (!queue.isEmpty()) {
            Integer id = queue.poll();
            result[index++] = id;

            // 将对应的相邻节点入度-1
            for (Integer tmp : edge[id]) {
                inEdge[tmp]--;
                if (inEdge[tmp] == 0) {
                    queue.offer(tmp);
                }
            }
        }

        if (index != result.length) {
            return new int[0];
        }

        return result;
    }
}
