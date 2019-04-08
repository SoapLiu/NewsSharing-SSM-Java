package com.liuyi.toutiao.service;

import com.liuyi.toutiao.dao.UserDAO;
import com.liuyi.toutiao.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired(required = false)
    private UserDAO userDAO;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

}
