package com.auction;

import com.products.Product;

/**
 * Class that represents the administrator of the auction house.
 */
final class Administrator {
    // SINGLETON
    private static Administrator INSTANCE;

    private Administrator() {
    }

    /**
     * Returns the instance of the object of this class. Only one can
     * be created because of the usage of Singleton Pattern.
     *
     * @return instance of class
     */
    public static Administrator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Administrator();
        }
        return INSTANCE;
    }

    /**
     * Add a product to auction house products list.
     *
     * @param product the product to be added
     */
    protected synchronized void addProduct(Product product) {
        AuctionHouse.getInstance().addProduct(product);
    }

    /**
     * Remove a product from auction house products list.
     *
     * @param product the product to be added
     */
    protected synchronized void removeProduct(Product product) {
        AuctionHouse.getInstance().removeProduct(product);
    }
}
