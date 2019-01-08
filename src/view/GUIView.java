package view;


import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

/**
 * This method carried the GUI related form creation and sending the loaded forms to the controller
 * using swing api.
 */
public class GUIView extends JFrame implements IGUIView {


  final JFrame mainFrame;
  final JPanel optionsPanel;
  final JPanel subPanel;
  private JPanel btnSubPanel;
  private JPanel formPanel;
  private List<JTextField> toClearList;
  private JTextField tickerCode;
  private JTextField portfolioName;
  private JTextField numOfStock = new JTextField();
  private JButton createButton;
  private JButton buyButton;
  private JButton costBasButton;
  private JButton totalValButton;
  private JButton getPortfolioBtn;
  private JButton getAllPFSBtn;
  private JButton exitButton;
  private JButton investFixedShare;
  private JButton investDollarCost;
  private JButton buyShare;
  private JButton backBtn;
  private JButton dollarCostSubmit;
  private JButton investFixedSubmit;
  private JButton buyButtonSubmit;
  private JButton createPFSubmit;
  private JButton cancelButton;
  private JButton plotgraph;
  private JComboBox combobox;
  private JComboBox jComboBox1;
  private JButton costBasisSubmit;
  private JButton totalValSubmit;
  private JButton getPFSubmit;
  private JButton options;
  private JButton saveButtonPf;
  private JButton retrievePF;
  private JLabel[] jLabelTicker;
  private JLabel[] jLabelPercent;
  private JTextField[] jTickers;
  private JTextField[] jPercents;
  private JRadioButton button1;
  private JRadioButton button2;
  private JTextField amount;
  private JTextField endDate;
  private JTextField date;
  private JTextField commission;
  private JTextField time;
  private JTextField frequency;
  private JComboBox invChoice;
  private JComboBox dateChoice;
  private JComboBox saveChoice;

  /**
   * This interface created the main frame added the options frame to display buttons and formpanel
   * to display the forms that has to be loaded when the buttons are clicked.
   */
  public GUIView(String caption) {
    super(caption);
    dataChoice();
    toClearList = new ArrayList<>();
    mainFrame = new JFrame();
    mainFrame.setTitle(caption);
    mainFrame.setVisible(true);
    mainFrame.setSize(1200, 700);
    mainFrame.setLocationRelativeTo(null);
    mainFrame.setResizable(false);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    TitledBorder border = new TitledBorder("Stocks For Newbies");
    border.setTitleJustification(TitledBorder.CENTER);
    border.setTitlePosition(TitledBorder.TOP);
    TitledBorder border1 = new TitledBorder("Forms");
    border1.setTitleJustification(TitledBorder.CENTER);
    border1.setTitlePosition(TitledBorder.TOP);
    formPanel = new JPanel();
    formPanel.setBorder(border1);
    optionsPanel = new JPanel(new BorderLayout());

    optionsPanel.setBorder(border);
    mainFrame.add(optionsPanel, BorderLayout.LINE_START);
    optionsPanel.setVisible(true);
    subPanel = new JPanel();
    btnSubPanel = new JPanel();
    btnSubPanel.setBorder(border);
    helperLoadBtnCreation();
    helperGetBtnCreation();
    optionsPanel.add(subPanel, BorderLayout.WEST);
  }

  /**
   * Before stating the application user will be asked to choose between dat or api to proceed with
   * the application, default is taken as file.
   */
  private void dataChoice() {
    final JPanel panel = new JPanel();
    final JLabel jLabel = new JLabel("Choose the data to work in this application");
    button1 = new JRadioButton("API");
    button1.setActionCommand("api");
    button2 = new JRadioButton("FILE");
    button2.setActionCommand("file");
    button2.setSelected(true);
    ButtonGroup group = new ButtonGroup();
    group.add(button1);
    group.add(button2);
    panel.add(jLabel);
    panel.add(button1);
    panel.add(button2);
    JOptionPane.showMessageDialog(null, panel);
  }

  /**
   * This method has all button that whill first create the form.
   */
  private void helperGetBtnCreation() {
    createPFSubmit = new JButton("Create");
    createPFSubmit.setActionCommand("getCreatedPF");
    buyButtonSubmit.setActionCommand("buyButtonSubmit");
    cancelButton = new JButton("Cancel");
    cancelButton.setActionCommand("cancel");
    dollarCostSubmit = new JButton("Perform Strategy");
    dollarCostSubmit.setActionCommand("dollarCostSubmit");
    costBasisSubmit = new JButton("Get Cost Basis");
    costBasisSubmit.setActionCommand("costBasisSubmit");
    totalValSubmit = new JButton("Get Total Value");
    totalValSubmit.setActionCommand("totalValSubmit");
    getPFSubmit = new JButton("Get Portfolio");
    getPFSubmit.setActionCommand("getPFSubmit");
    investFixedSubmit = new JButton("Make One Time Investment");
    investFixedSubmit.setActionCommand("investFixedSubmit");
    saveButtonPf = new JButton("Save Portfolio");
    retrievePF = new JButton("Retrieve Porfolio");
    createButton = new JButton("Create Portfolio");
  }

