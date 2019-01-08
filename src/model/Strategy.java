package model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the strategy model class which as various attributes such as strategyName, strategyType,
 * startDate, endDate, commission, amount, frequency and stores the objects.
 */
public class Strategy {
  private String strategyName;
  private StrategyType strategyType;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private double commission;
  private double amount;
  private int frequency;
  private Map<String, Double> tickerWeightMap = new HashMap<>();

  /**
   * This constructor will initialize the attributes that need to be used int the method.
   *
   * @param strategyName    as String
   * @param strategyType    as StrategyType Enum.
   * @param startDate       as LocalDatetime.
   * @param endDate         as LocalDateTime
   * @param commission      as double
   * @param amount          as double
   * @param frequency       as int
   * @param tickerWeightMap as map of ticker and their percents.
   */
  public Strategy(String strategyName, StrategyType strategyType, LocalDateTime startDate,
                  LocalDateTime endDate, double commission, double amount, int frequency,
                  Map<String, Double> tickerWeightMap) {
    this.strategyName = strategyName;
    this.strategyType = strategyType;
    this.startDate = startDate;
    this.endDate = endDate;
    this.commission = commission;
    this.amount = amount;
    this.frequency = frequency;
    this.tickerWeightMap = tickerWeightMap;
  }

  /**
   * Getter method to get strategy name.
   *
   * @return the name as String.
   */
  public String getStrategyName() {
    return strategyName;
  }

  /**
   * Getter method to get strategy type.
   *
   * @return enum startegy type.
   */
  public StrategyType getStrategyType() {
    return strategyType;
  }

  /**
   * Getter to get start Date.
   *
   * @return start date as localdate time
   */
  public LocalDateTime getStartDate() {
    return startDate;
  }

  /**
   * Getter to get the end date.
   *
   * @return end date as local date time.
   */
  public LocalDateTime getEndDate() {
    return endDate;
  }

  /**
   * Getter to get the commission as double.
   *
   * @return the commssion as double.
   */
  public double getCommission() {
    return commission;
  }

  /**
   * Getter to get the amount.
   *
   * @return the amount as double.
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Getter to get the frequency as int.
   *
   * @return frequency as int.
   */
  public int getFrequency() {
    return frequency;
  }

  /**
   * Getter to get tickers and their respective percents.
   *
   * @return the map of key as ticker and value as percents.
   */
  public Map<String, Double> getTickerWeightMap() {
    return tickerWeightMap;
  }
}
