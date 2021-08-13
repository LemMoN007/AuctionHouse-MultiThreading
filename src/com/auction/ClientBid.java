package com.auction;

import com.clients.Client;

final class ClientBid {
    private final Client client;
    private double bid;

    public ClientBid(Client client, double bid) {
        this.client = client;
        this.bid = bid;
    }

    public boolean contains(Client client) {
        return this.client == client;
    }

    public Client getClient() {
        return client;
    }

    public double getBid() {
        return bid;
    }

    protected void setBid(double bid) {
        this.bid = bid;
    }
}