  /**
   * This method has all buttons that will give rise to loading the form.
   */
  private void helperLoadBtnCreation() {
    plotgraph = new JButton("Plot a graph");
    buyButton = new JButton("Buy Options");
    costBasButton = new JButton("Cost Basis");
    totalValButton = new JButton("Total Value");
    getPortfolioBtn = new JButton("Get Portfolio");
    getAllPFSBtn = new JButton("Get All Portfolios");
    buyButtonSubmit = new JButton("Buy");
    investFixedShare = new JButton("One Time investment");
    investDollarCost = new JButton("Recurring Investment");
    buyShare = new JButton("Buy Share");
    backBtn = new JButton("Back");
    exitButton = new JButton("Exit");
    options = new JButton("Portfolio Operations");
    addButton("plotgraph", plotgraph);
    addButton("options", options);
    addButton("buyBtn", buyButton);
    addButton("costBasisBtn", costBasButton);
    addButton("totalValBtn", totalValButton);
    addButton("getPFBtn", getPortfolioBtn);
    addButton("getAllPFBtn", getAllPFSBtn);
    addButton("exitBtn", exitButton);

  }

  /**
   * This method is used to add buttons to options panel directly.
   *
   * @param actionCommand the action command of those buttons.
   * @param button        the name of the buttons.
   */
  private void addButton(String actionCommand, JButton button) {
    button.setActionCommand(actionCommand);
    subPanel.add(button);
  }

  @Override
  public String showSavePFForm() {
    Object[] savePFObj = {"Enter Portfolio", portfolioName = new JTextField()};
    int choice = JOptionPane.showConfirmDialog(null, savePFObj,
            "Save Portfolio", JOptionPane.OK_CANCEL_OPTION);
    if (choice == JOptionPane.OK_OPTION) {
      return getPFNameHelper();
    } else {
      cancelOperation();
      return null;
    }
  }

  @Override
  public String[] askIfWantsToRetrieveStrategy() {
    String[] arr;
    JLabel jLabel = new JLabel("Enter Y if u want to retieve and use a strategy");
    JRadioButton rad1 = new JRadioButton("y");
    JRadioButton rad2 = new JRadioButton("n");
    ButtonGroup buttonGroup = new ButtonGroup();
    rad1.setSelected(true);
    JPanel jPanel = new JPanel();
    jPanel.add(jLabel);
    buttonGroup.add(rad1);
    buttonGroup.add(rad2);
    jPanel.add(rad1);
    jPanel.add(rad2);
    int choice = JOptionPane.showConfirmDialog(null,
            "Click yes if u want to retrieve an existing startegy",
            "Click Yes", JOptionPane.OK_OPTION);
    if (choice == JOptionPane.YES_OPTION) {
      arr = new String[2];
      JTextField pfName = new JTextField();
      JTextField strategyName = new JTextField();
      Object[] retrievalForm = {"Portfolio Name:", pfName, "Strategy Name:", strategyName};
      int choice1 = JOptionPane.showConfirmDialog(null, retrievalForm,
              "Retrieve Strategy", JOptionPane.OK_CANCEL_OPTION);
      if (choice1 == JOptionPane.OK_OPTION) {
        arr[0] = strategyName.getText();
        arr[1] = pfName.getText();
        return arr;
      } else {
        cancelOperation();
        return null;
      }
    } else {
      return new String[0];
    }
  }

  @Override
  public String[] getPlotDetails() {
    String[] arr = new String[3];
    JTextField pfName = new JTextField();
    JTextField startDate = new JTextField();
    JTextField endDate = new JTextField();
    Object[] retrievalForm = {"Portfolio Name:", pfName, "Start Date:", startDate,
                              "End Date", endDate};
    int choice1 = JOptionPane.showConfirmDialog(null, retrievalForm,
            "Get Plot Details", JOptionPane.OK_CANCEL_OPTION);
    if (choice1 == JOptionPane.OK_OPTION) {
      arr[0] = pfName.getText();
      try {
        arr[1] = checkLocalDate(startDate.getText()).toString();
      } catch (Exception e) {
        startDate.setText("");
        JOptionPane.showMessageDialog(null, "Invalid date");
        return null;
      }
      try {
        arr[2] = checkLocalDate(endDate.getText()).toString();
      } catch (Exception e) {
        startDate.setText("");
        JOptionPane.showMessageDialog(null, "Invalid Time");
        return null;
      }
      return arr;
    } else {
      cancelOperation();
      return null;
    }
  }

  @Override
  public String retrievePFForm() {
    Object[] savePFObj = {"Enter Portfolio", portfolioName = new JTextField()};
    int choice = JOptionPane.showConfirmDialog(null, savePFObj,
            "Retrieve A Portfolio", JOptionPane.OK_CANCEL_OPTION);
    if (choice == JOptionPane.OK_OPTION) {
      return getPFNameHelper();
    } else {
      cancelOperation();
      return null;
    }
  }

