package com.utils.common;

import java.lang.instrument.Instrumentation;
import java.util.List;

/**
 * <p>
 * 计算对象内存大小
 * </p>
 *
 * @author wangliang
 * @since 2017/9/9
 */
public class MyAgent {

    private static volatile Instrumentation globalInstr;

    public static void premain(String args, Instrumentation inst) {
        globalInstr = inst;
    }

    public static long getObjectSize(Object obj) {
        if (globalInstr == null)
            throw new IllegalStateException("Agent not initted");
        return globalInstr.getObjectSize(obj);
    }

    public static long getListSize(List list) {
        if (globalInstr == null)
            throw new IllegalStateException("Agent not initted");
        long size = 0;
        for (Object obj : list) {
            size += getObjectSize(obj);
        }
        return size;
    }
}