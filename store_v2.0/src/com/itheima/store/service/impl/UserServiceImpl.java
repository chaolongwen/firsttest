package com.itheima.store.service.impl;

import java.sql.SQLException;

import com.itheima.store.dao.ProductDao;
import com.itheima.store.dao.UserDao;
import com.itheima.store.dao.impl.UserDaoImpl;
import com.itheima.store.domain.User;
import com.itheima.store.service.UserService;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.UUIDUtils;


/**
 * @author Administrator
 * 用户模块Service借口实现类
 */
public class UserServiceImpl implements UserService{


	@Override
	public User checkUsername(String username) throws SQLException{
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		User user = ud.checkUsername(username);
		return user;
	}

	@Override
	public void regist(User user) throws SQLException {
		//传过来的还缺 uid state code 
		String uid = UUIDUtils.getUUID();
		String code = UUIDUtils.getUUID()+UUIDUtils.getUUID();
		user.setUid(uid);
		user.setCode(code);
		user.setState(1); //1.代表未激活 2.代表已激活
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		ud.regist(user);
		
		//发送邮件先不做
	}

	@Override
	public User login(User user) throws SQLException{
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		User exitUser = ud.login(user);
		return exitUser;
	}

}
