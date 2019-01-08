import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import controller.IStockPortalController;
import controller.StockPortalController;
import model.StockPortal;
import model.StockPortalOperations;
import view.IStockPortalView;
import view.StockPortalView;

import static junit.framework.TestCase.assertEquals;

/**
 * This method will test the operation performed on the stock portal using the controller. This is
 * the integrating test cases for whole application.
 * <p></p>
 * In this method the following methods are called for respective user input: API or FILE chose then
 * only the operations can be performed. 0 - To get the menu of stock portal operations. 1 - To
 * create the portfolio, a user can create multiple portfolios. 2 - The buy shares for a particular
 * portfolio for which he needs to input the portfolio name for which you want to buy the shares,
 * the unique company ticker symbol, the money to invest and the date of investment. 3 - To  get the
 * total cost basis till certain date. 4 - To get the total value of shares on certain date. 5 - To
 * view a specific portfolio and holding under it. 6- To get all the portfolios. quit- To quit the
 * operations.
 */
public class StockPortalControllerTest {

  private StockPortalOperations model;
  private IStockPortalView view;
  private StringBuffer out;
  private Readable in;

  @Before
  public void setUp() {
    out = new StringBuffer();
    Readable in = new StringReader("");
    model = new StockPortal();
  }


  /**
   * This method tests until the controller is not called the view will output empty, proving that
   * no logic is added in the view.
   */
  @Test
  public void testControllerMethodNotCalled() {
    in = new StringReader("");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    assertEquals(out.toString(), "");
  }


