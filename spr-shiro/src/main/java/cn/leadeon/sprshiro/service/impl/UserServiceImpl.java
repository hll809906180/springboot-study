package cn.leadeon.sprshiro.service.impl;

import cn.leadeon.sprshiro.dao.UserDao;
import cn.leadeon.sprshiro.entity.User;
import cn.leadeon.sprshiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: he.l
 * @create: 2019-05-22 16:19
 **/
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
