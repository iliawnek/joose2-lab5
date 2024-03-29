package uk.ac.glasgow.jagora.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.beans.Observable;
import uk.ac.glasgow.jagora.*;

public class DefaultStockExchange implements StockExchange {

    private final Map<Stock, Market> markets;
    private World world;
    private List<Trader> registeredTraders;

    private final List<TickEvent<Trade>> tradeHistory;

    public DefaultStockExchange(World world) {
        this.world = world;
        markets = new HashMap<Stock, Market>();
        tradeHistory = new ArrayList<TickEvent<Trade>>();
        registeredTraders = new ArrayList<>();
    }

    @Override
    public void doClearing() {
        for (Market market : markets.values())
            tradeHistory.addAll(market.doClearing());
    }

    @Override
    public void placeBuyOrder(BuyOrder buyOrder) {
        getMarket(buyOrder.getStock()).placeBuyOrder(buyOrder);
    }

    @Override
    public void placeSellOrder(SellOrder sellOrder) {
        getMarket(sellOrder.getStock()).placeSellOrder(sellOrder);
    }

    @Override
    public void cancelBuyOrder(BuyOrder buyOrder) {
        getMarket(buyOrder.getStock()).cancelBuyOrder(buyOrder);
    }

    @Override
    public void cancelSellOrder(SellOrder sellOrder) {
        getMarket(sellOrder.getStock()).cancelSellOrder(sellOrder);
    }

    @Override
    public Double getBestOffer(Stock stock) {
        return getMarket(stock).getBestOffer();
    }

    @Override
    public Double getBestBid(Stock stock) {
        return getMarket(stock).getBestBid();
    }

    private Market getMarket(Stock stock) {
        Market market = markets.get(stock);
        if (market == null) {
            market = new ContinuousOrderDrivenMarket(stock, world);
            markets.put(stock, market);
        }
        return market;
    }

    @Override
    public List<TickEvent<Trade>> getTradeHistory(Stock stock) {
        return tradeHistory
                .stream()
                .filter(tradeEvent -> tradeEvent.getEvent().getStock().equals(stock))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer("[");
        String template = "%s:[bestBid=%.2f, bestOffer=%.2f], ";
        for (Stock stock : markets.keySet())
            result.append(String.format(template, stock.getName(), getBestOffer(stock), getBestBid(stock)));

        return result.delete(result.length() - 2, result.length()).append("]").toString();
    }

    @Override
    public List<Trader> getRegisteredTraders() {
        return registeredTraders;
    }

    @Override
    public void registerTrader(Trader trader) {
        registeredTraders.add(trader);
    }

    @Override
    public void removeTrader(Trader trader) {
        registeredTraders.remove(trader);
    }

    @Override
    public void notifyTraders() {
        for (Trader trader : registeredTraders) {
            Stock stock;
            for (Map.Entry<Stock, Market> entry : markets.entrySet()) {
                stock = entry.getKey();
                trader.notify(this, stock, getTradeHistory(stock));
            }
        }
    }

}
