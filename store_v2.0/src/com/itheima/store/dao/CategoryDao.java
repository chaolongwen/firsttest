package com.itheima.store.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itheima.store.domain.Category;

/**
 * 分类dao接口
 * @author Administrator
 *
 */
public interface CategoryDao {

	List<Category> findAll() throws SQLException;
	
}
