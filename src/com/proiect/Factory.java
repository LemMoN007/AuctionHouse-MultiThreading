package com.proiect;

import com.auction.Auction;
import com.clients.ClientBuilder;
import com.products.ProductBuilder;

import java.util.UUID;

/**
 * Factory class used for creating the objects based
 * on the data in the input file.
 */
public final class Factory {

    /**
     * Creates a new object based on the values received.
     *
     * @param values the input needed for creating the object
     * @return a new object of type Client/Product/Auction, based on the input
     */
    public static Object create(String[] values) {
        int uid = UUID.randomUUID().hashCode();
        switch (values[0]) {
            case Constants.PERSOANA_FIZICA -> {
                return new ClientBuilder(values[0])
                        .addID(uid)
                        .addNume(values[1])
                        .addAdresa(values[2])
                        .addNrParticipari(0)
                        .addNrLicitatiiCastigate(0)
                        .addDataNastere(values[3])
                        .build();
            }
            case Constants.PERSOANA_JURIDICA -> {
                return new ClientBuilder(values[0])
                        .addID(uid)
                        .addNume(values[1])
                        .addAdresa(values[2])
                        .addNrParticipari(0)
                        .addNrLicitatiiCastigate(0)
                        .addCapitalSocial(Double.parseDouble(values[3]))
                        .build();
            }
            case Constants.PAINTING -> {
                return new ProductBuilder(values[0])
                        .addID(uid)
                        .addPretVanzare(-1)
                        .addPretMinim(Double.parseDouble(values[1]))
                        .addAn(Integer.parseInt(values[2]))
                        .addNumePictor(values[3])
                        .build();
            }
            case Constants.FURNITURE -> {
                return new ProductBuilder(values[0])
                        .addID(uid)
                        .addPretVanzare(-1)
                        .addPretMinim(Double.parseDouble(values[1]))
                        .addAn(Integer.parseInt(values[2]))
                        .addTip(values[3])
                        .addMaterialMobila(values[4])
                        .build();
            }
            case Constants.JEWELRY -> {
                return new ProductBuilder(values[0])
                        .addID(uid)
                        .addPretVanzare(-1)
                        .addPretMinim(Double.parseDouble(values[1]))
                        .addAn(Integer.parseInt(values[2]))
                        .addMaterialBijuterie(values[3])
                        .addPiatraPretioasa(Boolean.parseBoolean(values[4]))
                        .build();
            }
            case Constants.AUCTION -> {
                return new Auction.Builder(uid)
                        .addNrParticipants(Integer.parseInt(values[1]))
                        .addIdProduct(-1)
                        .addNrMaxSteps(Integer.parseInt(values[2]))
                        .build();
            }
        }
//        throw new RuntimeException("Invalid input.");
        return new InvalidObject();
    }
}
