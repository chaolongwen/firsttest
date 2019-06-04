package com.itheima.store.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.itheima.store.dao.ProductDao;
import com.itheima.store.dao.impl.ProductDaoImpl;
import com.itheima.store.domain.PageBean;
import com.itheima.store.domain.Product;
import com.itheima.store.service.ProductService;
import com.itheima.store.utils.BeanFactory;

/**
 * 商品服务层实现类
 * @author Administrator
 *
 */
public class ProductServiceImpl implements ProductService {

	@Override
	public List<Product> findHot() throws SQLException{
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		List<Product> list = pd.findHot();
		return list;
	}

	@Override
	public List<Product> findNew() throws SQLException{
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		List<Product> list = pd.findNew();
		return list;
	}

	//返回的是一页的数据
	@Override
	public PageBean<Product> findByCid(String cid, Integer currPage) throws SQLException {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		
		PageBean<Product> pb = new PageBean<Product>();
		//当前页
		pb.setCurrPage(currPage);
		//每页记录数
		Integer pageSize = 12; //每页记录数
		pb.setPageSize(pageSize); 
		//当前分类总的数量
		Integer totalCount = pd.findCount(cid);
		pb.setTotalCount(totalCount);
		//总页数
		double tc = totalCount; //转格式
		Double num = Math.ceil(tc / pageSize); 
		pb.setTotalPage(num.intValue()); //取整
		
		//查出这一页的总记录
		int begin = (currPage-1) * pageSize; //第一页0 第二页12 第三页 24开始
		List<Product> list = pd.findPageByCid(cid, begin, pageSize);
		pb.setList(list); //这页的数据实体
		return pb;
	}

	@Override
	public Product findByPid(String pid) throws SQLException {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		Product p = pd.findByPid(pid);
		return p;
	}

}
