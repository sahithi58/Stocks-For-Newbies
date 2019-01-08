package controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import model.Stock;

/**
 * This is package private interface that carries all the common code validation in the two
 * controllers.
 */
interface IValidation {

  /**
   * This method when data and time passed as the string returns the local date time object for it.
   *
   * @param date the date plus time string.
   * @return the localdatetime object.
   */
  LocalDateTime getLocalDateTime(String date);

  /**
   * This method will retrieve the unique stock to diplay to user for fixed investemnt.
   *
   * @param stocksList the list of stocks that the portfolio has.
   * @return names of the stocks to view to user.
   */
  List<String> getUniqueStocks(List<Stock> stocksList);

  /**
   * This method will build a map for saving a strategy.
   *
   * @param amount       as String
   * @param startDate    as String
   * @param time         as String
   * @param localEndDate as Localdatetime
   * @param frequency    as String.
   * @param commission   as String.
   * @param mapper       map of ticker and percents.
   * @return map of String and String.
   */
  Map<String, String> buildMap(String amount, String startDate, String time, LocalDateTime
          localEndDate, String frequency, String commission, Map<String, Double> mapper);


}

