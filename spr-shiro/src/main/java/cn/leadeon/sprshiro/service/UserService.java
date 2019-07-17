package cn.leadeon.sprshiro.service;

import cn.leadeon.sprshiro.entity.User;

public interface UserService {
    User findByUsername(String username);
}
