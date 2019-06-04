package com.itheima.store.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.store.dao.UserDao;
import com.itheima.store.domain.User;
import com.itheima.store.utils.JDBCUtils;



/**
 * @author Administrator
 * 用户模块借口实现类
 */
public class UserDaoImpl implements UserDao {

	@Override
	public User checkUsername(String username) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where username=?";
		User exitUser=qr.query(sql, new BeanHandler<User>(User.class), username);	
		return exitUser;
	}

	@Override
	public void regist(User user) throws SQLException {
		
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {user.getUid(), user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),
				user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode()}; 
		qr.update(sql, params);
	}

	//登陆功能
	@Override
	public User login(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where username=? and password=? and state=?";
		User exitUser = qr.query(sql, new BeanHandler<User>(User.class),user.getUsername(),user.getPassword(),2); //匹配数据库中的state
		return exitUser;
	}
	
}
