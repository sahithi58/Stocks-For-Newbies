import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import view.IStockPortalView;
import view.StockPortalView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class test the view part of mvc architecture. It basiclly validates the input before sent to
 * controller.
 */
public class StockPortalViewTest {

  private IStockPortalView view;
  private StringBuffer out;
  private Readable in;

  @Before
  public void setUp() {
    out = new StringBuffer();
    Readable in = new StringReader("");
  }

  /**
   * Testing views empty constructor is created successfully.
   */
  @Test
  public void testBasic() {
    in = new StringReader("");
    view = new StockPortalView(in, out);
    assertEquals(out.toString(), "");
  }

  /**
   * Testing get portfolio name is inputting and giving output for valid porfolio name.
   */
  @Test
  public void testgetPortfolioName() {
    in = new StringReader("ssasa");
    view = new StockPortalView(in, out);
    assertEquals(view.getPortfolioName(), "ssasa");
    assertEquals(out.toString(), "");
  }

  /**
   * Testing get portfolio name is inputting and stopped after one invalid input.
   */
  @Test(expected = IllegalStateException.class)
  public void testgetPortfolioInvalidName() {
    in = new StringReader("ssasa@@");
    view = new StockPortalView(in, out);
    assertEquals(view.getPortfolioName(), in.toString());
    assertEquals(out.toString(), "");
  }

  /**
   * Testing get portfolio name is inputting and continued after one invalid input hence valid input
   * only got accepted.
   */
  @Test
  public void testgetPortfolioInvalidNameThenValid() {
    in = new StringReader("ssasa@@\nsahit");
    view = new StockPortalView(in, out);
    assertEquals(view.getPortfolioName(), "sahit");
    assertEquals(out.toString(), "Invalid Name Enter again\n");
  }


  /**
   * Testing get portfolio name is given as null, then illegal state exception is thrown.
   */
  @Test
  public void testgetPortfolioEmpty() {
    in = new StringReader("");
    view = new StockPortalView(in, out);
    try {
      view.getPortfolioName();
      fail();
    } catch (IllegalStateException e) {
      //Nothing here
    }
    try {
      view.getTickerSymbol();
      fail();
    } catch (IllegalStateException e) {
      //Nothing here
    }
    try {
      view.getTotalInvestAmount();
      fail();
    } catch (IllegalStateException e) {
      //Nothing here
    }
    try {
      view.getDate();
      fail();
    } catch (IllegalStateException e) {
      //Nothing here
    }
    try {
      view.getTime();
      fail();
    } catch (IllegalStateException e) {
      //Nothing here
    }
  }


  /**
   * Testing get portfolio name is given as empty space exception is thrown.
   */
  @Test
  public void testgetPortfolioEmptySpace() {
    in = new StringReader(" ");
    view = new StockPortalView(in, out);
    try {
      view.getPortfolioName();
      fail();
    } catch (IllegalStateException e) {
      //Nothing here
    }
    try {
      view.getTickerSymbol();
      fail();
    } catch (IllegalStateException e) {
      //Nothing here
    }
    try {
      view.getTotalInvestAmount();
      fail();
    } catch (IllegalStateException e) {
      //Nothing here
    }
    try {
      view.getDate();
      fail();
    } catch (IllegalStateException e) {
      //Nothing here
    }
    try {
      view.getTime();
      fail();
    } catch (IllegalStateException e) {
      //Nothing here
    }
  }

  /**
   * Testing valid date comes out properly.
   */
  @Test
  public void testDate() {
    in = new StringReader("2018-10-10");
    view = new StockPortalView(in, out);
    assertEquals(view.getDate(), "2018-10-10");
  }


  /**
   * Testing invalid date(mnth) is ignored.
   */
  @Test
  public void testInvalidDateMnth() {
    in = new StringReader("2018-210-10\nquit");
    view = new StockPortalView(in, out);
    assertEquals(view.getDate(), "quit");
  }

  /**
   * Testing invalid date year is ignored.
   */
  @Test
  public void testInvalidDateYr() {
    in = new StringReader("2s018-210-10\nquit");
    view = new StockPortalView(in, out);
    assertEquals(view.getDate(), "quit");
  }

  /**
   * Testing invalid date day is ignored.
   */
  @Test
  public void testInvalidDateDay() {
    in = new StringReader("2018-20-qq21\nquit");
    view = new StockPortalView(in, out);
    assertEquals(view.getDate(), "quit");
  }

  /**
   * Testing invalid time hr is ignored.
   */
  @Test
  public void testInvalidTime1() {
    in = new StringReader("100:10:90\nquit");
    view = new StockPortalView(in, out);
    assertEquals(view.getTime(), "quit");
  }

  /**
   * Testing invalid time mm is ignored.
   */
  @Test
  public void testInvalidTime2() {
    in = new StringReader("10:140:90\nquit");
    view = new StockPortalView(in, out);
    assertEquals(view.getTime(), "quit");
  }

  /**
   * Testing invalid time ss is ignored.
   */
  @Test
  public void testInvalidTime3() {
    in = new StringReader("10:40:910\nquit");
    view = new StockPortalView(in, out);
    assertEquals(view.getTime(), "quit");
  }

  /**
   * Testing valid time comes out properly.
   */
  @Test
  public void testInvalid() {
    in = new StringReader("10:40:10\nquit");
    view = new StockPortalView(in, out);
    assertEquals(view.getTime(), "10:40:10");
  }

  /**
   * Passing valid ticker and checking nothing goes wrong.
   */
  @Test
  public void testTicker() {
    in = new StringReader("asaja");
    view = new StockPortalView(in, out);
    assertEquals(view.getTickerSymbol(), "asaja");
  }


  /**
   * Passing valid ticker but passing quit in the begining ignores the ticker.
   */
  @Test
  public void testTickerQuitBegin() {
    in = new StringReader("quit\nasaja");
    view = new StockPortalView(in, out);
    assertEquals(view.getTickerSymbol(), "quit");
  }

  /**
   * Passing first invalid and then valid ignored invalid takes valid.
   */
  @Test
  public void testGetAmout() {
    in = new StringReader("ajwqw\n182.91");
    view = new StockPortalView(in, out);
    assertEquals(view.getTotalInvestAmount(), "182.91");
  }

  /**
   * Passing first invalid and then valid ignored invalid takes valid.
   */
  @Test
  public void testGetAmoutInt() {
    in = new StringReader("ajwqw\n182");
    view = new StockPortalView(in, out);
    assertEquals(view.getTotalInvestAmount(), "182");
  }

  /**
   * Passing first invalid, then quit and then valid , ignored valid takes quit.
   */
  @Test
  public void testGetAmoutQuitBw() {
    in = new StringReader("ajwqw\nquit\n182");
    view = new StockPortalView(in, out);
    assertEquals(view.getTotalInvestAmount(), "quit");
  }

  /**
   * This method tests the view if out is sent correctly.
   */
  @Test
  public void testView() {
    in = new StringReader("");
    view = new StockPortalView(in, out);
    view.viewMessage("Hello");
    assertEquals(out.toString(), "Hello\n");
  }

  /**
   * This method tests the view if out is sent correctly.
   */
  @Test
  public void testMessage() {
    in = new StringReader("");
    view = new StockPortalView(in, out);
    view.viewMessage("");
    assertEquals(out.toString(), "\n");
  }


  /**
   * This method tests the menu if printing correctly in console and not added to out.
   */
  @Test
  public void testViewMenu() {
    in = new StringReader("");
    view = new StockPortalView(in, out);
    view.viewOutMessage("Heyy");
    assertEquals(out.toString(), "");
  }
}