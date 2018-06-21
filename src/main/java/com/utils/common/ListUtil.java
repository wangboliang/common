package com.utils.common;

import java.util.*;

/**
 * <p>
 * List处理工具类
 * </p>
 *
 * @author wangliang
 * @since 2017/9/15
 */
public class ListUtil {

    /**
     * 去重
     *
     * @param list
     * @return
     */
    public static List<String> distinct(List<String> list) {
        List<String> resultList = new ArrayList<String>();
        Set<String> set = new HashSet<>(list.size());
        for (String str : list) {
            if (set.add(str)) {
                resultList.add(str);
            }
        }
        return resultList;
    }

    /**
     * 取差集
     *
     * @param firstArrayList
     * @param secondArrayList
     * @return
     */
    public static List<String> subtract(List<String> firstArrayList, List<String> secondArrayList) {
        List<String> resultList = new ArrayList<String>();
        LinkedList<String> result = new LinkedList<String>(firstArrayList);
        HashSet<String> othHash = new HashSet<String>(secondArrayList);
        Iterator<String> iter = result.iterator();
        while (iter.hasNext()) {
            if (othHash.contains(iter.next())) {
                iter.remove();
            }
        }
        resultList = new ArrayList<String>(result);
        return resultList;
    }

    /**
     * 取交集
     *
     * @param firstArrayList
     * @param secondArrayList
     * @return
     */
    public static List<String> intersection(List<String> firstArrayList, List<String> secondArrayList) {
        List<String> resultList = new ArrayList<String>();
        LinkedList<String> result = new LinkedList<String>(firstArrayList);
        HashSet<String> othHash = new HashSet<String>(secondArrayList);
        Iterator<String> iter = result.iterator();
        while (iter.hasNext()) {
            if (!othHash.contains(iter.next())) {
                iter.remove();
            }
        }
        resultList = new ArrayList<String>(result);
        return resultList;
    }

    /**
     * 取并集
     *
     * @param firstArrayList
     * @param secondArrayList
     * @return
     */
    public static List<String> union(List<String> firstArrayList, List<String> secondArrayList) {
        List<String> resultList = new ArrayList<String>();
        Set<String> firstSet = new TreeSet<String>(firstArrayList);
        for (String id : secondArrayList) {
            // 当添加不成功的时候 说明firstSet中已经存在该对象
            firstSet.add(id);
        }
        resultList = new ArrayList<String>(firstSet);
        return resultList;
    }

}
