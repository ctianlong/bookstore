package com.ctl.bookstore.domain;

/**
 * 封装了购物车中的商品, 包含对商品的引用以及购物车中该商品的数量
 *
 */
public class ShoppingCartItem {
	
	private Book book;
	private int quantity;
	
	public ShoppingCartItem(Book book) {
		this.book = book;
		this.quantity = 1;
	}
	
	public Book getBook() {
		return book;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public float getItemMoney(){
		return quantity  * book.getPrice();
	}
	
	public void increment(){
		quantity ++;
	}

}
