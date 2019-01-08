package model.strategies;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * This interface is to execute commands on the required.
 */
public interface StrategyOperations {

  /**
   * This method will implement the dollar cost averaging startegy, it takes portfolio name, amount,
   * local start date, frequenct, local end date(if not given taken as now date), commission and
   * mapper that has tickers and their corresponding percents. If a portfolio doesnt exist then this
   * method will create a portfolio and adds there stocks, if it is existing,it still does the
   * same.
   *
   * @param pfName         as String
   * @param amount         as String
   * @param localStartDate as local date time
   * @param frequency      as String
   * @param localEndDate   as Local Date time
   * @param commission     as String
   * @param mapper         as map of tickers and their percents.
   * @return boolean which returns false if strategy is success.
   */
  boolean implementDollarCostStrategy(String pfName, String amount, LocalDateTime localStartDate,
                                      String frequency, LocalDateTime localEndDate, String
                                              commission, Map<String, Double> mapper);

  /**
   * This strategy is only applicable to the existing portfolios and specifically to the stocks
   * under this portfolio, user will be given option only to add percents to existing stocks.
   *
   * @param pfName         as String
   * @param amount         as String
   * @param localStartDate as LocalDateTime
   * @param commission     as String
   * @param mapper         as map of tickers and percents.
   * @return boolean that returns flase if the strategy is success.
   */
  boolean implementFixedInvestmentStrategy(String pfName, String amount,
                                           LocalDateTime localStartDate, String commission,
                                           Map<String, Double> mapper);

}
