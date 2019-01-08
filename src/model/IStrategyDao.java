package model;

/**
 * The Data access class for the Strategy object.
 */
public interface IStrategyDao {

  /**
   * Saves the strategy in json format using the GSON libray by accepting the strategy object.
   * Creates a new json file with the strategy name as the file name. If the file exists with a name
   * already then it overwrites the file with new content for the same strategy name.
   */
  void saveStrategyToJson(Strategy strategy);

  /**
   * Reads the file and returns an object of the Strategy class. It takes the strategy name as input
   * and fetches the object using the same.
   */
  Strategy retrieveStrategyFromFile(String strategy);

}
