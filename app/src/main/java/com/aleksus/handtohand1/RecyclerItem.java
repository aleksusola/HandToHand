package com.aleksus.handtohand1;

public class RecyclerItem {

    private String title;
    private String description;
    private String collection;
    private String price;

    public RecyclerItem(String title, String description,String collection, String price) {
        this.title = title;
        this.description = description;
        this.collection = collection;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.title = price;
    }
}
