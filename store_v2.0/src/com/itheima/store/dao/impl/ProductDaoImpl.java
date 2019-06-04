package com.itheima.store.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.store.dao.ProductDao;
import com.itheima.store.domain.Product;
import com.itheima.store.utils.JDBCUtils;

/**
 * 商品dao层实现类
 * @author Administrator
 *
 */
public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> findNew() throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "SELECT * FROM product WHERE pflag=? ORDER BY pdate LIMIT ?";
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(Product.class), 0, 9);
		return list;
	}

	@Override
	public List<Product> findHot() throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "SELECT * FROM product WHERE pflag=? AND is_hot=? ORDER BY pdate LIMIT ?";
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(Product.class), 0, 1, 9);
		return list;
	}

	@Override
	public Integer findCount(String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "SELECT COUNT(*) FROM product WHERE pflag=? and cid=?";
		Long count = (Long) qr.query(sql, new ScalarHandler(), 0, cid);
		return count.intValue();
	}

	@Override
	public List<Product> findPageByCid(String cid, int begin, Integer pageSize) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "SELECT * FROM product WHERE pflag=? AND cid=? ORDER BY pdate LIMIT ?, ?";
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(Product.class), 0, cid, begin, pageSize);
		return list;
	}

	@Override
	public Product findByPid(String pid) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "SELECT * FROM product WHERE pid = ?";
		Product p = qr.query(sql, new BeanHandler<Product>(Product.class), pid);
		return p;
	}

}
