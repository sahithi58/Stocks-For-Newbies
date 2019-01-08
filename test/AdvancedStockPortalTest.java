import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import data.StockDataRetrievalEnum;
import model.AdvancedStockPortal;
import model.AdvancedStockPortalOperations;

import static junit.framework.TestCase.assertEquals;

public class AdvancedStockPortalTest {
  private AdvancedStockPortalOperations stockPortalOperations;

  @Before
  public void setup() {
    stockPortalOperations = new AdvancedStockPortal();
    stockPortalOperations.setDataRetrievalSource(StockDataRetrievalEnum.FILE);
  }

  /**
   * Tests buyShare on holidays should schedule it to be bought on the next working day but cost
   * basis and totalValue on the holiday will no change till the next working day if you buy with
   * commission.
   */
  @Test
  public void testBuyOnHolidayOrSaturdayOrSunday() {
    stockPortalOperations.createPortfolio("Retirement Portfolio");
    stockPortalOperations.buyShare("Retirement Portfolio", "AAPL",
            3400, LocalDateTime.parse("2018-10-20T11:30:00"), 2);
    assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[Portfolio Name " +
            ": Retirement Portfolio\n" +
            " 1. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-22T11:30," +
            " Number of Shares = 15.409018808067074, Commission = 2.0 \n" +
            "]");
    assertEquals(stockPortalOperations.getTotalValue("Retirement Portfolio",
            LocalDateTime.parse("2018-11-20T11:30:00")), 2727.0881486517105);
    stockPortalOperations.buyShare("Retirement Portfolio", "AAPL",
            3400, LocalDateTime.parse("2018-10-21T11:30:00"), 2);
    assertEquals("Portfolio Name : Retirement Portfolio\n" +
                    " 1. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-22T11:30, " +
                    "Number of Shares = 15.409018808067074, Commission = 2.0 \n" +
                    " 2. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-22T11:30," +
                    " Number of Shares = 15.409018808067074, Commission = 2.0 \n",
            stockPortalOperations.getPortFolio("Retirement Portfolio").toString());
    stockPortalOperations.buyShare("Retirement Portfolio", "AAPL",
            3400, LocalDateTime.parse("2018-01-15T11:30:00"), 2);
    assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[Portfolio Name " +
            ": Retirement Portfolio\n" +
            " 1. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-22T11:30, Number" +
            " of Shares = 15.409018808067074, Commission = 2.0 \n" +
            " 2. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-22T11:30, Number " +
            "of Shares = 15.409018808067074, Commission = 2.0 \n" +
            " 3. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-01-16T11:30, Number " +
            "of Shares = 19.297349452295816, Commission = 2.0 \n" +
            "]");
    stockPortalOperations.buyShare("Retirement Portfolio", "GOOG",
            5600, LocalDateTime.parse("2017-12-25T11:30:00"), 2);
    assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[Portfolio Name : " +
            "Retirement Portfolio\n" +
            " 1. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-22T11:30, Number " +
            "of Shares = 15.409018808067074, Commission = 2.0 \n" +
            " 2. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-22T11:30, Number " +
            "of Shares = 15.409018808067074, Commission = 2.0 \n" +
            " 3. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-01-16T11:30, Number " +
            "of Shares = 19.297349452295816, Commission = 2.0 \n" +
            " 4. Ticker = GOOG, CostBasis = $5600.0, Date Purchased = 2017-12-26T11:30, Number " +
            "of Shares = 5.299316766659727, Commission = 2.0 \n" +
            "]");
    stockPortalOperations.buyShare("Retirement Portfolio", "GOOG",
            5600, LocalDateTime.parse("2018-11-01T11:30:00"), 2);
    assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[Portfolio Name" +
            " : Retirement Portfolio\n" +
            " 1. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-22T11:30, Number " +
            "of Shares = 15.409018808067074, Commission = 2.0 \n" +
            " 2. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-22T11:30, Number" +
            " of Shares = 15.409018808067074, Commission = 2.0 \n" +
            " 3. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-01-16T11:30, Number" +
            " of Shares = 19.297349452295816, Commission = 2.0 \n" +
            " 4. Ticker = GOOG, CostBasis = $5600.0, Date Purchased = 2017-12-26T11:30, Number" +
            " of Shares = 5.299316766659727, Commission = 2.0 \n" +
            " 5. Ticker = GOOG, CostBasis = $5600.0, Date Purchased = 2018-11-01T11:30, Number " +
            "of Shares = 5.233644859813084, Commission = 2.0 \n" +
            "]");
    stockPortalOperations.buyShare("Retirement Portfolio", "GOOG",
            5600, LocalDateTime.parse("2017-10-13T11:30:00"), 2);
    assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[Portfolio Name" +
            " : Retirement Portfolio\n" +
            " 1. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-22T11:30, Number " +
            "of Shares = 15.409018808067074, Commission = 2.0 \n" +
            " 2. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-22T11:30, Number" +
            " of Shares = 15.409018808067074, Commission = 2.0 \n" +
            " 3. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-01-16T11:30, Number " +
            "of Shares = 19.297349452295816, Commission = 2.0 \n" +
            " 4. Ticker = GOOG, CostBasis = $5600.0, Date Purchased = 2017-12-26T11:30, Number " +
            "of Shares = 5.299316766659727, Commission = 2.0 \n" +
            " 5. Ticker = GOOG, CostBasis = $5600.0, Date Purchased = 2018-11-01T11:30, Number" +
            " of Shares = 5.233644859813084, Commission = 2.0 \n" +
            " 6. Ticker = GOOG, CostBasis = $5600.0, Date Purchased = 2017-10-13T11:30, Number " +
            "of Shares = 5.658394632608521, Commission = 2.0 \n" +
            "]");
  }

}
