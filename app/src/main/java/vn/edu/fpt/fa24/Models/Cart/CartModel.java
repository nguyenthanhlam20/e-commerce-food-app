package vn.edu.fpt.fa24.Models.Cart;

import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.fa24.Models.ProductModel;

@SuppressWarnings("ALL")
public class CartModel {
    private int cartId;

    private Integer userId;
    private List<CartItemModel> cartItems = new ArrayList<>();

    // Constructor to initialize all fields
    public CartModel(int cartId, Integer userId, List<CartItemModel> cartItems) {
        this.cartId = cartId;
        this.userId = userId;
        this.cartItems = cartItems;
    }

    public ArrayList<ProductModel> getProducts() {
        ArrayList<ProductModel> products = new ArrayList<>();
        for (CartItemModel cartItem : cartItems) {
            ProductModel product = new ProductModel();
            product.setProductName(cartItem.getProducts().getProductName());
            product.setProductImage(cartItem.getProducts().getProductImage());
            product.setDescription(cartItem.getProducts().getDescription());
            product.setBrandName(cartItem.getProducts().getBrandName());
            product.setUnitPrice(cartItem.getProducts().getDoubleUnitPrice());
            product.setNumberOfStock(cartItem.getProducts().getNumberOfStock());
            product.setProductId(cartItem.getProducts().getRawProductId());
            products.add(product);
        }
        return  products;
    }

    // Getters and Setters
    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public List<CartItemModel> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemModel> cartItems) {
        this.cartItems = cartItems;
    }
}
