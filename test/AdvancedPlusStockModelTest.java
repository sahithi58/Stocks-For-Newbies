import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import data.StockDataRetrievalEnum;
import model.AdvancedPlusStockPortal;
import model.IAdvancedPlusStockPortal;

import static junit.framework.TestCase.assertEquals;

/**
 * Test class for Stock Portal functionality for the newly added save and retrieve functions for a
 * portfolio and strategy.
 */
public class AdvancedPlusStockModelTest {

  private IAdvancedPlusStockPortal stockPortalOperations;

  @Before
  public void setup() {
    stockPortalOperations = new AdvancedPlusStockPortal();
    stockPortalOperations.setDataRetrievalSource(StockDataRetrievalEnum.FILE);
  }

  /**
   * Tests model functionality works for saving a portfolio to a jsonFile.
   */
  @Test
  public void testModelToSaveAPortfolioToFile() {
    stockPortalOperations.createPortfolio("Investment Portfolio");
    stockPortalOperations.setDataRetrievalSource(StockDataRetrievalEnum.FILE);
    stockPortalOperations.buyShare("Investment Portfolio", "MSFT",
            100, LocalDateTime.parse("2018-11-13T11:30:00"));
    assertEquals(stockPortalOperations.getCostBasis("Investment Portfolio",
            LocalDateTime.parse("2018-11-13T11:30:00")), 100.0);
    assertEquals(stockPortalOperations.getTotalValue("Investment Portfolio",
            LocalDateTime.parse("2018-11-13T11:30:00")), 100.0);
    assertEquals(stockPortalOperations.getTotalValue("Investment Portfolio",
            LocalDateTime.parse("2018-11-11T11:30:00")), 0.0);
    stockPortalOperations.savePortfolio("Investment Portfolio");
    Gson gson = new Gson();
    StringBuilder output = new StringBuilder();
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader("Portfolios\\Investment Portfolio.json"));
      String inputLine;
      while ((inputLine = br.readLine()) != null) {
        output.append(inputLine + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(output.toString().trim(), gson.toJson(stockPortalOperations
            .getPortFolio("Investment Portfolio")));
  }

  /**
   * Tests model functionality works for retrieve a portfolio to a jsonFile.
   */
  @Test
  public void testModelToRetrieveAPortfolioToFile() {
    stockPortalOperations.retrievePortfolio("Investment Portfolio");
    assertEquals(stockPortalOperations.getPortFolio("Investment Portfolio").toString(),
            "Portfolio Name : Investment Portfolio\n" +
                    " 1. Ticker = MSFT, CostBasis = $100.0, Date Purchased = 2018-11-13T11:30, " +
                    "Number of Shares = 0.9351037965214138, Commission = $0.0 \n");
  }

  /**
   * Tests model functionality works for retrieve a portfolio to a jsonFile.
   */
  @Test
  public void testModelToRetrieveFromUserCreatedFileAPortfolioToFile() {
    FileWriter file = null;
    try {
      file = new FileWriter("Portfolios\\User Portfolio.json");
      file.write("{\"stocks\":[{\"costBasis\":83758.0,\"ticker\":\"GOOG\",\"datePurchased\"" +
              ":{\"date\":{\"year\":2018,\"month\":8,\"day\":3},\"time\":{\"hour\":11,\"minute\"" +
              ":30,\"second\":0,\"nano\":0}},\"noOfShares\":68.44595533255428,\"commission\":0.0}]"
              + ",\"portFolioName\":\"User Portfolio\"}");
      file.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    stockPortalOperations.retrievePortfolio("User Portfolio");
    assertEquals(stockPortalOperations.getPortFolio("User Portfolio").toString(),
            "Portfolio Name : User Portfolio\n" +
                    " 1. Ticker = GOOG, CostBasis = $83758.0, Date Purchased = 2018-08-03T11:30," +
                    " Number of Shares = 68.44595533255428, Commission = $0.0 \n");
  }

  /**
   * Tests model functionality works for saving a strategy to a jsonFile.
   */
  @Test
  public void testModelToSaveAStrategyToFile() {
    stockPortalOperations.createPortfolio("Investment Portfolio");
    stockPortalOperations.setDataRetrievalSource(StockDataRetrievalEnum.FILE);
    stockPortalOperations.buyShare("Investment Portfolio", "MSFT",
            100, LocalDateTime.parse("2018-11-13T11:30:00"));
    assertEquals(stockPortalOperations.getCostBasis("Investment Portfolio",
            LocalDateTime.parse("2018-11-13T11:30:00")), 100.0);
    assertEquals(stockPortalOperations.getTotalValue("Investment Portfolio",
            LocalDateTime.parse("2018-11-13T11:30:00")), 100.0);
    assertEquals(stockPortalOperations.getTotalValue("Investment Portfolio",
            LocalDateTime.parse("2018-11-11T11:30:00")), 0.0);
    stockPortalOperations.savePortfolio("Investment Portfolio");
    Map<String, Double> map = new HashMap<>();
    map.put("MSFT", 100.0);
    stockPortalOperations.performDollarCostAveraging("Investment Portfolio",
            String.valueOf(2000),
            LocalDateTime.parse("2017-07-13T11:30:00"), String.valueOf(25),
            LocalDateTime.parse("2018-11-13T11:30:00"), String.valueOf(5), map);
    Map<String, String> mapStrategy = new HashMap<>();
    stockPortalOperations.saveStrategy(mapStrategy);
    Gson gson = new Gson();
    StringBuilder output = new StringBuilder();
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader("Strategy\\Strategy1.json"));
      String inputLine;
      while ((inputLine = br.readLine()) != null) {
        output.append(inputLine + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(output.toString().trim(),
            gson.toJson(stockPortalOperations.getPortFolio("Investment Portfolio")));
  }

  /**
   * Tests model functionality works for retrieve a strategy to a jsonFile.
   */
  @Test
  public void testModelToRetrieveAStrategyToFile() {
    stockPortalOperations.retrievePortfolio("Investment Portfolio");
    assertEquals(stockPortalOperations.getPortFolio("Investment Portfolio").toString(),
            "Portfolio Name : Investment Portfolio\n" +
                    " 1. Ticker = MSFT, CostBasis = $100.0, Date Purchased = 2018-11-13T11:30, " +
                    "Number of Shares = 0.9351037965214138, Commission = $0.0 \n");
  }
}
