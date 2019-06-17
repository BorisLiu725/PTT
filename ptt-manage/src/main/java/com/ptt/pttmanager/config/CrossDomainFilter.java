package com.ptt.pttmanager.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨域的过滤器
 * */
//@Component
public class CrossDomainFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("进入跨域拦截器。。");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//        String requestURI = httpServletRequest.getRequestURI();
//        if (requestURI.contains("/orderApi")){
            httpServletResponse.addHeader("Access-Control-Allow-Origin","*");
            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
            /* 允许跨域的请求方法GET, POST, HEAD 等 */
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
            /* 重新预检验跨域的缓存时间 (s) */
            httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
            /* 允许跨域的请求头 */
            httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
            /* 是否携带cookie */
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

//        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("出跨域拦截器。。");
    }
}
