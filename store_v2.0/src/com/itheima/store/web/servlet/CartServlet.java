package com.itheima.store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.store.domain.Cart;
import com.itheima.store.domain.CartItem;
import com.itheima.store.domain.Product;
import com.itheima.store.service.ProductService;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;

/**
 * 购物车类
 * Servlet implementation class CartServlet
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	
	/**
	 * 添加到购物车执行的方法:addCart
	 */
	public String addCart(HttpServletRequest req,HttpServletResponse resp){
		// 接收参数:
		try{
			String pid = req.getParameter("pid");
			Integer count = Integer.parseInt(req.getParameter("count"));
			System.out.println(pid);
			System.out.println(count);
			
			// 封装CartItem:
			CartItem cartItem = new CartItem();
			cartItem.setCount(count);
			ProductService productService = (ProductService) BeanFactory.getBean("ProductService");
			Product product = productService.findByPid(pid);
			System.out.println(product);
			cartItem.setProduct(product);

			// 调用Cart中的方法处理
			Cart cart = getCart(req);
			cart.addCart(cartItem);
			
			// 页面跳转
			resp.sendRedirect(req.getContextPath()+"/jsp/cart.jsp");
			return null;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}


	private Cart getCart(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Cart cart = (Cart) session.getAttribute("cart"); //用的都是一个Car
		if(cart == null) {
			cart = new Cart(); //这里新建了一个cart弄了我两个钟头 如果返回的是空，空的话没有方法调用就出错
			session.setAttribute("cart", cart);
		}
		return cart;
	}
	
	
	public String clearCart(HttpServletRequest req,HttpServletResponse resp) {
		try {
			HttpSession session = req.getSession();
			Cart cart = (Cart) session.getAttribute("cart");
			cart.clearCart();
			resp.sendRedirect(req.getContextPath()+"/jsp/cart.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String removeCart(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String pid = req.getParameter("pid");
			HttpSession session = req.getSession();
			Cart cart = (Cart) session.getAttribute("cart");
			cart.removeCart(pid);
			resp.sendRedirect(req.getContextPath()+"/jsp/cart.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
