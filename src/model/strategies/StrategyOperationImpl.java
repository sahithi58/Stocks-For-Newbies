package model.strategies;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.AdvancedStockPortal;

/**
 * This class is the implementation class for the types of strategy that can be performed on any
 * portfolio. Currently we support two types of strategies i.e. one time investment and recurring
 * investment.
 */
public class StrategyOperationImpl implements StrategyOperations {

  private AdvancedStockPortal model;

  public StrategyOperationImpl(AdvancedStockPortal model) {
    this.model = model;
  }

  /**
   * This method will perform the function of making recurring investments based in start and end
   * date, when start date is greater than end date the method throws an error. When the start date
   * and end date difference is greater than frequency then only start date stocks will be bought
   * successfully.
   */
  @Override
  public boolean implementDollarCostStrategy(String pfName, String amount, LocalDateTime
          localStartDate, String frequency, LocalDateTime localEndDate, String commission,
                                             Map<String, Double> mapper) {
    List<LocalDateTime> dateTimes = new ArrayList<>();
    dateTimes.add(localStartDate);
    double totalAmountInDouble = Double.parseDouble(amount);
    while (localStartDate.compareTo(localEndDate) <= -1 ||
            localStartDate.compareTo(localEndDate) == 0) {
      for (Map.Entry<String, Double> entry : mapper.entrySet()) {
        double amountPerShare = (totalAmountInDouble * entry.getValue()) / 100;
        model.buyShare(pfName, entry.getKey(), amountPerShare, localStartDate,
                Double.parseDouble(commission));
        System.out.println("Amount: " + amountPerShare + "startDate:" + localStartDate + "name: "
                + entry.getKey());
      }
      localStartDate = localStartDate.plusDays(Long.parseLong(frequency));
    }
    return false;
  }

  /**
   * This class will perform the command for fixed investment it will be executed when user enter 2
   * on buy shares menu, This investment will only be performed for existing portfolios and not new
   * ones.
   *
   * @param pfName         portfolio on which user wants to apply strategy
   * @param amount         amount of the investment to be done
   * @param localStartDate start date of the strategy
   * @param commission     commission per transaction
   * @param mapper         mapper object consisting of tickers and their weightage
   * @return returns true if strategy successfully applied
   */
  @Override
  public boolean implementFixedInvestmentStrategy(String pfName, String amount, LocalDateTime
          localStartDate, String commission, Map<String, Double> mapper) {
    double totalAmountInDouble = Double.parseDouble(amount);
    for (Map.Entry<String, Double> entry : mapper.entrySet()) {
      double amountPerShare = (totalAmountInDouble * entry.getValue()) / 100;
      model.buyShare(pfName, entry.getKey(), amountPerShare, localStartDate,
              Double.parseDouble(commission));
    }
    return false;
  }
}
