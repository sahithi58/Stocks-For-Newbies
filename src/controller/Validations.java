package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Stock;
import model.StrategyType;

/**
 * This class implements that interface, any validations to be done, this method can be user to
 * added common validations.
 */
public class Validations implements IValidation {


  /**
   * This method will convert the input into the format it wants for to sen to the model.
   *
   * @param date as String
   * @return date as Local datetime.
   */
  @Override
  public LocalDateTime getLocalDateTime(String date) {
    DateTimeFormatter mmddyyformat = DateTimeFormatter.ofPattern("yyyy-MM-dd[HH:mm:ss]");
    return LocalDateTime.parse(date, mmddyyformat);
  }


  /**
   * This method will return the unique stocks among the stock list to ask for percents from user.
   *
   * @param stocksList list of stocks.
   * @return list of unique tickers as string.
   */
  @Override
  public List<String> getUniqueStocks(List<Stock> stocksList) {
    List<String> unique = new ArrayList<>();
    for (Stock stock : stocksList) {
      if (!unique.contains(stock.getTicker())) {
        unique.add(stock.getTicker());
      }
    }
    return unique;
  }

  private Map<String, String> map = new HashMap<>();


  @Override
  public Map<String, String> buildMap(String amount, String startDate, String time,
                                      LocalDateTime localEndDate, String frequency,
                                      String commission, Map<String, Double> mapper) {
    map.put("Strategy Type", StrategyType.RECURRINGINVESTMENT.toString());
    map.put("Amount", amount);
    map.put("Start Date", startDate + time);
    map.put("End Date", localEndDate.toLocalDate().toString());
    map.put("Commission", commission);
    map.put("Frequency", frequency);
    String temp = new String();
    for (Map.Entry<String, Double> entry : mapper.entrySet()) {
      temp += (entry.getKey() + " " + entry.getValue() + ",");
    }
    temp.substring(0, temp.length() - 1);
    map.put("Ticker weight map", temp);
    return map;
  }

}
