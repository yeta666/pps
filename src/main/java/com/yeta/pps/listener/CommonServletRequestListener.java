package com.yeta.pps.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * Request监听器类
 * @author YETA
 * @date 2018/05/25/13:30
 */
@WebListener(value = "Request监听器")
public class CommonServletRequestListener implements ServletRequestListener {

    private static final Logger LOG = LoggerFactory.getLogger(CommonServletRequestListener.class);

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        //LOG.info("监听器requestDestroyed()...");
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        //servletRequestEvent.getServletRequest().getParameter("");       //获取参数
        //LOG.info("监听器requestInitialized()...");
    }
}
