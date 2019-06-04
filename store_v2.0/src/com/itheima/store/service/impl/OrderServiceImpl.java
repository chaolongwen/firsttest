package com.itheima.store.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.itheima.store.dao.OrderDao;
import com.itheima.store.domain.Order;
import com.itheima.store.domain.OrderItem;
import com.itheima.store.domain.PageBean;
import com.itheima.store.service.OrderService;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.JDBCUtils;

public class OrderServiceImpl implements OrderService {

	//同时保存
	@Override
	public void save(Order order) {
		Connection conn = null;
		try {
			//开启事务来完成操作
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			
			//保存操作
			OrderDao orderDao = (OrderDao) BeanFactory.getBean("OrderDao");
			orderDao.saveOrder(conn, order);
			
			for (OrderItem orderItem : order.getOrderItems()) {
				orderDao.saveOrderItem(conn, orderItem);
			}
			
			//提交事务
			DbUtils.commitAndCloseQuietly(conn);
		} catch (Exception e) {
			e.printStackTrace();
			DbUtils.rollbackAndCloseQuietly(conn);
		}
		
		
	}

	//分页查询
	@Override
	public PageBean<Order> findByUid(String uid, Integer currPage) throws Exception{
		PageBean<Order> pageBean = new PageBean<Order>();
		pageBean.setCurrPage(currPage);
		Integer pageSize = 5;
		pageBean.setPageSize(pageSize);
		//设置总记录
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("OrderDao");
		Integer totalCount = orderDao.findCountByUid(uid);
		pageBean.setTotalCount(totalCount);
		//总页数
		double tc = totalCount;
		Double num = Math.ceil(tc/pageSize);
		System.out.println(num);
		pageBean.setTotalPage(num.intValue());
		//查询每页的数据集合
		int begin = (currPage - 1) * pageSize;
		List<Order> list = orderDao.findPageByUid(uid,begin,pageSize);
		pageBean.setList(list);
		return pageBean;
	}

	@Override
	public Order findByOid(String oid) throws Exception {
		OrderDao orderDao = (OrderDao)BeanFactory.getBean("OrderDao");
		Order order = orderDao.findByOid(oid);
		return order;
	}

	@Override
	public void update(Order order) throws SQLException {

		OrderDao orderDao = (OrderDao) BeanFactory.getBean("Order");
		orderDao.update(order);
	}

}
