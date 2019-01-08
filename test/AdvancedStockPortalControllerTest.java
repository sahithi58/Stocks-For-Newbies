import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import controller.AdvancedStockPortalController;
import controller.IStockPortalController;
import model.AdvancedStockPortal;
import model.AdvancedStockPortalOperations;
import view.AdvancedStockPortalView;
import view.IAdvancedView;

import static junit.framework.TestCase.assertEquals;


/**
 * This method will test the operation performed on the advanced stock portal using the controller.
 * This is the integrating test cases for whole application.
 * <p></p>
 * In this method the following methods are called for respective user input: API or FILE chose then
 * only the operations can be performed. 0 - To get the menu of stock portal operations. 1 - To
 * create the portfolio, a user can create multiple portfolios. 2 - The buy shares for a particular
 * portfolio which will open 1. buy share with commission, 2. Fixed Investment 3. Dollar cost
 * average. 3 - To  get the total cost basis till certain date. 4 - To get the total value of shares
 * on certain date. 5 - To view a specific portfolio and holding under it. 6- To get all the
 * portfolios. quit- To quit the operations.
 */
public class AdvancedStockPortalControllerTest {

  private AdvancedStockPortalOperations model;
  private IAdvancedView view;
  private StringBuffer out;
  private Readable in;

  /**
   * Method to set up and initialize readable and appendable.
   */
  @Before
  public void setUp() {
    out = new StringBuffer();
    Readable in = new StringReader("");
    model = new AdvancedStockPortal();
  }

