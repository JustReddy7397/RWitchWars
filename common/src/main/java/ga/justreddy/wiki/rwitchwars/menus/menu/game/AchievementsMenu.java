package ga.justreddy.wiki.rwitchwars.menus.menu.game;

import com.cryptomorin.xseries.XMaterial;
import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.achievements.AbstractAchievement;
import ga.justreddy.wiki.rwitchwars.entity.GamePlayer;
import ga.justreddy.wiki.rwitchwars.entity.PlayerController;
import ga.justreddy.wiki.rwitchwars.menus.PaginatedSuperMenu;
import ga.justreddy.wiki.rwitchwars.utils.ItemStackBuilder;
import ga.justreddy.wiki.rwitchwars.utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AchievementsMenu extends PaginatedSuperMenu {

  public AchievementsMenu() {
    super(Utils.format("&aYour achievements"), 45);
  }

  @Override
  public void handleMenu(InventoryClickEvent e) {
    if (e.getCurrentItem().getType() == XMaterial.LIME_DYE.parseMaterial() && e.getCurrentItem().getItemMeta()
        .getDisplayName().equals(Utils.format("&aLeft"))
    ) {
      if (page == 0) {
        e.getWhoClicked().sendMessage(Utils.format("&cYou are already on the first page."));
      } else {
        page -= 1;
        super.open((Player) e.getWhoClicked());
      }
    } else if (e.getCurrentItem().getType() == XMaterial.LIME_DYE.parseMaterial() &&e.getCurrentItem().getItemMeta()
        .getDisplayName().equals(Utils.format("&aRight"))
    ) {
      if ((index + 1) >= RWitchWars.getWitchWars().getAchievementManager().getAchievementList().size()) {
        e.getWhoClicked().sendMessage(Utils.format("&cYou are on the last page."));
      } else {
        page += 1;
        super.open((Player) e.getWhoClicked());
      }
    } else if (e.getCurrentItem().getType() == XMaterial.BARRIER.parseMaterial()) {
      e.getWhoClicked().closeInventory();
    }
  }

  @Override
  public void setMenuItems(Player player) {
    inventory.clear();
    addMenuBorder();
    GamePlayer gamePlayer = PlayerController.getController().get(player);
    for (int i = 0; i < maxItemsPerPage; i++) {
      index = maxItemsPerPage * page + i;
      if (index >= RWitchWars.getWitchWars().getAchievementManager().getAchievementList().size()) break;
      if (RWitchWars.getWitchWars().getAchievementManager().getAchievementList().get(index) != null) {
        AbstractAchievement achievement = RWitchWars.getWitchWars().getAchievementManager().getAchievementList().get(index);
        ItemStackBuilder achievementBuilder;
        if (gamePlayer.getAchievements().getAchievements().contains(achievement.getId().getId())) {
          achievementBuilder = new ItemStackBuilder(XMaterial.DIAMOND.parseItem());
        } else {
          achievementBuilder = new ItemStackBuilder(XMaterial.COAL.parseItem());
        }
        List<String> lore = new ArrayList<>();
        for (String line : achievement.getDescription()) {
          lore.add("&7" + line);
        }
        lore.add("");
        lore.add(gamePlayer.getAchievements().getAchievements().contains(achievement.getId().getId()) ? "&aAchievement unlocked" : "&cAchievement locked");
        achievementBuilder
            .setName((gamePlayer.getAchievements().getAchievements().contains(achievement.getId().getId()) ? "&a" : "&c") + achievement.getName())
                .setLore(lore);
        inventory.addItem(achievementBuilder.build());
      }

    }


  }
}
