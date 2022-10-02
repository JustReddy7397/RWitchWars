package ga.justreddy.wiki.rwitchwars.menus.menu.shop;

import ga.justreddy.wiki.rwitchwars.menus.SuperMenu;
import ga.justreddy.wiki.rwitchwars.utils.ItemStackBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SelectMenu extends SuperMenu {

  public SelectMenu() {
    super("&aItem Shop", 27);
  }

  @Override
  public void handleMenu(InventoryClickEvent e) {

  }

  @Override
  public void setMenuItems(Player player) {

    setFillerGlass();


  }
}
