package model;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class uses the Gson library to save and retrieve portfolios from json objects. It create and
 * reads file from the directory "Portfolios" which must be located in the same location as its
 * jar.
 */
public class PortfolioDao implements IPortfolioDao {

  /**
   * This method will save the portfolio to the file in the form of a Json objct.\
   *
   * @param portfolio the portfolio operations object.
   */
  public void savePortfolioToJson(PortfolioOperations portfolio) {
    Gson gson = new Gson();
    FileWriter file;
    try {
      file = new FileWriter("Portfolios\\" + portfolio.getPortfolio() + ".json");
      file.write(gson.toJson(portfolio));
      file.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Retrieves the portfolio an and adds it to the existing list of portfolios.
   *
   * @param portfolio the name of portoflio as String.
   * @return the PortfolioOperations object.
   */
  public PortfolioOperations retrievePortfolioFromJson(String portfolio) {
    Gson gson = new Gson();
    PortfolioOperations portfolio1 = null;
    try {
      portfolio1 = gson.fromJson(new FileReader("Portfolios\\" + portfolio + ".json"),
              Portfolio.class);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Fie does not exist." + e.getMessage());
    }
    return portfolio1;
  }

}
