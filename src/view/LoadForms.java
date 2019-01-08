package view;

/**
 * This interface carries all the method that loads the forms related to view for futher operations
 * when called.
 */
public interface LoadForms {

  /**
   * This method loads the required for creating a portfolio.
   */
  void loadCreatePFForm();

  /**
   * This method loads the required form for buy share.
   *
   * @param arr it inputs the list of portfolios that user has created till then.
   */
  void loadBuyShareForm(String[] arr);

  /**
   * This method loads the cost basis for.
   *
   * @param arr input the list of portfolios user has created.
   */
  void loadCostBasisForm(String[] arr);

  /**
   * Loads the total value form for getting total value.
   *
   * @param arr input the list of portfolios user has created.
   */
  void loadTotalValForm(String[] arr);

  /**
   * Loads the get portfolio form for getting total value.
   *
   * @param arr input the list of portfolios user has created.
   */
  void loadGetPortfolioForm(String[] arr);

  /**
   * Loads the fixed investment form dynamically with the no of stocks and existing portoflio
   * names.
   *
   * @param arr  input the list of portfolios user has created.
   * @param name the choice as to he wants to buy stocks equally or not.
   */
  void loadFixedInvForm(String[] arr, String name);

  /**
   * Loads the recurring investment form dynamically with the no of stocks and existing portoflio
   * names.
   *
   * @param numOfStock      the no of stocks user want to buy
   * @param endDateChoice   if the user wants to give an end date
   * @param investingChoice if user wants to equally invest or not
   * @param arr             the list of portfolios.
   */
  void loadDollarCostForm(Integer numOfStock, String endDateChoice, String investingChoice,
                          String[] arr);

  /**
   * This method will ask the user to retrieve their saved porfolio.
   *
   * @return returns the input user requested.
   */
  String showSavePFForm();

  /**
   * This method will retrieve the list of stocks that the user will be investing on in that
   * portfolio.
   *
   * @param arr list of portfolios.
   * @return returns all attributes that are needed to load next form.
   */
  String[] getFixedInvBasic(String[] arr);

}
