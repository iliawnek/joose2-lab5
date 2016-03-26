package uk.ac.glasgow.jagora;

/**
 * @author Ken Li (iliawnek)
 */

public interface StockExchangeProxy {
	public void placeBuyOrder(BuyOrder buyOrder);
	public void placeSellOrder(SellOrder sellOrder);
	public void cancelBuyOrder(BuyOrder buyOrder);
	public void cancelSellOrder(SellOrder sellOrder);
	public Double getBestOffer(Stock stock);
	public Double getBestBid(Stock stock);
}
