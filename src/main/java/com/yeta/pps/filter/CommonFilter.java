package com.yeta.pps.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 过滤器类
 * 过滤器可以拿到原始的HTTP请求和响应的信息，但是拿不到方法的信息
 * @author YETA
 * @date 2018/05/24/20:25
 */
@WebFilter(filterName = "CommonFilter", urlPatterns = "/*")       //注解方式配置
public class CommonFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(CommonFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //LOG.info("过滤器init()...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //LOG.info("过滤器doFilter()...");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //设置返回头
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "0");
        response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With, userId, token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("XDomainRequestAllowed","1");

        HttpSession session = request.getSession();

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //LOG.info("过滤器destroy()...");
    }
}
