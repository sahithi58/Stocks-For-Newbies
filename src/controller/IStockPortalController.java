package controller;


/**
 * Interface for the investment stocks portal controller for learning trading stocks. An
 * implementation will work with the IStockPortalController interface to provide a stock portal. The
 * method here gets the type of data to be used for whole operation in one session selected can  be
 * changed in the main file.
 */
public interface IStockPortalController {

  /**
   * This method gives the types of operations that can be performed on the stock portal with
   * provided the data retrieval medium which can be either API or FILE for now. Given valid inputs
   * it makes the call to model to retrieve the required data or perform operations.
   */
  void handleRequest();
}
