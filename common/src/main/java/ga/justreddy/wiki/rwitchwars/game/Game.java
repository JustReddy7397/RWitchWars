package ga.justreddy.wiki.rwitchwars.game;

import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.achievements.AchievementType;
import ga.justreddy.wiki.rwitchwars.entity.GamePlayer;
import ga.justreddy.wiki.rwitchwars.enums.GameState;
import ga.justreddy.wiki.rwitchwars.enums.GameType;
import ga.justreddy.wiki.rwitchwars.enums.GeneratorType;
import ga.justreddy.wiki.rwitchwars.game.events.AbstractGameEvents;
import ga.justreddy.wiki.rwitchwars.game.shop.ShopVillager;
import ga.justreddy.wiki.rwitchwars.game.team.GameTeam;
import ga.justreddy.wiki.rwitchwars.generators.Generator;
import ga.justreddy.wiki.rwitchwars.utils.Cuboid;
import ga.justreddy.wiki.rwitchwars.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Witch;

public class Game {

    private String name;
    private String displayName;
    private FileConfiguration configuration;
    private int teamSize;
    private int maximum;
    private final int minimum;
    private GameType gameType;
    private GameState gameState;
    private List<AbstractGameEvents> events;
    private List<GamePlayer> players;
    private List<GameTeam> teams;
    private List<Generator> generators;
    private List<ShopVillager> shopVillagers;
    private Cuboid cuboidLobby;
    private Cuboid cuboidArena;
    private int timer;

    public Game(String name, FileConfiguration configuration) {
        this.name = name;
        this.configuration = configuration;
        this.teams = new ArrayList<>();
        this.players = new ArrayList<>();
        this.generators = new ArrayList<>();
        this.shopVillagers = new ArrayList<>();
        this.displayName = Utils.format(Utils.format(configuration.getString("displayName")));
        this.minimum = configuration.getInt("minimum");
        this.gameType = GameType.valueOf(configuration.getString("gameType").toUpperCase());
        if (gameType == GameType.SOLO) {
            this.teamSize = 1;
            this.maximum = 8;
        } else if(gameType == GameType.DUO) {
            this.teamSize = 2;
            this.maximum = 16;
        } else if (gameType == GameType.TRIO) {
            this.teamSize = 3;
            this.maximum = 12;
        } else if (gameType == GameType.SQUAD) {
            this.teamSize = 4;
            this.maximum = 16;
        }
        ConfigurationSection section = configuration.getConfigurationSection("generators");
        for (String key : section.getKeys(false)) {
            ConfigurationSection generatorSection = section.getConfigurationSection(key);
            generators.add(new Generator((Location) generatorSection.get("location"),
                    GeneratorType.valueOf(generatorSection.getString("type").toUpperCase()),
                    generatorSection.getInt("level")));
        }
        ConfigurationSection teamSection = configuration.getConfigurationSection("teams");
        for (String key : teamSection.getKeys(false)) {
            ConfigurationSection teamSection2 = teamSection.getConfigurationSection(key);
            teams.add(new GameTeam(key, teamSection2, teamSize));
        }

        ConfigurationSection shopVillagersSection = configuration.getConfigurationSection("shopVillagers");
        for (String key : shopVillagersSection.getKeys(false)){
            ConfigurationSection shopVillagersSection2 = shopVillagersSection.getConfigurationSection(key);
            ShopVillager shopVillager = new ShopVillager((Location) shopVillagersSection2.get("location"));
            shopVillagers.add(shopVillager);
        }
        Location highPoint;
        Location lowPoint;

        if (configuration.isSet("bound.lobby")) {
            highPoint = (Location) configuration.get("bound.lobby.high");
            lowPoint = (Location) configuration.get("bound.lobby.low");
            cuboidLobby = new Cuboid(highPoint, lowPoint);
        }

        if (configuration.isSet("bound.arena")) {
            highPoint = (Location) configuration.get("bound.arena.high");
            lowPoint = (Location) configuration.get("bound.arena.low");
            cuboidArena = new Cuboid(highPoint, lowPoint);
        }

    }

    public AbstractGameEvents getCurrentEvent() {
        for (AbstractGameEvents gameEvents : events) {
            if (gameEvents.isEnabled() && gameEvents.getTime() > 0) {
                return gameEvents;
            }
        }
        return null;
    }

    public GameTeam getTeamNoFull() {
        GameTeam lowest = teams.get(0);
        for (GameTeam gameTeam : teams) {
            if (!gameTeam.isFull()) {
                lowest = gameTeam;
            }
        }
        return lowest;
    }

    public List<Generator> getGenerators() {
        return generators;
    }

    public String getName() {
        return name;
    }

