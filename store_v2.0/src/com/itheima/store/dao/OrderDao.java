package com.itheima.store.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.itheima.store.domain.Order;
import com.itheima.store.domain.OrderItem;

/**
 * 订单dao层接口
 * @author Administrator
 *
 */
public interface OrderDao {

	void saveOrder(Connection conn, Order order) throws SQLException;

	void saveOrderItem(Connection conn, OrderItem orderItem) throws SQLException;

	Integer findCountByUid(String uid) throws SQLException;

	List<Order> findPageByUid(String uid, int begin, Integer pageSize) throws Exception;

	Order findByOid(String oid) throws Exception;

	void update(Order order) throws SQLException;

}
