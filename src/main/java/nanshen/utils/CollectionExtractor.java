/*
 * @(#)CollectionExtractor.java, 2014-11-6.
 *
 * Copyright 2014 Youdao, Inc. All rights reserved.
 * YOUDAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package nanshen.utils;

import java.util.*;

/**
 * 按照指定规则，从保存一种对象的容器中提取信息，生成另一种对象的容器
 * <p />
 * 比如UserProfile类中有一个字段uid，该类可以从UserProfile数组中提取出uid的数组
 * <p />
 * 该类有两个泛型变量DST和SRC，分别表示转换后的类型和转换前的类型
 * 
 * @author WANG Minghao
 */
public abstract class CollectionExtractor<DST, SRC> {

    /**
     * 从一个容器中提取出一个数组
     * <p />
     * 如将UserProfile的数组转换为uid的数组
     * <p />
     * <b>注意</b>：该方法会自动抛弃NULL值，可能导致提取数量和原始集合数量不一致
     * 
     * @param src
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<DST> extractList(Collection<SRC> src) {
        return extractListFromAll(src);
    }

    /**
     * 从若干个容器中提取出一个数组
     * <p>
     * 如将若干个UserProfile的数组转换为一个uid的数组
     * <p />
     * <b>注意</b>：该方法会自动抛弃NULL值，可能导致提取数量和原始集合数量不一致
     *
     * @param src
     * @return
     */
    public List<DST> extractListFromAll(Collection<SRC>... src) {
        int size = getTotalSize(src);
        List<DST> dsts = new ArrayList<DST>(size);
        for (Collection<SRC> collection : src) {
            for (SRC source : collection) {
                DST dst = convert(source);
                if (null != dst) {
                    dsts.add(dst);
                }
            }
        }
        return dsts;
    }

    /**
     * 从一个容器中提取出一个对应
     * <p>
     * 如将UserProfile的数组转换为uid到UserProfile的对应
     * 
     * @param src
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<DST, SRC> extractMap(Collection<SRC> src) {
        return extractMapFromAll(src);
    }

    /**
     * 从若干个容器中提取出一个对应
     * <p>
     * 如将若干个UserProfile的数组转换为一个uid到UserProfile的对应
     * 
     * @param src
     * @return
     */
    public Map<DST, SRC> extractMapFromAll(Collection<SRC>... src) {
        int size = getTotalSize(src);
        Map<DST, SRC> ret = new HashMap<DST, SRC>(size);
        for (Collection<SRC> collection : src) {
            for (SRC source : collection) {
                DST dst = convert(source);
                if (null != dst) {
                    ret.put(dst, source);
                }
            }
        }
        return ret;
    }

    /**
     * 从一个容器中提取出一个允许重复元素的对应
     * <p />
     * 如将多个晒单合并为一个晒单ID到晒单列表的对应
     *
     * @param src
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<DST, List<SRC>> extractListMap(Collection<SRC> src) {
        return extractListMapFromAll(src);
    }

    public Map<DST, List<SRC>> extractListMapFromAll(Collection<SRC>... src) {
        int size = getTotalSize(src);
        Map<DST, List<SRC>> ret = new HashMap<DST, List<SRC>>(size);
        for (Collection<SRC> collection : src) {
            for (SRC source : collection) {
                DST dst = convert(source);
                if (null != dst) {
                    List<SRC> list = ret.get(dst);
                    if (null == list) {
                        list = new ArrayList<SRC>();
                        ret.put(dst, list);
                    }
                    list.add(source);
                }
            }
        }
        return ret;
    }
    
    private int getTotalSize(Collection<SRC>... src) {
        int size = 0;
        for (Collection<SRC> collection : src) {
            size += collection.size();
        }
        return size;
    }

    /**
     * 转换方法，若返回空则不会出现在结果集合里
     * 
     * @param source
     * @return
     */
    protected abstract DST convert(SRC source);

}
