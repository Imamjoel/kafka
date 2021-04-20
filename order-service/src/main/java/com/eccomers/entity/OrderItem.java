package com.eccomers.entity;


import javax.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue
    private int orderItemId;

    @Column(nullable = false, length = 200)
    private String itemName;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private double quantitiy;

    @JoinColumn(name = "order_id")
    @ManyToOne
    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantitiy() {
        return quantitiy;
    }

    public void setQuantitiy(double quantitiy) {
        this.quantitiy = quantitiy;
    }

	@Override
	public String toString() {
		return "OrderItem [orderItemId=" + orderItemId + ", itemName=" + itemName + ", price=" + price + ", quantitiy="
				+ quantitiy + ", order=" + order + "]";
	}


    
}
