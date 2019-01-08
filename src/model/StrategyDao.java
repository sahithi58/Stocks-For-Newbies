package model;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class uses the Gson library to save and retrieve strategies from json objects. It create and
 * reads file from the directory "Strategy" which must be located in the same location as its jar.
 */
public class StrategyDao implements IStrategyDao {

  /**
   * This method saves the strategy into JSON Object when called.
   *
   * @param strategy as Strategy.
   */
  public void saveStrategyToJson(Strategy strategy) {
    Gson gson = new Gson();
    String json = gson.toJson(strategy);
    FileWriter file;
    try {
      file = new FileWriter("Strategy\\" + strategy.getStrategyName() + ".json");
      file.write(json);
      file.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method retrieves strategy from the file and returns the strategy object.
   *
   * @param strategy as String.
   * @return Strategy as Strategy Object.
   */
  public Strategy retrieveStrategyFromFile(String strategy) {
    Gson gson = new Gson();
    Strategy strategyObj = null;
    try {
      strategyObj = gson.fromJson(new FileReader("Strategy\\" + strategy + ".json"),
              Strategy.class);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File does not exist." + e.getMessage());
    }
    return strategyObj;
  }

}
