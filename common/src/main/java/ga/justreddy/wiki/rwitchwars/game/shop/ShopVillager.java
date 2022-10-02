package ga.justreddy.wiki.rwitchwars.game.shop;

import de.tr7zw.changeme.nbtapi.NBTEntity;
import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.nms.INms;
import org.bukkit.Location;
import org.bukkit.entity.Villager;

public class ShopVillager {

  private final Location location;
  private Villager villager;

  public ShopVillager(Location location) {
    this.location = location;
  }

  public void spawn() {
    villager = location.getWorld().spawn(location, Villager.class);
    NBTEntity entity = new NBTEntity(villager);
    entity.setString("shopVillager", "shop");
    RWitchWars.getWitchWars().getNms().removeAI(villager);
  }

  public void remove() {
    villager.remove();
  }

}
