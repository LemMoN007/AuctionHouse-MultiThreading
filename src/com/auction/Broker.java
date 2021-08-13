package com.auction;

import com.clients.Client;
import com.clients.PersoanaFizica;
import com.products.Product;
import com.proiect.Colors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that represents the individual broker.
 */
final class Broker {
    /** Map containing the list of broker's clients bids from each auction containing the broker. */
    private final Map<Auction, List<ClientBid>> clientMap = new HashMap<>();
    private double commission = 0;

    /**
     * Empty constructor for objects of type Broker.
     */
    public Broker() {
    }

    /**
     * Adds a new client.
     *
     * @param auction the auction the client is participating in
     * @param client the client to be added
     * @param maxPrice the maximum price the client is willing to bid
     */
    public void addClient(Auction auction, Client client, double maxPrice) {
        clientMap.computeIfAbsent(auction, k -> new ArrayList<>());
        clientMap.get(auction).add(new ClientBid(client, 0));
        client.increaseParticipations();
        client.setMaxPrice(auction, maxPrice);
    }

    /**
     * Asks all the broker's clients participating in the auction
     * given as parameter to make a new bid. It is guaranteed that
     * the broker has clients in this auction.
     *
     * @param auction the auction used
     * @param auctionPrice the current highest bid
     * @return the list of new clients bids
     */
    public List<ClientBid> askClientBid(Auction auction, double auctionPrice) {
        List<ClientBid> list = clientMap.get(auction);
        list.forEach(clientBid -> {
            double newBid = clientBid.getClient().askNewBid(auction, auctionPrice);
            clientBid.setBid(newBid);
        });
        return list;
    }

    /**
     * Gets the commission from the winning client.
     *
     * @param client the winning client
     * @param value sum paid for the product
     */
    public void getCommission(Client client, double value) {
        if (client instanceof PersoanaFizica) {
            commission += client.getNrParticipations() <= 5 ? value * 0.2 : value * 0.15;
            return;
        }
        commission += client.getNrParticipations() <= 25 ? value * 0.25 : value * 0.1;
    }

    public double getCommission() {
        return commission;
    }

    /**
     * Cleans all auctions data.
     */
    public void clean() {
        clientMap.clear();
    }

    /**
     * Cleans the data regarding the auction given as parameter.
     *
     * @param auction the auction used
     */
    protected void cleanAuction(Auction auction) {
        clientMap.remove(auction);
    }

    /**
     * Checks if the broker has clients in that auction.
     *
     * @param auction the auction used
     * @return true if the broker contains the auction, else false
     */
    public boolean containsAuction(Auction auction) {
        return clientMap.containsKey(auction);
    }

    /**
     * Checks if the broker has a certain client in a certain auction.
     *
     * @param auction the auction used
     * @param client the client searched for
     * @return true if the broker contains the client, else false
     */
    public boolean containsClient(Auction auction, Client client) {
        if (clientMap.containsKey(auction)) {
            return clientMap.get(auction)
                    .stream()
                    .anyMatch(co -> co.contains(client));
        }
        return false;
    }

    /**
     * Increases the number of auctions won for the client.
     *
     * @param client the client used
     */
    public void increaseWonAuctions(Client client) {
        client.increaseWonAuctions();
    }

    /**
     * Prints all the broker's clients in a certain auction given as parameter.
     *
     * @param auction the auction used
     */
    public void printParticipants(Auction auction) {
        if (containsAuction(auction)) {
            List<ClientBid> list = clientMap.get(auction);
            list.forEach(co -> System.out.println(Colors.ANSI_BLUE + co.getClient() +
                    ": " + co.getClient().getMaxPrice(auction) + Colors.ANSI_RESET));
        }
    }

    /**
     * Removes a product from the list of available products.
     *
     * @param product the product to be removed
     */
    protected synchronized void removeProduct(Product product) {
        AuctionHouse.getInstance().removeProduct(product);
    }

}
