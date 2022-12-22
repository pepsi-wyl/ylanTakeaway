package com.ylan.ylantakeaway.filter;

import com.alibaba.fastjson.JSON;
import com.ylan.ylantakeaway.common.BaseContext;
import com.ylan.ylantakeaway.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author by pepsi-wyl
 * @date 2022-12-17 15:03
 * 登陆检查过滤器
 */

@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = {"/*"})
public class LoginCheckFilter implements Filter {

    /**
     * 路径匹配器
     */
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    /**
     * 路径匹配，检查url是否放行
     *
     * @param url
     * @return
     */
    private boolean check(String url) {

        // 放行的请求
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
        };

        // URL进行匹配
        for (String s : urls) {
            boolean match = PATH_MATCHER.match(s, url);
            if (match) {
                return true;
            }
        }
        return false;
    }

    /**
     * 过滤器方法
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        log.info("拦截到请求:{}", request.getRequestURI());

        // 获取请求
        String url = request.getRequestURI();

        // 路径匹配
        boolean check = check(url);

        // 不需要处理，放行
        if (check) {
            log.info("拦截的请求:{}不需要处理", url);
            filterChain.doFilter(request, response);
            return;
        }

        // 需要处理，暂时不放行
        Object employeeID = request.getSession().getAttribute("employee");
        // 存在Session对象，放行
        if (employeeID != null) {
            log.info("拦截的请求:{}已登陆不需要处理", url);

            // 设置当前登陆用户的ID,线程内共享
            BaseContext.setCurrentId((Long) employeeID);

            filterChain.doFilter(request, response);
            return;
        }
        // 不存在Session对象，不放行
        log.info("拦截的请求:{}需要处理", url);
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

}