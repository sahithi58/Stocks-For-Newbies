package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.StockDataRetrievalEnum;
import model.IAdvancedPlusStockPortal;
import model.PortfolioOperations;
import view.IGUIView;

/**
 * This is gui controller which controls the GUI view and the existing model.
 */
public class GUIController implements IStockPortalController {

  private IAdvancedPlusStockPortal model;
  private IGUIView view;
  private IValidation validation;

  /**
   * This constructor initializes the model , the existing one and view a newly created one. This is
   * the method thats asks data choice in the beginning as well.
   *
   * @param model as IAdvancedPlusStockPortal.
   * @param view  as IGUIView
   */
  public GUIController(IAdvancedPlusStockPortal model, IGUIView view) {
    validation = new Validations();
    this.model = model;
    this.view = view;
    StockDataRetrievalEnum stockData = null;
    helperSetDataType(stockData);
    configureButtonListener();
  }

  /**
   * This method will initialize all the action commands that have been called in the view.
   */
  private void configureButtonListener() {
    Map<String, Runnable> buttonClickedMap = new HashMap<String, Runnable>();
    ButtonListener buttonListener = new ButtonListener();
    buttonClickedMap.put("loadForm", () -> {
      view.loadCreatePFForm();
    });
    buttonClickedMap.put("buyBtn", () -> {
      view.showAdvancedBuyOptions();
    });
    buttonClickedMap.put("retrievePF", () -> {
      helperRetrievePF();
    });
    buttonClickedMap.put("saveButtonPf", () -> {
      helperSavePF();
    });
    buttonClickedMap.put("options", () -> {
      view.showCURDAdvancedOptions();
    });
    buttonClickedMap.put("loadBuyShareForm", () -> {
      String[] arr = getExistingPFs();
      if (nullCheck(arr == null)) {
        return;
      }
      view.loadBuyShareForm(arr);
    });
    buttonClickedMap.put("plotgraph", () -> {
      helperPlotGraph();
    });

    buttonClickedMap.put("costBasisBtn", () -> {
      String[] arr = getExistingPFs();
      if (nullCheck(arr == null)) {
        return;
      }
      view.loadCostBasisForm(arr);
    });

    buttonClickedMap.put("investFixed", () -> {
      helperInvestFixed();
    });
    buttonClickedMap.put("investFixedSubmit", () -> {
      helperLoadFixedInvForm();

    });
    buttonClickedMap.put("totalValBtn", () -> {
      String[] arr = getExistingPFs();
      if (nullCheck(arr == null)) {
        return;
      }
      view.loadTotalValForm(arr);
    });
    buttonClickedMap.put("getPFBtn", () -> {
      String[] arr = getExistingPFs();
      if (nullCheck(arr == null)) {
        return;
      }
      view.loadGetPortfolioForm(arr);
    });
    buttonClickedMap.put("getAllPFBtn", () -> {
      helperGetAllPFS();
    });

    buttonClickedMap.put("back", () -> {
      view.showOption();
    });
    buttonClickedMap.put("totalValSubmit", () -> {
      helperTotalVal();
    });
    buttonClickedMap.put("costBasisSubmit", () -> {
      helperCostBasis();
    });
    buttonClickedMap.put("dollarCost", () -> {
      helperDollarCostForm();
    });
    buttonClickedMap.put("dollarCostSubmit", () -> {
      helperDollarCost();
    });
    buttonClickedMap.put("getPFSubmit", () -> {
      helperGetPF();
    });
    buttonClickedMap.put("exitBtn", () -> {
      System.exit(0);
    });
    buttonClickedMap.put("cancel", () -> {
      view.cancelOperation();
    });
    buttonClickedMap.put("getCreatedPF", () -> {
      helperCreatePF();
    });
    buttonClickedMap.put("buyButtonSubmit", () -> {
      helperBuyShare();
    });
    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.view.addActionListener(buttonListener);
  }

  private void helperDollarCostForm() {
    String[] arr1 = view.askIfWantsToRetrieveStrategy();
    if (arr1 == null) {
      return;
    }
    if (arr1.length != 0) {
      try {
        model.retrieveStrategyAndApplyToPortfolio(arr1[0], arr1[1]);
        view.viewMessage("Strategy applied to portfolio successfully");
      } catch (Exception e) {
        view.viewMessage("Something went wrong" + e.getMessage());
      }
      return;
    } else {
      String[] arr = getExistingPFs();
      String[] preFormArr = view.getTotalNumOfStock();
      if (nullCheck(preFormArr == null)) {
        return;
      }
      view.loadDollarCostForm(Integer.parseInt(preFormArr[2]), preFormArr[1], preFormArr[0], arr);
    }
  }

