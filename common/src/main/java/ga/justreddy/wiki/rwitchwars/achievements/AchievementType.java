package ga.justreddy.wiki.rwitchwars.achievements;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AchievementType {

  TRAITOR("traitor"),
  MURDERER("murderer"),
  SERIAL_KILLER("serialkiller");

  private final String id;

  public static AchievementType getById(String id) {
    for(AchievementType e : values()) {
      if(e.id.equals(id)) return e;
    }
    return null;
  }

}
