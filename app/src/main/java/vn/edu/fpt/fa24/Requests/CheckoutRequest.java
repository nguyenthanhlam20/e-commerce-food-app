package vn.edu.fpt.fa24.Requests;

import java.util.List;

public class CheckoutRequest {
    private String userId;
    private List<CartRequest> carts;
    private String requireDate;
    private String shipAddress;
    private String shipPrice;

    public CheckoutRequest() {}
    // All-args constructor
    public CheckoutRequest(String userId, List<CartRequest> carts, String requireDate, String shipAddress, String shipPrice) {
        this.userId = userId;
        this.carts = carts;
        this.requireDate = requireDate;
        this.shipAddress = shipAddress;
        this.shipPrice = shipPrice;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartRequest> getCarts() {
        return carts;
    }

    public void setCarts(List<CartRequest> carts) {
        this.carts = carts;
    }

    public String getRequireDate() {
        return requireDate;
    }

    public void setRequireDate(String requireDate) {
        this.requireDate = requireDate;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getShipPrice() {
        return shipPrice;
    }

    public void setShipPrice(String shipPrice) {
        this.shipPrice = shipPrice;
    }
}
