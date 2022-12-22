package com.ylan.ylantakeaway.common;

/**
 * 基于ThreadLocal封装工具类 获取当前登录用户id
 *
 * @author by ylan
 * @date 2022-12-19 16:13
 */

public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置值
     *
     * @param id
     */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    /**
     * 获取值
     *
     * @return
     */
    public static Long getCurrentId() {
        return threadLocal.get();
    }

}
