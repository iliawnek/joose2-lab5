package uk.ac.glasgow.jagora.impl;

import java.util.*;

import uk.ac.glasgow.jagora.*;

public abstract class AbstractTrader implements Trader {

	private final String name;
	private Double cash;
	private final Map<Stock,Integer> inventory;
	private Map<StockExchange, Map<Stock, List<TickEvent<Trade>>>> storedTradeHistory;
	
	public AbstractTrader(String name, Double cash, Map<Stock, Integer> inventory) {
		this.name = name;
		this.cash = cash;
		this.inventory = new HashMap<Stock,Integer>(inventory);
		storedTradeHistory = new HashMap<>();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Double getCash() {
		return cash;
	}
	
	@Override
	public Set<Stock> getTradingStocks() {
		return new HashSet<Stock>(inventory.keySet());
	}

	@Override
	public void sellStock(Stock stock, Integer quantity, Double price) throws TradeException {
		Integer availableQuantity = getInventoryHolding(stock);
		if (availableQuantity < quantity)
			throw new TradeException("Seller couldn't satisfy trade.", this);
		else {
			Double cost = quantity * price;
			cash += cost;
			inventory.put(stock, availableQuantity - quantity);
		}
	}

	@Override
	public void buyStock(Stock stock, Integer quantity, Double price) throws TradeException {
		double cost = price  * quantity;
		if (cash < cost)
			throw new TradeException("Buyer couldn't satisfy trade", this);
		else {
			Integer availableQuantity = getInventoryHolding(stock);
			cash -= cost;
			inventory.put(stock, availableQuantity + quantity);
		}
	}

	@Override
	public Integer getInventoryHolding(Stock stock) {
		return inventory.getOrDefault(stock, 0);
	}

	@Override
	public void notify(StockExchange stockExchange, Stock stock, List<TickEvent<Trade>> tradeHistory) {
		if (storedTradeHistory.containsKey(stockExchange)) {
			storedTradeHistory.get(stockExchange).put(stock, tradeHistory);
		}
		else {
			HashMap<Stock, List<TickEvent<Trade>>> trades = new HashMap<>();
			trades.put(stock, tradeHistory);
			storedTradeHistory.put(stockExchange, trades);
		}
	}

	@Override
	public List<TickEvent<Trade>> getStoredTradeHistory(StockExchange stockExchange, Stock stock) {
		if (storedTradeHistory.containsKey(stockExchange)) {
			if (storedTradeHistory.get(stockExchange).containsKey(stock)) {
				return storedTradeHistory.get(stockExchange).get(stock);
			}
			else return new ArrayList<>();
		}
		else return new ArrayList<>();
	}
	
	@Override 
	public String toString (){
		return String.format("%s[cash=%.2f,stocks=%s]",getName(),getCash(),inventory);
	}

}
