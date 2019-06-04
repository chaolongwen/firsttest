package com.itheima.store.web.servlet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.store.dao.CategoryDao;
import com.itheima.store.domain.Category;
import com.itheima.store.service.CategoryService;
import com.itheima.store.service.impl.CategoryServiceImpl;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    //查找所有分类的方法
	public String findAll(HttpServletRequest req, HttpServletResponse resp) {
		
		try {
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			List<Category> list = cs.findAll();
			
			//转成json数据然后发送到页面
			JSONArray jsonArray = JSONArray.fromObject(list);
			System.out.println(jsonArray.toString());
			
			resp.getWriter().println(jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return null;
	}
	
}
