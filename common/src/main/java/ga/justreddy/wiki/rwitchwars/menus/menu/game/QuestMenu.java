package ga.justreddy.wiki.rwitchwars.menus.menu.game;

import com.cryptomorin.xseries.XMaterial;
import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.entity.GamePlayer;
import ga.justreddy.wiki.rwitchwars.entity.PlayerController;
import ga.justreddy.wiki.rwitchwars.menus.SuperMenu;
import ga.justreddy.wiki.rwitchwars.quests.QuestManager;
import ga.justreddy.wiki.rwitchwars.quests.QuestType;
import ga.justreddy.wiki.rwitchwars.utils.ItemStackBuilder;
import ga.justreddy.wiki.rwitchwars.utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class QuestMenu extends SuperMenu {

  public QuestMenu() {
    super(Utils.format("&aQuests"), 27);
  }

  @Override
  public void handleMenu(InventoryClickEvent e) {
    if (e.getRawSlot() == 11) {
      QuestManager questManager = RWitchWars.getWitchWars().getQuestManager();
      GamePlayer gamePlayer = PlayerController.getController().get((Player) e.getWhoClicked());
      if (gamePlayer.getQuests().getStartedQuests().contains(QuestType.WITCHWARS_PLAYER.getId())
          || gamePlayer.getQuests().isQuestCompleted(QuestType.WITCHWARS_PLAYER)) {
        // TOOD edit message
        Utils.sendMessage(e.getWhoClicked(), "&cYou can't start this quest right now.");
        return;
      }
      gamePlayer.getQuests().startQuest(questManager.getById(QuestType.WITCHWARS_PLAYER));
      setMenuItems((Player) e.getWhoClicked());
    }
  }

  @Override
  public void setMenuItems(Player player) {
    QuestManager questManager = RWitchWars.getWitchWars().getQuestManager();
    GamePlayer gamePlayer = PlayerController.getController().get(player);
    ItemStackBuilder playGamesQuest = new ItemStackBuilder(XMaterial.PAPER.parseItem());
    playGamesQuest.setName(questManager.getById(QuestType.WITCHWARS_PLAYER).getDisplayName());
    List<String> playGamesLore = new ArrayList<>(
        Arrays.asList(questManager.getById(QuestType.WITCHWARS_PLAYER).getDescription()));
    if (gamePlayer.getQuests().isQuestCompleted(QuestType.WITCHWARS_PLAYER)) {
      playGamesLore.add("&cYou can do this quest again in: " + Utils.getDurationString(
          gamePlayer.getQuests().getCompletedQuests().get(QuestType.WITCHWARS_PLAYER)));
    } else if (gamePlayer.getQuests().getStartedQuests()
        .contains(QuestType.WITCHWARS_PLAYER.getId())) {
      playGamesLore.add("&cYou've already started this quest.");
    } else {
      playGamesLore.add("&aClick to start this quest");
    }
    playGamesQuest.setLore(playGamesLore);
    inventory.setItem(11, playGamesQuest.build());


  }
}
