package ga.justreddy.wiki.rwitchwars.v1_8_R3;

import ga.justreddy.wiki.rwitchwars.nms.INms;
import java.util.Random;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

public final class v1_8_R3 implements INms {

  @Override
  public boolean isLegacy() {
    return true;
  }

  @Override
  public void sendJsonMessage(Player player, String message) {
    IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a(message);
    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(iChatBaseComponent));
  }

  @Override
  public void sendTitle(Player player, String title, String subTitle) {
    PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
    PacketPlayOutTitle titleInfo = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 20, 60, 20);
    connection.sendPacket(titleInfo);
    IChatBaseComponent iChatBaseComponent;
    PacketPlayOutTitle titleSubTitle;
    if (title != null) {
      iChatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
      titleSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, iChatBaseComponent);
      connection.sendPacket(titleSubTitle);
    }
    if (subTitle != null) {
      iChatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subTitle + "\"}");
      titleSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, iChatBaseComponent);
      connection.sendPacket(titleSubTitle);
    }
  }

  @Override
  public void sendActionBar(Player player, String message) {
    if (message == null) return;
    IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
    PacketPlayOutChat actionBar = new PacketPlayOutChat(iChatBaseComponent, (byte) 2);
    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(actionBar);
  }

  @Override
  public void sendParticle(Location location, String type, int amount, float offsetX, float offsetY, float offsetZ, float data) {
    if (type == null) return;
    EnumParticle particle = EnumParticle.valueOf(type);
    float x = (float) location.getBlockX();
    float y = (float) location.getBlockY();
    float z = (float) location.getBlockZ();
    PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(particle, true, x, y, z, offsetX, offsetY, offsetZ, data, amount, 1);
    for(final Player player : location.getWorld().getPlayers()) {
      ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particles);
    }
  }

  @Override
  public void sendParticle(Player player, String type, int amount, float offsetX, float offsetY, float offsetZ, float data) {
    if (type == null) return;
    Location location = player.getLocation();
    EnumParticle particle = EnumParticle.valueOf(type);
    float x = (float) location.getBlockX();
    float y = (float) location.getBlockY();
    float z = (float) location.getBlockZ();
    PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(particle, true, x, y, z, offsetX, offsetY, offsetZ, data, amount, 1);
    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particles);
  }

  @Override
  public boolean valueOfParticle(String particle) {
    try{
      EnumParticle.valueOf(particle.toUpperCase());
    }catch (Exception ex) {
      return false;
    }
    return true;
  }

  @Override
  public ChunkGenerator getChunkGenerator() {
    return new ChunkGenerator() {
      @Override
      public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        ChunkData chunkData = createChunkData(world);
        return chunkData;
      }
    };
  }

  @Override
  public void setRule(World world, String type, String rule) {
    world.setGameRuleValue(type, rule);
  }

  @Override
  public void removeAI(Entity entity) {
    net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();
    NBTTagCompound tag = nmsEntity.getNBTTag();
    if (tag == null) {
      tag = new NBTTagCompound();
    }
    nmsEntity.c(tag);
    tag.setInt("NoAI", 1);
    tag.setBoolean("Silent", true);
    nmsEntity.f(tag);
  }

}
