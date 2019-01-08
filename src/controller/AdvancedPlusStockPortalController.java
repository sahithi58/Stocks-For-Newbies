package controller;

import java.util.InputMismatchException;
import java.util.Scanner;

import model.IAdvancedPlusStockPortal;
import view.IAdvancedPlusStockPortalView;

/**
 * The AdvancedPlusStockPortalController class implements the controller class which acts as medium
 * between view and model, it takes input from user through view, gets the computed values from
 * model by sending view as input parameters. This will handle all the model methods from a single
 * method, but the controller is asynchronous in nature. To restrict user from getting access to all
 * model methods indirectly we have implemented this to avoid any unwanted inputs and calls. This
 * will have same options as advanced stock portal plus it will have save, retrieve for portfolio.
 * i.e it mainly gives an option for the user to perform basic curd i.e. create, save, retrieve
 * options for portfolio.
 */
public class AdvancedPlusStockPortalController extends AdvancedStockPortalController implements
        IStockPortalController {
  private IAdvancedPlusStockPortal model;
  private IAdvancedPlusStockPortalView view;

  /**
   * The constructor of this controller will initialize model and view which will be used in the
   * overall method to fetch and retrieve data from view and model respectively.
   */
  public AdvancedPlusStockPortalController(IAdvancedPlusStockPortal model,
                                           IAdvancedPlusStockPortalView view) {
    super(model, view);
    this.model = model;
    this.view = view;
  }

  /**
   * This method will implements three major options methods: the operations here are: 0: Will show
   * the options available under option. 1: create portfolio. 2: Save Portfolio. 3. Retrieve
   * portfolio 5.Retrieve Strategy. exit: will exit the entire application quit: here will quit this
   * menu and go to the main menu.
   */
  @Override
  protected void helperCreatePF() {
    Scanner s = view.getInput();
    view.showCURDAdvancedOptions();
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
            view.showCURDAdvancedOptions();
            break;
          case "1":
            super.helperCreatePF();
            view.viewOutMessage("Press 0 to get options menu");
            break;
          case "2":
            helperSavePortfolio();
            view.viewOutMessage("Press 0 to get options menu");
            break;
          case "3":
            helperRetrievePortfolio();
            view.viewOutMessage("Press 0 to get options menu");
            break;
          default:
            view.viewMessage(String.format("Unknown command %s\n", in));
            view.viewOutMessage("Press 0 to get options menu");
            break;
        }
      } catch (InputMismatchException ime) {
        view.viewMessage("Bad length to " + in);
      }
    }
  }

  @Override
  protected void helperFixedInv() {
    super.helperFixedInv();
    String choice = view.getSavingStrategyChoice();
    if (choice.equalsIgnoreCase("Y")) {
      helperSaveStrategy();
    }
  }

  @Override
  protected void helperDollarCostAvg() {
    String choiceRetrieve = view.getRetrievingStrategyChoice();
    if (choiceRetrieve.equalsIgnoreCase("Y")) {
      helperRetrieveStrategy();
      return;
    }
    view.viewMessage("Please enter details to build a new recurring strategy");
    super.helperDollarCostAvg();
    String choiceSave = view.getSavingStrategyChoice();
    if (choiceSave.equalsIgnoreCase("Y")) {
      helperSaveStrategy();
    }
  }

  /**
   * This function takes portfolio name as the user input and validates it, if valid it will save
   * the portfolio on a json file.
   */
  private void helperSavePortfolio() {
    try {
      String pfName = view.getPortfolioName();
      quitCheck(pfName);
      model.savePortfolio(pfName);
      view.viewMessage("Portfolio " + pfName + " successfully saved.");
    } catch (Exception e) {
      view.viewMessage("Something went wrong while saving portfolio\n " + "More Information: "
              + e.getMessage() + "\n");
    }
  }


  /**
   * This function takes portfolio name as the user input and validates it, if valid it will load
   * the portfolio into the program.
   */
  private void helperRetrievePortfolio() {
    try {
      String pfName = view.getPortfolioName();
      quitCheck(pfName);
      model.retrievePortfolio(pfName);
      view.viewMessage("Portfolio " + pfName + " loaded successfully.");
    } catch (Exception e) {
      view.viewMessage("Something went wrong while loading portfolio\n " + "More Information: "
              + e.getMessage() + "\n");
    }
  }


  /**
   * This function takes strategy name as the user input and validates it, if valid it will save the
   * strategy on a json file.
   */
  private void helperSaveStrategy() {
    try {
      String sName = view.getStrategyName();
      quitCheck(sName);
      map.put("Strategy Name", sName);
      model.saveStrategy(map);
      view.viewMessage("Strategy " + sName + " successfully saved.");
      map.clear();
    } catch (Exception e) {
      view.viewMessage("Something went wrong while saving strategy\n " + "More Information: "
              + e.getMessage() + "\n");
    }
  }


  /**
   * This function takes strategy name and portfolio as the user input and validates it, if valid it
   * will apply the strategy on the given portfolio.
   */
  private void helperRetrieveStrategy() {
    try {
      view.viewMessage("Please enter the strategy name and the name of the portfolio you want to "
              + "apply the strategy on");
      String sName = view.getStrategyName();
      String pfName = view.getPortfolioName();
      quitCheck(pfName);
      try {
        model.createPortfolio(pfName);
      } catch (IllegalArgumentException e) {
        //do nothing
      }
      model.retrieveStrategyAndApplyToPortfolio(sName, pfName);
      view.viewMessage("Strategy " + sName + " applied on Portfolio " + pfName + " successfully.");
    } catch (Exception e) {
      view.viewMessage("Something went wrong while loading strategy and applying\n "
              + "More Information: " + e.getMessage() + "\n");
    }
  }

}
