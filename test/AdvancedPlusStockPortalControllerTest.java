import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import controller.AdvancedPlusStockPortalController;
import model.AdvancedPlusStockPortal;
import model.IAdvancedPlusStockPortal;
import view.AdvancedPlusStockPortalView;
import view.IAdvancedPlusStockPortalView;

import static org.junit.Assert.assertEquals;

public class AdvancedPlusStockPortalControllerTest {

  private IAdvancedPlusStockPortal model;
  private IAdvancedPlusStockPortalView view;
  private StringBuffer out;
  private Readable in;

  /**
   * Method to set up and initialize readable and appendable.
   */
  @Before
  public void setUp() {
    out = new StringBuffer();
    Readable in = new StringReader("");
    model = new AdvancedPlusStockPortal();
  }

  @Test
  public void test() {
    in = new StringReader("FILE\n" + "1\n1\nPF1\nquit\n 2\n1\nPF1\ngoog\n2000\n2018-10-10\n" +
            "10:10:10\n10"
            + "\n2" + "\nPF1\n2000\n2018-10-10\n20\ny\nquit\n1\n2\nPF1\n");
    view = new AdvancedPlusStockPortalView(in, out);
    AdvancedPlusStockPortalController iPortfolioController = new AdvancedPlusStockPortalController(
            model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Bought share successfully\n" + "\n" + "Fixed Investment successful\n" + "\n"
            + "Portfolio Name : PF1\n" + " 1. Ticker = goog, CostBasis = $2000.0, Date Purchased" +
            " = 2018-10-10T10:10:10," + " Number of Shares = 1.8497623055437376, Commission = " +
            "$10.0 \n" + " 2. Ticker = goog, CostBasis = $2000.0, Date Purchased = " +
            "2018-10-10T10:00," + " Number of Shares = 1.8497623055437376, Commission = " +
            "$20.0 \n" + "\n" + "\n" + "The total value is (in $): 4106.768280275984\n\n");
  }

  @Test
  public void testAPI() {
    in = new StringReader("API\n" + "1\n1\nPF1\nquit\n 2\n1\nPF1\ngoog\n2000\n2018-10-10\n" +
            "10:10:10\n10"
            + "\n2" + "\nPF1\n2000\n2018-10-10\n20\ny\nquit\n1\n2\nPF1\n");
    view = new AdvancedPlusStockPortalView(in, out);
    AdvancedPlusStockPortalController iPortfolioController = new AdvancedPlusStockPortalController(
            model, view);
    iPortfolioController.handleRequest();
    assertEquals(out.toString(), "FILE chosen\n" + "Portfolio: PF1 created successfully\n"
            + "\n" + "Bought share successfully\n" + "\n" + "Fixed Investment successful\n" + "\n"
            + "Portfolio Name : PF1\n" + " 1. Ticker = goog, CostBasis = $2000.0, Date Purchased" +
            " = 2018-10-10T10:10:10," + " Number of Shares = 1.8497623055437376, Commission = " +
            "$10.0 \n" + " 2. Ticker = goog, CostBasis = $2000.0, Date Purchased = " +
            "2018-10-10T10:00," + " Number of Shares = 1.8497623055437376, Commission = " +
            "$20.0 \n" + "\n" + "\n" + "The total value is (in $): 4106.768280275984\n\n");
  }
}
