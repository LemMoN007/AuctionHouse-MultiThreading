package com.clients;

/**
 * Builder used for objects of type Client.
 */
public final class ClientBuilder {
    Client client;

    public ClientBuilder(String type) {
        client = Factory.getNewProduct(type);
    }

    public ClientBuilder addID(int id) {
        client.setId(id);
        return this;
    }

    public ClientBuilder addNume(String nume) {
        client.setName(nume);
        return this;
    }

    public ClientBuilder addAdresa(String adresa) {
        client.setAddress(adresa);
        return this;
    }

    public ClientBuilder addNrParticipari(int nrParticipari) {
        client.setNrParticipations(nrParticipari);
        return this;
    }

    public ClientBuilder addNrLicitatiiCastigate(int nrLicitatiiCastigate) {
        client.setNrWonAuctions(nrLicitatiiCastigate);
        return this;
    }

    public ClientBuilder addDataNastere(String dataNastere) {
        ((PersoanaFizica) client).setDataNastere(dataNastere);
        return this;
    }

    public ClientBuilder addCapitalSocial(double capitalSocial) {
        ((PersoanaJuridica) client).setCapitalSocial(capitalSocial);
        return this;
    }

    public Client build() {
        return client;
    }

    private static class Factory {
        private static Client getNewProduct(String name) {
            return switch (name) {
                case "PersoanaFizica" -> new PersoanaFizica();
                case "PersoanaJuridica" -> new PersoanaJuridica();
                default -> throw new RuntimeException("Invalid client type.");
            };
        }
    }
}
