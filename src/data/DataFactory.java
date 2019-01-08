package data;


/**
 * This is a factory class which has a method getObj for creating objects of the data retrieval
 * methods.
 */
public class DataFactory {

  private static StockDataRetrievalOperations stockDataRetrievalOperations;

  /**
   * This method takes the data retrieval method chosen by the user returns a object of the given
   * enum chosen.
   */
  public static StockDataRetrievalOperations getObj(StockDataRetrievalEnum stockDataEnum) {
    if (stockDataEnum.equals(StockDataRetrievalEnum.API)) {
      stockDataRetrievalOperations = new APIDataRetrieval();
    } else if (stockDataEnum.equals(StockDataRetrievalEnum.FILE)) {
      stockDataRetrievalOperations = new FileDataRetrieval();
    }
    return stockDataRetrievalOperations;
  }

}
