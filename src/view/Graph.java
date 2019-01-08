package view;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.JFrame;

/**
 * The graph class generated the graph view for the user for portfolio performance.
 */
public class Graph extends JFrame {
  /**
   * This class takes in applicationTitle, chartTitle and the data-set as input.
   */
  public Graph(String applicationTitle, String chartTitle, DefaultCategoryDataset dataset) {
    super(applicationTitle);
    JFreeChart lineChart = ChartFactory.createLineChart(
            chartTitle,
            "Days", "Total value of the Portfolio",
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false);

    ChartPanel chartPanel = new ChartPanel(lineChart);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
    setContentPane(chartPanel);
  }
}