package model;

import java.nio.file.NoSuchFileException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Incorporates the advance functionality for Stock Portal. Accepts the commission fees. Along with
 * this it will also give all the functionality performed by stock portal operations and on top of
 * it it overloads buy share method with commission and overrides cost basis to encorporate the
 * commission fee as well.
 */
public interface AdvancedStockPortalOperations extends StockPortalOperations {

  /**
   * Allows user to buyShare with a commission value and on weekends or holiday when buy share is
   * called it will buy the share for next working day.
   *
   * @param portfolioName portfolioName
   * @param tickerSymbol  tickerSymbol
   * @param amount        amount
   * @param date          date
   * @param commission    commission
   */
  void buyShare(String portfolioName, String tickerSymbol, double amount, LocalDateTime date,
                double commission);

  /**
   * Checks the validity of the stock if the stock is invalid in the given data type, then it
   * returns false.
   *
   * @param stock takes stock name
   * @return true or false
   * @throws NoSuchFileException if stock name is invalid it will throw an exception
   */
  boolean isValidStock(String stock) throws NoSuchFileException;

  void performDollarCostAveraging(String pfName, String amount, LocalDateTime localStartDate,
                                  String frequency, LocalDateTime localEndDate, String commission,
                                  Map<String, Double> mapper);

  void performFixedInvestments(String pfName, String amount, LocalDateTime localStartDate,
                               String commission,
                               Map<String, Double> mapper);

}
