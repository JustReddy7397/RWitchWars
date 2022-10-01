package ga.justreddy.wiki.rwitchwars.controller;

import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.nms.INms;
import ga.justreddy.wiki.rwitchwars.game.map.FileUtil;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;

public class WorldController {

    private static WorldController controller;

    public static WorldController getController() {
        if (controller == null) controller = new WorldController();
        return controller;
    }

    public World createWorld(String name) {

        final INms nms = RWitchWars.getWitchWars().getNms();

        WorldCreator creator = new WorldCreator(name);
        creator.generator(nms.getChunkGenerator());
        World world = creator.createWorld();
        world.setDifficulty(Difficulty.NORMAL);
        world.setSpawnFlags(true, true);
        world.setPVP(true);
        world.setStorm(false);
        world.setThundering(false);
        world.setKeepSpawnInMemory(false);
        world.setAutoSave(false);
        world.setTicksPerMonsterSpawns(1);
        world.setTicksPerAnimalSpawns(1);
        world.setWeatherDuration(Integer.MAX_VALUE);
        world.setSpawnLocation(0, 20, 0);
        nms.setRule(world, "doMobSpawning", "false");
        nms.setRule(world, "doFireTick", "false");
        nms.setRule(world, "showDeathMessages", "false");
        nms.setRule(world, "announceAdvancements", "false");
        return world;
    }

    @SneakyThrows
    public void copyWorld(World world) {
        File worldFolder = new File(Bukkit.getWorldContainer().getParent(), world.getName());
        File mapFolder = new File(RWitchWars.getWitchWars().getDataFolder().getAbsolutePath() + "/data/worlds", world.getName());
        if (mapFolder.exists()) {
            FileUtil.delete(mapFolder);
        }
        FileUtil.copy(worldFolder, mapFolder);
    }

}
