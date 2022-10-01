package ga.justreddy.wiki.rwitchwars.storage;

import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.entity.GamePlayer;
import ga.justreddy.wiki.rwitchwars.entity.PlayerAchievements;
import ga.justreddy.wiki.rwitchwars.entity.PlayerCosmetics;
import ga.justreddy.wiki.rwitchwars.entity.PlayerStats;
import ga.justreddy.wiki.rwitchwars.utils.Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import lombok.SneakyThrows;

public class SQLite implements DataStorage {

  private final Connection connection;

  @SneakyThrows
  public SQLite() {
    connection = DriverManager.getConnection("jdbc:sqlite:" + RWitchWars.getWitchWars().getDataFolder().getAbsolutePath() + "/data/database.db");
  }


  @SneakyThrows
  @Override
  public void createData() {
    connection.createStatement().executeUpdate(
        "CREATE TABLE IF NOT EXISTS witchwars_player (uuid VARCHAR(100), name VARCHAR(100), cosmetics LONGTEXT, stats LONGTEXT, achievements LONGTEXT)"
    );
  }

  @SneakyThrows
  @Override
  public boolean createPlayerData(GamePlayer gamePlayer) {
    ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM witchwars_player WHERE uuid='"+gamePlayer.getUniqueIdString()+"'");
    return rs.next();
  }

  @SneakyThrows
  @Override
  public void loadPlayerData(GamePlayer gamePlayer) {
    if (!createPlayerData(gamePlayer)) {
      connection.createStatement().executeUpdate(
          "INSERT INTO witchwars_player (uuid, name, cosmetics, stats, achievements) VALUES ('"+gamePlayer.getUniqueIdString()+"', "
              + "'"+gamePlayer.getName()+"',"
              + "'"+ Utils.toJson(gamePlayer.getCosmetics(), PlayerCosmetics.class)+"', "
              + "'"+Utils.toJson(gamePlayer.getStats(), PlayerStats.class)+"', '"+Utils.toJson(gamePlayer.getAchievements(), PlayerAchievements.class)+"')"
      );
    }

    ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM witchwars_player WHERE uuid='"+gamePlayer.getUniqueIdString()+"'");

    if (rs.next()) {
      PlayerCosmetics cosmetics = Utils.formJson(rs.getString("cosmetics"), PlayerCosmetics.class);
      gamePlayer.setCosmetics(cosmetics);
      PlayerStats stats = Utils.formJson(rs.getString("stats"), PlayerStats.class);
      gamePlayer.setStats(stats);
      PlayerAchievements achievements = Utils.formJson(rs.getString("achievements"), PlayerAchievements.class);
      gamePlayer.setAchievements(achievements);
    }
  }

  @Override
  public void removePlayerData(GamePlayer gamePlayer) {
    // Empty Method
  }

  @SneakyThrows
  @Override
  public void savePlayerData(GamePlayer gamePlayer) {
    connection.createStatement().executeUpdate(
        "UPDATE witchwars_player SET name='"+gamePlayer.getName()+"', "
            + "cosmetics='"+Utils.toJson(gamePlayer.getCosmetics(), PlayerCosmetics.class)+"',"
            + "stats='"+Utils.toJson(gamePlayer.getStats(), PlayerStats.class)+"', achievements='"+Utils.toJson(gamePlayer.getAchievements(),
            PlayerAchievements.class)+"'"
    );
  }

  @SneakyThrows
  @Override
  public void close() {
    if (connection != null) connection.close();
  }
}
