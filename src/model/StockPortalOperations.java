package model;

import java.time.LocalDateTime;
import java.util.List;

import data.StockDataRetrievalEnum;


/**
 * This interface allows a user to perform the given operations as part of their stock portal. The
 * operations include creating a portfolio, buying shares w.r.t a portfolio, getting the total cost
 * basis and total value ata certain date, viewing a portfolio and viewing all the portfolios. The
 * logic of the model is completely independent of the type of the data user wants to work on. The
 * date and time from the user must be in military format i.e: yyyy-MM-dd HH:mm:ss.
 */
public interface StockPortalOperations {

  /**
   * This method allows a user to create one or more portfolios, the name of rhe portfolio can only
   * be alphabets or numbers no special characters are allwoed, the length of the name cannot be
   * greater than 50 words.
   *
   * @param portfolioName as String.
   */
  void createPortfolio(String portfolioName);

  /**
   * This method allows user to buy a share given name of the portfolio, the ticker symbol of the
   * company they want to buy the share, the total money he wants to invest on that share and the
   * past date and time during which he wants to buy the share. Allows user to buy share during the
   * valid trading hours for each day.
   *
   * @param portfolioName as String
   * @param tickerSymbol  as String is the unique code for each company like GOOG for google.
   * @param amount        as double is the money the user wants to invest on a share.
   * @param date          as LocalDateTime will take in the date and time during which he wants to
   *                      buy the share. Our application allows buying shares on the past.
   */
  void buyShare(String portfolioName, String tickerSymbol, double amount, LocalDateTime date);

  /**
   * This method will get the total cost basis at a certain date. It is basically the cumulative sum
   * of the total cost basis, till the day they requested.
   *
   * @param portfolioName as String is the name of the portfolio for which user wants the cost basis
   *                      for.
   * @param date          as LocalDateTime is the date till when they want to cost basis.
   */
  double getCostBasis(String portfolioName, LocalDateTime date);


  /**
   * This method will get the total value at a certain date. It is basically the cumulative sum of
   * the total value, till the day they requested.
   *
   * @param portfolioName as String is the name of the portfolio for which user wants the cost basis
   *                      for.
   * @param date          as LocalDateTime is the date till when they want to get the total value
   *                      for.
   */
  double getTotalValue(String portfolioName, LocalDateTime date);

  /**
   * This method will get all the porfolios created by the user till then as a form of String. The
   * string will have the name of the portfolio and all the stock in the portfolio.
   *
   * @return the list of all portfolios in the form of string.
   */
  List<PortfolioOperations> getAllPortfolios();

  /**
   * This method will return the porfolio that a user specifically wants to view.
   *
   * @param portfolioName as String is the name of the portfolio that the user wants to view.
   * @return the stocks in the portfolio as a String for view.
   */
  PortfolioOperations getPortFolio(String portfolioName);

  /**
   * This method will take in the type of data user wants to work on, as of now user can choose from
   * a file or throwugh an API call. The enum carries his choice, through which the object for that
   * data retrieval strategy will be created and used for the whole operations.
   *
   * @param stockData as enum which will carry the choice of data that user wants to use.
   */
  void setDataRetrievalSource(StockDataRetrievalEnum stockData);


}
