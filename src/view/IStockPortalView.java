package view;

import java.util.Scanner;

/**
 * This method will be the view of the application, it performs input and output operation to get
 * input from user and work on it.
 */
public interface IStockPortalView extends GuiCmdCommonInterface {

  /**
   * This method gets the name of the portfolio as input from the user and sends it to the
   * controller in the form of string. This method will not let user submit the null or empty
   * string, it validates the input before sent to controller.
   *
   * @return the name of the portfolio as String.
   */
  String getPortfolioName();

  /**
   * This method gets the ticker symbol as input from the user and returns it as string to sent to
   * the controller. This method will not let empty or null to pass to the controller, it validates
   * the basic things before sent to controller.
   *
   * @return the ticker symbol as String.
   */
  String getTickerSymbol();

  /**
   * This method gets the date from the user and sends them to the controller for operations on the
   * model. This method will keep on asking the user to give a valid input in ***** format for
   * further processing. This validation will prevent from controller facing unwanted issues with
   * redundant data.
   *
   * @return the date as local String.
   */
  String getDate();

  /**
   * This method gets the time from the user and sends them to the controller for operations on the
   * model. This method will keep on asking the user to give a valid input in ***** format for
   * further processing. This validation will prevent from controller facing unwanted issues with
   * redundant data.
   *
   * @return time as String.
   */
  String getTime();

  /**
   * This method gets the money that user wants to spend on that particular companies stock. In
   * method will only allow numbers to be inputted.
   *
   * @return the amount in double to send to controller.
   */
  String getTotalInvestAmount();


  /**
   * This method take the menu or contents from the controller after the operations are performed on
   * the data, and prints the menu to show user.
   *
   * @param message as String take the content from controller to show the user.
   */
  void viewOutMessage(String message);

  /**
   * It get the scanner call from the controller to take inputs from the user.
   *
   * @return new scanner object.
   */
  Scanner getInput();


  /**
   * This method gets the choice of the data that user want to work with in the current session.
   */
  String getDataChoice();

}
