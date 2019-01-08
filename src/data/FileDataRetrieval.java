package data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Properties;

/**
 * This class reads data from a FILE. The file names must be the TICKER symbol and the format of the
 * file must be a csv file consisting of a header which has timestamp,open,high,low,close,volume
 * data in this specific format. The user must give the path of the folder which has the files for
 * all the tickers he wants to trade on.
 */
public class FileDataRetrieval implements StockDataRetrievalOperations {
  private String filePath;

  FileDataRetrieval() {
    Properties prop = new Properties();
    try {
      FileInputStream properties = new FileInputStream("config.properties");
      prop.load(properties);
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.filePath = prop.getProperty("filePath");
  }


  @Override
  public String[] readData(String tickerSymbol) throws NoSuchFileException {
    StringBuilder output = new StringBuilder();
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(filePath + tickerSymbol + ".csv"));
      String inputLine;
      while ((inputLine = br.readLine()) != null) {
        output.append(inputLine + "\n");
      }
    } catch (IOException e) {
      throw new NoSuchFileException("No such file found " + tickerSymbol + ".csv");
    }
    return output.toString().split("\n");
  }

  @Override
  public boolean validateTickerSymbol(String tickerSymbol) {
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(filePath + tickerSymbol + ".csv"));
    } catch (IOException e) {
      return false;
    }
    return true;
  }

}
