package model;

/**
 * The Data access class for the Portfolio object.
 */
public interface IPortfolioDao {

  /**
   * Saves the portfolio in json format using the GSON libray by accepting the portfolio object.
   * Creates a new json file with the portfolio name as the file name. If the file exists with a
   * name already then it overwrites the file with new content for the same portfolio name.
   */
  void savePortfolioToJson(PortfolioOperations portfolio);

  /**
   * Reads the file and returns an object of the PortfolioOperations class. It takes the portfolio
   * name as input and fetches the object using the same.
   */
  PortfolioOperations retrievePortfolioFromJson(String portfolio);

}
