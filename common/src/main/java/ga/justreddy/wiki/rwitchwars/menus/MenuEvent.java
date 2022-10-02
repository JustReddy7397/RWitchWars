package ga.justreddy.wiki.rwitchwars.menus;

import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.entity.PlayerController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

public class MenuEvent implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent e){
        final InventoryHolder holder = e.getInventory().getHolder();
        if(holder instanceof SuperMenu) {
            e.setCancelled(true);
            if(e.getCurrentItem() == null) return;
            ((SuperMenu) holder).handleMenu(e);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        RWitchWars.getWitchWars().getOpenMenus().remove(PlayerController.getController().get((Player) e.getPlayer()));
    }

}