  /**
   * In this test checks that when 1 is entered, the user asks name of the portfolio when given
   * valid input creates portfolio successfully, checks via getting all portfolios in next command.
   * In this test checks that when 1 is entered and no output is given for name, then an exception
   * is thrown.
   */
  @Test
  public void testInput1() {
    in = new StringReader("File\n1\nSahithi\n6");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: Sahithi created successfully\n\n" +
            "[Portfolio Name : Sahithi[]]\n\n");
  }


  /**
   * In this test checks that when 1 is entered and no output is given for name, then waits for user
   * to input portfolio name.
   */
  @Test
  public void testOnlyInput1() {
    in = new StringReader("File\n1");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nSomething went wrong while creating " +
            "the portfolio\n" + " More Information Readable failed\n");
  }

  @Test
  public void testOnlyInput1quit() {
    in = new StringReader("File\n1\nquit");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n");
  }

  /**
   * Testing when input is given as quit exiting the application before data retrieval.
   */
  @Test
  public void testOnlyInput1newquit() {
    in = new StringReader("file\n");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n");
  }

  /**
   * Testing when one invalid input then input is given as quit exiting the application before data
   * retrieval.
   */
  @Test
  public void testOnlyInputQuitAfterDataCall() {
    in = new StringReader("sals\nquit\n");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "Invalid input Enter again.\n");
  }

  /**
   * In this test checks that when 1 is entered and no output is given for name, then waits for user
   * to input portfolio name.
   */
  @Test
  public void testOnlyInput2AsksPortfolioName() {
    in = new StringReader("File\n1\nEducation_Portfolio");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: " +
            "Education_Portfolio created successfully\n\n");
  }


  /**
   * In this test checks that when 1 is entered and no output is given for name, then waits for user
   * to input portfolio name.
   */
  @Test
  public void testOnlyInput2AsksPortfolioNameNoSpace() {
    in = new StringReader("File\n1\nEducationPortfolio\n2\nquit");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio:" +
            " EducationPortfolio created successfully\n\n");

  }


  /**
   * In this test checks that when 1 is entered the name of portfolio is one of the readcommands but
   * will not create an issue and a portfolio is created successfully. to input portfolio name.
   */
  @Test
  public void testOnlyInput2AsksPortfolioNameNumberAsCmd() {
    in = new StringReader("File\n1\n2\n2\nquit");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: 2 created successfully\n\n");
  }

  /**
   * In this test checks that when 1 is entered and a valid input with numbers is added, portfolio
   * created successfully to input portfolio name.
   */
  @Test
  public void testOnlyInput2AsksPortfolioNameNumbers() {
    in = new StringReader("File\n1\n2232312assd\n2\nquit");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: 2232312" +
            "assd created successfully\n\n");
  }

  /**
   * In this test checks that when 1 is entered and invalid input is entered then an exception is
   * thrown and nothing will be added to the portfolios.
   */
  @Test
  public void testOnlyInput2AsksPortfolioSpecialCharsError() {
    in = new StringReader("File\n1\nWeat@1\nquit");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nInvalid Name Enter again\n");
  }

  /**
   * In this test checks that when 1 is entered and null input is entered then an exception is
   * thrown and nothing will be added to the portfolios.
   */
  @Test
  public void testNullForCreatePortfolio() {
    in = new StringReader("File\n1\n\n2\nquit");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: 2 created successfully\n\n" +
            "The operation has been discarded\n");
  }

  /**
   * In this test checks that when 1 is entered and null input is entered then an exception is
   * thrown and nothing will be added to the portfolios.
   */
  @Test
  public void testEmptyForCreatePortfolio() {
    in = new StringReader("File\n1\n \n2");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: 2 created successfully\n\n");
  }

  /**
   * In this test checks buy portfolio is successful where before giving an input the.
   */
  @Test
  public void testBuyShareEmptyPortfolio() {
    in = new StringReader("File\n1\nSahithi\n2\n\n \nSahithi\nGOOG\n102\nquit");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: Sahithi created successfully\n\n");
  }

  /**
   * Testing when input is sent as empty nothing happens to application.
   */
  @Test
  public void testEmptyInput() {
    in = new StringReader("");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "");
  }

  /**
   * Testing only number is given for input without quit, view will throw Illegal state exception
   * whose message is captured by the controller and handled.
   */
  @Test
  public void test1() {
    in = new StringReader("File\n1");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nSomething went wrong while creating the" +
            " portfolio\n More Information Readable failed\n");
  }


  /**
   * Testing only number is given for input without quit, view will throw Illegal state exception
   * whose message is captured by the controller and handled.
   */
  @Test
  public void test0() {
    in = new StringReader("File\n0");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n");
  }

  /**
   * Testing only number is given for input without quit, view will throw Illegal state exception
   * whose message is captured by the controller and handled.
   */
  @Test
  public void test2() {
    in = new StringReader("File\n2");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nSomething went wrong while buying share\n"
            + " More Information: Readable failed\n\n");
  }

  /**
   * Testing only number is given for input without quit, view will throw Illegal state exception
   * whose message is captured by the controller and handled.
   */
  @Test
  public void test3() {
    in = new StringReader("File\n3");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nSomething went wrong while computing cost " +
            "basis\n" + " More Information: Readable failed\n\n");
  }

  /**
   * Testing only Invalid is given for input without quit, view will throw Illegal state exception
   * whose message is captured by the controller and handled.
   */
  @Test
  public void testInvalidNum() {
    in = new StringReader("File\n10\nquit");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nUnknown command 10\n\n");
  }

  /**
   * Testing only Invalid character is given for input without quit, view will throw Illegal state
   * exception whose message is captured by the controller and handled.
   */
  @Test
  public void testInvalidChar() {
    in = new StringReader("File\na\nquit");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nUnknown command a\n\n");
  }

  /**
   * Testing quit is given for input it exits the application.
   */
  @Test
  public void testquit() {
    in = new StringReader("File\nquit");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nThe operation has been discarded\n");
  }

  /**
   * Testing the scenario when the input is sent as empty only file is passed.
   */
  @Test
  public void testEmptyFile() {
    in = new StringReader("File\n ");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n");
  }

  /**
   * Testing the scenario when the input is sent as empty, nothing happens.
   */
  @Test
  public void testEmptyAll() {
    in = new StringReader("quit\n");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "");
  }

  /**
   * Testing creating porfolio valid no spaces or invalid inputs.
   */
  @Test
  public void testPerformCreateAllValid() {
    in = new StringReader("File\n1\nMyPortfolio");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: MyPortfolio created" +
            " successfully\n\n");
  }


  /**
   * Testing create portfolio with invalid inputs and spaces in between keeps waiting for the
   * portfolio name. The portfolio name can be given an underscore inbetween.
   */
  @Test
  public void testPerformCreateWithInvalids() {
    in = new StringReader("File\n1 \n ! @ # $$ % ^ & * ( sah@hsa My_Portfolio");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "" + "FILE chosen\nInvalid Name Enter again\n"
            + "Invalid Name Enter again\n" + "Invalid Name Enter again\n"
            + "Invalid Name Enter again\n" + "Invalid Name Enter again\n"
            + "Invalid Name Enter again\n" + "Invalid Name Enter again\n"
            + "Invalid Name Enter again\n" + "Invalid Name Enter again\n"
            + "Invalid Name Enter again" +
            "\nPortfolio: My_Portfolio created successfully\n\n");
  }

  /**
   * Testing create portfolio with invalid inputs and spaces in between keeps waiting for the
   * portfolio name.
   */
  @Test
  public void testPerformCreateWithLotsOfSpaces() {
    in = new StringReader("File\n1 \n !12 @ #    \n    ~~` $$ % ^ & * ( sah@hsa My_Portfolio");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nInvalid Name Enter again\n"
            + "Invalid Name Enter again\n" + "Invalid Name Enter again\n"
            + "Invalid Name Enter again\n" + "Invalid Name Enter again\n"
            + "Invalid Name Enter again\n" + "Invalid Name Enter again\n"
            + "Invalid Name Enter again\n" + "Invalid Name Enter again\n"
            + "Invalid Name Enter again\n" + "Invalid Name Enter again" +
            "\nPortfolio: My_Portfolio created successfully\n\n");
  }


  /**
   * Testing buying share with all valid inputs and valid time is considered as valid input.
   */
  @Test
  public void testPerformBuyShareAllValid() {
    in = new StringReader("File\n1\nMy_Portfolio\n2\nMy_Portfolio\nGOOG\n200.12" +
            "\n2018-10-10\n10:10:10");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: My_Portfolio created successfully\n"
            + "\n" + "Bought share successfully\n\n");
  }

  /**
   * Testing buying share with all valid inputs and valid time but the portfolio if non existant
   * throws an error.
   */
  @Test
  public void testPerformBuyWithNonExistingPorfolio() {
    in = new StringReader("File\n1\nMy_Portfolio\n2\nMyPortfolio\nGOOG\n200.12\n" +
            "2018-10-10\n10:10:10");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: My_Portfolio created successfully" +
            "\n\n" + "Something went wrong while buying share\n"
            + " More Information: Portfolio does not exist.\n\n");
  }

  /**
   * Testing buying share with all valid inputs and valid time but the portfolio if invalid and then
   * valid portfolio name.
   */
  @Test
  public void testPerformBuyWithInvalidThenExistantPortfolio() {
    in = new StringReader("File\n1\nMy_Portfolio\n2\nMy@Portfolio My_Portfolio\nGOOG" +
            "\n200.12\n2018-10-10\n10:10:10");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: My_Portfolio created successfully" +
            "\n\n" + "Invalid Name Enter again\nBought share successfully\n\n");
  }


  /**
   * Testing buying share with all valid inputs and invalid ticker will throw exceptiong form
   * model.
   */
  @Test
  public void testPerformBuyWithInvalidTicker() {
    in = new StringReader("File\n1\nMy_Portfolio\n2\nMy_Portfolio\njhdjaa\n200.12\n" +
            "2018-10-10\n10:10:10");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: My_Portfolio created successfully\n"
            + "\n" + "Something went wrong while buying share\n"
            + " More Information: No such file found jhdjaa.csv\n\n");
  }

  /**
   * Testing buying share with all valid inputs and invalid price will keep waiting. model.
   */
  @Test
  public void testPerformBuyWithInvalidPrice() {
    in = new StringReader("File\n1\nMy_Portfolio\n2\nMy_Portfolio\nGOOG\nasjsjd\n2018-10-10\n" +
            "10:10:10");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: My_Portfolio created " +
            "successfully\n\n" + "Invalid value please enter again\n" +
            "Invalid value please enter again\n"
            + "Invalid value please enter again\n"
            + "Something went wrong while buying share\n"
            + " More Information: Readable failed\n\n");

  }

  /**
   * Testing buying share with all valid inputs and then invalid and valid price inputs correctly.
   */
  @Test
  public void testPerformBuyWithInvalidThenValidPrice() {
    in = new StringReader("File\n1\nMy_Portfolio\n2\nMy_Portfolio\nGOOG\nasjsjd\n 200.12" +
            "\n2018-10-10\n10:10:10");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: My_Portfolio created successfully\n"
            + "\nInvalid value please enter again\n" + "Bought share successfully\n\n");
  }

  /**
   * Testing when invalid date is sent and then valid date, the operation is successful.
   */
  @Test
  public void testPerformBuyWithInvalidThenValidDate() {
    in = new StringReader("File\n1\nMy_Portfolio\n2\nMy_Portfolio\nGOOG\nasjsjd\n 200.12" +
            "\n2018:10:10\n10-10-10\n2018-10-10\n10:10:10");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: My_Portfolio created successfully\n"
            + "\n" + "Invalid value please enter again\n"
            + "Invalid value of date please enter again\n"
            + "Invalid value of date please enter again\nBought share successfully\n\n");
  }

  /**
   * Calling 2 before 1 will not break any operation as all are asynchronous calls in this.
   */
  @Test
  public void testCall2Before1() {
    in = new StringReader("File\n2\nMy_Portfolio\nGOOG\nasjsjd\n 200.12\n2018:10:1010-10-10\n" +
            "2018-10-10\n10:10:10");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nInvalid value please enter again\n"
            + "Invalid value of date please enter again\n"
            + "Something went wrong while buying share\n"
            + " More Information: You do not have any portfolios.\n\n");
  }

  /**
   * Calling 2 before 1 will not break any operation as all are asynchronous calls in this.
   */
  @Test
  public void testHavingMultiplePortfolios() {
    in = new StringReader("File\n1\nPF1\n1\nPF2\n" + "2\nPF1\nGOOG\nasjsjd\n 200.12\n" +
            "2018:10:10\n10-10-10\n2018-10-10\n10:10:10");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: PF1 created successfully\n"
            + "\n" + "Portfolio: PF2 created successfully\n" + "\n"
            + "Invalid value please enter again\n"
            + "Invalid value of date please enter again\n"
            + "Invalid value of date please enter again\nBought share successfully\n\n");
  }

  /**
   * Test method check if cost basis is returning the cumulative on invalid date format throws an
   * exception.
   */
  @Test
  public void testGetCostBasisOnInvalidDay() {
    in = new StringReader("File\n1\nPF1\n1\nPF2\n" + "2\nPF1\nGOOG\nasjsjd\n 200.12\n" +
            "2018:10:10\n10-10-10\n2018-10-10\n10:10:10\n3\nPF1\n2018:10:10\n10-10-10");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Portfolio: PF2 created successfully\n" + "\n"
            + "Invalid value please enter again\n" + "Invalid value of date please enter again\n"
            + "Invalid value of date please enter again\n" + "Bought share successfully\n" + "\n"
            + "Invalid value of date please enter again\n"
            + "Invalid value of date please enter again\n"
            + "Something went wrong while computing cost basis\n"
            + " More Information: Readable failed\n\n");
  }

  /**
   * Test method check if cost basis is returning the cumulative on invalid date format throws an
   * exception.
   */
  @Test
  public void testGetCostBasisOnSameDay() {
    in = new StringReader("File\n1\nPF1\n1\nPF2\n" + "2\nPF1\nGOOG\nasjsjd\n 200.12\n" +
            "2018-10-10\n10:10:10\n3\nPF1\n2018-10-10\n10:10:10");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: PF1 created successfully\n"
            + "\n" + "Portfolio: PF2 created successfully\n" + "\n"
            + "Invalid value please enter again\n"
            + "Bought share successfully\n" + "\n" + "The cost basis is: 200.12\n\n");
  }

  /**
   * Test method to check costo basis on after date will be cumulative sum of past days as well.
   */
  @Test
  public void testGetCostBasisAfterDate() {
    in = new StringReader("File\n1\nPF1\n1\nPF2\n" + "2\nPF1\nGOOG\nasjsjd\n 200.12\n" +
            "2018-10-10\n10:10:10\n3\nPF1\n2018-10-11\n10:10:10");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: PF1 created successfully\n"
            + "\n" + "Portfolio: PF2 created successfully\n" + "\n"
            + "Invalid value please enter again\n" + "Bought share successfully\n" + "\n"
            + "The cost basis is: 200.12\n\n");
  }

  /**
   * Testing cost basis for before date will return zero as no shares were bought before the above
   * date.
   */
  @Test
  public void testGetCostBasisBeforeDate() {
    in = new StringReader("File\n1\nPF1\n1\nPF2\n" + "2\nPF1\nGOOG\nasjsjd\n 200.12\n" +
            "2018-10-10\n10:10:10\n3\nPF1\n2018-10-09\n10:10:10");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: PF1 created successfully\n"
            + "\n" + "Portfolio: PF2 created successfully\n" + "\n"
            + "Invalid value please enter again\n"
            + "Bought share successfully\n" + "\n" + "The cost basis is: 0.0\n\n");
  }

  /**
   * Testing total value for before date will return zero as no shares were bought before the above
   * date.
   */
  @Test
  public void testGetTotalValBeforeDate() {
    in = new StringReader("File\n1\nPF1\n1\nPF2\n" + "2\nPF1\nGOOG\nasjsjd\n 200.12\n" +
            "2018-10-10\n10:10:10\n3\nPF1\n2018-10-09\n10:10:10\n4\nPF1\n2018-10-09\n10:10:10");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: PF1 created successfully\n"
            + "\n" + "Portfolio: PF2 created successfully\n" + "\n"
            + "Invalid value please enter again\n" + "Bought share successfully\n"
            + "\n" + "The cost basis is: 0.0\n\nThe total value is: 0.0\n\n");
  }

  /**
   * Testing total value for after date will return total value for the bought before the above
   * date.
   */
  @Test
  public void testGetTotalValAfterDate() {
    in = new StringReader("File\n1\nPF1\n1\nPF2\n" + "2\nPF1\nGOOG\nasjsjd\n 200.12\n" +
            "2018-10-10\n10:10:10\n3\nPF1\n2018-10-09\n10:10:10\n4\nPF1\n2018-10-11\n10:10:10");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: PF1 created successfully\n"
            + "\n" + "Portfolio: PF2 created successfully\n" + "\n"
            + "Invalid value please enter again\n" + "Bought share successfully\n"
            + "\n" + "The cost basis is: 0.0\n\nThe total value is: 199.76833428904385\n\n");
  }

  /**
   * Testing total value for after date will return total value for the bought before the above
   * date.
   */
  @Test
  public void testGetTotalValSameDate() {
    in = new StringReader("File\n1\nPF1\n1\nPF2\n" + "2\nPF1\nGOOG\nasjsjd\n 200.12\n" +
            "2018-10-10\n10:10:10\n3\nPF1\n2018-10-09\n10:10:10\n4\nPF1\n2018-10-10\n10:10:10");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: PF1 created successfully\n"
            + "\n" + "Portfolio: PF2 created successfully\n" + "\n"
            + "Invalid value please enter again\n" + "Bought share successfully\n"
            + "\n" + "The cost basis is: 0.0\n\nThe total value is: 200.12\n\n");
  }

  /**
   * Testing total value for that same date and time will return same value as the price brought.
   */
  @Test
  public void testGetTotalValSameDateTotalVal() {
    in = new StringReader("File\n1\nPF1\n1\nPF2\n" + "2\nPF1\nGOOG\nasjsjd\n 200.12\n" +
            "2018-10-10\n10:10:10\n3\n@1 PF1\n2018-10-09\n10:10:10\n4\n@3\nPF1\n" +
            "2018-10-10\n10:10:10");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: PF1 created successfully\n"
            + "\n" + "Portfolio: PF2 created successfully\n" + "\n"
            + "Invalid value please enter again\n" + "Bought share successfully\n\n"
            + "Invalid Name Enter again\n" + "The cost basis is: 0.0\n" + "\n"
            + "Invalid Name Enter again\nThe total value is: 200.12\n" + "\n");
  }


  /**
   * Testing total value for that same date and time will return same value as the price brought
   * 5min.
   */
  @Test
  public void testGetTotalValSameDateDiffTime() {
    in = new StringReader("File\n1\nPF1\n1\nPF2\n" + "2\nPF1\nGOOG\nasjsjd\n 200.12\n" +
            "2018-10-10\n10:10:10\n3\n@1 PF1\n2018-10-09\n10:10:10\n4\n@3\nPF1\n" +
            "2018-10-10\n10:10:30");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: PF1 created successfully\n" + "\n"
            + "Portfolio: PF2 created successfully\n" + "\n"
            + "Invalid value please enter again\n"
            + "Bought share successfully\n" + "\n"
            + "Invalid Name Enter again\nThe cost basis is: 0.0\n\nInvalid Name Enter again\n" +
            "The total value is: 200.12\n\n");
  }

  /**
   * Testing total value for that same date and time will return same value as the price brought
   * 5min.
   */
  @Test
  public void testGetTotalValBeforeDateValid() {
    in = new StringReader("File\n1\nPF1\n1\nPF2\n" + "2\nPF1\nGOOG\nasjsjd\n 200.12\n" +
            "2018-10-10\n10:10:10\n3\n@1 PF1\n2018-10-09\n10:10:10\n4\n@3\nPF1\n2018-10-09\n" +
            "10:10:30");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: PF1 created successfully\n"
            + "\n" + "Portfolio: PF2 created successfully\n" + "\n"
            + "Invalid value please enter again\n" + "Bought share successfully\n"
            + "\n" + "Invalid Name Enter again\nThe cost basis is: 0.0\n\n" +
            "Invalid Name Enter again\nThe total value is: 0.0\n\n");
  }

  /**
   * Testing total value valid but non existing portfolio.
   */
  @Test
  public void testGetTotalValNonExistinPF() {
    in = new StringReader("File\n1\nPF1\n1\nPF2\n" + "2\nPF1\nGOOG\nasjsjd\n" +
            " 200.12\n2018-10-10\n10:10:10\n3\n@1 PF1\n2018-10-09\n10:10:10\n4\n" +
            "@3\nPF4\n2018-10-09\n10:10:30");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: PF1 created successfully\n" + "\n"
            + "Portfolio: PF2 created successfully\n" + "\n"
            + "Invalid value please enter again\n" + "Bought share successfully\n"
            + "\n" + "Invalid Name Enter again\nThe cost basis is: 0.0\n" + "\n"
            + "Invalid Name Enter again\nSomething went wrong while computing cost basis\n"
            + " More Information: Portfolio does not exist.\n");
  }

  /**
   * Testing total value valid but invalid date will not let the operation takes place.
   */
  @Test
  public void testGetTotalValNonExistinPFWithInvalidDate() {
    in = new StringReader("File\n1\nPF1\n1\nPF2\n" + "2\nPF1\nGOOG\nasjsjd\n 200.12\n" +
            "2018-10-10\n10:10:10\n3\n@1 PF1\n2018-10-09\n10:10:10\n4\n@3\nPF4\n" +
            "2018-10:09\n10:10:30");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: PF1 created successfully\n"
            + "\n" + "Portfolio: PF2 created successfully\n" + "\n"
            + "Invalid value please enter again\n" + "Bought share successfully\n"
            + "\n" + "Invalid Name Enter again\nThe cost basis is: 0.0\n" + "\n"
            + "Invalid Name Enter again\n" + "Invalid value of date please enter again\n" +
            "Invalid value of date please enter again\n"
            + "Something went wrong while computing cost basis\n"
            + " More Information: Readable failed\n");
  }

  /**
   * Testing get portfolio  by name for multiple portfolios gives  wrt to the input given if given
   * an invalid input exceptuion i sthrown from the model.
   */
  @Test
  public void testGetPortFolio() {
    in = new StringReader("File\n1\nPF1\n1\nPF2\n" + "2\nPF1\nGOOG\nasjsjd\n " +
            "200.12\n2018-10-10\n10:10:10\n3\n@1 PF1\n2018-10-09\n10:10:10\n4\n"
            + "@3\nPF2\n2018-10-09\n10:10:30\n5\nport@!\nPF1\n5\nPF2\n5\nPF3\nexit");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: PF1 created successfully\n\n"
            + "Portfolio: PF2 created successfully\n" + "\n" +
            "Invalid value please enter again\nBought share successfully\n"
            + "\n" + "Invalid Name Enter again\nThe cost basis is: 0.0\n" + "\n"
            + "Invalid Name Enter again\nThe total value is: 0.0\n\n"
            + "Invalid Name Enter again\n" +
            "Portfolio Name : PF1[ticker=GOOG,costBasis=200.12, datePurchased=2018-10-10T10:10:10,"
            + " noOfShares=0.18508721629270639]\n" + "\n" + "Portfolio Name : PF2[]\n" + "\n"
            + "Something went wrong while getting: 'PF3' portfolio\n"
            + "More Information: Portfolio does not exist.\n\nThe operation has been discarded\n");
  }


  /**
   * Test to check get all portfolios is returning all the portfoils in one session.
   */
  @Test
  public void testGetAllPortFolio() {
    in = new StringReader("File\n1\nPF1\n1\nPF2\n" + "2\nPF1\nGOOG\nasjsjd\n 200.12\n" +
            "2018-10-10\n10:10:10\n3\n@1 PF1\n2018-10-09\n10:10:10\n4\n"
            + "@3\nPF2\n2018-10-09\n10:10:30\n5\nport@!\nPF1\n5\nPF2\n6\n");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: PF1 created successfully\n\n"
            + "Portfolio: PF2 created successfully\n" + "\nInvalid value please  " +
            "enter again\nBought share successfully\n" + "\n" + "Invalid Name Enter again\n" +
            "The cost basis is: 0.0\n" + "\n" + "Invalid Name Enter again\nThe total value is: " +
            "0.0\n\n" + "Invalid Name Enter again\nPortfolio Name : PF1[ticker=GOOG," +
            "costBasis=200.12, datePurchased=2018-10-10T10:10:10," +
            " noOfShares=0.18508721629270639]\n" + "\n" + "Portfolio Name : PF2[]\n" + "\n"
            + "Portfolio Name : PF1[ticker=GOOG,costBasis=200.12, " +
            "datePurchased=2018-10-10T10:10:10, noOfShares=0.18508721629270639]\n"
            + "Portfolio Name : PF2[]\n\n");
  }

  /**
   * Test to check operation has been called an hasnt been fully completed and second input is
   * called which led to illegal state exception.
   */
  @Test(expected = IllegalStateException.class)
  public void testIllegalState() {
    in = new StringReader("File\n1\nPF1\n1\nPF2\n" + "2\nPF1\nGOOG\nasjsjd\n" +
            " 200.12\n2018-10-10\n10:10:10\n3\n@1 PF1\n2018-10-09\n10:10:10\n4\n"
            + "@3\nPF2\n2018-10-09\n10:10:30\n5\nport@!\nPF1\n5\nPF2\n5\n6\n");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: PF1 created successfully\n\n"
            + "Portfolio: PF2 created successfully\n" + "\nInvalid value please enter" +
            " again\nBought share successfully\n" + "\n" + "The cost basis is: 0.0\n" + "\n"
            + "The total value is: 0.0\n\n" + "Portfolio Name : PF1[ticker=GOOG,costBasis=200.12," +
            " datePurchased=2018-10-10T10:10:10," + " noOfShares=0.18508721629270639]\n" + "\n"
            + "Portfolio Name : PF2[]\n" + "\n" + "Portfolio Name : PF1[ticker=GOOG," +
            "costBasis=200.12, datePurchased=2018-10-10T10:10:10, noOfShares=" +
            "0.18508721629270639]\n" + "Portfolio Name : PF2[]\n\n\n");
  }

  /**
   * Testing quit in between readcommands will say operation has been discarded and exits.
   */
  @Test
  public void testQuitInBetweenCommands() {
    in = new StringReader("File\n1\nPF0\nquit\nexit\n1\nPF0");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: PF0 created successfully\n" + "\n"
            + "Unknown command quit\n" + "\n" + "Something went wrong while creating the" +
            " portfolio\n" + " More Information Portfolio name already exists.\n" +
            "The operation has been discarded\n");

  }

  /**
   * Testing quit in between readcommands will say operation has been discarded and exits and so
   * testing any valid input after q willnot be excecuted.
   */
  @Test
  public void testCommandAfterQuit() {
    in = new StringReader("File\n1\nPF0\nquit\n1\nPPE");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: PF0 created successfully\n" + "\n"
            + "The operation has been discarded\n");
  }


  /**
   * Testing quit in between operationg in the command will say operation has been discarded and
   * exits and so testing any valid input after q willnot be executed.
   */
  @Test
  public void testTwoQuitInBetweenOperation() {
    in = new StringReader("File\n1\nquit\nquit\nPF1");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n");
  }


  /**
   * Testing quit in between operation in the command will say operation has been discarded and
   * waits for new input method.
   */
  @Test
  public void testQuitInBetweenOperation() {
    in = new StringReader("File\n1\nquit\n1\nPF1");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nPortfolio: PF1 created successfully\n" + "\n");
  }

  /**
   * Testing quit in before any command will say operation has been discarded and exits and so
   * testing any valid input after q willnot be executed.
   */
  @Test
  public void testQuitInBetweenOperation2() {
    in = new StringReader("File\nexit\n1\nPF1\nquit");
    view = new StockPortalView(in, out);
    IStockPortalController iPortfolioController = new StockPortalController(model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\nThe operation has been discarded\n");
  }

}
