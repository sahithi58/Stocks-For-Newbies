package model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This interface will have methods replaced to do all operations can be done on a portfolio. This
 * interface is package private because this methods are used buy the portfolio already.
 */

public interface PortfolioOperations {

  /**
   * From a portfolio we can get the view of the portfolio.
   *
   * @return the current requested portfolios name and the stock in it in the form of string thus
   *         making it unmodifiable.
   */
  String getPortfolio();

  /**
   * This method can get all the stocks of that current requested portfolio.
   *
   * @return it returns the list of stock
   */
  List<Stock> getStocks();

  /**
   * In this method a stock can be added to a portfolio.
   *
   * @param amount       the money that is being invested by the user.
   * @param tickerSymbol the companies unique code symbol which is assumed to be known to the user.
   * @param date         the past date or (***current date**) from which the stock is pulled.
   * @param pps          this will share the price per stock ie., given amount and ticker price we
   *                     could get the price per stock.
   */
  void addStockToPortfolio(double amount, String tickerSymbol, LocalDateTime date, double pps);

  /**
   * In this method a stock can be added to a portfolio with commission.
   *
   * @param amount       the money that is being invested by the user.
   * @param tickerSymbol the companies unique code symbol which is assumed to be known to the user.
   * @param date         the past date or (***current date**) from which the stock is pulled.
   * @param pps          this will share the price per stock ie., given amount and ticker price we
   *                     could get the price per stock.
   * @param commission   that the value
   */
  void addStockToPFWithCommission(double amount, String tickerSymbol, LocalDateTime date,
                                  double pps, double commission);

}