  /**
   * Test user can buy share successfully by giving commission value too.
   */
  @Test
  public void testBuyShareWithCommission() {
    in = new StringReader("API\n1\nPF1\n2\n1\nPF1\ngoog\n2000\n2018-10-10\n10:10:10\n10");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "API chosen\nPortfolio: PF1 created successfully\n" + "\n"
            + "Bought share successfully\n\n");
  }

  /**
   * Tests that the cost basis is as excepted once the user buys share with commission.
   */
  @Test
  public void testCostBasisWithCommission() {
    in = new StringReader("API\n1\nPF1\n2\n1\nPF1\ngoog\n2000\n2018-10-10\n10:10:10\n10\nquit" +
            "\n3\nPF1\n2018-10-11\n10:10:10\nquit\nquit");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "API chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Bought share successfully\n" + "\n" + "The cost basis is (in $): 2010.0\n\n");
  }

  /**
   * Tests that the user can do fixed investment by giving percentages and has the right cost basis
   * values if data retrieval method is API.
   */
  @Test
  public void testFixedInvestment() {
    in = new StringReader("API\n1\nPF1\n2\n1\nPF1\ngoog\n2000\n2018-10-10\n10:10:10\n10\nquit" +
            "\n3\nPF1\n2018-10-11\n10:10:10\n2\n1\nPF1\nfb\n1000\n2018-10-10\n10:10:10\n10\n" +
            "quit\n3\nPF1\n2018-10-11\n10:10:10\n2\n2\nPF1\n2000\n2018-10-10\n20\ny");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "API chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Bought share successfully\n" + "\n" + "The cost basis is (in $): 2010.0\n"
            + "\n" + "Bought share successfully\n" + "\n" + "The cost basis is (in $): 3020.0\n"
            + "\n" + "Fixed Investment successful\n\n");
  }

  /**
   * Tests that the user can do fixed investment by giving percentages and has the right cost basis
   * values if data retrieval method is API.
   */
  @Test
  public void testFixedInvestmentFILE() {
    in = new StringReader("FILE\n1\nPF1\n2\n1\nPF1\ngoog\n2000\n2018-10-10\n10:10:10\n10\nquit" +
            "\n3\nPF1\n2018-10-11\n10:10:10\n2\n1\nPF1\nfb\n1000\n2018-10-10\n10:10:10\n10\n" +
            "quit\n3\nPF1\n2018-10-11\n10:10:10\n2\n2\nPF1\n2000\n2018-10-10\n20\ny\n");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Bought share successfully\n" + "\n" + "The cost basis is (in $): 2010.0\n"
            + "\n" + "Bought share successfully\n" + "\n" + "The cost basis is (in $): 3020.0\n"
            + "\n" + "Fixed Investment successful" + "\n\n");
  }

  /**
   * Tests that the user can do one time investment by giving percentages and has the right cost
   * basis values if data retrieval method is File.
   */
  @Test
  public void testFixedInvestmentOneInvFILE() {
    in = new StringReader("FILE\n1\nPF1\n2\n1\nPF1\ngoog\n2000\n2018-10-10\n10:10:10\n10\n2" +
            "\nPF1\n2000\n2018-10-10\n20\ny\n");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Bought share successfully\n" + "\n" + "Fixed Investment successful\n" + "\n");
  }


  /**
   * Test to check if get portfolio and total value are working as expected and as old
   * functionality.
   */
  @Test
  public void testTotalValueAfterFixedInv() {
    in = new StringReader("FILE\n" + "1\nPF1\n 2\n1\nPF1\ngoog\n2000\n2018-10-10\n10:10:10\n10"
            + "\n2" + "\nPF1\n2000\n2018-10-10\n20\ny\nquit\n5\nPF1\n4\nPF1\n2018-10-12\n10:10:10");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Bought share successfully\n" + "\n" + "Fixed Investment successful\n" + "\n"
            + "Portfolio Name : PF1\n" + " 1. Ticker = goog, CostBasis = $2000.0, Date Purchased" +
            " = 2018-10-10T10:10:10," + " Number of Shares = 1.8497623055437376, Commission = " +
            "$10.0 \n" + " 2. Ticker = goog, CostBasis = $2000.0, Date Purchased = " +
            "2018-10-10T10:00," + " Number of Shares = 1.8497623055437376, Commission = " +
            "$20.0 \n" + "\n" + "\n" + "The total value is (in $): 4106.768280275984\n\n");
  }

  /**
   * Test to check on empty existing portfolio user doesn't give an end date and asks to buy shares
   * equally for all stocks.
   */
  @Test
  public void testDollarCostNoEndDateEqualShare() {
    in = new StringReader("API\n" + "1\nPF1\n2\n3\nPF1\n2000\n2018-11-25\nn\n1\n10\ny\n1\nGOOG"
            + "\nquit\n5\nPF1\n4\nPF1\n2018-11-26\n10:10:10\n4\nPF1\n2018-11-27\n10:10:10");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "API chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Portfolio Name : PF1\n" + " 1. Ticker = GOOG, CostBasis = $2000.0, Date" +
            " Purchased = 2018-11-26T10:00, Number of Shares = 1.9072686006370279, " +
            "Commission = $10.0 \n" + " 2. Ticker = GOOG, CostBasis = $2000.0, Date Purchased" +
            " = 2018-11-26T10:00, Number of Shares = 1.9072686006370279, Commission = $10.0 \n"
            + " 3. Ticker = GOOG, CostBasis = $2000.0, Date Purchased = 2018-11-27T10:00, Number" +
            " of Shares = 1.9149567698509204, Commission = $10.0 \n" + " 4. Ticker = GOOG, " +
            "CostBasis = $2000.0, Date Purchased = 2018-11-28T10:00, Number of Shares = " +
            "1.8412306785855665, Commission = $10.0 \n" + " 5. Ticker = GOOG, CostBasis = $2000.0,"
            + " Date Purchased = 2018-11-29T10:00, Number of Shares = 1.8525465568106558, " +
            "Commission = $10.0 \n" + "\n" + "\n" + "The total value is (in $): 4000.0\n"
            + "\n" + "The total value is (in $): 5983.940798382637\n\n");
  }


  /**
   * Test to check if dollar cost average is taking in the end date and equal share on stocks and
   * particularly on sunday and monday, checking if shares for sunday are bought on monday. And
   * checking total value is working as expected.
   */
  @Test
  public void testDollarCostYesEndDateEqualShareTotalValue() {
    in = new StringReader("API\n" + "1\nPF1\n2\n3\nPF1\n2000\n2018-11-25\ny\n2018-11-26\n1\n"
            + "10\ny\n1\nGOOG" + "\nquit\n5\nPF1\n4\nPF1\n2018-11-26\n10:10:10\n4\nPF1\n" +
            "2018-11-27\n10:10:10");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "API chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Portfolio Name : PF1\n" + " 1. Ticker = GOOG, CostBasis = $2000.0," +
            " Date Purchased = 2018-11-26T10:00, Number of Shares = 1.9072686006370279, " +
            "Commission = $10.0 \n" + " 2. Ticker = GOOG, CostBasis = $2000.0, Date Purchased" +
            " = 2018-11-26T10:00, Number of Shares = 1.9072686006370279, Commission = $10.0 \n"
            + "\n" + "\n" + "The total value is (in $): 4000.0\n" + "\n" + "The total value is " +
            "(in $): 3983.940798382637\n\n");
  }

  /**
   * Test to check if dollar cost average is taking in the end date and equal share on two stocks
   * and particularly on sunday and monday, checking if shares for sunday are bought on monday. And
   * checking total value is working as expected.
   */
  @Test
  public void testDollarCostYesEndDateEqualShareTwoStocksTotalValue() {
    in = new StringReader("FILE\n" + "1\nPF1\n2\n3\nPF1\n2000\n2017-10-14\ny\n2017-10-16\n1\n"
            + "10\ny\n2\nGOOG\nFB" + "\nquit\n5\nPF1\n4\nPF1\n2018-11-26\n10:10:10\n4\nPF1" +
            "\n2017-10-13\n10:10:10");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Portfolio Name : PF1\n" + " 1. Ticker = GOOG, CostBasis = $1000.0, D" +
            "ate Purchased = 2017-10-16T10:00, Number of Shares = 1.0080645161290323, " +
            "Commission = $10.0 \n" + " 2. Ticker = FB, CostBasis = $1000.0, Date Purchased =" +
            " 2017-10-16T10:00, Number of Shares = 5.730002292000917, Commission = $10.0 \n"
            + " 3. Ticker = GOOG, CostBasis = $1000.0, Date Purchased = 2017-10-16T10:00," +
            " Number of Shares = 1.0080645161290323, Commission = $10.0 \n" + " 4. Ticker = FB, " +
            "CostBasis = $1000.0, Date Purchased = 2017-10-16T10:00, Number of Shares = " +
            "5.730002292000917, Commission = $10.0 \n" + " 5. Ticker = GOOG, CostBasis = " +
            "$1000.0, Date Purchased = 2017-10-16T10:00, Number of Shares = 1.0080645161290323," +
            " Commission = $10.0 \n" + " 6. Ticker = FB, CostBasis = $1000.0, Date Purchased = " +
            "2017-10-16T10:00, Number of Shares = 5.730002292000917, Commission = $10.0 \n" + "\n"
            + "\n" + "The total value is (in $): 5515.602976458932\n" + "\n" + "The total value " +
            "is (in $): 0.0\n\n");
  }


  /**
   * Test to check if dollar cost average is taking in the end date and not equal share on two stock
   * and particularly on sunday and monday, checking if shares for sunday are bought on monday. And
   * checking total value is working as expected.
   */
  @Test
  public void testDollarCostYesEndDateNotEqualShareTwoStocksTotalValue() {
    in = new StringReader("FILE\n" + "1\nPF1\n2\n3\nPF1\n2000\n2017-10-14\ny\n2017-10-16\n1\n"
            + "10\nn\n2\nGOOG\n60\nFB\n40" + "\nquit\n5\nPF1\n4\nPF1\n2018-11-26\n10:10:10\n4\n" +
            "PF1\n2017-10-13\n10:10:10");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Portfolio Name : PF1\n" + " 1. Ticker = GOOG, CostBasis = $1200.0, Date" +
            " Purchased = 2017-10-16T10:00, Number of Shares = 1.2096774193548387, Commission" +
            " = $10.0 \n" + " 2. Ticker = FB, CostBasis = $800.0, Date Purchased = 2017-10-16" +
            "T10:00, Number of Shares = 4.584001833600733, Commission = $10.0 \n"
            + " 3. Ticker = GOOG, CostBasis = $1200.0, Date Purchased = 2017-10-16T10:00," +
            " Number of Shares = 1.2096774193548387, Commission = $10.0 \n" + " 4. Ticker = FB," +
            " CostBasis = $800.0, Date Purchased = 2017-10-16T10:00, Number of Shares = " +
            "4.584001833600733, Commission = $10.0 \n" + " 5. Ticker = GOOG, CostBasis = $1200.0, "
            + "Date Purchased = 2017-10-16T10:00, Number of Shares = 1.2096774193548387," +
            " Commission = $10.0 \n" + " 6. Ticker = FB, CostBasis = $800.0, Date Purchased " +
            "= 2017-10-16T10:00, Number of Shares = 4.584001833600733, Commission = $10.0 \n"
            + "\n" + "\n" + "The total value is (in $): 5680.974316651017\n" + "\n" + "The total " +
            "value is (in $): 0.0\n\n");
  }

  /**
   * Test to check if dollar cost average is not taking in the end date and  not equal share on two
   * stocks and particularly on sunday and monday, checking if shares for sunday are bought on
   * monday. And checking total value is working as expected.
   */
  @Test
  public void testDollarCostNoEndDateNotEqualShareTwoStocksTotalValue() {
    in = new StringReader("FILE\n" + "1\nPF1\n2\n3\nPF1\n2000\n2018-11-27\nn\n1\n"
            + "10\nn\n2\nGOOG\n60\nFB\n40" + "\nquit\n5\nPF1\n4\nPF1\n2018-11-28\n10:10:" +
            "10\n4\nPF1\n2017-10-13\n10:10:10");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Portfolio Name : PF1\n" + " 1. Ticker = GOOG, CostBasis = $1200.0, " +
            "Date Purchased = 2018-11-27T10:00, Number of Shares = 1.1489740619105522, " +
            "Commission = $10.0 \n" + " 2. Ticker = FB, CostBasis = $800.0, Date Purchased" +
            " = 2018-11-27T10:00, Number of Shares = 5.925925925925926, Commission = $10.0 \n"
            + " 3. Ticker = GOOG, CostBasis = $1200.0, Date Purchased = 2018-11-28T10:00, Number " +
            "of Shares = 1.10473840715134, Commission = $10.0 \n" + " 4. Ticker = FB, " +
            "CostBasis = $800.0, Date Purchased = 2018-11-28T10:00, Number of Shares = " +
            "5.849663644340451, Commission = $10.0 \n" + " 5. Ticker = GOOG," +
            " CostBasis = $1200.0, " +
            "Date Purchased = 2018-11-29T10:00, Number of Shares = 1.1115279340863935, " +
            "Commission = $10.0 \n" + " 6. Ticker = FB, CostBasis = $800.0, Date Purchased " +
            "= 2018-11-29T10:00, Number of Shares = 5.864672677956162, Commission = $10.0 \n" +
            "\n" + "\n" + "The total value is (in $): 4058.479724898729\n" + "\n" + "The total " +
            "value is (in $): 0.0\n\n");
  }


  /**
   * Test to check if dollar cost average is not taking in the end date and equal share on two
   * stocks and particularly on sunday and monday, checking if shares for sunday are bought on
   * monday. And checking total value is working as expected.
   */
  @Test
  public void testDollarCostNoEndDateEqualShareTwoStocksTotalValue() {
    in = new StringReader("FILE\n" + "1\nPF1\n2\n3\nPF1\n2000\n2018-11-27\nn\n1\n"
            + "10\ny\n2\nGOOG\nFB" + "\nquit\n5\nPF1\n4\nPF1\n2018-11-28\n10:10:10\n4\n" +
            "PF1\n2017-10-13\n10:10:10\n4\nPF1\n2018-11-29\n10:10:10");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Portfolio Name : PF1\n" + " 1. Ticker = GOOG, CostBasis = $1000.0, Date " +
            "Purchased = 2018-11-27T10:00, Number of Shares = 0.9574783849254602, " +
            "Commission = $10.0 \n" + " 2. Ticker = FB, CostBasis = $1000.0, Date Purchased" +
            " = 2018-11-27T10:00, Number of Shares = 7.407407407407407, Commission = $10.0 \n"
            + " 3. Ticker = GOOG, CostBasis = $1000.0, Date Purchased = 2018-11-28T10:00, Number " +
            "of Shares = 0.9206153392927833, Commission = $10.0 \n" + " 4. Ticker = FB," +
            " CostBasis = $1000.0, Date Purchased = 2018-11-28T10:00, Number of Shares " +
            "= 7.312079555425563, Commission = $10.0 \n" + " 5. Ticker = GOOG, CostBasis " +
            "= $1000.0, Date Purchased = 2018-11-29T10:00, Number of Shares = 0.9262732784053279," +
            " Commission = $10.0 \n" + " 6. Ticker = FB, CostBasis = $1000.0, Date " +
            "Purchased = 2018-11-29T10:00, Number of Shares = 7.330840847445202, " +
            "Commission = $10.0 \n" + "\n" + "\n" + "The total value is (in $): " +
            "4053.0787830946197\n" + "\n" + "The total value is (in $): 0.0\n\n" +
            "The total value is (in $): 6035.46581079744\n\n");
  }

  /**
   * Test to check if dollar cost average is taking in the end date and equal share on two stocks
   * and particularly on sunday and monday, checking if shares for sunday are bought on monday. And
   * checking total value is working as expected.
   */
  @Test
  public void testDollarCostNoEndDateEqualShareTwoStocksCostBasis() {
    in = new StringReader("FILE\n" + "1\nPF1\n2\n3\nPF1\n2000\n2018-11-27\nn\n1\n"
            + "10\ny\n2\nGOOG\nFB" + "\nquit\n5\nPF1\n3\nPF1\n2018-11-28\n10:10:10\n3\nPF1" +
            "\n2017-10-13\n10:10:10");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Portfolio Name : PF1\n" + " 1. Ticker = GOOG, CostBasis = $1000.0, " +
            "Date Purchased = 2018-11-27T10:00, Number of Shares = 0.9574783849254602, " +
            "Commission = $10.0 \n" + " 2. Ticker = FB, CostBasis = $1000.0, " +
            "Date Purchased = 2018-11-27T10:00, Number of Shares = 7.407407407407407," +
            " Commission = $10.0 \n" + " 3. Ticker = GOOG, CostBasis = $1000.0, Date Purchased = " +
            "2018-11-28T10:00, Number of Shares = 0.9206153392927833, Commission = $10.0 \n"
            + " 4. Ticker = FB, CostBasis = $1000.0, Date Purchased = 2018-11-28T10:00, " +
            "Number of Shares = 7.312079555425563, Commission = $10.0 \n" + " 5. Ticker = GOOG," +
            " CostBasis = $1000.0, Date Purchased = 2018-11-29T10:00, Number of Shares = " +
            "0.9262732784053279, Commission = $10.0 \n" + " 6. Ticker = FB, CostBasis = $1000.0," +
            " Date Purchased = 2018-11-29T10:00, Number of Shares = 7.330840847445202, " +
            "Commission = $10.0 \n" + "\n" + "\n" + "The cost basis is (in $): 4040.0\n" +
            "\n" + "The cost basis is (in $): 0.0\n\n");
  }

  /**
   * Test to check if dollar cost average is taking in the end date and not equal share on two stock
   * and particularly on sunday and monday, checking if shares for sunday are bought on monday. And
   * checking cost basis is working as expected summed with commission.
   */
  @Test
  public void testDollarCostNoEndDateNotEqualShareTwoStocksCostBasis() {
    in = new StringReader("FILE\n" + "1\nPF1\n2\n3\nPF1\n2000\n2018-11-27\nn\n1\n"
            + "10\nn\n2\nGOOG\n60\nFB\n40" + "\nquit\n5\nPF1\n3\nPF1\n2018-11-28\n10:10:" +
            "10\n3\nPF1\n2017-10-13\n10:10:10");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n" + "Portfolio: PF1 created successfully\n" +
            "\n" + "Portfolio Name : PF1\n" + " 1. Ticker = GOOG, CostBasis = $1200.0, Date" +
            " Purchased = 2018-11-27T10:00, Number of Shares = 1.1489740619105522, Commission " +
            "= $10.0 \n" + " 2. Ticker = FB, CostBasis = $800.0, Date Purchased = 2018-11-27T10:00,"
            + " Number of Shares = 5.925925925925926, Commission = $10.0 \n" + " 3. Ticker = GOOG,"
            + " CostBasis = $1200.0, Date Purchased = 2018-11-28T10:00, Number of Shares = " +
            "1.10473840715134, Commission = $10.0 \n" + " 4. Ticker = FB, CostBasis = $800.0, " +
            "Date Purchased = 2018-11-28T10:00, Number of Shares = 5.849663644340451, " +
            "Commission = $10.0 \n" + " 5. Ticker = GOOG, CostBasis = $1200.0, Date Purchased = " +
            "2018-11-29T10:00, Number of Shares = 1.1115279340863935, Commission = $10.0 \n"
            + " 6. Ticker = FB, CostBasis = $800.0, Date Purchased = 2018-11-29T10:00, Number " +
            "of Shares = 5.864672677956162, Commission = $10.0 \n" + "\n" + "\n"
            + "The cost basis is (in $): 4040.0\n" + "\n" + "The cost basis is (in $): 0.0\n\n");
  }


  /**
   * Test to check if dollar cost average is taking in the end date and not equal share on two stock
   * and particularly on sunday and monday, checking if shares for sunday are bought on monday. And
   * checking cost basis is working as expected.
   */
  @Test
  public void testDollarCostYesEndDateNotEqualShareTwoStocksCostBasis() {
    in = new StringReader("FILE\n" + "1\nPF1\n2\n3\nPF1\n2000\n2017-10-14\ny\n2017-10-17\n1\n"
            + "10\nn\n2\nGOOG\n60\nFB\n40" + "\nquit\n5\nPF1\n3\nPF1\n2018-11-26\n10:" +
            "10:10\n3\nPF1\n2017-10-13\n10:10:10");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Portfolio Name : PF1\n"
            + " 1. Ticker = GOOG, CostBasis = $1200.0," +
            " Date Purchased = 2017-10-16T10:00, Number of Shares = 1.2096774193548387," +
            " Commission = $10.0 \n" + " 2. Ticker = FB, CostBasis = $800.0," +
            " Date Purchased = 2017-10-16T10:00, Number of Shares = 4.584001833600733," +
            " Commission = $10.0 \n" + " 3. Ticker = GOOG, CostBasis = $1200.0," +
            " Date Purchased = 2017-10-16T10:00, Number of Shares = 1.2096774193548387," +
            " Commission = $10.0 \n" + " 4. Ticker = FB, CostBasis = $800.0," +
            " Date Purchased = 2017-10-16T10:00, Number of Shares = 4.584001833600733," +
            " Commission = $10.0 \n" + " 5. Ticker = GOOG, CostBasis = $1200.0," +
            " Date Purchased = 2017-10-16T10:00, Number of Shares = 1.2096774193548387," +
            " Commission = $10.0 \n" + " 6. Ticker = FB, CostBasis = $800.0, " +
            "Date Purchased = 2017-10-16T10:00, Number of Shares = 4.584001833600733," +
            " Commission = $10.0 \n" + " 7. Ticker = GOOG, CostBasis = $1200.0," +
            " Date Purchased = 2017-10-17T10:00, Number of Shares = 1.20945796125703," +
            " Commission = $10.0 \n" + " 8. Ticker = FB, CostBasis = $800.0," +
            " Date Purchased = 2017-10-17T10:00, Number of Shares = 4.542615410822781," +
            " Commission = $10.0 \n" + "\n" + "\n" + "The cost basis is (in $): 8080.0\n"
            + "\n" + "The cost basis is (in $): 0.0\n\n");
  }

  /**
   * Test to check if dollar cost average is taking in the end date and equal share on two stocks
   * and particularly on sunday and monday, checking if shares for sunday are bought on monday. And
   * checking cost basis is working as expected.
   */
  @Test
  public void testDollarCostYesEndDateEqualShareTwoStocksCostBasis() {
    in = new StringReader("FILE\n" + "1\nPF1\n2\n3\nPF1\n2000\n2017-10-14\ny\n2017-10-17\n1\n"
            + "10\ny\n2\nGOOG\nFB" + "\nquit\n5\nPF1\n3\nPF1\n2018-11-26\n10:10:10\n" +
            "3\nPF1\n2017-10-13\n10:10:10");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Portfolio Name : PF1\n"
            + " 1. Ticker = GOOG, CostBasis = $1000.0, Date Purchased = 2017-10-16T10:00," +
            " Number of Shares = 1.0080645161290323, Commission = $10.0 \n"
            + " 2. Ticker = FB, CostBasis = $1000.0, Date Purchased = 2017-10-16T10:00, " +
            "Number of Shares = 5.730002292000917, Commission = $10.0 \n"
            + " 3. Ticker = GOOG, CostBasis = $1000.0, Date Purchased = 2017-10-16T10:00," +
            " Number of Shares = 1.0080645161290323, Commission = $10.0 \n"
            + " 4. Ticker = FB, CostBasis = $1000.0, Date Purchased = 2017-10-16T10:00," +
            " Number of Shares = 5.730002292000917, Commission = $10.0 \n"
            + " 5. Ticker = GOOG, CostBasis = $1000.0, Date Purchased = 2017-10-16T10:00," +
            " Number of Shares = 1.0080645161290323, Commission = $10.0 \n"
            + " 6. Ticker = FB, CostBasis = $1000.0, Date Purchased = 2017-10-16T10:00, " +
            "Number of Shares = 5.730002292000917, Commission = $10.0 \n"
            + " 7. Ticker = GOOG, CostBasis = $1000.0, Date Purchased = 2017-10-17T10:00," +
            " Number of Shares = 1.0078816343808583, Commission = $10.0 \n"
            + " 8. Ticker = FB, CostBasis = $1000.0, Date Purchased = 2017-10-17T10:00, " +
            "Number of Shares = 5.678269263528476, Commission = $10.0 \n" + "\n" + "\n"
            + "The cost basis is (in $): 8080.0\n" + "\n" + "The cost basis is (in $): 0.0\n\n");
  }


  /**
   * Test when any input number will throw error when entered a negative value, in here tested for
   * commission, when entered negative asked for input again stating its an invalid input.
   */
  @Test
  public void testNegativeCommissionThrowsErrorDollarCost() {
    in = new StringReader("FILE\n" + "1\nPF1\n2\n3\nPF1\n2000\n2017-10-14\ny\n2017-10-16\n1\n"
            + "-10\ny\n2\nGOOG\nFB"
            + "\nquit\n5\nPF1\n3\nPF1\n2018-11-26\n10:10:10\n3\nPF1\n2017-10-13\n10:10:10");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Invalid value please enter again\n" + "Invalid value please enter again\n"
            + "Invalid input Enter again.\n" + "Invalid input Enter again.\n"
            + "Portfolio Name : PF1\n" + "\n" + "\n" + "The cost basis is (in $): 0.0\n"
            + "\n" + "The cost basis is (in $): 0.0\n" + "\n"
            + "Something went wrong while buying share\n"
            + " More Information: Readable failed\n\n");
  }

  /**
   * Test when any input number will throw error when entered a negative value, in here tested for
   * frequency.
   */
  @Test
  public void testNegativeFrequencyThrowsErrorDollarCost() {
    in = new StringReader("FILE\n" + "1\nPF1\n2\n3\nPF1\n2000\n2017-10-14\ny\n2017-10-17\n1\n"
            + "10\ny\n-2\nGOOG\nFB" + "\nquit\n5\nPF1\n3\nPF1\n2018-11-26\n10:10:10\n" +
            "3\nPF1\n2017-10-13\n10:10:10");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Invalid value please enter again\n" + "Invalid value please enter again\n"
            + "Invalid value please enter again\n" + "Portfolio Name : PF1\n" + "\n" + "\n"
            + "The cost basis is (in $): 0.0\n" + "\n" + "The cost basis is (in $): 0.0\n" + "\n"
            + "Something went wrong while buying share\n"
            + " More Information: For input string: \"quit\"\n\n");
  }

  /**
   * Test to check if dollar cost average and then fixed investment on a portfolio and applying and
   * checking if cost basis is calucalted with commission and particularly on sunday and monday,
   * checking if shares for sunday are bought on monday and checking cost basis is working as
   * expected.
   */
  @Test
  public void testThenDollarCostAndThenFixedInvCheckCostBasis() {
    in = new StringReader("FILE\n" + "1\nPF1\n2\n3\nPF1\n2000\n2017-10-14\ny\n2017-10-17\n1\n"
            + "10\ny\n2\nGOOG\nFB" + "\nquit\n5\nPF1\n3\nPF1\n2018-11-26\n10:10:10" +
            "\n3\nPF1\n2017-10-13\n10:10:10\n2\n2" + "\nPF1\n200\n2018-10-11\n10\ny\n");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Portfolio Name : PF1\n" + " 1. Ticker = GOOG, CostBasis = $1000.0, " +
            "Date Purchased = 2017-10-16T10:00, Number of Shares = 1.0080645161290323, " +
            "Commission = $10.0 \n" + " 2. Ticker = FB, CostBasis = $1000.0," +
            " Date Purchased = 2017-10-16T10:00, Number of Shares = 5.730002292000917," +
            " Commission = $10.0 \n" + " 3. Ticker = GOOG, CostBasis = $1000.0," +
            " Date Purchased = 2017-10-16T10:00, Number of Shares = 1.0080645161290323," +
            " Commission = $10.0 \n" + " 4. Ticker = FB, CostBasis = $1000.0, " +
            "Date Purchased = 2017-10-16T10:00, Number of Shares = 5.730002292000917," +
            " Commission = $10.0 \n" + " 5. Ticker = GOOG, CostBasis = $1000.0," +
            " Date Purchased = 2017-10-16T10:00, Number of Shares = 1.0080645161290323," +
            " Commission = $10.0 \n" + " 6. Ticker = FB, CostBasis = $1000.0," +
            " Date Purchased = 2017-10-16T10:00, Number of Shares = 5.730002292000917," +
            " Commission = $10.0 \n" + " 7. Ticker = GOOG, CostBasis = $1000.0," +
            " Date Purchased = 2017-10-17T10:00, Number of Shares = 1.0078816343808583," +
            " Commission = $10.0 \n" + " 8. Ticker = FB, CostBasis = $1000.0, " +
            "Date Purchased = 2017-10-17T10:00, Number of Shares = 5.678269263528476, " +
            "Commission = $10.0 \n" + "\n" + "\n" + "The cost basis is (in $): 8080.0\n"
            + "\n" + "The cost basis is (in $): 0.0\n" + "\n" + "Fixed Investment successful\n\n");
  }

  /**
   * Test to check cost basis is sum of total amount the shares are bought for and extra commission
   * that was cut for the transaction.
   */
  @Test
  public void testBuyShareWithCommissionViewContents() {
    in = new StringReader("FILE\n" + "1\nPF1\n2\n1\nPF1\nAAPL\n1000\n2018-10-10\n10:10:10" +
            "\n25\nquit\n5\nPF1\n3\nPF1\n2018-10-11\n10:10:10");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Bought share successfully\n" + "\n" + "Portfolio Name : PF1\n"
            + " 1. Ticker = AAPL, CostBasis = $1000.0, Date Purchased = 2018-10-10T10:10:10," +
            " Number of Shares = 4.621926418931411, Commission = $25.0 \n" + "\n" + "\n"
            + "The cost basis is (in $): 1025.0\n\n");
  }


  /**
   * Test to check cost basis is sum of total amount the shares and then a fixed investment for and
   * extra commission we sum it with cost basis on different dates we take cumulative.
   */
  @Test
  public void testFixedInvWithCommissionViewContents() {
    in = new StringReader("FILE\n" + "1\nPF1\n2\n1\nPF1\nAAPL\n1000\n2018-10-10\n10:10:10" +
            "\n25\n2\nPF1\n1000\n2018-10-12" +
            "\n25\ny\nquit\n5\nPF1\n3\nPF1\n2018-10-11\n10:10:10\n3\nPF1\n2018-10-12\n10:10:10");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Bought share successfully\n" + "\n" + "Fixed Investment successful\n" + "\n"
            + "Portfolio Name : PF1\n"
            + " 1. Ticker = AAPL, CostBasis = $1000.0, Date Purchased = 2018-10-10T10:10:10, " +
            "Number of Shares = 4.621926418931411, Commission = $25.0 \n"
            + " 2. Ticker = AAPL, CostBasis = $1000.0, Date Purchased = 2018-10-12T10:00," +
            " Number of Shares = 4.502273648192337, Commission = $25.0 \n" + "\n" + "\n"
            + "The cost basis is (in $): 1025.0\n" + "\n"
            + "The cost basis is (in $): 2050.0\n" + "\n");
  }

  /**
   * Test to check cost basis is sum of total amount the shares are bought for a strategy and extra
   * commission that was cut for the transaction.
   */
  @Test
  public void testCostBasisWithCommissionDollarCost() {
    in = new StringReader("FILE\n" + "1\nPF1\n2\n" + "3\nPF1\n2000\n2017-10-13\ny" +
            "\n2017-10-16\n1\n" + "10\ny\n2\nGOOG\nFB"
            + "\nquit\n5\nPF1\n3\nPF1\n2017-10-15\n10:10:10\n");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Portfolio Name : PF1\n" + " 1. Ticker = GOOG, CostBasis = $1000.0," +
            " Date Purchased = 2017-10-13T10:00, Number of Shares = 1.0104276129658072," +
            " Commission = $10.0 \n" + " 2. Ticker = FB, CostBasis = $1000.0, " +
            "Date Purchased = 2017-10-13T10:00, Number of Shares = 5.755726948313572," +
            " Commission = $10.0 \n" + " 3. Ticker = GOOG, CostBasis = $1000.0, " +
            "Date Purchased = 2017-10-16T10:00, Number of Shares = 1.0080645161290323, " +
            "Commission = $10.0 \n" + " 4. Ticker = FB, CostBasis = $1000.0, " +
            "Date Purchased = 2017-10-16T10:00, Number of Shares = 5.730002292000917," +
            " Commission = $10.0 \n" + " 5. Ticker = GOOG, CostBasis = $1000.0, " +
            "Date Purchased = 2017-10-16T10:00, Number of Shares = 1.0080645161290323, " +
            "Commission = $10.0 \n" + " 6. Ticker = FB, CostBasis = $1000.0," +
            " Date Purchased = 2017-10-16T10:00, Number of Shares = 5.730002292000917," +
            " Commission = $10.0 \n" + " 7. Ticker = GOOG, CostBasis = $1000.0, " +
            "Date Purchased = 2017-10-16T10:00, Number of Shares = 1.0080645161290323, " +
            "Commission = $10.0 \n" + " 8. Ticker = FB, CostBasis = $1000.0, " +
            "Date Purchased = 2017-10-16T10:00, Number of Shares = 5.730002292000917," +
            " Commission = $10.0 \n" + "\n" + "\n" + "The cost basis is (in $): 2020.0\n\n");
  }


  /**
   * Test to check cost basis after two dollar cost strategies are applied on the portfolio and
   * viewing if everythings correct.
   */
  @Test
  public void testCostBasisWithCommissionTwoTimesDollarCost() {
    in = new StringReader("FILE\n" + "1\nPF1\n2\n" + "3\nPF1\n2000\n2017-10-13\ny\n2017-10-16" +
            "\n1\n" + "10\ny\n2\nGOOG\nFB\nquit\n2\n3\nPF1\n2000\n2018-10-10\ny\n2018-10-12\n1" +
            "\n10\ny\n2\nGOOG\nFB\nquit\n" + "5\nPF1\n3\nPF1\n2017-10-15\n10:10:10\n3\nPF1" +
            "\n2018-10-13\n10:10:10");
    view = new AdvancedStockPortalView(in, out);
    IStockPortalController iPortfolioController = new AdvancedStockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Something went wrong while buying share\n"
            + " More Information: Portfolio does not exist.\n" + "\n"
            + "Unknown command FB\n" + "\n" + "Portfolio Name : PF1\n"
            + " 1. Ticker = GOOG, CostBasis = $1000.0, Date Purchased = 2017-10-13T10:00, " +
            "Number of Shares = 1.0104276129658072, Commission = $10.0 \n" + " 2. Ticker = FB," +
            " CostBasis = $1000.0, Date Purchased = 2017-10-13T10:00," +
            " Number of Shares = 5.755726948313572, Commission = $10.0 \n"
            + " 3. Ticker = GOOG, CostBasis = $1000.0, Date Purchased = 2017-10-16T10:00," +
            " Number of Shares = 1.0080645161290323, Commission = $10.0 \n" + " 4. Ticker = FB," +
            " CostBasis = $1000.0, Date Purchased = 2017-10-16T10:00," +
            " Number of Shares = 5.730002292000917, Commission = $10.0 \n"
            + " 5. Ticker = GOOG, CostBasis = $1000.0, Date Purchased = 2017-10-16T10:00," +
            " Number of Shares = 1.0080645161290323, Commission = $10.0 \n" + " 6. Ticker = FB," +
            " CostBasis = $1000.0, Date Purchased = 2017-10-16T10:00, " +
            "Number of Shares = 5.730002292000917, Commission = $10.0 \n"
            + " 7. Ticker = GOOG, CostBasis = $1000.0, Date Purchased = 2017-10-16T10:00," +
            " Number of Shares = 1.0080645161290323, Commission = $10.0 \n"
            + " 8. Ticker = FB, CostBasis = $1000.0, Date Purchased = 2017-10-16T10:00," +
            " Number of Shares = 5.730002292000917, Commission = $10.0 \n"
            + " 9. Ticker = GOOG, CostBasis = $1000.0, Date Purchased = 2018-10-10T10:00, " +
            "Number of Shares = 0.9248811527718688, Commission = $10.0 \n" + " 10. Ticker = FB, " +
            "CostBasis = $1000.0, Date Purchased = 2018-10-10T10:00, " +
            "Number of Shares = 6.605892456070816, Commission = $10.0 \n"
            + " 11. Ticker = GOOG, CostBasis = $1000.0, Date Purchased = 2018-10-11T10:00, " +
            "Number of Shares = 0.926509283623022, Commission = $10.0 \n" + " 12. Ticker = FB," +
            " CostBasis = $1000.0, Date Purchased = 2018-10-11T10:00," +
            " Number of Shares = 6.521030322791002, Commission = $10.0 \n"
            + " 13. Ticker = GOOG, CostBasis = $1000.0, Date Purchased = 2018-10-12T10:00," +
            " Number of Shares = 0.900835975785529, Commission = $10.0 \n" + " 14. Ticker = FB, " +
            "CostBasis = $1000.0, Date Purchased = 2018-10-12T10:00," +
            " Number of Shares = 6.504488096786782, Commission = $10.0 \n\n"
            + "\n" + "The cost basis is (in $): 2020.0\n" + "\n"
            + "The cost basis is (in $): 14140.0\n\n");
  }

}