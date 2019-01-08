package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The implementation class for the advanced features offered in the final rollout i.e. The ability
 * to save and retrieve portfolios and strategies from files for the user. And the added
 * functionality to build a graph to plot performance of the portfolio.
 */
public class AdvancedPlusStockPortal extends AdvancedStockPortal
        implements IAdvancedPlusStockPortal {

  private IStrategyDao strategyDao = new StrategyDao();
  private IPortfolioDao portfolioDao = new PortfolioDao();

  @Override
  public void savePortfolio(String portfolio) {
    portfolioDao.savePortfolioToJson(this.getPortFolio(portfolio));
  }

  @Override
  public List<PortfolioOperations> retrievePortfolio(String portfolio) {
    this.portfolios.add(portfolioDao.retrievePortfolioFromJson(portfolio));
    return this.portfolios;
  }

  @Override
  public void saveStrategy(Map<String, String> map) {
    DateTimeFormatter mmddyyformat = DateTimeFormatter.ofPattern("uuuu-MM-dd[HH:mm:ss]")
            .withResolverStyle(ResolverStyle.STRICT);
    String temp = map.get("Ticker weight map");
    String[] temp1 = temp.split(",");
    Map<String, Double> mapper = new HashMap<>();
    String[] pairs = temp.split(",");
    for (int i = 0; i < pairs.length; i++) {
      String pair = pairs[i];
      String[] keyValue = pair.split(" ");
      mapper.put(keyValue[0], Double.valueOf(keyValue[1]));
    }
    String time = "10:00:00";
    Strategy strategy = new Strategy(String.valueOf(map.get("Strategy Name")),
            StrategyType.valueOf(map.get("Strategy Type")),
            LocalDateTime.parse(map.get("Start Date"), mmddyyformat),
            LocalDateTime.parse(map.get("End Date") + time, mmddyyformat),
            Double.parseDouble(map.get("Commission")), Double.parseDouble(map.get("Amount")),
            Integer.valueOf(map.get("Frequency")), mapper);
    strategyDao.saveStrategyToJson(strategy);
  }

  @Override
  public void retrieveStrategyAndApplyToPortfolio(String strategy, String portfolio) {
    Strategy strategy1 = strategyDao.retrieveStrategyFromFile(strategy);
    if (strategy1.getStrategyType() == StrategyType.RECURRINGINVESTMENT) {
      this.performDollarCostAveraging(portfolio, String.valueOf(strategy1.getAmount()),
              strategy1.getStartDate(), String.valueOf(strategy1.getFrequency()),
              strategy1.getEndDate(), String.valueOf(strategy1.getCommission()),
              strategy1.getTickerWeightMap());
    } else if (strategy1.getStrategyType() == StrategyType.ONETIMEINVESTMENT) {
      this.performFixedInvestments(portfolio, String.valueOf(strategy1.getAmount()),
              strategy1.getStartDate(), String.valueOf(strategy1.getCommission()),
              strategy1.getTickerWeightMap());
    }
  }

  @Override
  public List<Double> plotGraph(String portfolioName, LocalDateTime startDate,
                                LocalDateTime endDate) {
    List<Double> totalValues = new ArrayList<>();
    for (LocalDateTime date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
      totalValues.add(this.getTotalValue(portfolioName, date));
    }
    return totalValues;
  }
}
