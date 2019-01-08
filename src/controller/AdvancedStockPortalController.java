package controller;

import java.nio.file.NoSuchFileException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.AdvancedStockPortalOperations;
import view.IAdvancedView;

/**
 * This class will implement the controller class which acts as medium between view and model, it
 * takes input from user through view, gets the computed values from model by sending view as input
 * parameters. This will handle all the model methods from a single method, but the controller is
 * asynchronous in nature. To restrict user from getting access to all model methods indirectly we
 * have implemented this to avoid any unwanted inputs and calls. This will have same opetions as
 * basic stock portal except it when chose to buy share it will view three options to perform buy
 * operation.
 */
public class AdvancedStockPortalController extends StockPortalController
        implements IStockPortalController {

  private AdvancedStockPortalOperations model;
  private IAdvancedView view;
  private IValidation validation;
  protected Map<String, String> map = new HashMap<>();

  /**
   * The constructor of this controller will initialize model and view which will be used in the
   * overall method to fetch and retrieve data from view and model respectively.
   */
  public AdvancedStockPortalController(AdvancedStockPortalOperations model, IAdvancedView view) {
    super(model, view);
    this.model = model;
    this.view = view;
    validation = new Validations();

  }


  /**
   * This method will implements three major buying methods: the operations here are: 0: Will show
   * the options available under buy share. 1: Buy share with commission. 2: Make fixed investments.
   * 3. Make Dollar cost recurring investments. exit: will exit the entire application quit: here
   * will quit this menu and go to the main menu.
   */
  @Override
  protected void helperBuyShare() {
    Scanner s = view.getInput();
    view.showAdvancedOptions();
    while (s.hasNext()) {
      String in = s.next();
      in = in.trim();
      try {
        switch (in) {
          case "exit":
            view.viewMessage("The operation has been discarded");
            System.exit(0);
            break;
          case "quit":
            super.handleRequest();
            break;
          case "0":
            view.showAdvancedOptions();
            break;
          case "1":
            getHelperBuyWithCommission();
            view.viewOutMessage("Press 0 to get buying menu");
            break;
          case "2":
            helperFixedInv();
            view.viewOutMessage("Enter Y or N");
            view.viewOutMessage("Press 0 to get buying menu");
            break;
          case "3":
            helperDollarCostAvg();
            view.viewOutMessage("Press 0 to get buying menu");
            break;
          default:
            view.viewMessage(String.format("Unknown command %s\n", in));
            view.viewOutMessage("Press 0 to get buy menu");
            break;
        }
      } catch (InputMismatchException ime) {
        view.viewMessage("Bad length to " + in);
      }
    }
  }

  /**
   * This helper will take dollar cost average inputs and validate them via view and send them to
   * command control for strategy implementation on model.
   */
  protected void helperDollarCostAvg() {
    try {
      boolean checkIfExist = false;
      List<String> getUniqueStocks = null;
      String pfName = view.getPortfolioName();
      quitCheck(pfName);
      try {
        model.createPortfolio(pfName);
      } catch (IllegalArgumentException e) {
        checkIfExist = true;
        getUniqueStocks = validation.getUniqueStocks(model.getPortFolio(pfName).getStocks());
        view.viewOutMessage("Portfolio is existing");
      }
      String amount = view.getTotalInvestAmount();
      quitCheck(amount);
      String startDate = view.getDate();
      quitCheck(startDate);
      String time = "10:00:00";
      LocalDateTime localStartDate = getLocalDateTime(startDate + time);
      String endDateChoice = view.getEndDateChoice();
      quitCheck(endDateChoice);
      LocalDateTime localEndDate = helperLocalEndDate(endDateChoice, time, localStartDate);
      String frequency = view.getFrequency();
      quitCheck(frequency);
      String commission = view.getCommission();
      quitCheck(commission);
      String isEqual = view.getInvestingChoice();
      quitCheck(isEqual);
      Map<String, Double> mapper = new HashMap<>();
      List<String> validStock = new ArrayList<>();
      if (checkIfExist && getUniqueStocks.size() != 0) {
        mapper = helperFixedWeights(isEqual, getUniqueStocks, mapper, pfName);
      } else {
        String num = view.getNumOfStock();
        quitCheck(num);
        int number = Integer.parseInt(num);
        if (isEqual.equalsIgnoreCase("Y")) {
          mapper = helperEqualWeights(mapper, validStock, number);
        } else {
          mapper = helperCustomWeights(mapper, validStock, number);
        }
      }
      model.performDollarCostAveraging(pfName, amount, localStartDate, frequency, localEndDate,
              commission, mapper);
      view.viewMessage("Recurring Strategy(Dollar cost investment) executed successfully");
      validation.buildMap(amount, startDate, time, localEndDate, frequency, commission, mapper);
    } catch (Exception e) {
      view.viewMessage("Something went wrong while buying share\n " + "More Information: "
              + e.getMessage() + "\n");
    }
  }


  /**
   * This helper will get all inputs for fixed investemnt validate them and send them to command for
   * making required investments.
   */
  protected void helperFixedInv() {
    try {
      String pfName = view.getPortfolioName();
      quitCheck(pfName);
      List<String> getUniqueStocks
              = validation.getUniqueStocks(model.getPortFolio(pfName).getStocks());
      if (getUniqueStocks.size() == 0) {
        throw new IllegalArgumentException("Cannot give weights as no stocks");
      }
      String amount = view.getTotalInvestAmount();
      quitCheck(amount);
      String date = view.getDate();
      quitCheck(date);
      String time = "10:00:00";
      LocalDateTime localDate = getLocalDateTime(date + time);
      String commission = view.getCommission();
      quitCheck(amount);
      String isEqual = view.getInvestingChoice();
      quitCheck(isEqual);
      Map<String, Double> mapper = new HashMap<>();
      mapper = helperFixedWeights(isEqual, getUniqueStocks, mapper, pfName);
      model.performFixedInvestments(pfName, amount, localDate, commission, mapper);
      view.viewMessage("One time investment strategy(Fixed investment) successful\n");
    } catch (Exception e) {
      view.viewMessage("Something went wrong while buying share\n " + "More Information: "
              + e.getMessage() + "\n");
    }
  }

  /**
   * This helper will get all the inputs required for commission and then buys share and includes
   * this commission at the stock level.
   */
  protected void getHelperBuyWithCommission() {
    try {
      String pfName = view.getPortfolioName();
      quitCheck(pfName);
      model.getPortFolio(pfName);
      String ticker = view.getTickerSymbol();
      quitCheck(ticker);
      String amount = view.getTotalInvestAmount();
      quitCheck(amount);
      double amountDouble = Double.parseDouble(amount);
      String date = view.getDate();
      quitCheck(date);
      String time = view.getTime();
      quitCheck(time);
      LocalDateTime localDate = getLocalDateTime(date + time);
      String commission = view.getCommission();
      double commissionDouble = Double.parseDouble(amount);
      quitCheck(commission);
      model.buyShare(pfName, ticker, amountDouble, localDate, commissionDouble);
      view.viewMessage("Bought Share Successfully");
    } catch (Exception e) {
      view.viewMessage("Something went wrong while buying share\n " + "More Information: "
              + e.getMessage() + "\n");
    }
  }

  /**
   * This helper will get the ticker symbol and validate it via model and if valid then returns the
   * input for further processing.
   *
   * @return the ticker symbol as String.
   * @throws NoSuchFileException when the data is not found for the ticker.
   */
  private String helperGetValidTicker() throws NoSuchFileException {
    String name = view.getTickerSymbol();
    quitCheck(name);
    while (!model.isValidStock(name)) {
      view.viewOutMessage("Invalid symbol enter again!");
      name = view.getTickerSymbol();
      quitCheck(name);
    }
    return name;
  }

  /**
   * This method will handle if user wants to input an end date or not. It takes input from view and
   * validates for further processing. if not given any date then current day is considered as end
   * date.
   *
   * @return local date as localdatetime.
   */
  private LocalDateTime helperLocalEndDate(String endDateChoice, String time,
                                           LocalDateTime localStartDate) {
    LocalDateTime localEndDate;
    if (endDateChoice.equalsIgnoreCase("Y")) {
      String endDate = view.getDate();
      quitCheck(endDate);
      localEndDate = getLocalDateTime(endDate + time);
      if (localEndDate.isBefore(localStartDate)) {
        throw new IllegalArgumentException("End date is before start date, which is invalid");
      }
    } else {
      localEndDate = LocalDateTime.now();
    }

    return localEndDate;
  }

  /**
   * This method will convert the input into the format it wants for to send to the model.
   *
   * @param date as String
   * @return date as Localdatetime.
   */
  private LocalDateTime getLocalDateTime(String date) {
    DateTimeFormatter mmddyyformat = DateTimeFormatter.ofPattern("uuuu-MM-dd[HH:mm:ss]")
            .withResolverStyle(ResolverStyle.STRICT);
    return LocalDateTime.parse(date, mmddyyformat);
  }

  /**
   * This method will handle the inputs when the choice is equla weights, it will only ask for the
   * sotcks they want to enter and not the weights.
   *
   * @return mapper as Map of String kay and double value.
   */
  private Map<String, Double> helperEqualWeights(Map<String, Double> mapper,
                                                 List<String> validStock, int num)
          throws NoSuchFileException {
    while (validStock.size() != num) {
      String name = helperGetValidTicker();
      if (name == null) {
        return null;
      }
      validStock.add(name);
    }
    double percent = 100 / validStock.size();
    for (String stock : validStock) {
      mapper = updateMapper(mapper, percent, stock);
    }
    return mapper;
  }

  /**
   * This method will handle the inputs when the choice is custom weights, it will only ask for the
   * stocks they want to enter and validate stock, it also checks if the stock is valid then
   * validates percent given to it.
   *
   * @return mapper as Map of String kay and double value.
   * @throws NoSuchFileException when the dat is not the file.
   */
  private Map<String, Double> helperCustomWeights(Map<String, Double> mapper,
                                                  List<String> validStock, int num)
          throws NoSuchFileException {
    double percentage = 0;
    while (validStock.size() != num) {
      String name = helperGetValidTicker();
      quitCheck(name);
      percentage = getValidatedPercentage(percentage, name, mapper);
      validStock.add(name);
      mapper = updateMapper(mapper, percentage, name);
    }
    validatePercent(mapper);
    return mapper;
  }

  /**
   * This method validates if the percentage is greater than hundred is passed as input.
   *
   * @param percentage as double
   * @param mapper     as map of ticker and percent.
   * @param name       the ticker code name.
   * @return the percentage if it is valid.
   */
  private double getValidatedPercentage(double percentage, String name,
                                        Map<String, Double> mapper) {
    String percent = view.getPercent(name);
    quitCheck(percent);
    percentage = Double.parseDouble(percent);
    double sum = 0;
    for (double f : mapper.values()) {
      sum += f;
    }
    while (percentage > 100 || (sum + percentage) > 100) {
      view.viewOutMessage("Percent cannot be greater than 100");
      percentage = Double.parseDouble(view.getPercent(name));
    }
    return percentage;
  }

  /**
   * This updates the mapper if the keys are same, i.e., same entries have been requested by the
   * user.
   *
   * @param mapper     as Map of string key and double value.
   * @param percentage the percent wrt to ticker.
   * @param name       the name of the ticker.
   * @return updates the ticker percent value.
   */
  private Map<String, Double> updateMapper(Map<String, Double> mapper,
                                           double percentage, String name) {
    if (mapper.containsKey(name)) {
      mapper.put(name, percentage + mapper.get(name));
    } else {
      mapper.put(name, percentage);
    }
    return mapper;
  }

  /**
   * FOr fixed investment, the user will be asked only weights if reuqersted custom weights, else
   * equal weights to all stocks will be assigned.
   *
   * @param isEqual as String carries user choice.
   * @param mapper  as map of ticker and percent.
   * @param pfName  as String the name of portfolio.
   * @return mapper with ticker as key and percent as value of it.
   */
  private Map<String, Double> helperFixedWeights(String isEqual, List<String> getUniqueStocks,
                                                 Map<String, Double> mapper, String pfName) {
    if (isEqual.equalsIgnoreCase("Y")) {
      double percent = 100 / getUniqueStocks.size();
      for (String stock : getUniqueStocks) {
        mapper.put(stock, percent);
      }
    } else {
      double percentage = 0;
      for (int i = 0; i < getUniqueStocks.size(); i++) {
        String ticker = getUniqueStocks.get(i);
        double percent = getValidatedPercentage(percentage, ticker, mapper);
        mapper.put(getUniqueStocks.get(i), percent);
      }
      validatePercent(mapper);
    }
    return mapper;
  }


  /**
   * This method will validate finally if the total is 100 and throws exceptiof if not.
   *
   * @param mapper the map of ticker and the percent as value.
   */
  private void validatePercent(Map<String, Double> mapper) {
    int sum = 0;
    for (Map.Entry<String, Double> entry : mapper.entrySet()) {
      sum += entry.getValue();
    }
    if (sum == 100) {
      return;
    } else {
      throw new IllegalArgumentException("Sum cannot be less than 100");
    }
  }
}
