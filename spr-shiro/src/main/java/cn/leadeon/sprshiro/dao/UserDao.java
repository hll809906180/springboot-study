package cn.leadeon.sprshiro.dao;

import cn.leadeon.sprshiro.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    User findByUsername(String username);
}
