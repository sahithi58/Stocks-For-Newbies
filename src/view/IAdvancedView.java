package view;


/**
 * This method will be the view of the application, it performs input and output operation to get
 * input from user and work on it it extends the existing Stock Portal view interface.
 */
public interface IAdvancedView extends IStockPortalView {

  /**
   * Ask for the choice before investing if they want to invest equally or not.
   *
   * @return the choice as y or n as String.
   */
  String getInvestingChoice();

  /**
   * Ask for the end date choice if they want to mention the end date or not.
   *
   * @return the choice as y or n as String.
   */
  String getEndDateChoice();

  /**
   * Asks the number of days after which each investment has to be repeated.
   *
   * @return the no of days as String.
   */
  String getFrequency();

  /**
   * Ask for the commission amount and validates it if number or not.
   *
   * @return the commission entered by user as String.
   */
  String getCommission();


  /**
   * Checks if the percent inputted is a number or not.
   *
   * @param name the name of the ticker to ask what percent they want to add for corresponding
   *             ticker.
   * @return the value of percents as String.
   */
  String getPercent(String name);

  /**
   * This asked user number of stocks he wants to add before entering ticker symbols or
   * percentages.
   *
   * @return the num of stocks as String.
   */
  String getNumOfStock();

  /**
   * This method will show the buy options to user for performing any operations related to buy.
   */
  void showAdvancedOptions();

}
