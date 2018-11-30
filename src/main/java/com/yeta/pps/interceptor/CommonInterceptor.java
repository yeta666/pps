package com.yeta.pps.interceptor;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 拦截器类
 * 拦截器可以拿到原始的HTTP请求和响应的信息，也可以拿到方法的信息，但是拿不到参数
 * Created by YETA666 on 2018/4/22 0022.
 */
@Component
public class CommonInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(CommonInterceptor.class);

    /**
     * 判断请求uri是在允许范围内
     * @param uri
     * @return
     */
    public boolean isPremited(String uri) {
        String[] permitUris = {"/login", "/upload", "/download", "/identifyingCode", "/error"};        //注册、登陆、注销、上传、获取图片、下载、获取验证码
        for (String permit : permitUris) {
            if (uri.indexOf(permit) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 在请求处理之前调用
     * @param request
     * @param response
     * @param handler
     * @return  true表示通过拦截，返回请求目标
     *          false表示没有通过拦截，返回空白页面
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOG.info("拦截器preHandle()...");

        //获取在线用户id
        HttpSession httpSession = request.getSession();
        ServletContext servletContext = httpSession.getServletContext();
        ConcurrentSkipListSet<String> onlineIds = (ConcurrentSkipListSet<String>) servletContext.getAttribute("onlineIds");

        //获取请求uri
        String uri = request.getServletPath();

        //判断是否访问的是允许的资源
        if (isPremited(uri)) {
            LOG.info("请求访问【{}】，允许的uri，放行", uri);
            return true;
        }

        //获取参数中的token
        String requestToken = request.getHeader("token");
        if (requestToken == null || "".equals(requestToken)) {
            LOG.info("请求访问【{}】，请求token为空，拦截", uri);
            throw new CommonException(CommonResponse.CODE14, CommonResponse.MESSAGE14);
        }

        //判断该用户名是否已经存在于在线用户列表
        for (String onlineId: onlineIds) {
            if (CommonUtil.getMd5(onlineId).equals(requestToken)) {
                LOG.info("请求访问【{}】，验证token通过，放行", uri);
                return true;
            }
        }

        //如果不存在
        LOG.info("请求访问【{}】，token超时或未登录，拦截", uri);
        throw new CommonException(CommonResponse.CODE14, CommonResponse.MESSAGE14);
    }

    /**
     * 在请求处理之后调用
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOG.info("拦截器postHandle()...");
    }

    /**
     * 在请求结束之后调用
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOG.info("拦截器afterCompletion()...");
    }
}
