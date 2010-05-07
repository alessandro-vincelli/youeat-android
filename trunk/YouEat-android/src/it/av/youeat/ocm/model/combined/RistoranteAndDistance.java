package it.av.youeat.ocm.model.combined;

import it.av.youeat.ocm.model.Ristorante;

public class RistoranteAndDistance {

    public RistoranteAndDistance() {
    }

    public RistoranteAndDistance(Ristorante ristorante, Long distance) {
        super();
        this.ristorante = ristorante;
        this.distance = distance;
    }

    private Ristorante ristorante;
    /**
     * distance in meters
     */
    private Long distance;

    public Ristorante getRistorante() {
        return ristorante;
    }

    public void setRistorante(Ristorante ristorante) {
        this.ristorante = ristorante;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

}
