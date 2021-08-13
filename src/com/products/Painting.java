package com.products;

final class Painting extends Product {
    private String painterName;
    private enum Colors {
        OIL,
        TEMPERA,
        ACRILIC
    }

    /**
     * Empty constructor for objects of type Painting.
     */
    public Painting() {
    }

    public String getPainterName() {
        return painterName;
    }

    public void setPainterName(String painterName) {
        this.painterName = painterName;
    }

    @Override
    public String toString() {
        return "Product (Painting made by " +
                "painterName='" + painterName + '\'' +
                ")";
    }
}
