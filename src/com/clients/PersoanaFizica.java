package com.clients;

public final class PersoanaFizica extends Client {
    private String dataNastere;

    /**
     * Empty constructor for objects of type PersoanaFizica.
     */
    public PersoanaFizica() {
    }

    public String getDataNastere() {
        return dataNastere;
    }

    protected void setDataNastere(String dataNastere) {
        this.dataNastere = dataNastere;
    }

    @Override
    public String toString() {
        return "'PersoanaFizica " + getName() + "'";
    }
}
