package com.auction;

import com.clients.Client;
import com.products.Product;
import com.proiect.Colors;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public final class AuctionHouse {
    private final Random random = new Random();
    /** List of products available at the auction house. */
    private final List<Product> listProducts;
    /** List of clients. */
    private final List<Client> listClients;
    /** List of auctions. */
    private final List<Auction> listAuctions;
    /** List of brokers. */
    private final List<Broker> listBrokers;
    private final Administrator administrator;

    // Modified Singleton
    private static AuctionHouse INSTANCE = null;

    private AuctionHouse(List<Product> listProducts, List<Client> listClients,
                         List<Auction> listAuctions, int brokers) {
        this.listProducts = Collections.synchronizedList(listProducts);
        this.listClients = listClients;
        this.listAuctions = listAuctions;
        this.listBrokers = new ArrayList<>();
        this.administrator = Administrator.getInstance();
        IntStream.range(0, brokers).mapToObj(i -> new Broker()).forEach(listBrokers::add);
    }

    /**
     * Returns the instance of the object of this class. Only one can
     * be created because of the usage of Singleton Pattern. The method
     * can only be called after "init()".
     *
     * @return the instance of the class
     */
    public static AuctionHouse getInstance() {
        if(INSTANCE == null) {
            throw new AssertionError("You have to call init first.");
        }
        return INSTANCE;
    }

    /**
     * The first method to be called in this class. Initializes all
     * the class fields and is used as part of the Singleton Pattern.
     *
     * @param listProducts list of products
     * @param listClients list of clients
     * @param listAuctions list of auctions
     * @param brokers number of brokers available
     * @return the instance of the class
     */
    public static AuctionHouse init(List<Product> listProducts, List<Client> listClients,
                                    List<Auction> listAuctions, int brokers) {
        if (INSTANCE != null) {
            throw new AssertionError("Class already initialized.");
        }
        INSTANCE = new AuctionHouse(listProducts, listClients, listAuctions, brokers);
        return INSTANCE;
    }

    /**
     * Adds a product to the list of available products.
     *
     * @param product the product to be added
     */
    protected synchronized void addProduct(Product product) {
        listProducts.add(product);
    }

    /**
     * Removes a product from the list of available products.
     *
     * @param product the product to be removed
     */
    protected synchronized void removeProduct(Product product) {
        listProducts.remove(product);
    }

    /**
     * The starting method of the class, starts the auctions and one thread
     * is assigned per auction. All auctions take place simultaneously.
     */
    public void start() {
        cleanAuction();
        ExecutorService executor = Executors.newFixedThreadPool(listAuctions.size());
        listAuctions.forEach(executor::execute);
        executor.shutdown();
    }

    /**
     * Setting up the auction, this includes obtaining the product, asking clients if they
     * want to participate, assigning brokers to clients and finally checking whether
     * the minimum requirements are met.
     *
     * @param auction the auction used
     */
    public synchronized void setupAuction(Auction auction) {
        int nrParticipants = 0;
        // check if the auction can take place
        if (auction.getNrParticipants() > listClients.size() || listProducts.size() == 0)
            return;

        // obtaining the product and removing it from the list of available products
        Product product = listProducts.get(0);
        auction.setIdProduct(product.getId());
        administrator.removeProduct(product);
        System.out.println(Colors.ANSI_RED + Thread.currentThread().getName() + "\n" + product +
                Colors.ANSI_RESET);

        // ask clients if they want to participate
        for (Client client : listClients) {
            double maxPrice;
            if ((maxPrice = client.askParticipation()) > 0) {
                // check if maximum price of the client if higher than the minimum price of product
                if (maxPrice < product.getMinimumPrice())
                    continue;
                // assign a random broker and modify accordingly
                Broker chosenBroker = listBrokers.get(random.nextInt(listBrokers.size()));
                chosenBroker.addClient(auction, client, maxPrice);
                nrParticipants++;
            }
        }

        // check whether the minimum requirements are met
        if (nrParticipants < auction.getNrParticipants()) {
            System.out.println(Colors.ANSI_YELLOW + "Too few participants, the auction is canceled.\n" +
                                Colors.ANSI_RESET);
            cleanBrokers(auction);
            return;
        }

        try {
            System.out.println(Colors.ANSI_BLUE + "Number of participants: " + nrParticipants + Colors.ANSI_RESET);
            printParticipants(auction);
            wait(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // start the auction
        beginAuction(auction, product);
    }

    /**
     * The auction begins, the clients start bidding for a certain number of rounds,
     * after which the highest bid wins if the price is higher than the minimum threshold.
     *
     * @param auction the auction used
     * @param product the product used for the auction
     */
    private synchronized void beginAuction(Auction auction, Product product) {
        notifyAll();
        ClientBid bestAll = new ClientBid(null, -1);
        ClientBid bestRound;

        System.out.println(Colors.ANSI_RED + ">>>[" + product + "]<<<" + Colors.ANSI_RESET);
        for (int i = 0; i < auction.getNrMaxSteps(); i++) {
            List<ClientBid> listOffers = askBid(auction, bestAll.getBid());

            System.out.println("Current bids: ");
            listOffers.stream()
                    .map(ClientBid::getBid)
                    .forEach(System.out::println);

            // get the highest bid of the round
            bestRound = listOffers.stream()
                    .max(Comparator.comparing(ClientBid::getBid)
                            .thenComparing(el -> el.getClient().getNrWonAuctions()))
                    .orElse(new ClientBid(null, 0));
            bestAll = bestAll.getBid() < bestRound.getBid() ? bestRound : bestAll;

            System.out.println("---------------");
            System.out.println(Colors.ANSI_PURPLE + "Current highest bid: " +
                    bestAll.getClient() + "\n" + bestAll.getBid() + Colors.ANSI_RESET);
            System.out.println("---------------");
        }

        // check minimum requirements
        if (bestAll.getBid() >= product.getMinimumPrice()) {
            System.out.println(Colors.ANSI_YELLOW + "Congratulations to " + bestAll.getClient() +
                    " for winning '" + product + "'\n" + Colors.ANSI_RESET);
            product.setSellPrice(bestAll.getBid());
            // get the winning client's broker
            Broker brokerWinner = findBroker(auction, bestAll.getClient());
            increaseWonAuctions(brokerWinner, bestAll.getClient());
            brokerCommission(brokerWinner, bestAll.getClient(), bestAll.getBid());
        } else {
            System.out.println(Colors.ANSI_YELLOW + "The minimum price requested by the seller has not been reached." +
                    "\n"  + Colors.ANSI_RESET);
        }

        printBrokersCommission();
    }

    private void cleanBrokers(Auction auction) {
        listBrokers.forEach(broker -> broker.cleanAuction(auction));
    }

    /**
     * Get the new bids from all the clients participating at the auction.
     *
     * @param auction the auction used
     * @param auctionPrice the current highest bid
     * @return the list of new bids
     */
    private List<ClientBid> askBid(Auction auction, double auctionPrice) {
        List<ClientBid> list = new ArrayList<>();
        // add returned lists of new bids
        listBrokers.stream()
                .filter(broker -> broker.containsAuction(auction))
                .map(broker -> broker.askClientBid(auction, auctionPrice))
                .forEach(list::addAll);
        return list;
    }

    /**
     * Finds the client's broker. It is guaranteed that it will always be found.
     *
     * @param auction the auction used
     * @param client the client
     * @return the client's broker
     */
    private synchronized Broker findBroker(Auction auction, Client client) {
        return listBrokers.stream()
                .filter(broker -> broker.containsClient(auction, client))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Broker not found, this should never happen."));
    }

    /**
     * Increase the number of auctions won by the client given as parameter.
     *
     * @param broker the winning client's broker
     * @param client the winning client
     */
    private void increaseWonAuctions(Broker broker, Client client) {
        broker.increaseWonAuctions(client);
    }

    /**
     * The winning client pays the commission to its broker.
     *
     * @param broker the winning client's broker
     * @param client the winning client
     * @param value sum paid for the product
     */
    private void brokerCommission(Broker broker, Client client, double value) {
        broker.getCommission(client, value);
    }

    /**
     * Prints the current profits of all the brokers.
     */
    private void printBrokersCommission() {
        System.out.println(Colors.ANSI_YELLOW + "Current Brokers Profits: ");
        listBrokers.stream()
                .mapToDouble(Broker::getCommission)
                .map(el -> Math.floor(el * 100) / 100)
                .forEach(System.out::println);
        System.out.println(Colors.ANSI_RESET);
    }

    private void printParticipants(Auction auction) {
        listBrokers.forEach(broker -> broker.printParticipants(auction));
        System.out.println();
    }

    /**
     * Cleans the last auction's data from clients and brokers accounts.
     */
    private synchronized void cleanAuction() {
        listClients.forEach(Client::clean);
        listBrokers.forEach(Broker::clean);
    }
}
