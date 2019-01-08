import java.io.InputStreamReader;

import controller.StockPortalController;
import model.StockPortal;
import model.StockPortalOperations;
import view.IStockPortalView;
import view.StockPortalView;

/**
 * This is demo mail class which will be used to create the jar file.
 */
public class StockDemo {

  /**
   * In the main method, we create the objects for model, view and controller and invoke the
   * controller to check the operations.
   */
  public static void main(String[] args) {
    IStockPortalView view = new StockPortalView(new InputStreamReader(System.in), System.out);
    StockPortalOperations model = new StockPortal();
    new StockPortalController(model, view).handleRequest();
  }
}
