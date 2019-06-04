package com.itheima.store.web.servlet;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.store.domain.Cart;
import com.itheima.store.domain.CartItem;
import com.itheima.store.domain.Order;
import com.itheima.store.domain.OrderItem;
import com.itheima.store.domain.PageBean;
import com.itheima.store.domain.User;
import com.itheima.store.service.OrderService;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.PaymentUtil;
import com.itheima.store.utils.UUIDUtils;

/**
 * 订单servlet类
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	public String save(HttpServletRequest req, HttpServletResponse resp) {
		//new一个order对象保存到数据库
		Order order = new Order();
		order.setOid(UUIDUtils.getUUID());
		order.setOrdertime(new Date());
		order.setState(1); //未付款
		
		//设置总金额
		Cart cart = (Cart) req.getSession().getAttribute("cart");
		if(cart == null) {
			req.setAttribute("msg", "购物车是空的喔");
			return "/jsp/msg.jsp";
		}
		order.setTotal(cart.getTotal());
		
		//存放用户
		User user = (User) req.getSession().getAttribute("existUser");
		if(user == null) {
			req.setAttribute("msg", "用户还没登陆，请先登陆");
			return "/jsp/login.jsp";
		}
		order.setUser(user);
	
		//设置订单项
		for (CartItem cartItem : cart.getMap().values()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(UUIDUtils.getUUID());
			orderItem.setCount(cartItem.getCount());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);
			
			order.getOrderItems().add(orderItem);
		}
	
		//调用服务曾完成保存
		OrderService orderService = (OrderService) BeanFactory.getBean("OrderService");
		orderService.save(order);
		
		//清空购物车
		cart.clearCart();
		//页面跳转
		req.setAttribute("order", order);
		return "/jsp/order_info.jsp";
	}
	
	//分页查询我的订单
	public String findByUid(HttpServletRequest req, HttpServletResponse resp) {
		try {
			//获取请求参数
			Integer currPage = Integer.parseInt(req.getParameter("currPage"));
			User user = (User) req.getSession().getAttribute("existUser");
			
			//调用业务层处理
			OrderService orderService = (OrderService) BeanFactory.getBean("OrderService");
			PageBean<Order> pageBean = orderService.findByUid(user.getUid(), currPage);
			
			req.setAttribute("pageBean", pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//跳转到我的订单页
		return "/jsp/order_list.jsp";
	}
	
	//根据订单id查询订单项与商品
	public String findByOid(HttpServletRequest req, HttpServletResponse resp) {
		try {
			//接收参数
			String oid = req.getParameter("oid");
			
			//调用业务层处理
			OrderService orderService  = (OrderService) BeanFactory.getBean("OrderService");
			Order order = orderService.findByOid(oid);
			req.setAttribute("order", order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/jsp/order_info.jsp";
	}
	
	//付款方法
	public String payOrder(HttpServletRequest req, HttpServletResponse resp){
		try {
			//接收参数
			String oid = req.getParameter("oid");
			String name = req.getParameter("name");
			String address = req.getParameter("address");
			String telephone = req.getParameter("telephone");
			String pd_FrpId = req.getParameter("pd_FrpId");
			
			//修改数据库 完善订单表信息
			OrderService orderService = (OrderService) BeanFactory.getBean("OrderService");
			Order order = orderService.findByOid(oid);
			order.setAddress(address);
			order.setName(name);
			order.setTelephone(telephone);
			
			orderService.update(order);
			
			//付款
			// 准备参数:
			String p0_Cmd = "Buy";
			String p1_MerId = "10001126856";
			String p2_Order = oid;
			String p3_Amt = "0.01";
			String p4_Cur = "CNY";
			String p5_Pid = "";
			String p6_Pcat = "";
			String p7_Pdesc = "";
			String p8_Url = "http://localhost:8080/store_v2.0/OrderServlet?method=callBack";
			String p9_SAF = "";
			String pa_MP = "";
			String pr_NeedResponse = "1";
			String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
			String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
			
			StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
			sb.append("p0_Cmd=").append(p0_Cmd).append("&");
			sb.append("p1_MerId=").append(p1_MerId).append("&");
			sb.append("p2_Order=").append(p2_Order).append("&");
			sb.append("p3_Amt=").append(p3_Amt).append("&");
			sb.append("p4_Cur=").append(p4_Cur).append("&");
			sb.append("p5_Pid=").append(p5_Pid).append("&");
			sb.append("p6_Pcat=").append(p6_Pcat).append("&");
			sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
			sb.append("p8_Url=").append(p8_Url).append("&");
			sb.append("p9_SAF=").append(p9_SAF).append("&");
			sb.append("pa_MP=").append(pa_MP).append("&");
			sb.append("pd_FrpId=").append(pd_FrpId).append("&");
			sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
			sb.append("hmac=").append(hmac);
			
			//把参数添加到链接后面加密跳转到易宝由易宝访问银行
			resp.sendRedirect(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//处理银行返回的数据
	public String callBack(HttpServletRequest req, HttpServletResponse resp) {
		try {
			//接收返回的参数
			String oid = req.getParameter("r6_Order");
			String money = req.getParameter("r3_Amt");
			
			//修改订单状态
			OrderService orderService = (OrderService) BeanFactory.getBean("OrderService");
			Order order = orderService.findByOid(oid);
			order.setState(2);
			orderService.update(order);
			
			//把信息放消息也
			req.setAttribute("msg", "您的订单："+oid+"付款成功，付款的金额为"+money);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/jsp/msg.jsp";
	}
}
