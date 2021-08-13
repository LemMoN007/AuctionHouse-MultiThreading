package com.products;

/**
 * Builder used for objects of type Product.
 */
public final class ProductBuilder {
    Product product;

    public ProductBuilder(String type) {
        product = Factory.getNewProduct(type);
    }

    public ProductBuilder addID(int id) {
        product.setId(id);
        return this;
    }

    public ProductBuilder addPretVanzare(double pretVanzare) {
        product.setSellPrice(pretVanzare);
        return this;
    }

    public ProductBuilder addPretMinim(double pretMinim) {
        product.setMinimumPrice(pretMinim);
        return this;
    }

    public ProductBuilder addAn(int an) {
        product.setYear(an);
        return this;
    }

    public ProductBuilder addMaterialBijuterie(String material) {
        ((Jewelry) product).setMaterial(material);
        return this;
    }

    public ProductBuilder addMaterialMobila(String material) {
        ((Furniture) product).setMaterial(material);
        return this;
    }

    public ProductBuilder addPiatraPretioasa(boolean piatraPretioasa) {
        ((Jewelry) product).setGemstone(piatraPretioasa);
        return this;
    }

    public ProductBuilder addNumePictor(String numePictor) {
        ((Painting) product).setPainterName(numePictor);
        return this;
    }

    public ProductBuilder addTip(String tip) {
        ((Furniture) product).setType(tip);
        return this;
    }

    public Product build() {
        return product;
    }

    private static class Factory {
        private static Product getNewProduct(String name) {
            return switch (name) {
                case "Tablou" -> new Painting();
                case "Mobila" -> new Furniture();
                case "Bijuterie" -> new Jewelry();
                default -> throw new RuntimeException("Invalid product type.");
            };
        }
    }
}
