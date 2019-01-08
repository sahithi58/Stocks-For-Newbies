package view;

/**
 * This interface has common method between the command line and gui interfaces, infuture of any
 * common methods need to be added, that can be added here.
 */
public interface GuiCmdCommonInterface {

  String getDataChoice();

  /**
   * This method will show the operations available to perform on the application in the form of
   * menu.
   */
  void showOption();

  /**
   * This method take the message or contents from the controller after the operationg are performed
   * on the data, and gives the inputs that controller wants to show the user.
   *
   * @param message gets the data from controller as to what has confirmation that he quit the
   *                function.
   */
  void viewMessage(String message);



}
