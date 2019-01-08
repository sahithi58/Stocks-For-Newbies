import java.io.InputStreamReader;

import controller.AdvancedStockPortalController;
import model.AdvancedStockPortal;
import model.AdvancedStockPortalOperations;
import view.AdvancedStockPortalView;
import view.IAdvancedView;

/**
 * This method will demo the stock plus plus requirements asked for making fixed investments and
 * dollar cost average and commission related information.
 */
public class AdvancedStockDemo {

  /**
   * In the main method, we create the objects for model, view and controller and invoke the
   * controller to check the operations.
   */
  public static void main(String[] args) {
    IAdvancedView view = new AdvancedStockPortalView(new InputStreamReader(System.in), System.out);
    AdvancedStockPortalOperations model = new AdvancedStockPortal();
    new AdvancedStockPortalController(model, view).handleRequest();
  }
}
