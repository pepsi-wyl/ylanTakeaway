package com.ylan.ylantakeaway.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author by ylan
 * 通用返回结果，响应服务端返回的数据
 */

@Data
public class R<T> {

    /**
     * 编码：1成功，0和其它数字为失败
     */
    private Integer code;

    /**
     * 数据
     */
    private T data;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 返回成功结果
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    /**
     * 返回失败结果
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    /**
     * 动态数据
     */
    private Map map = new HashMap();

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}