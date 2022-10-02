package ga.justreddy.wiki.rwitchwars.menus.menu;

import com.cryptomorin.xseries.XMaterial;
import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.entity.GamePlayer;
import ga.justreddy.wiki.rwitchwars.entity.PlayerController;
import ga.justreddy.wiki.rwitchwars.enums.GameType;
import ga.justreddy.wiki.rwitchwars.menus.SuperMenu;
import ga.justreddy.wiki.rwitchwars.utils.ItemStackBuilder;
import ga.justreddy.wiki.rwitchwars.utils.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

public class SelectGameTypeMenu extends SuperMenu implements Listener {

  private final FileConfiguration configuration;
  private final World world;
  private final File file;
  private final List<GamePlayer> peepee = new ArrayList<>();

  public SelectGameTypeMenu(FileConfiguration configuration, World world, File file) {
    super(Utils.format("&aSelect GameType"), 27);
    this.configuration = configuration;
    this.world = world;
    Bukkit.getPluginManager().registerEvents(this, RWitchWars.getWitchWars());
    this.file = file;
  }

  @SneakyThrows
  @Override
  public void handleMenu(InventoryClickEvent e) {
    if (e.getRawSlot() == 10) {
      peepee.remove(PlayerController.getController().get((Player) e.getWhoClicked()));
      configuration.set("options.gametype", GameType.SOLO.name());
      configuration.save(file);
      Utils.sendMessage(e.getWhoClicked(), "&aSuccessfully set the games GameType to: " + GameType.SOLO.name());
      Utils.sendMessage(e.getWhoClicked(), "&aTeleporting you to the arena...");
      Bukkit.getScheduler().runTaskLater(RWitchWars.getWitchWars(), () -> {
        e.getWhoClicked().closeInventory();
        Bukkit.getScheduler().runTaskLater(RWitchWars.getWitchWars(), () -> {
          e.getWhoClicked().teleport(world.getSpawnLocation());
        }, 5L);
      }, 5L);
    }
  }

  @Override
  public void setMenuItems(Player player) {
    if (!peepee.contains(PlayerController.getController().get(player))) {
      peepee.add(PlayerController.getController().get(player));
    }
    ItemStackBuilder soloBuilder = new ItemStackBuilder(XMaterial.LIME_WOOL.parseItem());
    soloBuilder.setName("&aSolo");
    soloBuilder.setLore("&7Select this to make the GameType solo", "&7You can not change this, so be careful!");
    inventory.setItem(10, soloBuilder.build());

    ItemStackBuilder doubleBuilder = new ItemStackBuilder(XMaterial.GREEN_WOOL.parseItem());
    doubleBuilder.setName("&2Doubles");
    doubleBuilder.setLore("&7Select this to make the GameType doubles", "&7You can not change this, so be careful!");
    inventory.setItem(12, doubleBuilder.build());

    ItemStackBuilder trioBuilder = new ItemStackBuilder(XMaterial.ORANGE_WOOL.parseItem());
    trioBuilder.setName("&63v3v3v3");
    trioBuilder.setLore("&7Select this to make the GameType trios ( 3v3v3v3 )", "&7You can not change this, so be careful!");
    inventory.setItem(14, trioBuilder.build());

    ItemStackBuilder squadBuilder = new ItemStackBuilder(XMaterial.RED_WOOL.parseItem());
    squadBuilder.setName("&c4v4v4v4");
    squadBuilder.setLore("&7Select this to make the GameType squads ( 4v4v4v4 )", "&7You can not change this, so be careful!");
    inventory.setItem(16, squadBuilder.build());
    setFillerGlass();
  }

  @EventHandler
  public void onInventoryClose(InventoryCloseEvent event) {
    if (!peepee.contains(PlayerController.getController().get((Player) event.getPlayer()))) return;
    if (event.getInventory().getHolder() instanceof SuperMenu) {
      Bukkit.getScheduler().runTask(RWitchWars.getWitchWars(), () -> open((Player) event.getPlayer()));
    }
  }

}
