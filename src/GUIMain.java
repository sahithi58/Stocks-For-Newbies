import java.io.File;
import java.io.InputStreamReader;

import controller.AdvancedPlusStockPortalController;
import controller.GUIController;
import model.AdvancedPlusStockPortal;
import model.IAdvancedPlusStockPortal;
import view.AdvancedPlusStockPortalView;
import view.GUIView;
import view.IAdvancedPlusStockPortalView;
import view.IGUIView;

/**
 * This is gui main class which will be used to create the jar file. It has two runnable mode i.e.
 * view mode console and view mode GUI. According to what the user inputs as the mode the object
 * will be created and the application will be run.
 */
public class GUIMain {

  /**
   * In the main method, we create the objects for model, view and controller and invoke the
   * controller to check the operations.
   */
  public static void main(String[] args) {
    IAdvancedPlusStockPortal model = new AdvancedPlusStockPortal();
    if (!args[0].equalsIgnoreCase("-view")) {
      System.exit(0);
    } else {
      File dirPortfolio = new File("Portfolios\\");
      File dirStrategy = new File("Strategy\\");
      if (!dirPortfolio.exists()) {
        dirPortfolio.mkdirs();
      }
      if (!dirStrategy.exists()) {
        dirStrategy.mkdirs();
      }
      if (args[1].equalsIgnoreCase("gui")) {
        IGUIView view = new GUIView("Dummy Stock Application");
        new GUIController(model, view).handleRequest();
      } else if (args[1].equalsIgnoreCase("console")) {
        IAdvancedPlusStockPortalView view = new AdvancedPlusStockPortalView(new
                InputStreamReader(System.in), System.out);
        new AdvancedPlusStockPortalController(model, view).handleRequest();
      } else {
        throw new IllegalArgumentException("Undefined application mode chosen.");
      }
    }
  }
}
