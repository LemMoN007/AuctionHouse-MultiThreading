package com.products;

// POT FI CREATE DOAR PRIN FACTORY
final class Jewelry extends Product {
    private String material;
    private boolean gemstone;

    /**
     * Empty constructor for objects of type Jewelry.
     */
    public Jewelry() {
    }

    public String getMaterial() {
        return material;
    }

    public boolean isGemstone() {
        return gemstone;
    }

    protected void setMaterial(String material) {
        this.material = material;
    }

    protected void setGemstone(boolean gemstone) {
        this.gemstone = gemstone;
    }

    @Override
    public String toString() {
        return "Product (Jewelry from " +
                "material='" + material + '\'' +
                ", gemstone=" + gemstone +
                ")";
    }
}
