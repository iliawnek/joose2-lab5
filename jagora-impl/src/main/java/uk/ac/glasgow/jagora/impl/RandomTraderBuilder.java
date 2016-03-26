package uk.ac.glasgow.jagora.impl;

import uk.ac.glasgow.jagora.Stock;

import java.util.Random;

public class RandomTraderBuilder extends AbstractTraderBuilder {
	protected Integer maxTradeQuantity;
    protected Integer initialTradeQuantity;
	protected double priceRange;
    protected Random random;
    protected Stock stock;

    public RandomTraderBuilder setMaxTradeQuantity(Integer maxTradeQuantity) {
        this.maxTradeQuantity = maxTradeQuantity;
        return this;
    }

    public RandomTraderBuilder setPriceRange(double priceRange) {
        this.priceRange = priceRange;
        return this;
    }

    public RandomTraderBuilder setRandom(Random random) {
        this.random = random;
        return this;
    }

    public RandomTraderBuilder setStock(Stock stock) {
        this.stock = stock;
        return this;
    }

    public RandomTraderBuilder setInitialTradeQuantity(Integer initialTradeQuantity) {
        this.initialTradeQuantity = initialTradeQuantity;
        return this;
    }

    @Override
    public RandomTrader build() {
        return new RandomTrader(name, cash, stock, initialTradeQuantity, maxTradeQuantity, priceRange, random);
    }
}