    public GameType getGameType() {
        return gameType;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Cuboid getCuboidArena() {
        return cuboidArena;
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public int getMaximum() {
        return maximum;
    }

    public int getMinimum() {
        return minimum;
    }

    public Cuboid getCuboidLobby() {
        return cuboidLobby;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public List<AbstractGameEvents> getEvents() {
        return events;
    }

    public List<GamePlayer> getPlayers() {
        return players;
    }

    public List<GameTeam> getTeams() {
        return teams;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setConfiguration(FileConfiguration configuration) {
        this.configuration = configuration;
    }


    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public List<GamePlayer> getAlivePlayers() {
        return players.stream().filter(g -> !g.isDead()).collect(Collectors.toList());
    }

    public List<GamePlayer> getSpectators() {
        return players.stream().filter(GamePlayer::isDead).collect(Collectors.toList());
    }

    public List<GameTeam> getAliveTeams() {
        return teams.stream().filter(gameTeam -> !gameTeam.getGamePlayers().isEmpty()).collect(Collectors.toList());
    }

    public boolean isFull() {
        return getPlayers().size() >= getMaximum();
    }

    public void onGamePlayerJoin(GamePlayer gamePlayer, GameTeam gameTeam) {
        Player player = gamePlayer.getPlayer();
        if (gameState == GameState.DISABLED) {
            Utils.errorCommand(player, "This game is disabled.");
            return;
        }

        if (gameState == GameState.PLAYING || gameState == GameState.ENDING) {
            Utils.errorCommand(player, "This game has already started.");
            return;
        }

        if (isFull()) {
            Utils.errorCommand(player, "This game is full.");
            return;
        }
        players.add(gamePlayer);
        gamePlayer.setGame(this);
        setTeam(gamePlayer, gameTeam);

    }

    public void onGamePlayerLeave(GamePlayer gamePlayer, boolean silent) {
        for (AchievementType type : AchievementType.values()) {
            RWitchWars.getWitchWars().getAchievementManager()
                .removeAchievementCounter(gamePlayer,
                    RWitchWars.getWitchWars().getAchievementManager().getById(type));
        }
    }

    public void setTeam(GamePlayer gamePlayer, GameTeam team) {
        if (team != null) {
            if (team.getSize() < this.getTeamSize()) {
                GameTeam current = gamePlayer.getGameTeam();
                if (current != null) {
                    current.removePlayer(gamePlayer);
                }
                team.addPlayer(gamePlayer);
            }
        } else {
            setTeam(gamePlayer, getTeamNoFull());
        }
    }

    public void onCountDown() {
        if (timer > 0) timer--;
        switch(gameState) {
            case WAITING:
                if (getPlayerCount() >= getMaximum()) {
                    setGameState(GameState.STARTING);
                    timer = 10;
                }
                break;
            case STARTING:
                if (getPlayerCount() < getMinimum()) {
                    setGameState(GameState.WAITING);
                    sendTitle("&cGame Cancelled", "&cNot enough players to start.");
                    return;
                }

                if (timer <= 0) {
                    onGameStart();
                    return;
                }

                if (timer < 6) {
                    // TODO: Make timer send to player
                }
                break;
            case PLAYING:
                AbstractGameEvents currentEvent = getCurrentEvent();
                if (getAliveTeams().size() == 1) {
                    onGameEnd(getAliveTeams().get(0));
                } else if (getAliveTeams().size() < 1) {
                    onGameEnd(null);
                    return;
                }
                if (events == null) {
                    if (getAliveTeams().size() == 1) {
                        onGameEnd(getAliveTeams().get(0));
                    } else if (getAliveTeams().size() < 1) {
                        onGameEnd(null);
                        return;
                    }
                }
                if (currentEvent.isEnabled()) currentEvent.update();
                timer = currentEvent.getTime();
                if (timer <= 0) currentEvent.start();
                break;

        }
    }

    public void onGameStart() {
        setGameState(GameState.PLAYING);
        for (Generator generator : getGenerators()) {
            generator.runTaskTimer(RWitchWars.getWitchWars(), 0, 20L);
        }
        shopVillagers.forEach(ShopVillager::spawn);
        teams.forEach(GameTeam::spawnWitch);
        teams.forEach(GameTeam::teleportToSpawn);

    }

    public void onGameEnd(GameTeam gameTeam) {
        setGameState(GameState.ENDING);
        for (Generator generator : getGenerators()) {
            generator.cancel();
        }

        if (gameTeam != null) {

        }

    }

    public void onGameRestart() {
        shopVillagers.forEach(ShopVillager::remove);
    }

    public GameTeam getGameTeamById(String id) {
        GameTeam gameTeam = null;
        for (GameTeam gameTeam1 : teams) {
            if (gameTeam1.getId().equals(id)) gameTeam = gameTeam1;
        }
        return gameTeam;
    }

    public GameTeam getGameTeamByWitch(Witch witch) {
        GameTeam gameTeam = null;
        for (GameTeam gameTeam1 : teams) {
            if (gameTeam1.getWitch() == witch) gameTeam = gameTeam1;
        }
        return gameTeam;
    }

    public int getPlayerCount() {
        return getAlivePlayers().size();
    }

    public void sendTitle(String title, String subTitle) {
        getAlivePlayers()
            .forEach(gamePlayer ->
                gamePlayer.sendTitle(title, subTitle)
            );
    }

}
