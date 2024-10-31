package vn.edu.fpt.fa24.Models.Order;

import vn.edu.fpt.fa24.Models.ProductModel;

public class OrderDetailsModel  {
    private int quantity;
    private double totalPrice;
    private ProductModel product;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }
}
