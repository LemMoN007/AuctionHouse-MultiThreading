package com.auction;

/**
 * Class that represents the individual auction.
 */
public final class Auction implements Runnable {
    private final int id;
    private final int nrParticipants;
    private int idProduct;
    private final int nrMaxSteps;

    private Auction(Builder builder) {
        this.id = builder.id;
        this.nrParticipants = builder.nrParticipants;
        this.idProduct = builder.idProduct;
        this.nrMaxSteps = builder.nrMaxSteps;
    }

    /**
     * The run() method of the class implementing Runnable interface.
     */
    @Override
    public void run() {
        AuctionHouse.getInstance().setupAuction(this);
    }

    public int getId() {
        return id;
    }

    public int getNrParticipants() {
        return nrParticipants;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public int getNrMaxSteps() {
        return nrMaxSteps;
    }

    protected void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    // BUILDER PATTERN
    public static class Builder {
        private final int id;
        private int nrParticipants;
        private int idProduct;
        private int nrMaxSteps;

        public Builder(int id) {
            this.id = id;
        }

        public Builder addNrParticipants(int nrParticipants) {
            this.nrParticipants = nrParticipants;
            return this;
        }

        public Builder addIdProduct(int idProduct) {
            this.idProduct = idProduct;
            return this;
        }

        public Builder addNrMaxSteps(int nrMaxSteps) {
            this.nrMaxSteps = nrMaxSteps;
            return this;
        }

        public Auction build() {
            return new Auction(this);
        }

    }

    @Override
    public String toString() {
        return "Auction with " +
                "id=" + id +
                ", nrMaxSteps=" + nrMaxSteps +
                ".";
    }


}
