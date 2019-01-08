package controller;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

import data.StockDataRetrievalEnum;
import model.StockPortalOperations;
import view.IStockPortalView;

/**
 * This class will implement the controller class which acts as medium between view and model, it
 * takes input from user through view, gets the computed values from model by sending view as input
 * parameters. This will handle all the model methods from a single method, but the controller is
 * asynchronous in nature. To restrict user from getting access to all model methods indirectly we
 * have implemented this to avid any unwanted inputs and calls. In this method the following methods
 * are called for respective user input: API or FILE chose then only the operations can be
 * performed. 0 - To get the menu of stock portal operations. 1 - To create the portfolio, a user
 * can create multiple portfolios. 2 - The buy shares for a particular portfolio for which he needs
 * to input the portfolio name for which you want to buy the shares, the unique company ticker
 * symbol, the money to invest and the date of investment. 3 - To  get the total cost basis till
 * certain date. 4 - To get the total value of shares on certain date. 5 - To view a specific
 * portfolio and holding under it. 6- To get all the portfolios. quit- To quit the operations.
 */
public class StockPortalController implements IStockPortalController {

  private IStockPortalView view;
  private StockPortalOperations model;
  private Scanner s;
  private IValidation validation;

  /**
   * The constructor of this controller will initialize model and view which will be used in the
   * overall method to fetch and retrieve data from view and model respectively.
   */
  public StockPortalController(StockPortalOperations model, IStockPortalView view) {
    this.view = view;
    this.model = model;
    s = view.getInput();
    validation = new Validations();
    StockDataRetrievalEnum stockData = null;
    helperSetDataType(stockData);
  }

