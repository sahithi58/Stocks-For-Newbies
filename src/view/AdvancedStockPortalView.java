package view;


/**
 * This view will act as view for latest checks but also carries alll the method from basic view for
 * doing both the operations successfully.
 */
public class AdvancedStockPortalView extends StockPortalView implements IAdvancedView {

  /**
   * This constructor takes in the readable as input and appendable as output.
   *
   * @param in  as Readable.
   * @param out as Appendable.
   */
  public AdvancedStockPortalView(Readable in, Appendable out) {
    super(in, out);
  }


  @Override
  public void viewOutMessage(String message) {
    System.out.append(message + "\n");
  }


  @Override
  public String getInvestingChoice() {
    System.out.println("Please enter 'Y' if you want to invest equally in portfolio, else 'N' ");
    return helperGetChoice("Y", "N");

  }

  protected String helperGetChoice(String y, String n) {
    if (s.hasNext()) {
      String next = s.next();
      if (next.equalsIgnoreCase(y) || next.equalsIgnoreCase(n)) {
        return next;
      } else if (next.equalsIgnoreCase("quit")) {
        return "quit";
      } else {
        validationMessage("Invalid input Enter again.\n");
        return helperGetChoice("y", "n");
      }
    } else {
      throw new IllegalStateException("Readable failed");
    }
  }


  @Override
  public String getEndDateChoice() {
    System.out.println("Enter Y if u want to mention end date else N");
    return helperGetChoice("Y", "N");
  }

  @Override
  public String getFrequency() {
    System.out.println("please enter the frequency of investment dates:");
    String next = helperInputNumber();
    return next;
  }

  @Override
  public String getCommission() {
    System.out.println("Enter the commission you want to add in $:");
    String next = helperInputNumber();
    return next;
  }

  @Override
  public String getPercent(String name) {
    System.out.println("Enter percent for " + name + ": ");
    String next = helperInputNumber();
    return next;
  }

  @Override
  public String getNumOfStock() {
    System.out.println("Enter the number of stock u want to add");
    String next = helperInputNumber();
    return next;
  }

  @Override
  public void showAdvancedOptions() {
    System.out.append("Hello welcome to buying operations\n");
    System.out.append("Press 1: To buy a share\n");
    System.out.append("Press 2: To do one time investment\n");
    System.out.append("Press 3: To do recurring investment\n");
    System.out.append("Enter quit: To go back to the main menu\n");
    System.out.append("Enter exit: To exit the application\n");
  }

}
