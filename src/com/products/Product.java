package com.products;

/**
 * Abstract Class that represents all Products.
 */
public abstract class Product {
    private int id;
    private double sellPrice;
    private double minimumPrice;
    private int year;

    public Product() {
    }

    public int getId() {
        return id;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public double getMinimumPrice() {
        return minimumPrice;
    }

    public int getYear() {
        return year;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    protected void setMinimumPrice(double minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    protected void setYear(int year) {
        this.year = year;
    }
}
