package com.trafimchuk.veranika.task5.model;

public enum Currency {

    RUB(1.00),
    USD(2.55),
    EUR(2.56);

    private final double rate;

    Currency(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }
}