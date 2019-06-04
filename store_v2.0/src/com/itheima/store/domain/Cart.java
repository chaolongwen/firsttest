package com.itheima.store.domain;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 购物车实体类
 * 订单项
 * 总总额
 * @author Administrator
 *
 */
public class Cart {

	//map集合用于存放购物项的集合列表，因为删除购物项，要用map,用商品id做喂map的key，购物项作为map的value
	private Map<String, CartItem> map = new LinkedHashMap<String, CartItem>();
	private Double total=0d;
	
	public Map<String, CartItem> getMap() {
		return map;
	}
	
	public Double getTotal() {
		return total;
	}
	
	/**
	 * 购物项的方法:
	 */
	// 将购物项添加到购物车:
	public void addCart(CartItem cartItem){
		// 判断购物项是否已经在购物车中
		String pid = cartItem.getProduct().getPid();
		// 判断pid是否在map的key中:
		if(map.containsKey(pid)){
			// 购物车中已经存在该商品
			CartItem _cartItem = map.get(pid);
			_cartItem.setCount(_cartItem.getCount()+cartItem.getCount());
		}else{
			// 购物车中没有该商品:
			map.put(pid, cartItem);
		}
		total += cartItem.getSubtotal();
	}
		
	
	//从购物车中移除购物项 传入pid直接移除，会返回一个value
	public void removeCart(String pid) {
		CartItem item = map.remove(pid);
		total -= item.getSubtotal();
	}
	
	//清空购物车
	public void clearCart() {
		map.clear();
		total = 0d;
	}
	
}
