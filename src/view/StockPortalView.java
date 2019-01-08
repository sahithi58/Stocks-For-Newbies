package view;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Scanner;

/**
 * This method implements the view of the controller interface to get input from the user to
 * controller and show results from controller to the user.
 */
public class StockPortalView implements IStockPortalView {

  private Appendable out;
  protected Scanner s;

  /**
   * This constructor takes in the readable as input and appendable as output.
   *
   * @param in  as Readable.
   * @param out as Appendable.
   */
  public StockPortalView(Readable in, Appendable out) {
    s = new Scanner(in);
    this.out = out;
  }

  @Override
  public void showOption() {
    System.out.append("Hello welcome to portfolio application\n");
    System.out.append("Press 0:    To get menu\n");
    System.out.append("Press 1:    Options\n");
    System.out.append("Press 2:    To Buy shares\n");
    System.out.append("Press 3:    To Get cost basis of a particular portfolio on given date\n");
    System.out.append("Press 4:    To Get the total value of a particular portfolio on given" +
            " date\n");
    System.out.append("Press 5:    To Get a certain portfolio\n");
    System.out.append("Press 6:    To Get all my portfolios\n");
    System.out.append("Enter quit: To go back to the main menu\n");
    System.out.append("Enter exit: To exit the application\n");
  }

  @Override
  public void viewMessage(String message) {
    validationMessage(message + "\n");
  }

  @Override
  public void viewOutMessage(String message) {
    System.out.append(message + "\n");
  }


  /**
   * This method validates if the input is in the format requested by the controller.
   *
   * @param nextIn as input string on which validation is performed recursively.
   * @return the validate correct user input for the date.
   */
  private String checkInputDate(String nextIn) {
    try {
      checkLocalDate(nextIn);
      return nextIn;
    } catch (Exception e) {
      validationMessage("Invalid value of date please enter again\n");
      if (s.hasNext()) {
        String next = s.next();
        if (next.equalsIgnoreCase("quit")) {
          return "quit";
        } else {
          return this.checkInputDate(next);
        }
      } else {
        throw new IllegalStateException("Readable failed");
      }
    }
  }


  /**
   * This method validates if the input is in the format requested by the controller.
   *
   * @param nextIn as input string on which validation is performed recursively.
   * @return the validate correct user input for the date.
   */
  private String checkInputTime(String nextIn) {
    try {
      checkLocalTime(nextIn);
      return nextIn;
    } catch (Exception e) {
      validationMessage("Invalid value of time please enter again\n");
      if (s.hasNext()) {
        String next = s.next();
        if (next.equalsIgnoreCase("quit")) {
          return "quit";
        } else {
          return this.checkInputTime(next);
        }
      } else {
        throw new IllegalStateException("Readable failed");
      }
    }
  }

  /**
   * This method checks if appendable failed or not gets messages and checks them here.
   *
   * @param s as String is the messgae inputted to appendable.
   */
  protected void validationMessage(String s) {
    try {
      out.append(s);
    } catch (IOException e) {
      throw new IllegalStateException("Appendable failed");
    }
  }


  @Override
  public String getPortfolioName() {
    System.out.append("Enter portfolio name\n");
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

  /**
   * This method validates if the input has only alphabets numbers and underscore.
   *
   * @param nextIn next input given taken via scanner.
   * @return waits for valid input and returns it as a string.
   */
  protected String checkPortfolioName(String nextIn) {
    if (!nextIn.matches("^[a-zA-Z0-9_ ]*$")) {
      validationMessage("Invalid Name Enter again\n");
      if (s.hasNext()) {
        String next = s.next();
        if (next.equalsIgnoreCase("quit")) {
          return "quit";
        } else {
          return checkPortfolioName(next);
        }
      } else {
        throw new IllegalStateException("Readable failed");
      }
    } else {
      return nextIn;
    }
  }

  @Override
  public String getTickerSymbol() {
    System.out.append("Enter ticker code\n");
    if (s.hasNext()) {
      String next = s.next().trim();
      if (next.equalsIgnoreCase("quit")) {
        return "quit";
      }
      return next;
    } else {
      throw new IllegalStateException("Readable failed");
    }
  }

  @Override
  public String getDate() {
    System.out.append("Enter the date of the stock in: yyyy-mm-dd format\n");
    if (s.hasNext()) {
      String next = s.next();
      if (next.equalsIgnoreCase("quit")) {
        return "quit";
      } else {
        return checkInputDate(next).trim();
      }
    } else {
      throw new IllegalStateException("Readable failed");
    }
  }

  @Override
  public String getTime() {
    System.out.append("Enter the time of the stock in: hh:mm:ss format\n");
    if (s.hasNext()) {
      String next = s.next();
      if (next.equalsIgnoreCase("quit")) {
        return "quit";
      } else {
        return checkInputTime(next).trim();
      }
    } else {
      throw new IllegalStateException("Readable failed");
    }
  }

  @Override
  public String getTotalInvestAmount() {
    System.out.println("Enter the total money you want to invest in $");
    String next = helperInputNumber();
    return next;
  }

  /**
   * This method will convert the input into the format it wants for to send to the model.
   *
   * @param date as String
   * @return date as Localdate.
   */
  private LocalDate checkLocalDate(String date) {
    DateTimeFormatter mmddyyformat = DateTimeFormatter.ofPattern("uuuu-MM-dd")
            .withResolverStyle(ResolverStyle.STRICT);
    LocalDate localDate = LocalDate.parse(date, mmddyyformat);
    if (localDate.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException();
    }
    return localDate;
  }


  /**
   * This method will convert the input to time format and checks if its a valid 24hrs format input
   * or not.
   *
   * @param date as String
   * @return date as Localtime.
   */
  private LocalTime checkLocalTime(String date) {
    DateTimeFormatter hhmmss = DateTimeFormatter.ofPattern("HH:mm:ss")
            .withResolverStyle(ResolverStyle.STRICT);
    return LocalTime.parse(date, hhmmss);
  }


  protected String helperInputNumber() {
    if (s.hasNext()) {
      String next = s.next();
      if (next.equalsIgnoreCase("quit")) {
        return "quit";
      } else {
        return checkInputIfNumber(next).trim();
      }
    } else {
      throw new IllegalStateException("Readable failed");
    }
  }

  /**
   * This method validates if the amount money only has numbers in it, it only takes in the positive
   * real numbers.
   *
   * @param cost as input string on which validation is performed recursively.
   * @return the validate correct user input for the amount.
   */
  private String checkInputIfNumber(String cost) {
    if (cost.matches("(\\d+(\\.\\d+)?)")) {
      return cost;
    } else {
      validationMessage("Invalid value please enter again\n");
      if (s.hasNext()) {
        String next = s.next();
        if (next.equalsIgnoreCase("quit")) {
          return "quit";
        } else {
          return this.checkInputIfNumber(next);
        }
      } else {
        throw new IllegalStateException("Readable failed");
      }
    }
  }


  @Override
  public Scanner getInput() {
    return s;
  }

  @Override
  public String getDataChoice() {
    if (s.hasNext()) {
      String next = s.next();
      if (next.equalsIgnoreCase("API") || next.equalsIgnoreCase("FILE")) {
        return next;
      } else {
        validationMessage("Invalid input Enter again.\n");
        return getDataChoice();
      }
    } else {
      throw new IllegalStateException("Readable failed");
    }

  }

}
