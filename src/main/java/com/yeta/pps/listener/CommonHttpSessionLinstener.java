package com.yeta.pps.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Session监听器类
 * @author YETA
 * @date 2018/05/25/13:30
 */
@WebListener(value = "Session监听器")
public class CommonHttpSessionLinstener implements HttpSessionListener {

    private static final Logger LOG = LoggerFactory.getLogger(CommonHttpSessionLinstener.class);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        LOG.info("监听器sessionCreated()【{}】...", httpSessionEvent.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        LOG.info("监听器sessionDestroyed()【{}】...", httpSessionEvent.getSession().getId());

        HttpSession session = httpSessionEvent.getSession();
        Object userId = session.getAttribute("userId");
        if (userId != null) {
            ConcurrentSkipListSet<String> onlineIds = (ConcurrentSkipListSet<String>) session.getServletContext().getAttribute("onlineIds");
            for (String onlineId : onlineIds) {
                if (onlineId.equals(userId)) {
                    onlineIds.remove(onlineId);
                    break;
                }
            }
        }
    }
}
