package com.itheima.store.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itheima.store.domain.Category;

/**
 * 分类服务器层接口
 * @author Administrator
 *
 */
public interface CategoryService {

	List<Category> findAll() throws SQLException;

}
