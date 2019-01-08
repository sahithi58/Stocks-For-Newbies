import java.io.InputStreamReader;

import controller.AdvancedPlusStockPortalController;
import model.AdvancedPlusStockPortal;
import model.IAdvancedPlusStockPortal;
import view.AdvancedPlusStockPortalView;
import view.IAdvancedPlusStockPortalView;

public class AdvancedPlusStockDemo {
  /**
   * In the main method, we create the objects for model, view and controller and invoke the
   * controller to check the operations.
   */
  public static void main(String[] args) {
    IAdvancedPlusStockPortalView view = new AdvancedPlusStockPortalView(
            new InputStreamReader(System.in), System.out);
    IAdvancedPlusStockPortal model = new AdvancedPlusStockPortal();
    new AdvancedPlusStockPortalController(model,view).handleRequest();
  }
}
