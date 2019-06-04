package com.itheima.store.web.servlet;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.store.domain.PageBean;
import com.itheima.store.domain.Product;
import com.itheima.store.service.CategoryService;
import com.itheima.store.service.ProductService;
import com.itheima.store.service.impl.ProductServiceImpl;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.CookieUtils;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	//分页返回商品数据
	public String findByCid(HttpServletRequest req, HttpServletResponse resp) {
		try {
			//获得请求参数
			String cid = req.getParameter("cid");
			Integer currPage = Integer.parseInt(req.getParameter("currPage"));
			
			//调用服务层
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			PageBean<Product> pageBean = ps.findByCid(cid, currPage);
			
			//封装分页对象返回json数据页面解析
			req.setAttribute("pageBean", pageBean);
			return "/jsp/product_list.jsp";
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	//显示某个商品
	public String findByPid(HttpServletRequest req, HttpServletResponse resp) {
		try {
			//获取参数
			String pid = req.getParameter("pid");
			//调用业务层
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			Product p = ps.findByPid(pid);
			
			//浏览记录
			//1.从浏览器获取cookie
			Cookie[] cookies = req.getCookies();
			//2、判断是否有浏览历史cookie，没有的话呢就放进去，有的话再判断
			Cookie cookie = CookieUtils.findCookie(cookies, "history");
			if(cookie == null) {
				Cookie c = new Cookie("history", pid);
				c.setPath("/store_v2.0");
				c.setMaxAge(7*24*60*60);
				resp.addCookie(c);
			}else {
				//如果有的话呢 判断是否有这个商品pid
				String value = cookie.getValue();
				String[] split = value.split("-");
				//转成集合方便操作
				LinkedList<String> list = new LinkedList<String>(Arrays.asList(split));
				//判断是否在里面 移除，再添加到前面
				if(list.contains(pid)) {
					list.remove(pid);
					list.addFirst(pid);
				} else {
					//如果不在里面的话判断是否大于6，不大于的话直接增加大于的话先减最后一个再增加
					if(list.size()>6) {
						list.removeLast();
						list.addFirst(pid);
					}else {
						list.addFirst(pid);
					}
				}
				//用字符串凭借
				StringBuffer sb = new StringBuffer();
				for (String string : list) {
					sb.append(string).append("-");
				}
				String idStr = sb.toString().substring(0, sb.length()-1);
				Cookie c = new Cookie("history", idStr);
				c.setPath("store_v2.0");
				c.setMaxAge(7*24*60*60);
				resp.addCookie(c);
			}
			
			//把结果存进域里面
			req.setAttribute("p", p);
			return "/jsp/product_info.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
