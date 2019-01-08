import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

import model.StockPortal;
import model.PortfolioDao;
import model.StockPortalOperations;
import data.StockDataRetrievalEnum;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

/**
 * Test class for Stock Portal funtionality. It tests both API call method and read from File
 * method. By default it tests via read file. Files used for testing a re maintained in the
 * testInputFiles folder within the test directory.
 */
public class StockPortalTest {
  private StockPortalOperations stockPortalOperations;

  @Before
  public void setup() {
    stockPortalOperations = new StockPortal();
    stockPortalOperations.setDataRetrievalSource(StockDataRetrievalEnum.FILE);
  }

  /**
   * Tests model functionality works with File.
   */
  @Test
  public void testModelWorksWithFile() {
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
    assertEquals(stockPortalOperations.getPortFolio("Investment Portfolio").toString(),
            "Portfolio Name : Investment Portfolio\n" +
                    " 1. Ticker = MSFT, CostBasis = $100.0, Date Purchased = 2018-11-13T11:30, " +
                    "Number of Shares = 0.9351037965214138, Commission = $0.0 \n");
    stockPortalOperations.createPortfolio("Education Portfolio");
    stockPortalOperations.buyShare("Education Portfolio", "GOOG",
            83758, LocalDateTime.parse("2018-08-03T11:30:00"));
    assertEquals(stockPortalOperations.getCostBasis("Education Portfolio",
            LocalDateTime.parse("2018-11-13T11:30:00")), 83758.0);
    assertEquals(stockPortalOperations.getTotalValue("Education Portfolio",
            LocalDateTime.parse("2018-11-13T11:30:00")), 70913.43202229285);
    assertEquals(stockPortalOperations.getPortFolio("Education Portfolio").toString(),
            "Portfolio Name : Education Portfolio\n" +
                    " 1. Ticker = GOOG, CostBasis = $83758.0, Date Purchased = 2018-08-03T11:30, " +
                    "Number of Shares = 68.44595533255428, Commission = $0.0 \n");
    PortfolioDao stockPortalDao = new PortfolioDao();
    stockPortalDao.savePortfolioToJson(stockPortalOperations.getPortFolio("Education "
            + "Portfolio"));
    stockPortalDao.retrievePortfolioFromJson("Education Portfolio");
  }

  /**
   * Tests functionality with api.
   */
  @Test
  public void testModelWorksWithAPI() {
    stockPortalOperations.createPortfolio("Investment Portfolio");
    stockPortalOperations.setDataRetrievalSource(StockDataRetrievalEnum.API);
    stockPortalOperations.buyShare("Investment Portfolio", "AAPL",
            250, LocalDateTime.parse("2018-10-24T11:30:00"));
    stockPortalOperations.buyShare("Investment Portfolio", "GOOG",
            100, LocalDateTime.parse("2018-07-16T11:30:00"));
    stockPortalOperations.buyShare("Investment Portfolio", "FB",
            100, LocalDateTime.parse("2018-08-16T11:30:00"));
    stockPortalOperations.buyShare("Investment Portfolio", "MSFT",
            100, LocalDateTime.parse("2018-07-16T11:30:00"));
    stockPortalOperations.buyShare("Investment Portfolio", "AMZN",
            100, LocalDateTime.parse("2018-07-16T11:30:00"));
    stockPortalOperations.buyShare("Investment Portfolio", "PCG",
            100, LocalDateTime.parse("2018-11-12T11:30:00"));
    assertEquals(stockPortalOperations.getCostBasis("Investment Portfolio",
            LocalDateTime.parse("2018-07-16T11:30:00")), 300.0);
    assertEquals(stockPortalOperations.getTotalValue("Investment Portfolio",
            LocalDateTime.parse("2018-10-24T11:30:00")), 611.1934741260671);
    assertEquals(stockPortalOperations.getTotalValue("Investment Portfolio",
            LocalDateTime.parse("2018-07-16T11:30:00")), 300.0);
    assertEquals(stockPortalOperations.getTotalValue("Investment Portfolio",
            LocalDateTime.parse("2018-10-24T11:30:00")), 611.1934741260671);
    assertEquals(stockPortalOperations.getCostBasis("Investment Portfolio",
            LocalDateTime.parse("2018-10-24T11:30:00")), 650.0);
    assertEquals(stockPortalOperations.getCostBasis("Investment Portfolio",
            LocalDateTime.parse("2018-11-14T11:30:00")), 750.0);
    assertEquals(stockPortalOperations.getTotalValue("Investment Portfolio",
            LocalDateTime.parse("2018-11-09T11:30:00")), 609.0928744226111);
    assertEquals(stockPortalOperations.getTotalValue("Investment Portfolio",
            LocalDateTime.parse("2018-11-11T11:30:00")), 609.0928744226111);
    assertEquals(stockPortalOperations.getTotalValue("Investment Portfolio",
            LocalDateTime.parse("2018-11-10T11:30:00")), 609.0928744226111);
    assertEquals(stockPortalOperations.getTotalValue("Investment Portfolio",
            LocalDateTime.parse("2018-08-03T11:30:00")), 306.3935131423397);
    assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[Portfolio Name : " +
            "Investment Portfolio\n" +
            " 1. Ticker = AAPL, CostBasis = $250.0, Date Purchased = 2018-10-24T11:30, Number " +
            "of Shares = 1.16230415175043 \n" +
            " 2. Ticker = GOOG, CostBasis = $100.0, Date Purchased = 2018-07-16T11:30, Number" +
            " of Shares = 0.08446944740087511 \n" +
            " 3. Ticker = FB, CostBasis = $100.0, Date Purchased = 2018-08-16T11:30, Number " +
            "of Shares = 0.5724098454493418 \n" +
            " 4. Ticker = MSFT, CostBasis = $100.0, Date Purchased = 2018-07-16T11:30, Number" +
            " of Shares = 0.953197979220284 \n" +
            " 5. Ticker = AMZN, CostBasis = $100.0, Date Purchased = 2018-07-16T11:30, Number" +
            " of Shares = 0.05486998556919379 \n" +
            " 6. Ticker = PCG, CostBasis = $100.0, Date Purchased = 2018-11-12T11:30, Number " +
            "of Shares = 3.032140691328078 \n" +
            "]");
    assertEquals(stockPortalOperations.getPortFolio("Investment Portfolio").toString(),
            "Portfolio Name : Investment Portfolio\n" +
                    " 1. Ticker = AAPL, CostBasis = $250.0, Date Purchased = 2018-10-24T11:30," +
                    " Number of Shares = 1.16230415175043 \n" +
                    " 2. Ticker = GOOG, CostBasis = $100.0, Date Purchased = 2018-07-16T11:30, " +
                    "Number of Shares = 0.08446944740087511 \n" +
                    " 3. Ticker = FB, CostBasis = $100.0, Date Purchased = 2018-08-16T11:30," +
                    " Number of Shares = 0.5724098454493418 \n" +
                    " 4. Ticker = MSFT, CostBasis = $100.0, Date Purchased = 2018-07-16T11:30," +
                    " Number of Shares = 0.953197979220284 \n" +
                    " 5. Ticker = AMZN, CostBasis = $100.0, Date Purchased = 2018-07-16T11:30, " +
                    "Number of Shares = 0.05486998556919379 \n" +
                    " 6. Ticker = PCG, CostBasis = $100.0, Date Purchased = 2018-11-12T11:30, " +
                    "Number of Shares = 3.032140691328078 \n");
  }

