package vn.edu.fpt.fa24.Models;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductModel {

    private int productId;
    private String productName;
    private String productImage;
    private String description;
    private String brandName;
    private Double unitPrice;
    private Integer numberOfStock;

    public ProductModel() {

    }

    public String getProductId() {
        return String.valueOf(productId);
    }

    public int getRawProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getFormattedUnitPrice() {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        return currencyFormatter.format(unitPrice);
    }

    public String getUnitPrice() {
        return unitPrice.toString();
    }

    public Double getDoubleUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getNumberOfStock() {
        return numberOfStock;
    }

    public void setNumberOfStock(Integer numberOfStock) {
        this.numberOfStock = numberOfStock;
    }
}
