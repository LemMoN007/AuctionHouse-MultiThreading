package com.proiect;

import com.auction.AuctionHouse;
import com.auction.Auction;
import com.clients.Client;
import com.products.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Core class that reads the input from a file and based
 * on it builds the lists used for creating the Auction House.
 */
public final class Database {
    // SINGLETON
    private static Database INSTANCE;

    private Database() {
    }

    /**
     * Returns the instance of the object of this class. Only one can
     * be created because of the usage of Singleton Pattern.
     *
     * @return instance of class
     */
    public static Database getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new Database();
        }
        return INSTANCE;
    }

    /**
     * Reads the input from the file with the name given as parameter,
     * builds the lists needed for creating the Auction House and then
     * starts the auction for all the products available.
     *
     * @param file name of the input file
     */
    public void start(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            List<Product> listProducts = new ArrayList<>();
            List<Client> listClients = new ArrayList<>();
            List<Auction> listAuctions = new ArrayList<>();
            int brokers = Integer.parseInt(br.readLine().split(" ")[0]);

            String input;
            while ((input = br.readLine()) != null) {
                String[] values = input.split(" ");
                // create the object based on the given input
                Object result = Factory.create(values);

                // add the created object to a list based on type
                switch (getType(result)) {
                    case Constants.CLIENT -> listClients.add((Client) result);
                    case Constants.PRODUCT -> listProducts.add((Product) result);
                    case Constants.AUCTION -> listAuctions.add((Auction) result);
                }
            }
            // create the auction house and start the auctions for the available products
            AuctionHouse auctionHouse = AuctionHouse.init(listProducts, listClients, listAuctions, brokers);
            int nrRounds = listProducts.size() / listAuctions.size() + 1;
            for (int i = 0; i < nrRounds; i++) {
                auctionHouse.start();
                Thread.sleep(6000);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("This should never happen.", e);
        }
    }

    private boolean isClient(Object obj) {
        return obj instanceof Client;
    }

    private boolean isProduct(Object obj) {
        return obj instanceof Product;
    }

    private boolean isAuction(Object obj) {
        return obj instanceof Auction;
    }

    /**
     * Determines the type of the object given as parameter.
     *
     * @param obj the object analyzed
     * @return type of object as String
     */
    private String getType(Object obj) {
        if (isClient(obj))
            return Constants.CLIENT;
        if (isProduct(obj))
            return Constants.PRODUCT;
        if (isAuction(obj))
            return Constants.AUCTION;
        throw new RuntimeException("Invalid value.");
    }
}
