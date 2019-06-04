package com.itheima.store.utils;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 * 基础servlet
 */
public class BaseServlet extends HttpServlet{
	/*
	 * 思路：
	 * 继承这个类就会调用service方法
	 * 获取参数,参数是方法名字
	 * 用反射获得子类对象
	 * 方法执行
	 * */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//解决乱码问题
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		//获取参数
		String methodName=req.getParameter("method");
		//方法参数为空要做判断
		if(methodName==null||" ".equals(methodName)) {
			resp.getWriter().println("method参数不能为空");
			return;
		}
		//反射获取子类对象
		Class clazz = this.getClass();
		//根据名字与参数获取对应的方法
		try {
			Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			//是当前对象的方法执行，要传入当前对象
			String path=(String) method.invoke(this, req, resp);
			System.out.println("执行完了对应的方法获得了对应的跳转路径");
			//跳转到对应的路径下面 转发不会改变路径
			if(path!=null) {
				req.getRequestDispatcher(path).forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
