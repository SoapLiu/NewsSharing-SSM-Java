package com.liuyi.toutiao.service;

import com.liuyi.toutiao.dao.LoginTicketDAO;
import com.liuyi.toutiao.dao.UserDAO;
import com.liuyi.toutiao.model.LoginTicket;
import com.liuyi.toutiao.model.User;
import com.liuyi.toutiao.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {

    @Autowired(required = false)
    private UserDAO userDAO;

    @Autowired(required = false)
    private LoginTicketDAO loginTicketDAO;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    public Map<String, Object> register(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isBlank(username)) {
            map.put("msgname", "用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)) {
            map.put("msgpwd", "密码不能为空");
            return map;
        }
        User user = userDAO.selectByName(username);
        if(user != null) {
            map.put("msgname", "用户名已经被注册");
            return map;
        }

        //密码强度
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setHeadUrl("/images/img/default.png");
        user.setPassword(MD5Util.MD5EncodeUtf8(password, user.getSalt()));
        userDAO.addUser(user);

        map.put("ticket", addLoginTicket(user.getId()));
        return map;

    }

    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isBlank(username)) {
            map.put("msgname", "用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)) {
            map.put("msgpwd", "密码不能为空");
            return map;
        }
        User user = userDAO.selectByName(username);
        if(user == null) {
            map.put("msgname", "用户名不存在");
            return map;
        }

        if(!MD5Util.MD5EncodeUtf8(password, user.getSalt()).equals(user.getPassword())) {
            map.put("msgpwd", "密码错误");
            return map;
        }

        //下发ticket给用户
        map.put("ticket", addLoginTicket(user.getId()));
        map.put("userId", user.getId());
        return map;
    }

    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket, 1);
    }

    private String addLoginTicket(int userId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 24 * 60 * 60 * 1000);
        loginTicket.setExpired(date);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDAO.addTicket(loginTicket);
        return loginTicket.getTicket();
    }


}
