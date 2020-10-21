package studio.craftory.craftory_utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {


  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Permissions {

    public static final String BASE = "craftory-utils.calculate";
    public static final String SAVE_LOCATIONS = "craftory-utils.calculate.saveLocations";
    public static final String USE_PLAYER_LOCATIONS = "craftory-utils.calculate.usePlayerLocations";
    public static final String MANAGE_PLAYER_LOCATIONS = "craftory-utils.calculate.managePlayersSavedLocations";
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Commands {

    public static final String REMOVE_SAVED = "removeSaved";

    public static final String LIST_SAVED = "listSaved";
    public static final String DISTANCE_NO_Y = "distanceNoY";
    public static final String MAIN = "";
    public static final String HELP = "help";
    public static final String DISTANCE = "distance";
    public static final String CALC = "calc";
    public static final String SAVE = "save";
    public static final String CENTRE = "centre";
  }

}
