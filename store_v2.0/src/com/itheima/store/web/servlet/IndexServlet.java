package com.itheima.store.web.servlet;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.store.domain.Product;
import com.itheima.store.service.ProductService;
import com.itheima.store.service.UserService;
import com.itheima.store.service.impl.ProductServiceImpl;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;

/**
 * 跳转到首页 访问到这个类之后就会执行继承的service方法 然后就会执行index方法 回到service方法再执行跳转路径
 */
public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    public String index(HttpServletRequest req,HttpServletResponse resp) {
    	System.out.println("经过了service方法之后执行我index方法");
    	
    	ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		try {
			//查询热门商品
			List<Product> hot_list = ps.findHot();
			req.setAttribute("hot_list", hot_list);
			
			//查询最新商品
			List<Product> new_list = ps.findNew();
			req.setAttribute("new_list", new_list);
			
		} catch (Exception e) {
			e.printStackTrace();
			new RuntimeException();
		}
    	
    	return "/jsp/index.jsp";
    }
    
}