  /**
   * This helper will call view to create the fixed investment form.
   */
  private void helperLoadFixedInvForm() {
    try {
      String[] arr = view.makeFixedInv();
      if (nullCheck(arr == null)) {
        return;
      }
      Map<String, Double> mapper = new HashMap<>();
      int j = 0;
      for (int i = 1; j < Integer.parseInt(arr[0]); i = i + 2) {
        mapper.put(arr[i + 5], Double.parseDouble(arr[i + 6]));
        j = j + 1;
      }
      model.performFixedInvestments(arr[5], arr[2],
              validation.getLocalDateTime(arr[3] + arr[4]), arr[1], mapper);
      view.viewMessage("One time investment successful");
      view.cancelOperation();
    } catch (Exception e) {
      view.viewMessage("Something went wrong try again! More:" + e.getMessage());
    }
    view.cancelOperation();
  }

  /**
   * Helper to plot the graph for portfolio performance analysis.
   */
  private void helperPlotGraph() {
    String[] arr = view.getPlotDetails();
    if (nullCheck(arr == null)) {
      return;
    }
    LocalDateTime startDate = validation.getLocalDateTime(arr[1] + "10:10:10");
    LocalDateTime endDate = validation.getLocalDateTime(arr[2] + "10:10:10");
    List<Double> totalVals = model.plotGraph(arr[0], startDate, endDate);
    try {
      view.plot(totalVals, startDate, endDate);
    } catch (Exception e) {
      view.viewMessage("Cannot build graph" + e.getMessage());
    }
  }

  /**
   * Helper to save the portfolio.
   */
  private void helperSavePF() {
    try {
      String name = view.showSavePFForm();
      if (nullCheck(name == null)) {
        return;
      }
      model.savePortfolio(name);
      view.viewMessage("Portfolio Saved Successfully");
    } catch (Exception e) {
      view.viewMessage("Something went wrong: " + e.getMessage());
    }
  }

  /**
   * Helper to retrieve the requested portfolio.
   */
  private void helperRetrievePF() {
    try {
      String name = view.retrievePFForm();
      if (nullCheck(name == null)) {
        return;
      }
      model.retrievePortfolio(name);
      view.viewMessage("Portfolio retrieved Successfully");
    } catch (Exception e) {
      view.viewMessage("Something went wrong: " + e.getMessage());
    }
  }

  /**
   * Helper to call the models fixed investment method and user those values to view.
   */
  private void helperInvestFixed() {
    String[] arr = getExistingPFs();
    if (nullCheck(arr == null)) {
      return;
    }
    if (arr.length == 0) {
      view.viewMessage("No portfolio, please create it to make one time investment");
      return;
    }
    String[] name = view.getFixedInvBasic(arr);
    if (nullCheck(name == null)) {
      return;
    }
    List<String> getUniqueStocks
            = validation.getUniqueStocks(model.getPortFolio(name[0]).getStocks());
    if (getUniqueStocks.size() == 0) {
      view.viewMessage("No stocks to make a fixed investment, please buy.");
      return;
    }
    String[] newArr = getUniqueStocks.toArray(new String[getUniqueStocks.size()]);
    view.loadFixedInvForm(newArr, name[1]);
  }

  /**
   * Helper to run the dollar cost averaging strtategy and also saving the strategy post the call.
   */
  private void helperDollarCost() {
    try {
      String[] val = view.dollarCostInv();
      if (nullCheck(val == null)) {
        return;
      }
      String pfName = val[1];
      try {
        model.createPortfolio(pfName);
      } catch (Exception e) {
        //Do nothing
      }
      Map<String, Double> mapper = new HashMap<>();
      int j = 0;
      for (int i = 1; j < Integer.parseInt(val[0]); i = i + 2) {
        mapper.put(val[i + 8], Double.parseDouble(val[i + 9]));
        j = j + 1;
      }
      LocalDateTime localDate = validation.getLocalDateTime(val[3] + val[4]);
      if (val[6].equals("")) {
        val[6] = "10:10:10";
      }
      LocalDateTime endDate;
      if (val[5].equals("")) {
        LocalDate date = LocalDate.now();
        endDate = validation.getLocalDateTime(date.toString() + val[6]);
      } else {
        endDate = validation.getLocalDateTime(val[5] + val[6]);
      }
      model.performDollarCostAveraging(val[1], val[2], localDate, val[7], endDate, val[8], mapper);
      view.viewMessage("Recurring investmentt executed successfully");
      String[] choice = view.askIfWantsToSave();
      if (choice[0].equalsIgnoreCase("Y")) {
        String nameToSave = choice[1];
        Map<String, String> map = validation.buildMap(val[2], val[3], val[4], endDate, val[7],
                val[8], mapper);
        map.put("Strategy Name", nameToSave);
        model.saveStrategy(map);
        view.viewMessage("Strategy Saved Successfully");
      }
    } catch (Exception e) {
      view.viewMessage("Something went wrong while buying share\n " + "More Information: "
              + e.getMessage() + "\n");
    }
  }