  /**
   * Portfolio name value cannot be passed as null.
   */
  @Test
  public void testCreatePFNull() {
    StockPortalOperations stockPortalOperations = new StockPortal();
    try {
      stockPortalOperations.createPortfolio(null);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Portfolio name cannot be null/empty.");
    }

  }

  /**
   * Trying to make same pruchace on same share.
   */
  @Test
  public void testSamePurchaseSameShare() {
    stockPortalOperations.createPortfolio("Investment Portfolio");
    stockPortalOperations.buyShare("Investment Portfolio", "AAPL",
            250, LocalDateTime.parse("2018-10-24T11:30:00"));
    stockPortalOperations.buyShare("Investment Portfolio", "AAPL",
            400, LocalDateTime.parse("2018-10-24T11:30:00"));
    assertEquals(stockPortalOperations.getPortFolio("Investment Portfolio").toString(),
            "Portfolio Name : Investment Portfolio\n" +
                    " 1. Ticker = AAPL, CostBasis = $250.0, Date Purchased = 2018-10-24T11:30," +
                    " Number of Shares = 1.16230415175043 \n" +
                    " 2. Ticker = AAPL, CostBasis = $400.0, Date Purchased = 2018-10-24T11:30, " +
                    "Number of Shares = 1.859686642800688 \n");
  }

