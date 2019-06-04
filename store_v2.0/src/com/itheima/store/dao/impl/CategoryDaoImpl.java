package com.itheima.store.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.store.dao.CategoryDao;
import com.itheima.store.domain.Category;
import com.itheima.store.utils.JDBCUtils;
/**
 * 分类dao接口实现类
 * @author Administrator
 *
 */
public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> findAll() throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from category";
		List<Category> list = qr.query(sql, new BeanListHandler<Category>(Category.class));
		return  list;
	}

}
