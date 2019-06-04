package com.itheima.store.dao;

import java.sql.SQLException;

import com.itheima.store.domain.User;

/**
 * @author Administrator
 * 用户模块Dao借口
 */
public interface UserDao {

	User checkUsername(String username) throws SQLException;

	void regist(User user) throws SQLException;

	User login(User user) throws SQLException;

}
