package model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * This interface is the contract that states the advanced functionality provided to save/retrieve
 * portfolio/strategy.
 */
public interface IAdvancedPlusStockPortal extends AdvancedStockPortalOperations {

  /**
   * This function takes portfolio name as input and save the portfolio object in a file. It will
   * overwrite the file if the file with same portfolio name exists.
   */
  void savePortfolio(String portfolio);

  /**
   * This function returns the given portfolio after reading it from the file and adding it to the
   * current list of portfolios that the user has so that the user can access the portfolio in the
   * session.
   */
  List<PortfolioOperations> retrievePortfolio(String portfolio);

  /**
   * This function takes Map of strategy attributes as input and saves the strategy object in a
   * file. It will overwrite the file if the file with same strategy name exists.
   */
  void saveStrategy(Map<String, String> map);

  /**
   * It takes strategy name and portfolio name as input i.e the strategy and the portfolio on which
   * the strategy has to be applied and applies the strategy on the portfolio.
   */
  void retrieveStrategyAndApplyToPortfolio(String strategy, String portfolio);

  List<Double> plotGraph(String portfolioName, LocalDateTime startDate, LocalDateTime endDate);

}
