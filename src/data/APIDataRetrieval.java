package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class reads TIME_SERIES_DAILY data from ALPHA VANTAGE API.
 */
public class APIDataRetrieval implements StockDataRetrievalOperations {

  private List<String> apiKey = new ArrayList<>(Arrays.asList("CUDCIFEJDKDAXNWN",
          "6ORC0D5BJ8DI646K", "8D4422VQNF4UJJJU", "QLOGOWFMSJGIKD8L", "DF93J9NKMRL5VIRP"));
  private static int i = 0;
  private static HashMap<String, String> apisFetched;
  private List<String> tickers;
  private List<String> allTickers;

  /**
   * This constructor will initialized the list of tickers called and APIsfetched map for caching.
   */
  APIDataRetrieval() {
    this.tickers = new ArrayList<>();
    this.allTickers = new ArrayList<>();
    apisFetched = new HashMap<>();
  }

  @Override
  public String[] readData(String tickerSymbol) throws NoSuchFileException {
    StringBuilder output;
    if (apisFetched.containsKey(tickerSymbol)) {
      return apisFetched.get(tickerSymbol).split("\n");
    } else {
      output = fetchFromApi(tickerSymbol);
      if (output.toString().contains("Error")) {
        throw new NoSuchFileException("Invalid API call. Ticker does not exist.");
      }
    }
    return output.toString().split("\n");
  }

  @Override
  public boolean validateTickerSymbol(String tickerSymbol) {
    if (allTickers.contains(tickerSymbol)) {
      return true;
    } else {
      StringBuilder output = null;
      output = fetchFromApi(tickerSymbol);
      if (output.toString().contains("Error")) {
        return false;
      }
      allTickers.add(tickerSymbol);
    }
    return true;
  }

  private StringBuilder fetchFromApi(String tickerSymbol) {
    URL url = null;
    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + tickerSymbol.toUpperCase() + "&apikey=" + apiKey.get(i) + "&datatype=csv");
      i = (i + 1) / 6;
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
    BufferedReader in = null;
    StringBuilder output = new StringBuilder();
    try {
      InputStream response = url.openStream();
      in = new BufferedReader(
              new InputStreamReader(response));
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        output.append(inputLine + "\n");
      }
    } catch (IOException e) {
      throw new NoSuchElementException("No price data found for " + tickerSymbol);
    }
    String outString = "";
    if (apisFetched.size() <= 50) {
      outString = output.toString();
      if (outString.contains("Error")) {
        return output;
      }
      apisFetched.put(tickerSymbol, outString);
      tickers.add(tickerSymbol);
    } else {
      String ticker = tickers.remove(0);
      apisFetched.remove(ticker);
    }
    return output;
  }

}
