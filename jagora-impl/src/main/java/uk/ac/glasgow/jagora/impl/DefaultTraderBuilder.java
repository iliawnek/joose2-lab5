package uk.ac.glasgow.jagora.impl;

import uk.ac.glasgow.jagora.Stock;

import java.util.Map;

public class DefaultTraderBuilder extends AbstractTraderBuilder {

    @Override
    public DefaultTrader build() {
        return new DefaultTrader(name, cash, inventory);
    }
}
