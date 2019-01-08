package view;

/**
 * This view will act as view for latest checks but also carries alll the method from basic view for
 * doing both the operations successfully.
 */
public class AdvancedPlusStockPortalView extends AdvancedStockPortalView
                                            implements IAdvancedPlusStockPortalView {
  /**
   * This constructor takes in the readable as input and appendable as output.
   *
   * @param in  as Readable.
   * @param out as Appendable.
   */
  public AdvancedPlusStockPortalView(Readable in, Appendable out) {
    super(in, out);
  }

  @Override
  public void showCURDAdvancedOptions() {
    System.out.append("Hello welcome to buying operations\n");
    System.out.append("Press 1: To create a portfolio\n");
    System.out.append("Press 2: To save a portfolio\n");
    System.out.append("Press 3: To retrieve a portfolio\n");
    System.out.append("Enter quit: To go back to the main menu\n");
    System.out.append("Enter exit: To exit the application\n");
  }

  @Override
  public String getSavingPortfolioChoice() {
    System.out.println("Please enter 'Y' if you want to save the portfolio else 'N' ");
    return helperGetChoice("Y", "N");
  }

  @Override
  public String getSavingStrategyChoice() {
    System.out.println("Please enter 'Y' if you want to save the strategy else 'N' ");
    return helperGetChoice("Y", "N");
  }

  @Override
  public String getRetrievingPortfolioChoice() {
    System.out.println("Please enter 'Y' if you want to retrieve the portfolio else 'N' ");
    return helperGetChoice("Y", "N");
  }

  @Override
  public String getRetrievingStrategyChoice() {
    System.out.println("Please enter 'Y' if you want to retrieve an existing strategy else 'N' ");
    return helperGetChoice("Y", "N");
  }

  @Override
  public String getStrategyName() {
    System.out.append("Enter strategy name\n");
    if (s.hasNext()) {
      String next = s.next().trim();
      if (next.equalsIgnoreCase("quit")) {
        return "quit";
      } else {
        return checkPortfolioName(next);
      }
    } else {
      throw new IllegalStateException("Readable failed");
    }
  }
}
