package ga.justreddy.wiki.rwitchwars.achievements;

import ga.justreddy.wiki.rwitchwars.utils.Utils;

public enum AchievementDifficulty {

  EASY("&aEasy"),
  NORMAL("&6Normal"),
  HARD("&cHard"),
  EXTREME("&5Extreme")
  ;


  private final String identifier;

  AchievementDifficulty(String identifier) {
    this.identifier = identifier;
  }

  public String getIdentifier() {
    return Utils.format(identifier);
  }
}
