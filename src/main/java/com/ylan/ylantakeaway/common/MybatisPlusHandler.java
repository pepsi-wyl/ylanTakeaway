package com.ylan.ylantakeaway.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author by ylan
 * @date 2022-12-17 17:37
 * MybatisPlus填充策略
 */

@Slf4j
@Component
public class MybatisPlusHandler implements MetaObjectHandler {

    /**
     * 插入时候的填充策略
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Long empId = (Long) request.getSession().getAttribute("employee");
        this.setFieldValByName("createTime", new Date(), metaObject);
        // 使用RequestContextHolder获取Session
        this.setFieldValByName("createUser", empId, metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        // 使用ThreadLocal获取Session中的值
        this.setFieldValByName("updateUser", BaseContext.getCurrentId(), metaObject);
    }

    /**
     * 更新时候的填充策略
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Long empId = (Long) request.getSession().getAttribute("employee");
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateUser", empId, metaObject);
    }
}