package com.ctl.bookstore.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
	
	private Map<Integer, ShoppingCartItem> books = new HashMap<Integer, ShoppingCartItem>();
	
	/**
	 * 向购物车中添加一件商品		
	 * @param book
	 */
	public void addBook(Book book){
		ShoppingCartItem sci = books.get(book.getId());
		if(sci == null){
			sci = new ShoppingCartItem(book);
			books.put(book.getId(), sci);
		}else{
			sci.increment();
		}
	}
	
	/**
	 * 检验购物车中是否有 id 指定的商品		
	 * @param id
	 * @return
	 */
	public boolean hasBook(Integer id){
		return books.containsKey(id);
	}
	
	public Map<Integer, ShoppingCartItem> getBooks() {
		return books;
	}
	
	/**
	 * 获取购物车中的所有的 ShoppingCartItem 组成的集合
	 * @return
	 */
	public Collection<ShoppingCartItem> getItems(){
		return books.values();
	}
	
	/**
	 * 返回购物车中商品的总数量
	 * @return
	 */
	public int getBookNumber(){
		int bookNumber = 0;
		for(ShoppingCartItem sci: books.values()){
			bookNumber += sci.getQuantity();
		}
		return bookNumber;
	}
	
	/**
	 * 获取购物车中所有的商品的总的钱数
	 * @return
	 */
	public float getTotalMoney(){
		int totalMoney = 0;
		for(ShoppingCartItem sci: books.values()){
			totalMoney += sci.getItemMoney();
		}
		return totalMoney;
	}
	
	/**
	 * 返回购物车是否为空
	 * @return
	 */
	public boolean isNoBook(){
		return books.isEmpty();
	}
	
	/**
	 * 清空购物车
	 */
	public void clear(){
		books.clear();
	}
	
	/**
	 * 移除指定的购物项
	 * @param id
	 */
	public void removeItem(int id){
		books.remove(id);
	}
	
	/**
	 * 修改指定购物项的数量
	 */
	public void updateItemQuantity(int id, int quantity){
		ShoppingCartItem sci = books.get(id);
		if(sci != null){
			sci.setQuantity(quantity);			
		}
	}

}
