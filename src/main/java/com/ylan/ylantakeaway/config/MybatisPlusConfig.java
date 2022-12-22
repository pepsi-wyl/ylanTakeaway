package com.ylan.ylantakeaway.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by ylan
 * @date 2022-12-17 20:35
 * MybatisPlus配置类
 */

@Slf4j
@MapperScan("com.ylan.ylantakeaway.mapper")
@Configuration
public class MybatisPlusConfig {

    /**
     * 拦截器注册
     *
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {

        /**
         * 创建拦截器对象
         */
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // page分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        return interceptor;
    }
}