  @Override
  public void handleRequest() {
    view.showOption();
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
            return;
          case "0":
            view.showOption();
            break;
          case "1":
            helperCreatePF();
            break;
          case "2":
            helperBuyShare();
            break;
          case "3":
            helperGetCostBasis();
            break;
          case "4":
            helperGetTotalValue();
            break;
          case "5":
            helperGetPortfolio();
            break;
          case "6":
            helperGetAllPFS();
            break;
          default:
            view.viewMessage(String.format("Unknown command %s\n", in));
            view.viewOutMessage("Press 0 to get menu");
            break;
        }
      } catch (InputMismatchException ime) {
        view.viewMessage("Bad length to " + in);
      }
    }
  }


  /**
   * This helper method will invoke the call to model, and when receives and output sends it to view
   * to print it.
   */
  protected void helperGetAllPFS() {
    try {
      for (int i = 0; i < model.getAllPortfolios().size(); i++) {
        view.viewMessage(model.getAllPortfolios().get(i) + "\n");
      }
    } catch (Exception e) {
      view.viewMessage("Something went wrong in get portfolios\n More Information: "
              + e.getMessage() + "\n");
    }
    view.viewOutMessage("Press 0 to get menu\n");
  }

  /**
   * This method will act as a helper to get the values from view and send them to model, if anyone
   * of the inout is quit it right away exits the application.
   */
  protected void helperGetPortfolio() {
    try {
      String pfName;
      pfName = view.getPortfolioName();
      quitCheck(pfName);
      view.viewMessage(model.getPortFolio(pfName) + "\n");
    } catch (Exception e) {
      view.viewMessage("Something went wrong while getting: " + view.getPortfolioName()
              + "portfolio\n" + "More Information: " + e.getMessage() + "\n");
    }
    view.viewOutMessage("Press 0 to get menu\n");
  }

  /**
   * This helper method gets the input from view and change string to local date and sends it to the
   * model. if anytime the input is quit, it exits the application.
   */
  protected void helperGetTotalValue() {
    try {
      String pfName = view.getPortfolioName();
      quitCheck(pfName);
      String date = view.getDate();
      quitCheck(date);
      String time = view.getTime();
      quitCheck(time);
      LocalDateTime localDate = validation.getLocalDateTime(date + time);
      double totalValue = model.getTotalValue(pfName, localDate);
      view.viewMessage("The total value is (in $): " + totalValue + "\n");
    } catch (Exception e) {
      view.viewMessage("Something went wrong while computing total value\n " + "More Information: "
              + e.getMessage());
    }
    view.viewOutMessage("Press 0 to get menu\n");
  }

  /**
   * This helper method gets input from the view and checks anytime it is quit if yes exits the
   * application, else it will send the input to controller to get output from it.
   */
  protected void helperGetCostBasis() {
    try {
      String pfName = view.getPortfolioName();
      quitCheck(pfName);
      String date = view.getDate();
      quitCheck(date);
      String time = view.getTime();
      quitCheck(time);
      LocalDateTime localDate = validation.getLocalDateTime(date + time);
      double costS = model.getCostBasis(pfName, localDate);
      view.viewMessage("The cost basis is (in $): " + costS + "\n");
    } catch (Exception e) {
      view.viewMessage("Something went wrong while computing cost basis\n " + "More Information: "
              + e.getMessage() + "\n");
    }
    view.viewOutMessage("Press 0 to get menu\n");
  }

  /**
   * This method will invoke the quit function when required. and this will be called first before
   * displaying methods. One a data retrieval type is selected the whole operation will be performed
   * using that type of retrieval only.
   */
  protected void helperSetDataType(StockDataRetrievalEnum stockData) {
    view.viewOutMessage("Please select data you want to work with either 'FILE' or 'API' " +
            "If FILE make " + "sure you refer to SETUP-README.txt");
    try {
      String choice = view.getDataChoice();
      if (choice.equalsIgnoreCase("API")) {
        stockData = StockDataRetrievalEnum.API;
      } else if (choice.equalsIgnoreCase("FILE")) {
        stockData = StockDataRetrievalEnum.FILE;
      }
      view.viewMessage(stockData + " chosen");
      if (stockData == null) {
        view.viewMessage("please add file");
        return;
      }
      model.setDataRetrievalSource(stockData);
    } catch (Exception e) {
      view.viewMessage("The action to choose data failed because:" + e.getMessage());
      return;
    }
  }

  /**
   * This helper method gets the input from the view and checks if anyone is quit, if yes it quits
   * the application. else it will make the model call and gets out from the model and sents to view
   * to print it.
   */
  protected void helperBuyShare() {
    try {
      String pfName = view.getPortfolioName();
      quitCheck(pfName);
      String ticker = view.getTickerSymbol();
      quitCheck(ticker);
      String amount = view.getTotalInvestAmount();
      quitCheck(amount);
      String date = view.getDate();
      quitCheck(date);
      String time = view.getTime();
      quitCheck(time);
      LocalDateTime localDate = validation.getLocalDateTime(date + time);
      model.buyShare(pfName, ticker, Double.parseDouble(amount), localDate);
      view.viewMessage("Bought share successfully\n");
    } catch (Exception e) {
      view.viewMessage("Something went wrong while buying share\n " + "More Information: "
              + e.getMessage() + "\n");

    }
    view.viewOutMessage("Press 0 to get menu\n");
  }


  /**
   * This method checks if the input from the view is quit in between the operation then exists the
   * method. Else it will get the valid portfolio name from the view arfter validations to proceed
   * on with further operations.
   */
  protected void helperCreatePF() {
    try {
      String portfolioName = view.getPortfolioName();
      quitCheck(portfolioName);
      model.createPortfolio(portfolioName);
      view.viewMessage("Portfolio: " + portfolioName + " created successfully\n");
    } catch (Exception e) {
      view.viewMessage("Something went wrong while creating the portfolio\n More Information "
              + e.getMessage());
    }
  }

  /**
   * This method check the input from the user coming from view is quit. If yes it invokes true,
   * which will make a call in main controller method to quit.
   *
   * @param isQuit as String
   */
  protected void quitCheck(String isQuit) {
    if (isQuit.equalsIgnoreCase("quit")) {
      handleRequest();
    }
  }
}
