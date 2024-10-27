package vn.edu.fpt.fa24.Models.Cart;

import java.math.BigDecimal;

import vn.edu.fpt.fa24.Models.ProductModel;

@SuppressWarnings("ALL")
public class CartItemModel {
    private int cartId;
    private int productId;
    private int quantity;
    private BigDecimal unitPrice;
    private double totalPrice;
    private ProductModel products;

    public CartItemModel() {}

    public CartItemModel(int cartId, int productId, int quantity, BigDecimal unitPrice, double totalPrice, ProductModel products) {
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.products = products;
    }

    public ProductModel getProducts() {
        return products;
    }

    public void setProducts(ProductModel products) {
        this.products = products;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductIdStr() {
        return String.valueOf(productId);
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getQuantityStr() {
        return String.valueOf(quantity);
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

}