  /**
   * Tests that a user cannot buy a share on the weekend or public holiday.
   */
  @Test
  public void testCannotBuyStockOnWeekend() {
    stockPortalOperations.createPortfolio("Investment Portfolio");
    try {
      stockPortalOperations.buyShare("Investment Portfolio", "PCG",
              100, LocalDateTime.parse("2018-11-11T11:30:00"));
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Cannot buy stock on 2018-11-11 as it is the Weekend or"
              + " a Public Holiday or data not available for given date.");
    }
  }

  /**
   * Tests that a user cannot buy a share on the data older that what the api returns.
   */
  @Test
  public void testCannotBuyStockOnVeryOldDate() {
    stockPortalOperations.createPortfolio("Investment Portfolio");
    try {
      stockPortalOperations.buyShare("Investment Portfolio", "PCG",
              100, LocalDateTime.parse("1981-01-11T11:30:00"));
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Cannot buy stock on 1981-01-11 as it is the Weekend or"
              + " a Public Holiday or data not available for given date.");
    }
  }


  /**
   * When portfolio is sent as null checking if illegal argument exception is being thrown.
   */
  @Test
  public void nullPortfolioAll() {
    try {
      stockPortalOperations.createPortfolio(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[]");
    }
    try {
      stockPortalOperations.buyShare(null, "GOOG", 3090,
              LocalDateTime.parse("2018-01-11T11:30:00"));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[]");
    }
    try {
      stockPortalOperations.getTotalValue(null,
              LocalDateTime.parse("2018-01-11T11:30:00"));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[]");
    }
    try {
      stockPortalOperations.getCostBasis(null,
              LocalDateTime.parse("2018-01-11T11:30:00"));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[]");
    }
    try {
      stockPortalOperations.getPortFolio(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[]");
    }
  }

  /**
   * When portfolio is sent as empty checking if illegal argument exception is being thrown.
   */
  @Test
  public void emptyPortFolio() {
    try {
      stockPortalOperations.createPortfolio("");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[]");
      assertEquals(e.getMessage(), "Portfolio name cannot be null/empty.");
    }
    try {
      stockPortalOperations.buyShare("", "GOOG", 3090,
              LocalDateTime.parse("2018-01-11T11:30:00"));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[]");
    }
    try {
      stockPortalOperations.getTotalValue("",
              LocalDateTime.parse("2018-01-11T11:30:00"));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[]");
    }
    try {
      stockPortalOperations.getCostBasis("",
              LocalDateTime.parse("2018-01-11T11:30:00"));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[]");
    }
    try {
      stockPortalOperations.getPortFolio("");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[]");
    }
  }


  /**
   * Test method to check when invalid portfolio is being sent to buy share an Exception is being
   * thrown.
   */
  @Test
  public void testBuyShareInvalidPF() {
    stockPortalOperations.createPortfolio("Education Portfolio");
    try {
      stockPortalOperations.buyShare("Educational Port", "GOOG",
              20930, LocalDateTime.parse("2018-10-10T11:30:00"));
      fail();
    } catch (NoSuchElementException e) {
      assertEquals(stockPortalOperations.getPortFolio("Education Portfolio").toString()
              , "Portfolio Name : Education Portfolio\n");
    }
  }

  /**
   * Test method to check when valid portfolio with spaces is considered as valid input and existing
   * one.
   */
  @Test
  public void testBuyShareValidPortFolioWithSpaces() {
    stockPortalOperations.createPortfolio("Education Portfolio");
    stockPortalOperations.buyShare("  Education   Portfolio  ", "GOOG",
            20930, LocalDateTime.parse("2018-10-10T11:30:00"));
    assertEquals(stockPortalOperations.getPortFolio("Education Portfolio").toString(),
            "Portfolio Name : Education Portfolio[ticker=GOOG,costBasis=20930.0, "
                    + "datePurchased=2018-10-10T11:30, noOfShares=19.357762527515213]");
  }


  /**
   * Test method to check when valid portfolio with spaces is considered as valid input and existing
   * one.
   */
  @Test
  public void testBuyShareValidPortFolioWithCaseDifference() {
    stockPortalOperations.createPortfolio("Education Portfolio");
    stockPortalOperations.buyShare("  EducatION   pORTfolio  ", "GOOG",
            20930, LocalDateTime.parse("2018-10-10T11:30:00"));
    assertEquals(stockPortalOperations.getPortFolio("Education Portfolio").toString(),
            "Portfolio Name : Education Portfolio\n" +
                    " 1. Ticker = GOOG, CostBasis = $20930.0, Date Purchased = " +
                    "2018-10-10T11:30, Number of Shares = 19.357762527515213 \n");
  }

  /**
   * Test method to check when valid portfolio with no spaces in the name is considered as valid
   * input.
   */
  @Test
  public void testBuyShareValidPortFolioWithoutSpace() {
    stockPortalOperations.createPortfolio("Education");
    stockPortalOperations.buyShare("  EducatION ", "GOOG",
            20930, LocalDateTime.parse("2018-10-10T11:30:00"));
    assertEquals(stockPortalOperations.getPortFolio("Education").toString(),
            "Portfolio Name : Education[ticker=GOOG,costBasis=20930.0,"
                    + " datePurchased=2018-10-10T11:30, noOfShares=19.357762527515213]");
  }


  /**
   * Test method to check when valid portfolio with invalid character is considered as invalid input
   * and throws no such element exception.
   */
  @Test
  public void testBuyShareValidPortFolioWithInvalidChs() {
    stockPortalOperations.createPortfolio("Education Portfolio");
    try {
      stockPortalOperations.buyShare("Education $$ Portfolio", "GOOG",
              20930, LocalDateTime.parse("2018-10-10T11:30:00"));
      fail();
    } catch (NoSuchElementException e) {
      assertEquals(stockPortalOperations.getPortFolio("Education Portfolio")
                      .toString(), "Portfolio Name : Education Portfolio\n");
    }
  }

  /**
   * When ticker symbol is sent as null or empty an Illegal argument exception is thrown.
   */
  @Test
  public void tickerSymbolNull() {
    stockPortalOperations.createPortfolio("Education");
    try {
      stockPortalOperations.buyShare("Education", null,
              8900, LocalDateTime.parse("2018-10-10T11:30:00"));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(stockPortalOperations.getPortFolio("Education").toString(),
              "Portfolio Name : Education[]");
    }
  }


  /**
   * When ticker symbol is sent as empty an Illegal argument exception is thrown.
   */
  @Test
  public void tickerSymbolEmpty() {
    stockPortalOperations.createPortfolio("Education");
    try {
      stockPortalOperations.buyShare("Education", "",
              8900, LocalDateTime.parse("2018-10-10T11:30:00"));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(stockPortalOperations.getPortFolio("Education").toString(),
              "Portfolio Name : Education[]");
    }
  }

  /**
   * When ticker symbol is sent is invalid and requested for a an API call IllegalArgumentException
   * is thrown stating that invalid API call for the given ticker is not found.
   */
  @Test
  public void tickerSymbolIsInvalidForAPI() {
    stockPortalOperations.setDataRetrievalSource(StockDataRetrievalEnum.API);
    stockPortalOperations.createPortfolio("Education");
    try {
      stockPortalOperations.buyShare("Education", "jaasde",
              8900, LocalDateTime.parse("2018-10-10T11:30:00"));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid API call. Ticker does not exist.");
      assertEquals(stockPortalOperations.getPortFolio("Education").toString(),
              "Portfolio Name : Education[]\n");
    }
  }

  /**
   * When ticker symbol is sent is invalid and requested for a an File IllegalArgumentException is
   * thrown that no such file found for the given ticker is not found.
   */
  @Test
  public void tickerSymbolIsInvalidForFile() {
    stockPortalOperations.createPortfolio("Education");
    try {
      stockPortalOperations.buyShare("Education", "jaasde",
              8900, LocalDateTime.parse("2018-10-10T11:30:00"));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "No such file found jaasde.csv");
      assertEquals(stockPortalOperations.getPortFolio("Education").toString(),
              "Portfolio Name : Education[]");
    }
  }

  /**
   * When ticker symbol is sent is not in API and also requested for non API call, then ticker is
   * considered as valid.
   */
  @Test(expected = IllegalArgumentException.class)
  public void tickerSymbolIsGivenByUserWithSharePrice() {
    stockPortalOperations.createPortfolio("Education");
    stockPortalOperations.buyShare("Education", "jaasde",
            8900, LocalDateTime.parse("2018-10-10T11:30:00"));
  }


  /**
   * When ticker symbol is sent is in API and also requested for non API call, then ticker is
   * considered as valid and the share price is taken as the one given by user.
   */
  @Test
  public void tickerSymbolInAPIIsGivenByUserWithSharePrice() {
    stockPortalOperations.createPortfolio("Education");
    stockPortalOperations.buyShare("Education", "GOOG",
            8900, LocalDateTime.parse("2018-10-10T11:30:00"));
    assertEquals(stockPortalOperations.getPortFolio("Education").toString(),
            "Portfolio Name : Education\n" +
                    " 1. Ticker = GOOG, CostBasis = $8900.0, Date Purchased = " +
                    "2018-10-10T11:30, Number of Shares = 8.231442259669633 \n");
  }

  /**
   * When ticker symbol is sent is in API and also requested for non API call, then ticker is
   * considered as valid and the share price is taken as the one given by user.
   */
  @Test
  public void tickerSymbolInAPIIsGivenByUserToAPIPrice() {
    stockPortalOperations.createPortfolio("Education");
    stockPortalOperations.buyShare("Education", "GOOG",
            8900, LocalDateTime.parse("2018-10-10T11:30:00"));
    assertEquals(stockPortalOperations.getPortFolio("Education").toString(),
            "Portfolio Name : Education[ticker=GOOG,costBasis=8900.0, "
                    + "datePurchased=2018-10-10T11:30, noOfShares=8.231442259669633]");
  }


  /**
   * When a valid tickersymbol is given with API and without API then the price is considered
   * different for both of them.
   */
  @Test
  public void testSameTickerDifferentCall() {
    stockPortalOperations.createPortfolio("Education");
    stockPortalOperations.buyShare("Education", "GOOG",
            8900, LocalDateTime.parse("2018-10-10T11:30:00"));
    stockPortalOperations.buyShare("Education", "GOOG",
            8900, LocalDateTime.parse("2018-10-10T11:30:00"));
    assertEquals(stockPortalOperations.getPortFolio("Education").toString(),
            "Portfolio Name : Education[ticker=GOOG,costBasis=8900.0, "
                    + "datePurchased=2018-10-10T11:30, noOfShares=8.231442259669633, "
                    + "ticker=GOOG,costBasis=8900.0, datePurchased=2018-10-10T11:30, "
                    + "noOfShares=8.231442259669633]");
  }

  /**
   * Testing when the price entered to buy a share if negative Illegal argument exception is thrown.
   * Also testing when a value is sent as negative to share price an exception is thrown.
   */
  @Test
  public void testBuySharePriceNegative() {
    stockPortalOperations.createPortfolio("Education");
    try {
      stockPortalOperations.buyShare("Education", "GOOG",
              -8900, LocalDateTime.parse("2018-10-10T11:30:00"));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(stockPortalOperations.getPortFolio("Education").toString(),
              "Portfolio Name : Education[]");
    }
    stockPortalOperations.buyShare("Education", "GOOG",
            10, LocalDateTime.parse("2018-10-10T11:30:00"));
    stockPortalOperations.buyShare("Education", "GOOG",
            19000, LocalDateTime.parse("2018-10-10T11:30:00"));
    assertEquals(stockPortalOperations.getPortFolio("Education").toString(),
            "Portfolio Name : Education[ticker=GOOG,costBasis=10.0, "
                    + "datePurchased=2018-10-10T11:30, noOfShares=0.009248811527718687, "
                    + "ticker=GOOG,costBasis=19000.0, datePurchased=2018-10-10T11:30,"
                    + " noOfShares=17.572741902665506]");
  }


  /**
   * Any for other the asked date format will give date format exception even if date is valid.
   */
  @Test(expected = DateTimeParseException.class)
  public void testIllegalDate() {
    stockPortalOperations.createPortfolio("Edu");
    stockPortalOperations.buyShare("Edu", "GOOG",
            29910, LocalDateTime.parse("10/10/2018T11:30:00"));
  }

  /**
   * Any for other the asked date format will give date format exception even if date is valid.
   */
  @Test(expected = DateTimeParseException.class)
  public void testLegalDateNotAsPerFormat() {
    stockPortalOperations.createPortfolio("Edu");
    stockPortalOperations.buyShare("Edu", "GOOG",
            29910, LocalDateTime.parse("10-10-2018T11:30:00"));
    stockPortalOperations.getCostBasis("Edu",
            LocalDateTime.parse("10-10-2018T11:30:00"));
    stockPortalOperations.getTotalValue("Edu",
            LocalDateTime.parse("10-10-2018T11:30:00"));
  }


  /**
   * Any for other the asked date format will give date format exception even if date is valid and
   * month is invalid.
   */
  @Test(expected = DateTimeParseException.class)
  public void testCorrectFormatIllegalMonth() {
    stockPortalOperations.createPortfolio("Edu");
    stockPortalOperations.buyShare("Edu", "GOOG",
            29910, LocalDateTime.parse("2018-13-12T11:30:00"));
    stockPortalOperations.getCostBasis("Edu",
            LocalDateTime.parse("2018-13-12T11:30:00"));
    stockPortalOperations.getTotalValue("Edu",
            LocalDateTime.parse("2018-13-12T11:30:00"));
  }

  /**
   * Any for other the asked date format will give date format exception even if date is invalid.
   */
  @Test(expected = DateTimeParseException.class)
  public void testCorrectFormatIllegalDate() {
    stockPortalOperations.createPortfolio("Edu");
    stockPortalOperations.buyShare("Edu", "GOOG",
            29910, LocalDateTime.parse("2018-09-32T11:30:00"));
    stockPortalOperations.getCostBasis("Edu",
            LocalDateTime.parse("2018-09-32T11:30:00"));
    stockPortalOperations.getTotalValue("Edu",
            LocalDateTime.parse("2018-09-32T11:30:00"));
  }

  /**
   * Testing when date is 9th and instead of 09 when only 9 is given it should throw error date.
   */
  @Test(expected = DateTimeParseException.class)
  public void testCorrectFormatLegalDateNoZeros() {
    stockPortalOperations.createPortfolio("Edu");
    stockPortalOperations.buyShare("Edu", "GOOG",
            29910, LocalDateTime.parse("2018-9-12T11:30:00"));
  }

  /**
   * Any for other the asked date format will give date format as per the requirement works
   * correctly.
   */
  @Test
  public void testCorrectFormatLegalDate() {
    stockPortalOperations.createPortfolio("Edu");
    stockPortalOperations.buyShare("Edu", "GOOG",
            29910, LocalDateTime.parse("2018-09-12T11:30:00"));
    assertEquals(stockPortalOperations.getCostBasis("Edu",
            LocalDateTime.parse("2018-09-12T11:30:00")), 29910.0);
    assertEquals(stockPortalOperations.getTotalValue("Edu",
            LocalDateTime.parse("2018-09-12T11:30:00")), 29910.0);
  }

  /**
   * Any for other the asked date format will give date format exception even if date is valid and
   * in future.
   */
  @Test
  public void testFutureDateStockCannotBuy() {
    stockPortalOperations.createPortfolio("Edu");
    try {
      stockPortalOperations.buyShare("Edu", "GOOG",
              29910, LocalDateTime.parse("2018-12-20T11:30:00"));
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Cannot buy stocks in future.");
    }
  }

  /**
   * Any for other the asked date format will give date format exception even if date is valid and
   * in future.
   */
  @Test
  public void testCorrectFormatVeryOld() {
    stockPortalOperations.createPortfolio("Edu");
    try {
      stockPortalOperations.buyShare("Edu", "GOOG",
              29910, LocalDateTime.parse("1980-12-22T11:30:00"));
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Cannot buy stock on 1980-12-22 " +
              "as it is the Weekend or a Public Holiday or data not available for given date.");
      assertEquals(stockPortalOperations.getCostBasis("edu",
              LocalDateTime.parse("1980-12-22T11:30:00")), 0.0);
      assertEquals(stockPortalOperations.getTotalValue("edu",
              LocalDateTime.parse("1980-12-22T11:30:00")), 0.0);
    }
  }

  /**
   * Test if a valid name but existing portfolio name but the portfolio entered is empty.
   */
  @Test
  public void testGetCostBasisLegalExistingAndEmptyPF() {
    stockPortalOperations.createPortfolio("Edu");
    assertEquals(stockPortalOperations.getCostBasis("Edu",
            LocalDateTime.parse("2017-12-12T11:30:00")), 0.0);
    assertEquals(stockPortalOperations.getTotalValue("Edu",
            LocalDateTime.parse("2017-12-12T11:30:00")), 0.0);
  }

  /**
   * Test if a valid name but and portfolio is valid and added in past and now asked cost basis for
   * today.
   */
  @Test
  public void testGetCostBasisNonEmptyPFDatePastOrderWayPast() {
    stockPortalOperations.createPortfolio("Edu");
    stockPortalOperations.buyShare("edu", "GOOG", 12121,
            LocalDateTime.parse("2017-10-10T11:30:00"));
    assertEquals(stockPortalOperations.getCostBasis("Edu",
            LocalDateTime.parse("2017-12-12T11:30:00")), 12121.0);
    assertEquals(stockPortalOperations.getTotalValue("Edu",
            LocalDateTime.parse("2017-12-12T11:30:00")), 12966.952580711495);
  }

  /**
   * Test if a valid name but and portfolio is valid and added in now and now asked cost basis for
   * same day.
   */
  @Test
  public void testGetCostBasisNonEmptyPFDateAndGetDateSame() {
    stockPortalOperations.createPortfolio("Edu");
    stockPortalOperations.buyShare("edu", "GOOG", 12121,
            LocalDateTime.parse("2017-12-13T11:30:00"));
    assertEquals(stockPortalOperations.getCostBasis("Edu",
            LocalDateTime.parse("2017-12-13T11:30:00")), 12121.0);
    assertEquals(stockPortalOperations.getTotalValue("Edu",
            LocalDateTime.parse("2017-12-13T11:30:00")), 12121.0);
  }


  /**
   * The portfolio is called even before its created will thrown an exception.
   */
  @Test(expected = NoSuchElementException.class)
  public void testGetPortfolioNotCreated() {
    stockPortalOperations.getPortFolio("Random Not Created");
  }


  /**
   * The portfolio is called its created and empty, portfolio name is not case sensitive will lead
   * to empty print.
   */
  @Test(expected = NoSuchElementException.class)
  public void testGetPortfolioCreatedEmpty() {
    stockPortalOperations.getPortFolio("Random yes Created");
    assertEquals(stockPortalOperations.getPortFolio("Random Yes Created"), "");
  }


  /**
   * Test if a valid name, portfolio is valid and added in now and now asked to get portfolio of
   * same day.
   */
  @Test
  public void testGetCostBasisNonEmptyAndValid() {
    stockPortalOperations.createPortfolio("Edu");
    stockPortalOperations.buyShare("edu", "GOOG", 12121,
            LocalDateTime.parse("2017-12-13T11:30:00"));
    assertEquals(stockPortalOperations.getPortFolio("edu").toString(),
            "Portfolio Name : Edu\n" +
                    " 1. Ticker = GOOG, CostBasis = $12121.0, Date Purchased = 2017-12-13T11:30," +
                    " Number of Shares = 11.647975706556732 ");
    assertEquals(stockPortalOperations.getTotalValue("edu",
            LocalDateTime.parse("2017-12-13T11:30:00")), 12121.0);
    assertEquals(stockPortalOperations.getCostBasis("edu",
            LocalDateTime.parse("2017-12-13T11:30:00")), 12121.0);

  }


  /**
   * Test if a all valid cost basis and total value called multiple times no change in  values until
   * share is added.
   */
  @Test
  public void testGetAllMultipleCalls() {
    stockPortalOperations.createPortfolio("Edu");
    stockPortalOperations.buyShare("edu", "GOOG", 12121,
            LocalDateTime.parse("2017-12-13T11:30:00"));
    assertEquals(stockPortalOperations.getPortFolio("edu").toString(),
            "Portfolio Name " +
                    ": Edu[ticker=GOOG,costBasis=12121.0, datePurchased=2017-12-13T11:30," +
                    " noOfShares=11.647975706556732]");
    assertEquals(stockPortalOperations.getTotalValue("edu",
            LocalDateTime.parse("2017-12-13T11:30:00")), 12121.0);
    assertEquals(stockPortalOperations.getCostBasis("edu",
            LocalDateTime.parse("2017-12-13T11:30:00")), 12121.0);
    assertEquals(stockPortalOperations.getTotalValue("edu",
            LocalDateTime.parse("2017-12-13T11:30:00")), 12121.0);
    assertEquals(stockPortalOperations.getCostBasis("edu",
            LocalDateTime.parse("2017-12-13T11:30:00")), 12121.0);
    assertEquals(stockPortalOperations.getTotalValue("edu",
            LocalDateTime.parse("2017-12-13T11:30:00")), 12121.0);
    assertEquals(stockPortalOperations.getCostBasis("edu",
            LocalDateTime.parse("2017-12-13T11:30:00")), 12121.0);
    assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[Portfolio Name :" +
            " Edu[ticker=GOOG,costBasis=12121.0, datePurchased=2017-12-13T11:30, " +
            "noOfShares=11.647975706556732]]");
  }

  /**
   * Tests that non alpha numeric values not allowed to as a portfolio name.
   */
  @Test
  public void testNonAplhaNumericPortfolioName() {
    String portfolioNames = "!@#$%^&*()-][}{\\/";
    for (int i = 0; i < portfolioNames.length(); i++) {
      try {
        stockPortalOperations.createPortfolio(String.valueOf(portfolioNames.charAt(i)));
      } catch (IllegalArgumentException e) {
        assertEquals(e.getMessage(), "Portfolio name can only be alphanumeric.");
        assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[]");
      }
    }
  }

  /**
   * Tests buy share at 09:00:01 and at 15:59:59 then at 16:00:00 should be failed.
   */
  @Test
  public void basicEdgeCaseBuyingShare() {
    stockPortalOperations.createPortfolio("Retirement Portfolio");
    stockPortalOperations.createPortfolio("Educational Portfolio");
    stockPortalOperations.setDataRetrievalSource(StockDataRetrievalEnum.FILE);
    stockPortalOperations.buyShare("Educational Portfolio", "AAPL",
            3400, LocalDateTime.parse("2018-11-01T09:00:01"));
    assertEquals(stockPortalOperations.getCostBasis("Educational Portfolio",
            LocalDateTime.now()), 3400.0);
    assertEquals(stockPortalOperations.getTotalValue("Educational Portfolio",
            LocalDateTime.parse("2018-11-09T09:00:01")), 3128.4222842228423);
    assertEquals(stockPortalOperations.getCostBasis("Retirement Portfolio",
            LocalDateTime.now()), 0.0);
    assertEquals(stockPortalOperations.getTotalValue("Retirement Portfolio",
            LocalDateTime.now()), 0.0);
    stockPortalOperations.buyShare("Retirement Portfolio", "GOOG",
            5600, LocalDateTime.parse("2018-11-01T11:30:00"));
    assertEquals(stockPortalOperations.getCostBasis("Educational Portfolio",
            LocalDateTime.now()), 3400.0);
    assertEquals(stockPortalOperations.getTotalValue("Educational Portfolio",
            LocalDateTime.parse("2018-11-09T09:00:01")), 3128.4222842228423);
    assertEquals(stockPortalOperations.getCostBasis("Retirement Portfolio",
            LocalDateTime.now()), 5600.0);
    assertEquals(stockPortalOperations.getTotalValue("Retirement Portfolio",
            LocalDateTime.parse("2018-11-09T09:00:01")), 5579.85046728972);
    stockPortalOperations.buyShare("Retirement Portfolio", "GOOG",
            5600, LocalDateTime.parse("2018-11-01T15:59:59"));
    assertEquals(stockPortalOperations.getCostBasis("Educational Portfolio",
            LocalDateTime.parse("2018-11-09T09:00:01")), 3400.0);
    assertEquals(stockPortalOperations.getTotalValue("Educational Portfolio",
            LocalDateTime.parse("2018-11-09T09:00:01")), 3128.4222842228423);
    assertEquals(stockPortalOperations.getCostBasis("Retirement Portfolio",
            LocalDateTime.now()), 11200.0);
    assertEquals(stockPortalOperations.getTotalValue("Retirement Portfolio",
            LocalDateTime.parse("2018-11-09T09:00:01")), 11159.70093457944);
    assertEquals(stockPortalOperations.getPortFolio("Retirement Portfolio").toString(),
            "Portfolio Name : Retirement Portfolio\n" +
                    " 1. Ticker =GOOG, CostBasis =5600.0, Date Purchased =2018-11-01T11:30, " +
                    "Number of Shares =5.233644859813084 \n" +
                    " 2. Ticker =GOOG, CostBasis =5600.0, Date Purchased =2018-11-01T15:59:59," +
                    " Number of Shares =5.233644859813084 \n");
    assertEquals(stockPortalOperations.getPortFolio(
            "Educational Portfolio").toString(),
            "Portfolio Name : Educational Portfolio\n" +
                    " 1. Ticker =AAPL, CostBasis =3400.0, Date Purchased =2018-11-01T09:00:01," +
                    " Number of Shares =15.300153001530015 \n");
    assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[Portfolio Name :" +
            " Retirement Portfolio\n" +
            " 1. Ticker =GOOG, CostBasis =5600.0, Date Purchased =2018-11-01T11:30, Number of" +
            " Shares =5.233644859813084 \n" +
            " 2. Ticker =GOOG, CostBasis =5600.0, Date Purchased =2018-11-01T15:59:59, Number of " +
            "Shares =5.233644859813084 \n" +
            ", Portfolio Name : Educational Portfolio\n" +
            " 1. Ticker =AAPL, CostBasis =3400.0, Date Purchased =2018-11-01T09:00:01, Number of" +
            " Shares =15.300153001530015 \n" +
            "]");
    try {
      stockPortalOperations.buyShare("Retirement Portfolio", "GOOG",
              5600, LocalDateTime.parse("2018-11-01T16:00:00"));
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Stock market closed.");
    }
  }

  /**
   * Tests buyShare on holidays should schedule it to be bought on the next working day but cost
   * basis and totalValue on the holiday will no change till the next working day.
   */
  @Test
  public void testBuyOnHolidayOrSaturdayOrSunday() {
    stockPortalOperations.createPortfolio("Retirement Portfolio");
    stockPortalOperations.setDataRetrievalSource(StockDataRetrievalEnum.FILE);
    stockPortalOperations.buyShare("Retirement Portfolio", "AAPL",
            3400, LocalDateTime.parse("2018-10-20T11:30:00"));
    assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[Portfolio Name :" +
            " Retirement Portfolio\n" +
            " 1. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-22T11:30, Number" +
            " of Shares = 15.409018808067074 \n" +
            "]");
    stockPortalOperations.buyShare("Retirement Portfolio", "AAPL",
            3400, LocalDateTime.parse("2018-10-21T12:30:00"));
    assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[Portfolio Name : " +
            "Retirement Portfolio\n" +
            " 1. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-22T11:30, Number of" +
            " Shares = 15.409018808067074 \n" +
            " 2. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-22T12:30, Number of "
            + "Shares = 15.409018808067074 \n" +
            "]");
    stockPortalOperations.buyShare("Retirement Portfolio", "AAPL",
            3400, LocalDateTime.parse("2018-01-15T11:30:00"));
    assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[Portfolio Name : " +
            "Retirement Portfolio\n" +
            " 1. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-22T11:30, Number" +
            " of Shares = 15.409018808067074 \n" +
            " 2. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-22T12:30, Number " +
            "of Shares = 15.409018808067074 \n" +
            " 3. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-01-16T11:30, Number " +
            "of Shares = 19.297349452295816 \n" +
            "]");
    stockPortalOperations.buyShare("Retirement Portfolio", "GOOG",
            5600, LocalDateTime.parse("2017-12-25T11:30:00"));
    assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[Portfolio Name :" +
            " Retirement Portfolio\n" +
            " 1. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-22T11:30, Number " +
            "of Shares = 15.409018808067074 \n" +
            " 2. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-22T12:30, Number " +
            "of Shares = 15.409018808067074 \n" +
            " 3. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-01-16T11:30, Number " +
            "of Shares = 19.297349452295816 \n" +
            " 4. Ticker = GOOG, CostBasis = $5600.0, Date Purchased = 2017-12-26T11:30, Number " +
            "of Shares = 5.299316766659727 \n" +
            "]");
    stockPortalOperations.buyShare("Retirement Portfolio", "GOOG",
            5600, LocalDateTime.parse("2018-11-01T11:30:00"));
    assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[Portfolio Name :" +
            " Retirement Portfolio\n" +
            " 1. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-22T11:30, Number" +
            " of Shares = 15.409018808067074 \n" +
            " 2. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-22T12:30, Number of" +
            " Shares = 15.409018808067074 \n" +
            " 3. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-01-16T11:30, Number " +
            "of Shares = 19.297349452295816 \n" +
            " 4. Ticker = GOOG, CostBasis = $5600.0, Date Purchased = 2017-12-26T11:30, Number" +
            " of Shares = 5.299316766659727 \n" +
            " 5. Ticker = GOOG, CostBasis = $5600.0, Date Purchased = 2018-11-01T11:30, Number" +
            " of Shares = 5.233644859813084 \n" +
            "]");
    stockPortalOperations.buyShare("Retirement Portfolio", "GOOG",
            5600, LocalDateTime.parse("2017-10-13T11:30:00"));
    assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[Portfolio Name : " +
            "Retirement Portfolio\n" +
            " 1. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-22T11:30, Number " +
            "of Shares = 15.409018808067074 \n" +
            " 2. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-22T12:30, Number" +
            " of Shares = 15.409018808067074 \n" +
            " 3. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-01-16T11:30, Number " +
            "of Shares = 19.297349452295816 \n" +
            " 4. Ticker = GOOG, CostBasis = $5600.0, Date Purchased = 2017-12-26T11:30, Number" +
            " of Shares = 5.299316766659727 \n" +
            " 5. Ticker = GOOG, CostBasis = $5600.0, Date Purchased = 2018-11-01T11:30, Number " +
            "of Shares = 5.233644859813084 \n" +
            " 6. Ticker = GOOG, CostBasis = $5600.0, Date Purchased = 2017-10-13T11:30, Number " +
            "of Shares = 5.658394632608521 \n" +
            "]");
  }

  /**
   * Tests costBasis and getTotalValue holidays returns valid values.
   */
  @Test
  public void testCanShowCostBasisTotalValueHolidayOrSaturdayOrSunday() {
    stockPortalOperations.createPortfolio("Retirement Portfolio");
    stockPortalOperations.setDataRetrievalSource(StockDataRetrievalEnum.FILE);
    stockPortalOperations.buyShare("Retirement Portfolio", "AAPL",
            3400, LocalDateTime.parse("2018-10-19T11:30:00"));
    assertEquals(stockPortalOperations.getTotalValue("Retirement Portfolio",
            LocalDateTime.parse("2018-10-20T11:30:00")), 3400.0);
    assertEquals(stockPortalOperations.getTotalValue("Retirement Portfolio",
            LocalDateTime.parse("2018-10-21T11:30:00")), 3400.0);
    assertEquals(stockPortalOperations.getTotalValue("Retirement Portfolio",
            LocalDateTime.parse("2018-01-15T11:30:00")), 0.0);
    assertEquals(stockPortalOperations.getTotalValue("Retirement Portfolio",
            LocalDateTime.parse("2018-11-01T11:30:00")), 3445.114221877707);
    assertEquals(stockPortalOperations.getAllPortfolios().toString(), "[Portfolio Name :" +
            " Retirement Portfolio\n" +
            " 1. Ticker = AAPL, CostBasis = $3400.0, Date Purchased = 2018-10-19T11:30, Number " +
            "of Shares = 15.503169030139984 \n" +
            "]");
    assertEquals(stockPortalOperations.getCostBasis("Retirement Portfolio",
            LocalDateTime.parse("2018-10-20T11:30:00")), 3400.0);
    assertEquals(stockPortalOperations.getCostBasis("Retirement Portfolio",
            LocalDateTime.parse("2018-10-21T11:30:00")), 3400.0);
    assertEquals(stockPortalOperations.getCostBasis("Retirement Portfolio",
            LocalDateTime.parse("2018-01-15T11:30:00")), 0.0);
    assertEquals(stockPortalOperations.getCostBasis("Retirement Portfolio",
            LocalDateTime.parse("2018-11-01T11:30:00")), 3400.0);
  }

}