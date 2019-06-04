package com.itheima.store.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.itheima.store.dao.CategoryDao;
import com.itheima.store.dao.UserDao;
import com.itheima.store.dao.impl.CategoryDaoImpl;
import com.itheima.store.domain.Category;
import com.itheima.store.service.CategoryService;
import com.itheima.store.utils.BeanFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;


/**
 * 分类服务层实现类
 * @author Administrator
 *
 */
public class CategoryServiceImpl implements CategoryService {

	@Override
	public List<Category> findAll() throws SQLException{
		
		
		//做优化，首次查询的时候从数据库查，然后存入缓存
		//再次查询的时候直接从缓存中取，减少对数据开的访问次数
		//实体类要实现序列化接口，对象可能要保存到硬盘
		
		//缓存使用方法/
		//读取配置文件
		CacheManager cm = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		//获得对应名称的缓存区
		Cache cache = cm.getCache("categoryCache");
		//判断缓存中是否有list缓存
		Element element = cache.get("list");
		List<Category> list = null;
		if(element == null) {
			System.out.print("缓存中没有数据，去数据库中查找");
			CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
			list = cd.findAll();
			element = new Element("list", list);
			cache.put(element);
		}else {
			System.out.println("缓存中有数据，直接返回");
			list = (List<Category>) element.getObjectValue();
		}
		return list;
	}

}
