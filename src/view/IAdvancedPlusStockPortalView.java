package view;

public interface IAdvancedPlusStockPortalView extends IAdvancedView {

  /**
   * This method will show the buy options to user for performing any operations related to buy.
   */
  void showCURDAdvancedOptions();

  String getSavingPortfolioChoice();

  String getSavingStrategyChoice();

  String getRetrievingPortfolioChoice();

  String getRetrievingStrategyChoice();

  String getStrategyName();
}
