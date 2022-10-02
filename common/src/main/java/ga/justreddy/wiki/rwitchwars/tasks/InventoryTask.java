package ga.justreddy.wiki.rwitchwars.tasks;

import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.entity.GamePlayer;
import ga.justreddy.wiki.rwitchwars.menus.SuperMenu;
import java.util.Map;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryTask extends BukkitRunnable {

  @Override
  public void run() {
    for (Map.Entry<GamePlayer, SuperMenu> entry : RWitchWars.getWitchWars().getOpenMenus().entrySet()) {
      entry.getValue().setMenuItems(entry.getKey().getPlayer());
    }
  }
}
