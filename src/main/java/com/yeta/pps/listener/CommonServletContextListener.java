package com.yeta.pps.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * ServletContext监听器类
 * @author YETA
 * @date 2018/05/25/13:30
 */
@WebListener(value = "ServletContext监听器")
public class CommonServletContextListener implements ServletContextListener {

    private static final Logger LOG = LoggerFactory.getLogger(CommonServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOG.info("监听器contextInitialized()...");

        //初始化的时候新建一个Set，用来存所有在线用户
        ConcurrentSkipListSet<String> onlineIds = new ConcurrentSkipListSet<>();
        servletContextEvent.getServletContext().setAttribute("onlineIds", onlineIds);

        //初始化的时候新建一个Set，用来存所有在线客户
        ConcurrentSkipListSet<String> onlineClientIds = new ConcurrentSkipListSet<>();
        servletContextEvent.getServletContext().setAttribute("onlineClientIds", onlineClientIds);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOG.info("监听器contextDestroyed()...");

        //销毁的时候移除
        servletContextEvent.getServletContext().removeAttribute("onlineIds");
        servletContextEvent.getServletContext().removeAttribute("onlineClientIds");
    }
}