  /**
   * Helper to return the total value of that portofolio at that date and time.
   */
  private void helperTotalVal() {
    try {
      String[] arr = view.getCostBasis();
      if (nullCheck(arr == null)) {
        return;
      }
      LocalDateTime localDate = validation.getLocalDateTime(arr[1] + arr[2]);
      double totalValue = model.getTotalValue(arr[0], localDate);
      view.viewMessage("The total value is (in $): " + totalValue + "\n");
    } catch (Exception e) {
      view.viewMessage("Something went wrong while computing total value\n "
              + "More Information: " + e.getMessage());
    }
    view.cancelOperation();
  }

  /**
   * Helper to return the cost basis of that portfolio at that date and time.
   */
  private void helperCostBasis() {
    try {
      String[] arr = view.getCostBasis();
      if (nullCheck(arr == null)) {
        return;
      }
      LocalDateTime localDate = validation.getLocalDateTime(arr[1] + arr[2]);
      double costS = model.getCostBasis(arr[0], localDate);
      view.viewMessage("The cost basis is (in $): " + costS + "\n");
    } catch (Exception e) {
      view.viewMessage("Something went wrong while computing total value\n "
              + "More Information: " + e.getMessage());
    }
    view.cancelOperation();
  }

  /**
   * Helper to get the portfolio from the model and view as String in the view.
   */
  private void helperGetPF() {
    try {
      String name = view.getPortfolio();
      if (nullCheck(name == null)) {
        return;
      }
      view.viewLongMessage(model.getPortFolio(name).toString());
    } catch (Exception e) {
      view.viewMessage("Something went wrong while getting: " + view.getPortfolio()
              + "portfolio\n" + "More Information: " + e.getMessage() + "\n");
    }
    view.cancelOperation();
  }

  /**
   * Helper to buy share via model and showing success to user via view.
   */
  private void helperBuyShare() {
    try {
      String[] buyShares = view.getBuyShares();
      if (nullCheck(buyShares == null)) {
        return;
      }
      LocalDateTime localDate = validation.getLocalDateTime(buyShares[4] + buyShares[5]);
      if (!model.isValidStock(buyShares[1])) {
        view.viewMessage("Invalid stock, enter again");
        view.setTickerToEmpty();
        return;
      }
      model.buyShare(buyShares[0], buyShares[1], Double.parseDouble(buyShares[3]), localDate,
              Double.parseDouble(buyShares[2]));
      view.viewMessage("Bought share successfully\n");
    } catch (Exception e) {
      view.viewMessage("Something went wrong while buying share\n " + "More Information: "
              + e.getMessage() + "\n");
    }
    view.cancelOperation();
  }


  /**
   * Helper to call model to create the portfolio and displaying post result to the view.
   */
  private void helperCreatePF() {
    String name = null;
    try {
      name = view.getCreateForm();
      if (nullCheck(name == null)) {
        return;
      }
      model.createPortfolio(name);
      view.viewMessage("Portfolio: " + name + " Created Successfully");
      view.cancelOperation();
    } catch (Exception e) {
      view.viewMessage("Something went wrong while creating portfolio: " + name + ", More: "
              + e.getMessage());
    }
  }

  /**
   * If null quit the method check.
   *
   * @param b a condition is true then return true.
   */
  private boolean nullCheck(boolean b) {
    return b;
  }


  /**
   * This method will invoke the quit function when required. and this will be called first before
   * displaying methods. One a data retrieval type is selected the whole operation will be performed
   * using that type of retrieval only.
   */
  protected void helperSetDataType(StockDataRetrievalEnum stockData) {
    try {
      String choice = view.getDataChoice();
      if (nullCheck(choice == null)) {
        return;
      }
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


  @Override
  public void handleRequest() {
    //Nothing here.
  }

  /**
   * Helper method to get all the existing portfolios, when a button is clicked.
   *
   * @return the array of string.
   */
  private String[] getExistingPFs() {
    List<PortfolioOperations> pfs = model.getAllPortfolios();
    String[] arr = new String[pfs.size()];
    int i = 0;
    for (PortfolioOperations portfolioOperations : pfs) {
      arr[i] = portfolioOperations.getPortfolio();
      i++;
    }
    return arr;
  }

  /**
   * Displays the output of all the portfolio to the view.
   */
  private void helperGetAllPFS() {
    try {
      view.viewLongMessage(model.getAllPortfolios() + "\n");
    } catch (Exception e) {
      view.viewMessage("Something went wrong in get portfolios\n More Information: "
              + e.getMessage() + "\n");
    }
  }

}