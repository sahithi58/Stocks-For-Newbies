package model;

import java.nio.file.NoSuchFileException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.NoSuchElementException;

import model.strategies.StrategyOperationImpl;
import model.strategies.StrategyOperations;

/**
 * The method implements Advanced Stock portal interface, which extends Stock Portal class and has
 * the functionality of doing transaction on stocks with the commission fee.
 */
public class AdvancedStockPortal extends StockPortal implements AdvancedStockPortalOperations {

  private StrategyOperations strategyOperations;

  /**
   * This constructor initializes the list of strategis and strategies.
   */
  public AdvancedStockPortal() {
    super();
    strategyOperations = new StrategyOperationImpl(this);
  }


  @Override
  public boolean isValidStock(String stock) throws NoSuchFileException {
    return stockDataRetrievalOperations.validateTickerSymbol(stock);
  }

  /**
   * This method add stock to the portfolio with commission as input.
   */
  private void helperToAddStock(double commission, LocalDateTime date, PortfolioOperations
          portfolio, String tickerSymbol, double amount) {
    if (nullCheck(tickerSymbol) && negativeAndZeroAmount(amount)) {
      long i = 0;
      while (i <= 3) {
        try {
          double sharePrice = 0;
          if (amount != 0) {
            sharePrice = getStockPrice(tickerSymbol, date.plusDays(i));
          }
          portfolio.addStockToPFWithCommission(amount, tickerSymbol, date.plusDays(i),
                  (amount / sharePrice), commission);
          break;
        } catch (NoSuchElementException e) {
          i++;
        } catch (NoSuchFileException e) {
          throw new IllegalArgumentException(e.getMessage());
        }
      }
    }
  }

  @Override
  public void buyShare(String portfolioName, String tickerSymbol, double amount, LocalDateTime date,
                       double commission) {
    PortfolioOperations portfolio = getPortfolioByName(portfolioName);
    marketClosedCheck(date);
    helperToAddStock(commission, date, portfolio, tickerSymbol, amount);
  }

  @Override
  public double getCostBasis(String portfolioName, LocalDateTime date) {
    PortfolioOperations portfolio = getPortfolioByName(portfolioName);
    checkIfFutureDate(date);
    double costBasis = 0;
    for (Stock s : portfolio.getStocks()) {
      if (s.getDatePurchased().compareTo(date) <= 0) {
        costBasis += s.getCostBasis() + s.getCommission();
      }
    }
    return costBasis;
  }

  @Override
  public void performDollarCostAveraging(String pfName, String amount, LocalDateTime localStartDate,
                                         String frequency, LocalDateTime localEndDate,
                                         String commission, Map<String, Double> mapper) {
    strategyOperations.implementDollarCostStrategy(pfName, amount, localStartDate, frequency,
            localEndDate, commission, mapper);
  }

  @Override
  public void performFixedInvestments(String pfName, String amount, LocalDateTime localStartDate,
                                      String commission, Map<String, Double> mapper) {
    strategyOperations.implementFixedInvestmentStrategy(pfName, amount, localStartDate, commission,
            mapper);
  }

}
