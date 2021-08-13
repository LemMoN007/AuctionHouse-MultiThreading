package com.products;

final class Furniture extends Product {
    private String type;
    private String material;

    /**
     * Empty constructor for objects of type Furniture.
     */
    public Furniture() {
    }

    public String getType() {
        return type;
    }

    public String getMaterial() {
        return material;
    }

    protected void setType(String type) {
        this.type = type;
    }

    protected void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "Product (Furniture of " +
                "type='" + type + '\'' +
                ", material='" + material + '\'' +
                ")";
    }
}
