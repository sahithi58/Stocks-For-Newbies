package model;

import java.time.LocalDateTime;

/**
 * This the model class for a Stock. The attributes of a Stock are costBasis, ticker, datePurchased,
 * noOfShares. As once declared cannot be updated this stock class has been declared to final.
 */
public final class Stock {

  private final double costBasis;
  private final String ticker;
  private final LocalDateTime datePurchased;
  private final double noOfShares;
  private final double commission;

  /**
   * This constructor initialized the costbasis, date purchased ticker and no of shares purchased.
   *
   * @param costBasis     as double
   * @param ticker        as String
   * @param datePurchased as localdatetime
   * @param noOfShares    as double
   */
  Stock(double costBasis, String ticker, LocalDateTime datePurchased, double noOfShares,
        double commission) {
    this.costBasis = costBasis;
    this.ticker = ticker;
    this.datePurchased = datePurchased;
    this.noOfShares = noOfShares;
    this.commission = commission;
  }

  /**
   * Copy constructor for stock class.
   */
  Stock(Stock stock) {
    this.costBasis = stock.costBasis;
    this.ticker = stock.ticker;
    this.datePurchased = stock.datePurchased;
    this.noOfShares = stock.noOfShares;
    this.commission = stock.commission;
  }

  /**
   * This constructor initialized the costbasis, date purchased ticker and no of shares purchased.
   *
   * @param costBasis     as double
   * @param ticker        as String
   * @param datePurchased as localdatetime
   * @param noOfShares    as double
   */
  Stock(double costBasis, String ticker, LocalDateTime datePurchased, double noOfShares) {
    this.costBasis = costBasis;
    this.ticker = ticker;
    this.datePurchased = datePurchased;
    this.noOfShares = noOfShares;
    this.commission = 0.0;
  }

  /**
   * This method gets the cost basis of the stock.
   *
   * @return costbasis as double.
   */
  double getCostBasis() {
    return costBasis;
  }

  /**
   * This method gets the ticker symbol as string.
   *
   * @return ticker symbol as string.
   */
  public String getTicker() {
    return ticker;
  }

  /**
   * This method will outupt the date purchased when called.
   *
   * @return datePurchased as localdatetime.
   */
  LocalDateTime getDatePurchased() {
    return datePurchased;
  }

  /**
   * This method will return the number of shares.
   *
   * @return number of shares can be fractional is the assumption.
   */
  double getNoOfShares() {
    return noOfShares;
  }

  public double getCommission() {
    return commission;
  }

  @Override
  public String toString() {
    return "Ticker = " + ticker + ", CostBasis = $" + costBasis +
            ", Date Purchased = " + datePurchased +
            ", Number of Shares = " + noOfShares + ", Commission = $" + commission;
  }

}
