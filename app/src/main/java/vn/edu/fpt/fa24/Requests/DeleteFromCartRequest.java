package vn.edu.fpt.fa24.Requests;

public class DeleteFromCartRequest {

    private String userId;
    private String productId;

    // All-args constructor
    public DeleteFromCartRequest(String userId, String productId) {
        this.userId = userId;
        this.productId = productId;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}

