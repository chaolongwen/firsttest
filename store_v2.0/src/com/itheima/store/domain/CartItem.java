package com.itheima.store.domain;

/**
 * 订单项
 * 商品
 * 商品数量
 * 商品小结
 * @author Administrator
 *
 */
public class CartItem {
	
	private Product product;
	private Integer count;
	private Double subtotal;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	// 不是设置的是直接获取 购物项里面商品的数量乘与商品商城价格
	public Double getSubtotal() {
		return count * product.getShop_price();
	}
	
	// 
	/*public void setTotal(Double total) {
		this.total = total;
	}*/
	
	
}
