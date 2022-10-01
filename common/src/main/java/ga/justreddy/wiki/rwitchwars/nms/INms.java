package ga.justreddy.wiki.rwitchwars.nms;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

public interface INms {

    boolean isLegacy();

    void sendJsonMessage(Player player, String message);

    void sendTitle(Player player, String title, String subTitle);

    void sendActionBar(Player player, String message);

    void sendParticle(Location location, String type, int amount, float offsetX, float offsetY, float offsetZ, float data);

    void sendParticle(Player player, String type, int amount, float offsetX, float offsetY, float offsetZ, float data);

    boolean valueOfParticle(String particle);

    ChunkGenerator getChunkGenerator();

    void setRule(World world, String type, String rule);

    void removeAI(Entity entity);

}
