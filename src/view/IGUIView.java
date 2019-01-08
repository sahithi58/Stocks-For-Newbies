package view;

import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

/**
 * This is the view for representing and performing the method related to Graphical user , in
 * swing.
 */
public interface IGUIView extends GuiCmdCommonInterface, LoadForms {

  /**
   * This method will add listeners to each buttion in on which this method is called.
   *
   * @param listener as ActionListener.
   */
  void addActionListener(ActionListener listener);

  /**
   * This method will get the values from the created form.
   *
   * @return it returns portfolio name as string.
   */
  String getCreateForm();

  /**
   * This method will call the retrieve portfolio method which will retrieve ask for the name of
   * portfolio it wants to retrieve.
   *
   * @return the portfolio as String.
   */
  String retrievePFForm();


  /**
   * This method will get the form attributes of the buy share method and sends it to the
   * controller.
   *
   * @return all attributes as array of strings.
   */
  String[] getBuyShares();

  /**
   * This method will get the form attributes of the cost basis form method and sends it to the
   * controller.
   *
   * @return all attributes as array of strings.
   */
  String[] getCostBasis();

  /**
   * This method will get the form attributes of the total value form method and sends it to the
   * controller.
   *
   * @return all attributes as array of strings.
   */
  String[] getTotalValue();

  /**
   * This method will get the form attributes of making fixed investment form method and sends it to
   * the controller.
   *
   * @return all attributes as array of strings.
   */
  String[] makeFixedInv();

  /**
   * This method will get the form attributes of the recurring strategy or here dollarcost average
   * form method and sends it to the controller.
   *
   * @return all attributes as array of strings.
   */
  String[] dollarCostInv();

  /**
   * This method will get the form attributes of get portfolio form method and sends it to the
   * controller.
   *
   * @return all attributes as array of strings.
   */
  String getPortfolio();

  /**
   * This method will get the form attributes of the for dynamically creating the dollar cost averge
   * form andd sends it to the controller.
   *
   * @return all attributes as array of strings.
   */
  String[] getTotalNumOfStock();

  /**
   * This method will clear the attributes in a form , like a refresh.
   */
  void cancelOperation();

  /**
   * This method will show advanced buy options that are provided via this interface.
   */
  void showAdvancedBuyOptions();

  /**
   * This method when encounters invalid ticker call this method to make the ticker column empty.
   */
  void setTickerToEmpty();

  /**
   * This method asks if the user wants to persis the portfolio, if yes then response is sent to
   * controller.
   *
   * @return all attributes as array of strings.
   */
  String[] askIfWantsToSave();

  /**
   * This method asks if the user wants to retrieve and apply the strategy to any portfolio, for
   * which user has to provide inputs as portfolio name and unique strategy name.
   *
   * @return all attributes as array of strings.
   */
  String[] askIfWantsToRetrieveStrategy();

  /**
   * This method gets all the plot details required to make a plot.
   *
   * @return returns array of inputs which are date and portfolio given by the user.
   */
  String[] getPlotDetails();

  /**
   * This method will show the buy options to user for performing any operations related to buy.
   */
  void showCURDAdvancedOptions();

  /**
   * This method will plot the graph for total value vs date.
   *
   * @param totalVals the list of computed totals vals
   * @param start     the start date since when the user wants to see the graph.
   * @param end       the end date that user will give until when he wants to see his graph.
   */
  void plot(List<Double> totalVals, LocalDateTime start, LocalDateTime end);

  /**
   * For scroller functionality for long messages.
   */
  void viewLongMessage(String message);
}
