package ga.justreddy.wiki.rwitchwars.entity;

import ga.justreddy.wiki.rwitchwars.achievements.AbstractAchievement;
import java.util.ArrayList;
import java.util.List;

public class PlayerAchievements {

  private final List<String> achievements;

  public PlayerAchievements() {
    this.achievements = new ArrayList<>();
  }

  public void addAchievement(AbstractAchievement achievement) {
    this.achievements.add(achievement.getId().getId());
  }

  public void removeAchievement(AbstractAchievement achievement) {
    this.achievements.remove(achievement.getId().getId());
  }

  public List<String> getAchievements() {
    return achievements;
  }
}
