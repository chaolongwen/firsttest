package com.itheima.store.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import com.itheima.store.domain.User;
import com.itheima.store.service.ProductService;
import com.itheima.store.service.UserService;
import com.itheima.store.service.impl.UserServiceImpl;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.MyDateConverter;

/**
 * 用户Servlet
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	//点击注册按钮跳转到注册页面
	public String registUI(HttpServletRequest req,HttpServletResponse resp) {
		System.out.println("执行完父类的service方法后执行到注册跳转");
		return "/jsp/regist.jsp";
	}	
	
	//注册页面点击用户名后进行用户名校验
	public void checkUsername(HttpServletRequest req,HttpServletResponse resp) throws IOException {
		/*
		 * 通过用户名查询数据库查看这个用户名是否存在
		 * 如果存在回写1 不存在回写2
		 * */
		//获取参数
		try {
			String username=req.getParameter("username");
			UserService us = (UserService) BeanFactory.getBean("UserService");
			User user = us.checkUsername(username);
			if(user==null) { //不存在返回1
				resp.getWriter().println("1");
			}else { //存在就返回2
				resp.getWriter().println("2");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//注册方法
	public String regist(HttpServletRequest req, HttpServletResponse resp) {
		/*
		 * 获得请求参数
		 * 封装到user类中
		 * 增加没有的属性
		 * 交给服务层保存到数据库中
		 * */
		try {
			Map<String, String[]> map = req.getParameterMap();
			User user = new User();
			//将日期字符串转成date类型才能存进数据库
			ConvertUtils.register(new MyDateConverter(), Date.class);
			BeanUtils.populate(user, map);
			UserService us = (UserService) BeanFactory.getBean("UserService");
			us.regist(user);
			//页面跳转
			req.setAttribute("msg", "注册成功！请去邮箱激活");
			return "/jsp/msg.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	//登陆跳转
	public String loginUI(HttpServletRequest req, HttpServletResponse resp) {
		
		return "/jsp/login.jsp";
	}
	
	//登陆功能
	public String login(HttpServletRequest req, HttpServletResponse resp) {
		try {
			//先判断验证码对不对
			String code = req.getParameter("code");
			String code1 = (String) req.getSession().getAttribute("code");
			req.getSession().removeAttribute("code");
			if(!code.equalsIgnoreCase(code1)) {
				req.setAttribute("msg", "验证码不正确，请重新输入");
				return "/jsp/login.jsp";
			}
			
			//获取参数
			Map<String, String[]> map = req.getParameterMap();
			//封装参数
			User user = new User();
			BeanUtils.populate(user, map);			
			//调用service
			UserService us = (UserService) BeanFactory.getBean("UserService");
			User exitUser = us.login(user);
			
			//判断是否有这个用户
			if(exitUser == null) {
				req.setAttribute("msg", "用户名或密码或用户未激活!");
				return "/jsp/login.jsp";
			}else {
				//如果有就存入session域中 重定向
				req.getSession().setAttribute("existUser", exitUser);
				System.out.println(req.getContextPath());
				resp.sendRedirect(req.getContextPath()+"/index.jsp");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	//退出功能
	public String logOut(HttpServletRequest req,HttpServletResponse resp) {
		try {
			// 把session销毁，session中没有exitUser对象就可以了
			req.getSession().invalidate();
			resp.sendRedirect(req.getContextPath()+"/jsp/index.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
