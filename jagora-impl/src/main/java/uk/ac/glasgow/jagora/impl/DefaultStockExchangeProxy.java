package uk.ac.glasgow.jagora.impl;

import uk.ac.glasgow.jagora.*;

/**
 * @author Ken Li (iliawnek)
 */

public class DefaultStockExchangeProxy implements StockExchangeProxy {
    StockExchange stockExchange;

    public DefaultStockExchangeProxy(StockExchange stockExchange) {
        this.stockExchange = stockExchange;
    }

    @Override
    public void placeBuyOrder(BuyOrder buyOrder) {
        stockExchange.placeBuyOrder(buyOrder);
    }

    @Override
    public void placeSellOrder(SellOrder sellOrder) {
        stockExchange.placeSellOrder(sellOrder);
    }

    @Override
    public void cancelBuyOrder(BuyOrder buyOrder) {
        stockExchange.cancelBuyOrder(buyOrder);
    }

    @Override
    public void cancelSellOrder(SellOrder sellOrder) {
        stockExchange.cancelSellOrder(sellOrder);
    }

    @Override
    public Double getBestOffer(Stock stock) {
        return stockExchange.getBestOffer(stock);
    }

    @Override
    public Double getBestBid(Stock stock) {
        return stockExchange.getBestBid(stock);
    }
}
