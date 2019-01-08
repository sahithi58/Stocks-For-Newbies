package model;

import java.nio.file.NoSuchFileException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import data.DataFactory;
import data.StockDataRetrievalEnum;
import data.StockDataRetrievalOperations;

/**
 * The method implements Stock portal interface, which will have operations such as creating
 * portfolio, buying shares, getting cost basis and value basis, gettting portfolio by name, getting
 * all portfolios. By default our application assumes that the name of the portfolio cannot have
 * spaces in it but can use Underscore to declare two names. Out applications also take both date
 * and time to proceed with the operations.
 */
public class StockPortal implements StockPortalOperations {

  protected List<PortfolioOperations> portfolios;
  protected StockDataRetrievalOperations stockDataRetrievalOperations;

  /**
   * This constructor initialized the list of portfolios that can be added by the user.
   */
  public StockPortal() {
    this.portfolios = new ArrayList<>();
  }

  /**
   * If a portfolio with same name exists then throw an error. If portfolio name is not either
   * alphabets or numbers or alphanumeric throws and error. The portfolio name cannot have spaces in
   * it instead user can user underscore to differentiate two portfolios.
   */
  @Override
  public void createPortfolio(String portfolio) throws IllegalArgumentException {
    if (nullCheck(portfolio)) {
      if (!portfolio.matches("^[a-zA-Z0-9_ ]*$")) {
        throw new IllegalArgumentException("Portfolio name can only be alphanumeric.");
      }
      for (int i = 0; i < portfolios.size(); i++) {
        if (portfolios.get(i).getPortfolio().equalsIgnoreCase(portfolio)) {
          throw new IllegalArgumentException("Portfolio name already exists.");
        }
      }
      PortfolioOperations portfolioOperations = new Portfolio(portfolio);
      portfolios.add(portfolioOperations);
    }
  }

  /**
   * Throws an exception if the Portfolio name is null or empty.
   */
  protected boolean nullCheck(String inputStr) throws IllegalArgumentException {
    if (inputStr == null || inputStr.isEmpty()) {
      throw new IllegalArgumentException("Portfolio/Strategy name cannot be null/empty.");
    }
    return true;
  }

  /**
   * Helper method to get the portfolio Object when the user passes portfolioName.
   */
  protected PortfolioOperations getPortfolioByName(String portfolioName)
          throws NoSuchElementException {
    PortfolioOperations portfolio = null;
    boolean flag = false;
    if (nullCheck(portfolioName)) {
      if (portfolios.size() > 0) {
        for (PortfolioOperations p : portfolios) {
          if (p.getPortfolio().replace(" ", "").
                  equalsIgnoreCase(portfolioName.replace(" ", ""))) {
            portfolio = p;
            flag = true;
            break;
          }
        }
      } else {
        throw new NoSuchElementException("You do not have any portfolios.");
      }
      if (!flag) {
        throw new NoSuchElementException("Portfolio does not exist.");
      }
    }
    return portfolio;
  }

  @Override
  public void buyShare(String portfolioName, String tickerSymbol, double amount, LocalDateTime date)
          throws IllegalArgumentException {
    PortfolioOperations portfolio = getPortfolioByName(portfolioName);
    marketClosedCheck(date);
    if (!checkIfFutureDate(date)) {
      if (nullCheck(tickerSymbol) && negativeAndZeroAmount(amount)) {
        long i = 0;
        while (i <= 3) {
          try {
            double sharePrice = 0;
            if (amount != 0) {
              sharePrice = getStockPrice(tickerSymbol, date.plusDays(i));
            }
            portfolio.addStockToPortfolio(amount, tickerSymbol, date.plusDays(i),
                    (amount / sharePrice));
            break;
          } catch (NoSuchElementException e) {
            i++;
          } catch (NoSuchFileException e) {
            throw new IllegalArgumentException(e.getMessage());
          }
        }
      }
    }
  }

  /**
   * Checks if the market is open i.e valid time.
   */
  protected void marketClosedCheck(LocalDateTime date) {
    if (date.toLocalTime().getHour() < 9 || date.toLocalTime().getHour() > 15) {
      throw new IllegalArgumentException("Stock market closed.");
    }
  }

  /**
   * Amount entered cannot be negative.
   */
  protected boolean negativeAndZeroAmount(double amount) throws IllegalArgumentException {
    if (amount < 0) {
      throw new IllegalArgumentException("The price entered is invalid.");
    }
    return true;
  }

  @Override
  public double getCostBasis(String portfolioName, LocalDateTime date) {
    PortfolioOperations portfolio = getPortfolioByName(portfolioName);
    checkIfFutureDate(date);
    double costBasis = 0;
    for (Stock s : portfolio.getStocks()) {
      if (s.getDatePurchased().compareTo(date) <= 0) {
        costBasis += s.getCostBasis();
      }
    }
    return costBasis;
  }

  @Override
  public double getTotalValue(String portfolioName, LocalDateTime date) {
    PortfolioOperations portfolio = getPortfolioByName(portfolioName);
    double totalValue = 0;
    checkIfFutureDate(date);
    for (Stock s : portfolio.getStocks()) {
      if (s.getDatePurchased().compareTo(date) <= 0) {
        long i = 0;
        while (i <= 3) {
          try {
            totalValue += getStockPrice(s.getTicker(), date.minusDays(i)) * s.getNoOfShares();
            break;
          } catch (NoSuchElementException e) {
            i++;
          } catch (NoSuchFileException e) {
            throw new IllegalArgumentException(e.getMessage());
          }
        }
      }
    }
    return totalValue;
  }

  @Override
  public List<PortfolioOperations> getAllPortfolios() {
    List<PortfolioOperations> allPortfolios = new ArrayList<>();
    allPortfolios.addAll(portfolios);
    return Collections.unmodifiableList(allPortfolios);
  }

  @Override
  public PortfolioOperations getPortFolio(String portfolioName) {
    return new Portfolio(getPortfolioByName(portfolioName));
  }

  @Override
  public void setDataRetrievalSource(StockDataRetrievalEnum stockData) {
    stockDataRetrievalOperations = DataFactory.getObj(stockData);
  }

  /**
   * Fetches stock price the given data source a array of strings, with each string containing
   * timestamp,open,high,low,close,volume data values and looks for the stock price on the given
   * date. If found stock price is zero or negative throws an error.
   */
  protected double getStockPrice(String tickerSymbol, LocalDateTime date)
          throws NoSuchFileException {
    String[] stockData = stockDataRetrievalOperations.readData(tickerSymbol);
    String[] temp = new String[0];
    boolean flag = false;
    for (int i = 1; i < stockData.length; i++) {
      if (stockData[i].contains(date.toLocalDate().toString())) {
        temp = stockData[i].split(",");
        flag = true;
        break;
      }
    }
    if (!flag) {
      throw new NoSuchElementException("Cannot buy stock on " + date.toLocalDate()
              + " as data not available for given date.");
    }
    negativeAndZeroAmount(Double.valueOf(temp[4]));
    return Double.valueOf(temp[4]);
  }

  protected boolean checkIfFutureDate(LocalDateTime date) {
    if (LocalDateTime.now().compareTo(date) == -1) {
      throw new IllegalArgumentException("Cannot buy stocks in future.");
    }
    return false;
  }

}
