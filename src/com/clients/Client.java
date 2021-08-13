package com.clients;

import com.auction.Auction;

import java.util.*;

/**
 * Abstract Class that represents all Clients.
 */
public abstract class Client {
    private int id;
    private String name;
    private String address;
    private int nrParticipations;
    private int nrWonAuctions;
    /** Map used for storing the maximum price willing to bid at an auction. */
    private final Map<Auction, Double> maxMap = new HashMap<>();

    public Client() {
    }

    /**
     * @return the maximum price willing to bid, else 0
     */
    public double askParticipation() {
        if (new Random().nextBoolean()) {
            return 500 + new Random().nextInt(7000);
        }
        return 0;
    }

    /**
     * @param auction the auction participating in
     * @param auctionPrice the current highest bid
     * @return the minimum between the new bid and the maximum price
     */
    public double askNewBid(Auction auction, double auctionPrice) {
        double offer = auctionPrice + new Random().nextInt(500);
        return Math.min(getMaxPrice(auction), offer);
    }

    /**
     * Cleans all auctions data.
     */
    public void clean() {
        maxMap.clear();
    }

    public void increaseParticipations() {
        nrParticipations++;
    }

    public void increaseWonAuctions() {
        nrWonAuctions++;
    }

    public double getMaxPrice(Auction auction) {
        return maxMap.containsKey(auction) ? maxMap.get(auction) : 0;
    }

    /**
     * Add a maximum price willing to bid at an auction.
     *
     * @param auction the auction participating in
     * @param maxPrice the maximum price willing to bid
     */
    public void setMaxPrice(Auction auction, double maxPrice) {
        maxMap.putIfAbsent(auction, maxPrice);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getNrParticipations() {
        return nrParticipations;
    }

    public int getNrWonAuctions() {
        return nrWonAuctions;
    }

    protected void setId(int id) {
        this.id = id;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setAddress(String address) {
        this.address = address;
    }

    protected void setNrParticipations(int nrParticipations) {
        this.nrParticipations = nrParticipations;
    }

    protected void setNrWonAuctions(int nrWonAuctions) {
        this.nrWonAuctions = nrWonAuctions;
    }
}
