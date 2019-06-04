package com.itheima.store.service;

import java.sql.SQLException;

import com.itheima.store.domain.Order;
import com.itheima.store.domain.PageBean;

/**
 * 订单服务接口
 * @author Administrator
 *
 */
public interface OrderService {

	void save(Order order);

	PageBean<Order> findByUid(String uid, Integer currPage) throws Exception;

	Order findByOid(String oid) throws Exception;

	void update(Order order) throws SQLException;

}
