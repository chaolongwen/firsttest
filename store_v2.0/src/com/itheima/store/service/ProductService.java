package com.itheima.store.service;

import java.sql.SQLException;
import java.util.List;

import com.itheima.store.domain.PageBean;
import com.itheima.store.domain.Product;

/**
 * 商品服务器接口
 * @author Administrator
 *
 */
public interface ProductService {

	List<Product> findHot() throws SQLException;

	List<Product> findNew() throws SQLException;

	PageBean<Product> findByCid(String cid, Integer currPage) throws SQLException;

	Product findByPid(String pid) throws SQLException;

}
