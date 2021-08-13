package com.clients;

final class PersoanaJuridica extends Client {
    private double capitalSocial;
    private enum Companie {
        SRL,
        SA
    }

    /**
     * Empty constructor for objects of type PersoanaJuridica.
     */
    public PersoanaJuridica() {
    }

    public double getCapitalSocial() {
        return capitalSocial;
    }

    protected void setCapitalSocial(double capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    @Override
    public String toString() {
        return "'PersoanaJuridica " + getName() + "'";
    }
}
