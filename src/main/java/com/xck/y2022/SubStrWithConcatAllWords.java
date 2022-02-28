package com.xck.y2022;

import java.util.*;

/**
 * 串联所有单词的子串
 * <p>
 * 示例：
 * "bbarbarfoobar"
 * ["foo","bar"]
 * [4, 7]
 *
 * @author xuchengkun
 * @date 2022/02/27 21:45
 **/
public class SubStrWithConcatAllWords {

    public static void main(String[] args) {
//        System.out.println(findSubstring2("bbarbarfoobar", new String[]{"foo", "bar"}));
//        System.out.println(findSubstring2("wordgoodgoodgoodbestword", new String[]{"word", "good", "best", "good"}));
//        System.out.println(findSubstring2("barfoofoobarthefoobarman", new String[]{"bar","foo","the"}));
//        System.out.println(findSubstring2("barfoothefoobarman", new String[]{"foo","bar"}));
        System.out.println(findSubstring2("bcabbcaabbccacacbabccacaababcbb", new String[]{"c","b","a","c","a","a","a","b","c"}));
    }


    public static List<Integer> findSubstring1(String s, String[] words) {
        //因为所有单词长度是相同的，所以这里规定读入的个数
        int wordLen = words[0].length();
        //总长度，因为是全匹配，所以长度固定不变
        int totalLen = 0;
        Map<String, Integer> wordMap = new HashMap<>(words.length);
        for (int i = 0; i < words.length; i++) {
            Integer count = wordMap.get(words[i]);
            if (count == null) {
                count = 0;
            }
            wordMap.put(words[i], count + 1);
            totalLen += wordLen;
        }

        List<Integer> list = new ArrayList<>();

        Map<String, Integer> tmpMap = new HashMap<>(words.length);
        for (int i = 0; i < s.length() - totalLen + 1; i++) {
            int j = i, totalCount = 0;
            if (!tmpMap.isEmpty()) {
                tmpMap.clear();
            }
            while (j < s.length()) {
                String tmpStr = s.substring(j, j + wordLen);
                Integer count = wordMap.get(tmpStr);
                Integer tmpCount = tmpMap.get(tmpStr);
                if (count != null) {
                    if (tmpCount == null) {
                        tmpMap.put(tmpStr, 1);
                    } else if (tmpCount < count) {
                        tmpMap.put(tmpStr, tmpCount + 1);
                    } else {
                        break;
                    }
                    ++totalCount; //增加匹配单词数量

                    if (totalCount == words.length) { //匹配
                        list.add(i);
                        break;
                    }
                    j += wordLen; //每次移动固定长度
                } else {
                    break;
                }
            }
        }

        return list;
    }

    public static List<Integer> findSubstring2(String s, String[] words) {
        //因为所有单词长度是相同的，所以这里规定读入的个数
        int wordLen = words[0].length();
        //总长度，因为是全匹配，所以长度固定不变
        int totalLen = wordLen * words.length;

        if (totalLen > s.length()) return new ArrayList<>(0);

        Map<String, Integer> wordMap = new HashMap<>(words.length);
        for (int i = 0; i < words.length; i++) {
            Integer count = wordMap.get(words[i]);
            if (count == null) {
                count = 0;
            }
            wordMap.put(words[i], count + 1);
        }

        List<Integer> list = new ArrayList<>();

        Map<String, Integer> tmpMap = new HashMap<>(words.length);
        for (int i = 0; i < wordLen; i++) {
            int start = i, wordCount = 0; //点前串的起始位置，和当前串的匹配数量
            tmpMap.clear();
            for (int j = i; j < s.length() && start < s.length() - totalLen + 1; j += wordLen) {
                String tmpStr = s.substring(j, j + wordLen);
                Integer count = wordMap.get(tmpStr);

                if (count == null) { //改单词不存在
                    tmpMap.clear();
                    start = j + wordLen; //重新下个单词开始
                    wordCount = 0; //清空计数
                } else {
                    Integer tmpCount = tmpMap.get(tmpStr);
                    if (tmpCount != null && tmpCount == count) { //多余单词
                        //寻找该单词第一次出现的位置，移除包含该单词，以及之前单词的统计记录
                        //sortgoodfastgood...这里good重复出现，start从0置为8
                        int old = start;
                        start = removeRepeat(s, old, j, tmpStr, tmpMap);
                        wordCount = wordCount - ((start - old) / wordLen) + 1;
                    } else if (tmpCount == null) {
                        tmpMap.put(tmpStr, 1);
                        ++wordCount;
                    } else {
                        tmpMap.put(tmpStr, tmpCount + 1);
                        ++wordCount;
                    }
                }

                if (wordCount == words.length) { //每次匹配都从下一个单词开始
                    list.add(start);
                    String removeStr = s.substring(start, start + wordLen);
                    Integer tmpCount = tmpMap.get(removeStr); //一定存在的
                    tmpMap.put(removeStr, tmpCount - 1); //移除开头单词
                    start = start + wordLen;
                    --wordCount;
                }
            }
        }

        return list;
    }

    public static int removeRepeat(String s, int start, int end, String repeatWord, Map<String, Integer> tmpMap) {
        int wordLen = repeatWord.length();
        for (int i = start; i < end; i += wordLen) {
            String tmpStr = s.substring(i, i + wordLen);
            if (tmpStr.equals(repeatWord)) { //找到重复单词
                return i + wordLen; //从重复单词的下一个开始
            } else {
                Integer tmpCount = tmpMap.get(tmpStr); //一定存在的
                tmpMap.put(tmpStr, tmpCount - 1);
            }
        }

        return end;
    }
}
