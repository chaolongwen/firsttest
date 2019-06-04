package com.itheima.store.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.store.dao.OrderDao;
import com.itheima.store.domain.Order;
import com.itheima.store.domain.OrderItem;
import com.itheima.store.domain.Product;
import com.itheima.store.utils.JDBCUtils;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void saveOrder(Connection conn, Order order) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		Object[] params = {order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),
				order.getName(),order.getTelephone(),order.getUser().getUid()};
		qr.update(conn, sql, params);
	}

	@Override
	public void saveOrderItem(Connection conn, OrderItem orderItem) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orderitem values(?,?,?,?,?)";
		Object[] params = {orderItem.getItemid(),orderItem.getCount(),orderItem.getSubtotal(),
				orderItem.getProduct().getPid(),orderItem.getOrder().getOid()};
		qr.update(conn, sql, params);
	}

	//总共有几条订单
	@Override
	public Integer findCountByUid(String uid) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select count(*) from orders where uid=?";
		Long count = (Long) qr.query(sql, new ScalarHandler(), uid);
		return count.intValue();
	}

	@Override
	public List<Order> findPageByUid(String uid, int begin, Integer pageSize) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where uid=? order by ordertime desc limit ?,?";
		//根据用户id查询用户有多少个订单
		List<Order> list= qr.query(sql, new BeanListHandler<Order>(Order.class), uid, begin, pageSize);
		
		for (Order order : list) {
			//多表查询出订单项和对应的商品
			sql = "SELECT * FROM orderitem o,product p WHERE o.pid = p.pid AND o.oid=?";
			List<Map<String,Object>> oList = qr.query(sql, new MapListHandler(), order.getOid());
			for (Map<String, Object> map : oList) {
				Product product = new Product();
				BeanUtils.populate(product, map);
				OrderItem orderItem = new OrderItem();
				BeanUtils.populate(orderItem, map);
				
				//订单项下有商品
				orderItem.setProduct(product);
				//订单下有多个订单项
				order.getOrderItems().add(orderItem);
			}
		}
		return list;
	}

	@Override
	public Order findByOid(String oid) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where oid=?";
		Order order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);
		//查询出来的order里面的orderItem没有值，需要再次查询填进去
		sql = "select * from orderitem o,product p where o.pid = p.pid and o.oid = ?";
		List<Map<String, Object>> oList = qr.query(sql, new MapListHandler(), oid);
		for (Map<String, Object> map : oList) {
			Product p = new Product();
			BeanUtils.populate(p, map);
			
			OrderItem orderItem = new OrderItem();
			BeanUtils.populate(orderItem, map);
			
			orderItem.setProduct(p);
			order.getOrderItems().add(orderItem);
		}
		return order;
	}

	@Override
	public void update(Order order) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update orders set total = ?,state = ? ,address = ?,name=?,telephone = ? where oid = ?";
		Object[] params = {order.getTotal(), order.getState(), order.getAddress(), order.getName(), order.getTelephone(), order.getOid()};
		qr.update(sql, params);
	}

}
