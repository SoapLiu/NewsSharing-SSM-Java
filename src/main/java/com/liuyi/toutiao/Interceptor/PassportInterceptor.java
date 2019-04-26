package com.liuyi.toutiao.Interceptor;

import com.liuyi.toutiao.dao.LoginTicketDAO;
import com.liuyi.toutiao.dao.UserDAO;
import com.liuyi.toutiao.model.HostHolder;
import com.liuyi.toutiao.model.LoginTicket;
import com.liuyi.toutiao.model.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class PassportInterceptor implements HandlerInterceptor {

    private static final Logger logger = Logger.getLogger(PassportInterceptor.class);

    @Resource
    private LoginTicketDAO loginTicketDAO;

    @Resource
    private UserDAO userDAO;

    @Resource
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object o) throws Exception {
        String ticket = null;
        if(httpServletRequest.getCookies() != null) {
            for(Cookie cookie : httpServletRequest.getCookies()) {
                if(cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }

        if(ticket != null) {
            LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
            if(loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0) {
                return true;
            }
            User user = userDAO.selectById(loginTicket.getUserId());
            hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o,
                           ModelAndView modelAndView) throws Exception {
        if(modelAndView != null && hostHolder.getUser() != null) {
            modelAndView.addObject("user", hostHolder.getUser());
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o,
                                Exception e) throws Exception {
        hostHolder.clear();
    }
}
