package com.itheima.store.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.itheima.store.domain.User;

/**
 * 权限过滤器，没有登陆的话不给访问订单，购物车
 */
public class PrivilegeFilter implements Filter {

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * 在这里做权限
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//获取session中的用户，如果没有登陆就返回错误信息跳转，登陆了放行
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession().getAttribute("existUser");
		if(user == null) {
			req.setAttribute("msg", "您还是没有登陆，没有权限访问");
			req.getRequestDispatcher("/jsp/msg.jsp").forward(req, response);
			return; //结束程序
		}
		
		chain.doFilter(req, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
