package ga.justreddy.wiki.rwitchwars.entity;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class PlayerCosmetics {

  private int messagesSelect;
  private final List<Integer> messages;
  private int trailSelect;
  private final List<Integer> trails;
  private int killEffectsSelect;
  private final List<Integer> killEffects;
  private int danceSelect;
  private final List<Integer> dances;

  public PlayerCosmetics() {
    this.messagesSelect = 0;
    this.messages = new ArrayList<>();
    this.messages.add(messagesSelect);
    this.trails = new ArrayList<>();
    this.trails.add(trailSelect);
    this.killEffectsSelect = 0;
    this.killEffects = new ArrayList<>();
    this.killEffects.add(killEffectsSelect);
    this.danceSelect = 0;
    this.dances = new ArrayList<>();
    this.dances.add(danceSelect);
  }

  public boolean hasTrail(int id) {
    return this.trails != null && this.trails.contains(id);
  }

  public void addTrail(int id) {
    this.trails.add(id);
  }

  public boolean hasKillEffects(int id) {
    return this.killEffects != null && this.killEffects.contains(id);
  }

  public void addKillEffect(int id) {
    this.killEffects.add(id);
  }

  public boolean hasDance(int id) {
    return this.dances != null && this.dances.contains(id);
  }

  public void addDance(int id) {
    this.dances.add(id);
  }

}
