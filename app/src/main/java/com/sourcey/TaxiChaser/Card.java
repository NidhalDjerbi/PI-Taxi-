package com.sourcey.TaxiChaser;

/**
 * Created by nidhal on 26/12/2017.
 */

public class Card<S, S1, S2> {
    String numTaxi,NomChauff,Distance;

    public Card(String numTaxi, String nomChauff, String distance) {
        this.numTaxi = numTaxi;
        NomChauff = nomChauff;
        Distance = distance;
    }

    public String getNumTaxi() {
        return numTaxi;
    }

    public void setNumTaxi(String numTaxi) {
        this.numTaxi = numTaxi;
    }

    public String getNomChauff() {
        return NomChauff;
    }

    public void setNomChauff(String nomChauff) {
        NomChauff = nomChauff;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }
}
