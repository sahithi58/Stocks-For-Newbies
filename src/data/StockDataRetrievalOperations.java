package data;

import java.nio.file.NoSuchFileException;

/**
 * This class retrieves the data from the mentioned data source. The classes implementing this
 * interface should throw NoSuchElementFoundException if the data was not found.
 */
public interface StockDataRetrievalOperations {

  /**
   * This function readData has to be implemented in every data retrieval. It should read data from
   * any given data source that implements it and should return a array of Strings. Each string
   * should contain timestamp,open,high,low,close,volume data in that specific order separated by
   * commas.
   */
  String[] readData(String tickerSymbol) throws NoSuchFileException;

  /**
   * Returns true if the ticker symbol is a valid ticker symbol.
   */
  boolean validateTickerSymbol(String tickerSymbol) throws NoSuchFileException;
}
