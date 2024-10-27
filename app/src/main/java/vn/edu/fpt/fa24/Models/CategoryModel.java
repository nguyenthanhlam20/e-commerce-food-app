package vn.edu.fpt.fa24.Models;

public class CategoryModel {

    private String categoryName;
    private String categoryDescription;
    private String categoryImage;
    private int categoryId;

    // Constructor
    public CategoryModel(int categoryId, String categoryName, String categoryDescription, String categoryImage) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.categoryImage = categoryImage;
    }

    // Getter and Setter for categoryId
    public String getCategoryId() {
        return String.valueOf(categoryId);
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    // Getter and Setter for categoryName
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // Getter and Setter for categoryDescription
    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    // Getter and Setter for categoryImage
    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }
}
