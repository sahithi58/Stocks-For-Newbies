package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This methods implements the types of operations that can be performed on a portfolio. Here
 * generally the portfolio is a list of stocks. Each portfolio is independent of the other and the
 * name of the portfolios or not case sensitive. User can create any length for the name of
 * portfolio, but can only give alphanumeric symbols to create a portfolio for its name and no
 * special characters are allowed.
 */
class Portfolio implements PortfolioOperations {

  private List<Stock> stocks;
  private String portFolioName;

  /**
   * This constructor takes in the portfolio name and initializes it for further operation to be
   * done in the class. It also initializes stocks to a new ArrayList for each portfolio defined.
   *
   * @param portFolioName as String is the name of the portfolio to be created.
   * @throws IllegalArgumentException when the name of portfolio is given as empty or null.
   */
  Portfolio(String portFolioName) throws IllegalArgumentException {
    if (portFolioName.isEmpty() || portFolioName == null) {
      throw new IllegalArgumentException("Name of the portfolio cannot be empty or null");
    }
    this.portFolioName = portFolioName;
    this.stocks = new ArrayList<>();
  }

  /**
   * Copy constructor for Portfolio class.
   */
  public Portfolio(PortfolioOperations portfolio) {
    this.portFolioName = portfolio.getPortfolio();
    List<Stock> temp = new ArrayList<>();
    for (int i = 0; i < portfolio.getStocks().size(); i++) {
      temp.add(new Stock(portfolio.getStocks().get(i)));
    }
    this.stocks = temp;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < stocks.size(); i++) {
      stringBuilder.append("\n " + (i + 1) + ". ").append(stocks.get(i)).append(" ");
    }
    return "Portfolio Name : " + portFolioName + stringBuilder.toString() + "\n";
  }

  @Override
  public String getPortfolio() {
    return portFolioName;
  }

  @Override
  public List<Stock> getStocks() {
    return this.stocks;
  }

  @Override
  public void addStockToPortfolio(double amount, String tickerSymbol, LocalDateTime date,
                                  double pps) {
    stocks.add(new Stock(amount, tickerSymbol, date, pps));
  }

  @Override
  public void addStockToPFWithCommission(double amount, String tickerSymbol, LocalDateTime date,
                                         double pps, double commission) {
    stocks.add(new Stock(amount, tickerSymbol, date, pps, commission));
  }

}
