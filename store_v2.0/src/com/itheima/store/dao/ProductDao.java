package com.itheima.store.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.store.domain.Product;

/**
 * 商品dao层接口
 * @author Administrator
 *
 */
public interface ProductDao {

	List<Product> findHot() throws SQLException;

	List<Product> findNew() throws SQLException;

	Integer findCount(String cid) throws SQLException ;

	List<Product> findPageByCid(String cid, int begin, Integer pageSize) throws SQLException;

	Product findByPid(String pid) throws SQLException;

}
