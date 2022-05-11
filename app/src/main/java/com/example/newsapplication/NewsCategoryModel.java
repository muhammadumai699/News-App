package com.example.newsapplication;


public class NewsCategoryModel {

    private String categories;
    private String categoryImageUrl;


    //Constructor
    public NewsCategoryModel(String categories, String categoryImageUrl) {
        this.categories = categories;
        this.categoryImageUrl = categoryImageUrl;
    }


    //Getters & Setters
    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getCategoryImageUrl() {
        return categoryImageUrl;
    }

    public void setCategoryImageUrl(String categoryImageUrl) {
        this.categoryImageUrl = categoryImageUrl;
    }
}
