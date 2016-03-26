package uk.ac.glasgow.jagora.impl;

import uk.ac.glasgow.jagora.Stock;

import java.util.Map;

public abstract class AbstractTraderBuilder {
    protected String name;
    protected Double cash;
    protected Map<Stock, Integer> inventory;

    public AbstractTraderBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public AbstractTraderBuilder setCash(Double cash) {
        this.cash = cash;
        return this;
    }

    public AbstractTraderBuilder addStock(Stock stock, Integer quantity) {
        if (inventory.containsKey(stock)) {
            inventory.put(stock, inventory.get(stock) + quantity);
        }
        else {
            inventory.put(stock, quantity);
        }
        return this;
    }

    public AbstractTraderBuilder addStocks(Map<Stock, Integer> stocks) {
        for (Map.Entry<Stock, Integer> entry : stocks.entrySet()) {
            addStock(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public abstract AbstractTrader build();
}