  /**
   * This method will get the portfolio names and validates it.
   *
   * @return same as input if correct.
   */
  private String getPFNameHelper() {
    if (inputCheck(portfolioName, "^[a-zA-Z0-9_ ]*$")) {
      return portfolioName.getText();
    } else {
      JOptionPane.showMessageDialog(null, "Invalid Input");
      portfolioName.setText("");
      return null;
    }
  }

  /**
   * This method will add the buttons to sub panel under options panel.
   *
   * @param actionCommand as the buttons command.
   * @param button        the button that is declared on the top.
   */
  private void addButtonToBtnSubPanel(String actionCommand, JButton button) {
    button.setActionCommand(actionCommand);
    btnSubPanel.add(button);
  }

  @Override
  public void plot(List<Double> totalVals, LocalDateTime startDate, LocalDateTime endDate) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    int i = 0;
    for (LocalDateTime date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
      dataset.addValue(totalVals.get(i), "Value of Portfolio", date.toString());
      i++;
    }
    Graph chart = new Graph(
            "Performance of portfolio over the time frame",
            "Total value v/s Time", dataset);

    chart.pack();
    RefineryUtilities.centerFrameOnScreen(chart);
    chart.setVisible(true);
  }

  @Override
  public void addActionListener(ActionListener actionListener) {
    createButton.addActionListener(actionListener);
    buyButton.addActionListener(actionListener);
    costBasButton.addActionListener(actionListener);
    totalValButton.addActionListener(actionListener);
    getPortfolioBtn.addActionListener(actionListener);
    getAllPFSBtn.addActionListener(actionListener);
    exitButton.addActionListener(actionListener);
    backBtn.addActionListener(actionListener);
    buyShare.addActionListener(actionListener);
    investFixedShare.addActionListener(actionListener);
    investDollarCost.addActionListener(actionListener);
    createPFSubmit.addActionListener(actionListener);
    cancelButton.addActionListener(actionListener);
    buyButtonSubmit.addActionListener(actionListener);
    dollarCostSubmit.addActionListener(actionListener);
    costBasisSubmit.addActionListener(actionListener);
    totalValSubmit.addActionListener(actionListener);
    getPFSubmit.addActionListener(actionListener);
    investFixedSubmit.addActionListener(actionListener);
    saveButtonPf.addActionListener(actionListener);
    retrievePF.addActionListener(actionListener);
    options.addActionListener(actionListener);
    plotgraph.addActionListener(actionListener);
  }

  @Override
  public void loadCreatePFForm() {
    JPanel createFormPanel = setUpCreatePanel("Create Portfolio", BoxLayout.PAGE_AXIS);
    portfolioLabel(createFormPanel);
    createFormPanel.add(createPFSubmit);
    createFormPanel.add(cancelButton);
    repaintNewForm(createFormPanel);
  }

  /**
   * This method should be called after every form is loaded.
   *
   * @param createFormPanel that panel to which this feature has been added.
   */
  private void repaintNewForm(JPanel createFormPanel) {
    formPanel.removeAll();
    formPanel.add(createFormPanel);
    mainFrame.add(formPanel);
    formPanel.revalidate();
    formPanel.repaint();
  }

  @Override
  public void setTickerToEmpty() {
    tickerCode.setText("");
  }

  @Override
  public void loadBuyShareForm(String[] arr) {
    JPanel createFormPanel = setUpCreatePanel("Buy a share", BoxLayout.PAGE_AXIS);
    if (arr.length == 0) {
      repaintNewForm(createFormPanel);
      JOptionPane.showMessageDialog(null, "You do not have any portfolio");
      return;
    }
    existingPFLabel(createFormPanel, arr);
    tickerLabel(createFormPanel);
    commissionLabel(createFormPanel);
    amountLabel(createFormPanel);
    dateLabel(createFormPanel);
    timeLabel(createFormPanel);
    createFormPanel.add(buyButtonSubmit);
    createFormPanel.add(cancelButton);
    repaintNewForm(createFormPanel);
  }

  @Override
  public void loadCostBasisForm(String[] arr) {
    JPanel createFormPanel = new JPanel();
    TitledBorder titledBorder = new TitledBorder("Calculate Cost Basis");
    createFormPanel.setBorder(titledBorder);
    evaluatingForm(arr, costBasisSubmit, createFormPanel);
  }

  @Override
  public void loadTotalValForm(String[] arr) {
    JPanel createFormPanel = new JPanel();
    TitledBorder titledBorder = new TitledBorder("Calculate Total Value");
    createFormPanel.setBorder(titledBorder);
    evaluatingForm(arr, totalValSubmit, createFormPanel);
  }

  /**
   * This method is used to evaluate the portfolio to check if it is currently empty or not.
   *
   * @param arr             array of portfolios.
   * @param totalValSubmit  the total value per portfolio.
   * @param createFormPanel the panel to which these features has been added.
   */
  private void evaluatingForm(String[] arr, JButton totalValSubmit, JPanel createFormPanel) {
    createFormPanel.setLayout(new BoxLayout(createFormPanel, BoxLayout.PAGE_AXIS));
    if (arr.length == 0) {
      repaintNewForm(createFormPanel);
      JOptionPane.showMessageDialog(null, "You do not have any portfolio");
      return;
    }
    existingPFLabel(createFormPanel, arr);
    dateLabel(createFormPanel);
    timeLabel(createFormPanel);
    createFormPanel.add(totalValSubmit);
    createFormPanel.add(cancelButton);
    repaintNewForm(createFormPanel);
  }

  @Override
  public void loadGetPortfolioForm(String[] arr) {
    JPanel createFormPanel = setUpCreatePanel("Get Portfolio Details", BoxLayout.PAGE_AXIS);
    if (arr.length == 0) {
      repaintNewForm(createFormPanel);
      JOptionPane.showMessageDialog(null, "You do not have any portfolio");
      return;
    }
    existingPFLabel(createFormPanel, arr);
    createFormPanel.add(getPFSubmit);
    repaintNewForm(createFormPanel);
  }

  @Override
  public void loadFixedInvForm(String[] arr, String invChoice) {
    JPanel createFormPanel = setUpCreatePanel("Make a one time investment", BoxLayout.PAGE_AXIS);
    jLabelPercent = new JLabel[arr.length];
    jPercents = new JTextField[arr.length];
    jTickers = new JTextField[arr.length];
    if (arr.length == 0) {
      repaintNewForm(createFormPanel);
      JOptionPane.showMessageDialog(null, "You do not have any portfolio");
      return;
    } else {
      String stocks = "";
      for (String a : arr) {
        stocks += a + ", ";
      }
      JLabel label = new JLabel();
      label.setText("Stocks in portfolio are:" + stocks);
      createFormPanel.add(label);
      commissionLabel(createFormPanel);
      amountLabel(createFormPanel);
      dateLabel(createFormPanel);
      for (int i = 0; i < arr.length; i++) {
        if (invChoice.equalsIgnoreCase("n")) {
          jTickers[i] = new JTextField();
          jTickers[i].setText(arr[i]);
          addFields(createFormPanel, i, "Enter the percent for " + arr[i]);
        } else {
          jTickers[i] = new JTextField();
          jTickers[i].setText(arr[i]);
          jPercents[i] = new JTextField();
          double num = 100 / arr.length;
          jPercents[i].setText(String.valueOf(num));
        }
      }
      createFormPanel.add(investFixedSubmit);
      createFormPanel.add(cancelButton);
    }
    repaintNewForm(createFormPanel);
  }

  /**
   * This label can be user to create the time textfield and label and add to any other tab.
   *
   * @param createFormPanel that panel to which this feature has been added.
   */
  private void timeLabel(JPanel createFormPanel) {
    JLabel tilabel = new JLabel();
    tilabel.setText("Time (in hh:mm:ss format):");
    time = new JTextField(20);
    tilabel.setLabelFor(time);
    toClearList.add(time);
    createFormPanel.add(tilabel);
    createFormPanel.add(time);
  }

  /**
   * This label can be user to create the combo box to display existing portfolios.
   *
   * @param createFormPanel that panel to which this feature has been added.
   */
  private void existingPFLabel(JPanel createFormPanel, String[] arr) {
    combobox = new JComboBox(arr);
    createFormPanel.add(combobox);
  }

  /**
   * This label can be user to create the start date label and add to any other tab.
   *
   * @param createFormPanel that panel to which this feature has been added.
   */
  private void dateLabel(JPanel createFormPanel) {
    JLabel dlabel = new JLabel();
    dlabel.setText("Date (in yyyy-mm-dd format):");
    date = new JTextField(20);
    dlabel.setLabelFor(date);
    toClearList.add(date);
    createFormPanel.add(dlabel);
    createFormPanel.add(date);
  }

  /**
   * This label can be user to create the end date textfield and label and add to any other tab.
   *
   * @param createFormPanel that panel to which this feature has been added.
   */
  private void endDateLabel(JPanel createFormPanel) {
    JLabel dlabel = new JLabel();
    dlabel.setText("End Date (in yyyy-mm-dd format):");
    endDate = new JTextField(20);
    dlabel.setLabelFor(endDate);
    toClearList.add(endDate);
    createFormPanel.add(dlabel);
    createFormPanel.add(endDate);
  }

  @Override
  public String[] askIfWantsToSave() {
    String[] arr = new String[2];
    Object[] ifWantsToSave = {"Do you want to save this Strategy",
                              setSavingChoice("Choose 'Y' is yes."),
                              "Enter a unique name for the strategy", numOfStock};

    int choice = JOptionPane.showConfirmDialog(null, ifWantsToSave,
            "Saving Strategy", JOptionPane.OK_CANCEL_OPTION);
    if (choice == JOptionPane.OK_OPTION) {
      if (saveChoice.getSelectedItem().toString().equalsIgnoreCase("n")) {
        arr[0] = "n";
      } else {
        arr[0] = "Y";
      }
      arr[1] = numOfStock.getText();
      return arr;
    } else {
      cancelOperation();
      return null;
    }
  }

  /**
   * This label can be user to create the commission textfield and label and add to any other tab.
   *
   * @param createFormPanel that panel to which this feature has been added.
   */
  private void commissionLabel(JPanel createFormPanel) {
    JLabel clabel = new JLabel();
    clabel.setText("Commission(in $):");
    commission = new JTextField(20);
    clabel.setLabelFor(commission);
    toClearList.add(commission);
    createFormPanel.add(clabel);
    createFormPanel.add(commission);
  }

  /**
   * This label can be user to create the ticker symbol textfield and label and add to any other
   * tab.
   *
   * @param createFormPanel that panel to which this feature has been added.
   */
  private void tickerLabel(JPanel createFormPanel) {
    JLabel tlabel = new JLabel();
    tlabel.setText("Ticker Code:");
    tickerCode = new JTextField(20);
    tlabel.setLabelFor(tickerCode);
    toClearList.add(tickerCode);
    createFormPanel.add(tlabel);
    createFormPanel.add(tickerCode);
  }

  /**
   * This label can be user to create the porfolio textfield and label and add to any other tab.
   *
   * @param createFormPanel that panel to which this feature has been added.
   */
  private void portfolioLabel(JPanel createFormPanel) {
    JLabel label = new JLabel();
    label.setText("Portfolio Name:");
    portfolioName = new JTextField(20);
    label.setLabelFor(portfolioName);
    toClearList.add(portfolioName);
    createFormPanel.add(label);
    createFormPanel.add(portfolioName);
  }

  @Override
  public void cancelOperation() {
    for (JTextField textField : toClearList) {
      textField.setText("");
    }
  }

  @Override
  public String getCreateForm() {
    return getPFNameHelper();
  }

  @Override
  public String[] getBuyShares() {
    String totalErrorMessge = "";
    String[] allShares = new String[6];
    allShares[0] = combobox.getSelectedItem().toString();
    allShares[1] = tickerCode.getText();
    totalErrorMessge = settingInput(totalErrorMessge, allShares, commission, 2,
            "Commission can only be a number\n");
    totalErrorMessge = settingInput(totalErrorMessge, allShares, amount, 3,
            "Amount can only be a number\n");
    try {
      allShares[4] = checkLocalDate(date.getText()).toString();
    } catch (Exception e) {
      date.setText("");
      totalErrorMessge += e.getMessage();
      totalErrorMessge += "Invalid date, enter in yyyy-mm-ddd format.\n";
    }
    try {
      allShares[5] = checkLocalTime(time.getText()).toString();
    } catch (Exception e) {
      time.setText("");
      totalErrorMessge += "Invalid time, enter in hh:mm:ss format.\n";
    }
    if (!totalErrorMessge.equals("")) {
      JOptionPane.showMessageDialog(null, totalErrorMessge);
      return null;
    }
    return allShares;
  }

  /**
   * This method checks if the input is validated if yes, then itreturns error as empty.
   *
   * @param totalErrorMessge cumulative errors in the form
   * @param allShares        array of string that are retrieved from controller.
   * @param amount           the total amount added.
   * @param i                the location where this input has to be set.
   * @param s                the error if any.
   * @return the error as  string
   */
  private String settingInput(String totalErrorMessge, String[] allShares, JTextField amount,
                              int i, String s) {
    if (inputCheck(amount, "(\\d+(\\.\\d+)?)")) {
      allShares[i] = amount.getText();
    } else {
      totalErrorMessge += s;
      amount.setText("");
    }
    return totalErrorMessge;
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

  @Override
  public String[] getCostBasis() {
    return helperLoadTotalCostBas();
  }

  @Override
  public String[] getTotalValue() {
    return helperLoadTotalCostBas();
  }

  /**
   * This helper is user to specifically check the data and time before lading the cost basis.
   *
   * @return string[] that will be sent to controller.
   */
  private String[] helperLoadTotalCostBas() {
    String totalErrorMessge = "";
    String[] allShares = new String[3];
    allShares[0] = combobox.getSelectedItem().toString();
    try {
      allShares[1] = checkLocalDate(date.getText()).toString();
    } catch (Exception e) {
      date.setText("");
      totalErrorMessge += "Invalid date enter in yyyy-mm-dd format";
    }
    try {
      allShares[2] = checkLocalTime(time.getText()).toString();
    } catch (Exception e) {
      time.setText("");
      totalErrorMessge += "Invalid time enter in hh:mm:ss format";
    }
    if (!totalErrorMessge.equals("")) {
      JOptionPane.showMessageDialog(null, totalErrorMessge);
      return null;
    }
    return allShares;
  }

  @Override
  public String[] makeFixedInv() {
    String totalErrorMessge = "";
    String[] allShares = new String[6 + 2 * jPercents.length];
    totalErrorMessge = settingInput(totalErrorMessge, allShares, commission, 1,
            "Commission can only be a number\n");
    totalErrorMessge = settingInput(totalErrorMessge, allShares, amount, 2,
            "Amount can only be a number\n");
    allShares[5] = jComboBox1.getSelectedItem().toString();
    try {
      allShares[3] = checkLocalDate(date.getText()).toString();
      allShares[4] = "10:10:10";
    } catch (Exception e) {
      date.setText("");
      totalErrorMessge += "Invalid Date (Enter in YYYY-mmm-dd format)";
    }

    double sum = 0;
    if (invChoice.getSelectedItem().toString().equalsIgnoreCase("n")) {
      totalErrorMessge = validateTickers(totalErrorMessge, sum);
    }
    if (!totalErrorMessge.equalsIgnoreCase("")) {
      viewMessage(totalErrorMessge);
      return null;
    } else {
      int j = 0;
      allShares[0] = String.valueOf(jTickers.length);
      for (int i = 1; j < jTickers.length; i = i + 2) {
        allShares[i + 5] = jTickers[j].getText();
        allShares[i + 6] = jPercents[j].getText();
        j = j + 1;
      }
      return allShares;
    }
  }

  @Override
  public String[] dollarCostInv() {
    String totalErrorMessge = "";
    String[] allShares = new String[9 + (2 * jTickers.length)];
    allShares[0] = String.valueOf(jTickers.length);
    allShares[1] = portfolioName.getText();
    totalErrorMessge = settingInput(totalErrorMessge, allShares, commission, 8,
            "Commission can only be a number\n");
    totalErrorMessge = settingInput(totalErrorMessge, allShares, amount, 2,
            "Amount can only be a number\n");
    totalErrorMessge = settingInput(totalErrorMessge, allShares, frequency, 7,
            "Amount can only be a number\n");
    try {
      allShares[3] = checkLocalDate(date.getText()).toString();
      allShares[4] = "10:10:10";
      if (endDate == null) {
        allShares[5] = "";
        allShares[6] = "";
      } else {
        if (endDate.getText().equals("")) {
          allShares[5] = "";
          allShares[6] = "";
        } else {
          allShares[5] = checkLocalDate(endDate.getText()).toString();
          allShares[6] = "10:10:10";
        }
      }
    } catch (Exception e) {
      totalErrorMessge += "Time or Date is invalid." + e.getMessage();
    }
    double sum = 0;
    if (invChoice.getSelectedItem().toString().equalsIgnoreCase("n")) {
      totalErrorMessge = validateTickers(totalErrorMessge, sum);
    }
    if (!totalErrorMessge.equalsIgnoreCase("")) {
      viewMessage(totalErrorMessge);
      return null;
    } else {
      int j = 0;
      for (int i = 1; j < jTickers.length; i = i + 2) {
        allShares[i + 8] = jTickers[j].getText();
        allShares[i + 9] = jPercents[j].getText();
        j = j + 1;
      }
      return allShares;
    }
  }

  /**
   * This method will validate of percents sum up to 1000 or less or more.
   *
   * @param totalErrorMessge the String to append errors throughtout the form.
   * @return the String that returns the error message if any.
   */
  private String validateTickers(String totalErrorMessge, double sum) {
    for (int i = 0; i < jTickers.length; i++) {
      if (Double.parseDouble(jPercents[i].getText()) > 100) {
        jPercents[i].setText("");
        totalErrorMessge += "The percent value is more then 100 for :" + jTickers[i].getText();
      }
      sum += Double.parseDouble(jPercents[i].getText());
    }
    if (sum != 100) {
      totalErrorMessge += "Sum of percents is:" + sum + ", it should be 100 to proceed.";
    }
    return totalErrorMessge;
  }

  @Override
  public String getPortfolio() {
    String name = combobox.getSelectedItem().toString();
    return name;
  }

  @Override
  public String getDataChoice() {
    if (button1.isSelected()) {
      button2.setSelected(false);
      return "API";
    } else {
      button1.setSelected(false);
      return "FILE";
    }
  }

  @Override
  public void viewMessage(String message) {
    JOptionPane.showMessageDialog(null, message, "Message",
            JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void viewLongMessage(String message) {
    JPanel jPanel = new JPanel();
    JTextArea textArea = new JTextArea(6, 25);
    textArea.setText(message);
    textArea.setEditable(false);
    JScrollPane jPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jPanel.add(jPane);
    JOptionPane.showMessageDialog(null, jPanel, "Message",
            JOptionPane.INFORMATION_MESSAGE);
  }


  @Override
  public void showAdvancedBuyOptions() {
    btnSubPanel.removeAll();
    addButtonToBtnSubPanel("loadBuyShareForm", buyShare);
    addButtonToBtnSubPanel("investFixed", investFixedShare);
    addButtonToBtnSubPanel("dollarCost", investDollarCost);
    addButtonToBtnSubPanel("back", backBtn);
    mainFrame.remove(optionsPanel);
    mainFrame.remove(formPanel);
    mainFrame.add(btnSubPanel, BorderLayout.WEST);
    mainFrame.revalidate();
    mainFrame.repaint();
  }

  @Override
  public void showCURDAdvancedOptions() {
    btnSubPanel.removeAll();
    addButtonToBtnSubPanel("loadForm", createButton);
    addButtonToBtnSubPanel("saveButtonPf", saveButtonPf);
    addButtonToBtnSubPanel("retrievePF", retrievePF);
    addButtonToBtnSubPanel("back", backBtn);
    mainFrame.remove(optionsPanel);
    mainFrame.remove(formPanel);
    mainFrame.add(btnSubPanel, BorderLayout.WEST);
    mainFrame.revalidate();
    mainFrame.repaint();
  }

  @Override
  public void showOption() {
    formPanel.removeAll();
    mainFrame.remove(btnSubPanel);
    mainFrame.add(optionsPanel, BorderLayout.WEST);
    mainFrame.revalidate();
    mainFrame.repaint();
  }

  /**
   * This label can be user to create the total investing amount textfield and label and add to any
   * other tab.
   *
   * @param createFormPanel that panel to which this feature has been added.
   */
  private void amountLabel(JPanel createFormPanel) {
    JLabel alabel = new JLabel();
    alabel.setText("Total Amount(in $):");
    amount = new JTextField(20);
    alabel.setLabelFor(amount);
    toClearList.add(amount);
    createFormPanel.add(alabel);
    createFormPanel.add(amount);
  }

  /**
   * This method will create a  investing choice label for any panel that wants to create one.
   *
   * @param text the Jlabel input to show to user.
   * @return the Jpanel that has been added with these features.
   */
  private JPanel setInvestingChoiceLabel(String text) {
    JPanel panel = new JPanel();
    JLabel invChoiceLabl = new JLabel(text);
    String[] arr = new String[2];
    arr[0] = "y";
    arr[1] = "n";
    invChoice = new JComboBox(arr);
    panel.add(invChoiceLabl);
    panel.add(invChoice);
    return panel;
  }

  /**
   * This method will create a  investing choice label for any panel that wants to create one.
   *
   * @param text the Jlabel input to show to user.
   * @return the Jpanel that has been added with these features.
   */
  private JPanel setSavingChoice(String text) {
    JPanel panel = new JPanel();
    JLabel saveChoiceLabel = new JLabel(text);
    String[] arr = new String[2];
    arr[0] = "y";
    arr[1] = "n";
    saveChoice = new JComboBox(arr);
    panel.add(saveChoiceLabel);
    panel.add(saveChoice);
    return panel;
  }

  /**
   * This method will create a  data choice label for any panel that wants to create one.
   *
   * @param text the Jlabel input to show to user.
   * @return the Jpanel that has been added with these features.
   */
  private JPanel setDateChoiceLabel(String text) {
    JPanel panel = new JPanel();
    JLabel dateChoiceL = new JLabel(text);
    String[] arr = new String[2];
    arr[0] = "y";
    arr[1] = "n";
    dateChoice = new JComboBox(arr);
    panel.add(dateChoiceL);
    panel.add(dateChoice);
    return panel;
  }

  @Override
  public String[] getTotalNumOfStock() {
    String[] arr = new String[3];
    Object[] initalForm = {"Invest Equally ?",
                           setInvestingChoiceLabel("Choose 'Y' if you want to invest " +
                           "equally on all stocks"), "End Date?",
                           setDateChoiceLabel("Choose y if u want to enter the date"),
                           "Num of stock", numOfStock};
    int isOk = JOptionPane.showConfirmDialog(null, initalForm, "Enter Input",
            JOptionPane.OK_CANCEL_OPTION);
    if (isOk == JOptionPane.OK_OPTION) {
      String invChoice1 = invChoice.getSelectedItem().toString();
      String dateChoice1 = dateChoice.getSelectedItem().toString();
      arr[0] = invChoice1.equalsIgnoreCase("y") ? "Y" : "n";
      arr[1] = dateChoice1.equalsIgnoreCase("y") ? "y" : "n";
      if (checkInputNumStock(arr)) {
        return null;
      }
      return arr;
    } else {
      cancelOperation();
      return null;
    }
  }

  /**
   * This method will check if the value is a number or not, if now it will give user in a pop up.
   *
   * @param arr to append the neww value.
   * @return returns true if success.
   */
  private boolean checkInputNumStock(String[] arr) {
    if (inputCheck(numOfStock, "(\\d+(\\.\\d+)?)")) {
      arr[2] = numOfStock.getText();
    } else {
      JOptionPane.showMessageDialog(null,
              "Number of Stocks can only be number");
      return true;
    }
    return false;
  }


  /**
   * This method will check if the input matches its basic criteria like it should be a number or
   * alphanumberic.
   *
   * @param numOfStock as TextField.
   * @param s          as the regex.
   * @return true if validated and correct.
   */
  private boolean inputCheck(JTextField numOfStock, String s) {
    return numOfStock.getText().matches(s);
  }

  @Override
  public void loadDollarCostForm(Integer numOfStock, String endDateChoice, String investingChoice,
                                 String[] arr) {
    JPanel createFormPanel = setUpCreatePanel("Make a recurring investment", BoxLayout.Y_AXIS);
    portfolioLabel(createFormPanel);
    amountLabel(createFormPanel);
    dateLabel(createFormPanel);
    if (endDateChoice.equalsIgnoreCase("y")) {
      endDateLabel(createFormPanel);
    }
    frequencyLabel(createFormPanel);
    commissionLabel(createFormPanel);
    jLabelTicker = new JLabel[numOfStock];
    jTickers = new JTextField[numOfStock];
    jLabelPercent = new JLabel[numOfStock];
    jPercents = new JTextField[numOfStock];
    for (int i = 0; i < numOfStock; i++) {
      addFieldsTicker(createFormPanel, i, "Enter the ticker code");
      if (investingChoice.equalsIgnoreCase("n")) {
        addFields(createFormPanel, i, "Enter the percent");
      } else {
        jPercents[i] = new JTextField();
        double num = 100 / numOfStock;
        jPercents[i].setText(String.valueOf(num));
      }
    }
    createFormPanel.add(dollarCostSubmit);
    createFormPanel.add(cancelButton);
    repaintNewForm(createFormPanel);
  }

  private JPanel setUpCreatePanel(String s, int yAxis) {
    JPanel createFormPanel = new JPanel();
    TitledBorder titledBorder = new TitledBorder(s);
    createFormPanel.setBorder(titledBorder);
    createFormPanel.setLayout(new BoxLayout(createFormPanel, yAxis));
    return createFormPanel;
  }


  /**
   * This method will get the input from user an yes or no, if he wants to invest equally.
   *
   * @param arr list of portfolios.
   * @return will returns an array of strings to proceed to controller.
   */
  @Override
  public String[] getFixedInvBasic(String[] arr) {
    jComboBox1 = new JComboBox<>(arr);
    String[] newArr = new String[2];
    Object[] fixedInvInitial = {"Invest Equally ?",
                                 setInvestingChoiceLabel("Choose 'Y' if you want to invest " +
                                 "equally on all stocks"), "Portfolio:", jComboBox1};
    int isOk = JOptionPane.showConfirmDialog(null, fixedInvInitial,
            "One Time Investment", JOptionPane.OK_CANCEL_OPTION);
    return helperChoice(newArr, isOk, invChoice.getSelectedItem().toString());
  }


  /**
   * This method will get input from the user and proceeds to give it to controller.
   *
   * @param newArr will return an array of String.
   * @param isOk   will return what has user chose in the confirmation tab.
   * @return will returns an array of strings to proceed to controller.
   */
  private String[] helperChoice(String[] newArr, int isOk, String choice) {
    if (isOk == JOptionPane.OK_OPTION) {
      String choicePF = jComboBox1.getSelectedItem().toString();
      newArr[0] = choicePF;
      newArr[1] = choice;
      return newArr;
    } else {
      cancelOperation();
      return null;
    }
  }


  /**
   * This method add fields to the form dynamically based on the request provide dby user.
   *
   * @param createFormPanel the jpanel in which the developer wants to place this.
   * @param s               the input text label he wants to show to user.
   */
  private void addFields(JPanel createFormPanel, int i, String s) {
    jLabelPercent[i] = new JLabel();
    jLabelPercent[i].setText(s);
    jPercents[i] = new JTextField();
    toClearList.add(jPercents[i]);
    createFormPanel.add(jLabelPercent[i]);
    createFormPanel.add(jPercents[i]);
  }

  /**
   * This method add fields to the form dynamically based on the request provide dby user.
   *
   * @param createFormPanel the jpanel in which the developer wants to place this.
   * @param s               the input text label he wants to show to user.
   */
  private void addFieldsTicker(JPanel createFormPanel, int i, String s) {
    jLabelTicker[i] = new JLabel();
    jLabelTicker[i].setText(s);
    jTickers[i] = new JTextField();
    toClearList.add(jTickers[i]);
    createFormPanel.add(jLabelTicker[i]);
    createFormPanel.add(jTickers[i]);
  }


  /**
   * This method will create the frequency label for any panel that calls this.
   *
   * @param createFormPanel the jpanel in which the developer wants to place this.
   */
  private void frequencyLabel(JPanel createFormPanel) {
    JLabel label = new JLabel();
    label.setText("Frequency of Investment:");
    frequency = new JTextField(20);
    label.setLabelFor(frequency);
    toClearList.add(frequency);
    createFormPanel.add(label);
    createFormPanel.add(frequency);
  }

}