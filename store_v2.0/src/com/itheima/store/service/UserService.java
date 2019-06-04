package com.itheima.store.service;

import java.sql.SQLException;

import com.itheima.store.domain.User;

/**
 * @author Administrator
 * 用户模块Service接口
 */
public interface UserService {

	User checkUsername(String username) throws SQLException;

	void regist(User user) throws SQLException;

	User login(User user) throws SQLException;

}
