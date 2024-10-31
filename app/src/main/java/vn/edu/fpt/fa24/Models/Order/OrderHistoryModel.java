package vn.edu.fpt.fa24.Models.Order;

import java.time.LocalDateTime;
import java.util.List;

public class OrderHistoryModel {
    private int orderID;
    private String orderDate;
    private String requireDate;
    private String shipAddress;
    private double shipPrice;
    private boolean status;
    private List<OrderDetailsModel> orderDetails;

    // Getters and Setters
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
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

    public double getShipPrice() {
        return shipPrice;
    }

    public void setShipPrice(double shipPrice) {
        this.shipPrice = shipPrice;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public List<OrderDetailsModel> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailsModel> orderDetails) {
        this.orderDetails = orderDetails;
    }

}
