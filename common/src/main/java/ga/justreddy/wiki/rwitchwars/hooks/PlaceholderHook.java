package ga.justreddy.wiki.rwitchwars.hooks;

import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.achievements.AchievementType;
import ga.justreddy.wiki.rwitchwars.controller.GameController;
import ga.justreddy.wiki.rwitchwars.entity.GamePlayer;
import ga.justreddy.wiki.rwitchwars.entity.PlayerController;
import ga.justreddy.wiki.rwitchwars.utils.Utils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaceholderHook extends PlaceholderExpansion {

  @Override
  public @NotNull String getIdentifier() {
    return RWitchWars.getWitchWars().getDescription().getName();
  }

  @Override
  public @NotNull String getAuthor() {
    return RWitchWars.getWitchWars().getDescription().getAuthors().get(0);
  }

  @Override
  public @NotNull String getVersion() {
    return RWitchWars.getWitchWars().getDescription().getVersion();
  }

  @Override
  public boolean canRegister() {
    return true;
  }

  @Override
  public boolean persist() {
    return true;
  }

  @Override
  public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {

    if (player == null) return "";

    final GamePlayer gamePlayer = PlayerController.getController().get(player);

    String[] id = params.split("_");

    switch (id[0]) {
      case "achievements":
        switch (id[1]) {
          case "unlocked":
            return String.valueOf(gamePlayer.getAchievements().getAchievements().size());
          case "all":
            return String.valueOf(RWitchWars.getWitchWars().getAchievementManager().getAchievementList().size());
          case "difficulty":
            try {
              return RWitchWars.getWitchWars().getAchievementManager().getById(AchievementType.getById(id[2])).getDifficulty().getIdentifier();
            }catch (NullPointerException ex) {
              return Utils.format("&cAchievement not found!");
            }
          case "name":
            try {
              return RWitchWars.getWitchWars().getAchievementManager().getById(AchievementType.getById(id[2])).getName();
            }catch (NullPointerException ex) {
              return Utils.format("&cAchievement not found!");
            }
          case "description":
            try {
              StringBuilder stringBuilder = new StringBuilder();
              for (String description : RWitchWars.getWitchWars().getAchievementManager().getById(AchievementType.getById(id[2])).getDescription()) {
                stringBuilder.append(description).append("\n");
              }
              return stringBuilder.toString();
            }catch (NullPointerException ex) {
              return Utils.format("&cAchievement not found!");
            }
        }
        break;
      case "arena":
        switch (id[1]) {
          case "status":
            try {
              return GameController.getController().getGameByName(id[2]).getGameState().getIdentifier();
            }catch (NullPointerException ex) {
              return Utils.format("&cArena not found!");
            }
          case "players":
            try {
              return String.valueOf(GameController.getController().getGameByName(id[2]).getPlayerCount());
            }catch (NullPointerException ex) {
              return Utils.format("&cArena not found!");
            }
          case "maximum":
            try {
              return String.valueOf(GameController.getController().getGameByName(id[2]).getMaximum());
            }catch (NullPointerException ex) {
              return Utils.format("&cArena not found!");
            }
          case "minimum":
            try {
              return String.valueOf(GameController.getController().getGameByName(id[2]).getMinimum());
            }catch (NullPointerException ex) {
              return Utils.format("&cArena not found!");
            }
          case "mode":
            try {
              return GameController.getController().getGameByName(id[2]).getGameType().getIdentifier();
            }catch (NullPointerException ex) {
              return Utils.format("&cArena not found!");
            }
          case "name":
            try {
              return GameController.getController().getGameByName(id[2]).getName();
            }catch (NullPointerException ex) {
              return Utils.format("&cArena not found!");
            }
          case "displayname":
            try {
              return Utils.format(GameController.getController().getGameByName(id[2]).getDisplayName());
            }catch (NullPointerException ex) {
              return Utils.format("&cArena not found!");
            }
        }
        // TODO
    }

    return "INVALID PLACEHOLDER";
  }
}
