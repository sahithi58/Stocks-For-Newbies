package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * This button listener is independent to view and controller. It maintains association with the
 * controller.
 */
public class ButtonListener implements ActionListener {
  Map<String, Runnable> buttonClickedActions;

  /**
   * This constructor is un initialized.
   */
  public ButtonListener() {
    //Nothing is here
  }

  /**
   * Set the map for key typed events. Key typed events in Java Swing are characters.
   */
  public void setButtonClickedActionMap(Map<String, Runnable> map) {
    buttonClickedActions = map;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (buttonClickedActions.containsKey(e.getActionCommand())) {
      buttonClickedActions.get(e.getActionCommand()).run();
    }
  }
}
