package ga.justreddy.wiki.rwitchwars.utils;

import ga.justreddy.wiki.rwitchwars.RWitchWars;
import java.util.ArrayList;
import java.util.List;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemStackBuilder {

  private final ItemStack ITEM_STACK;

  private static final RWitchWars PLUGIN = RWitchWars.getWitchWars();

  public ItemStackBuilder(ItemStack item) {
    this.ITEM_STACK = item;
  }


  public ItemStackBuilder setAmount(int amount) {
    ITEM_STACK.setAmount(amount);
    return this;
  }

  public ItemStackBuilder setFlags(ItemFlag... flags) {
    ItemMeta meta = ITEM_STACK.getItemMeta();
    meta.addItemFlags(flags);
    ITEM_STACK.setItemMeta(meta);
    return this;
  }

  public ItemStackBuilder setName(String name) {
    final ItemMeta meta = ITEM_STACK.getItemMeta();
    meta.setDisplayName(Utils.format(name));
    ITEM_STACK.setItemMeta(meta);
    return this;
  }

  public ItemStackBuilder setName(String name, Player player) {
    final ItemMeta meta = ITEM_STACK.getItemMeta();
    meta.setDisplayName(Utils.format(PlaceholderAPI.setPlaceholders(player, name)));
    ITEM_STACK.setItemMeta(meta);
    return this;
  }

  public ItemStackBuilder setSkullOwner(String owner) {
    try {
      SkullMeta im = (SkullMeta) ITEM_STACK.getItemMeta();
      im.setOwner(owner);
      ITEM_STACK.setItemMeta(im);
    } catch (ClassCastException expected) {
    }
    return this;
  }

  public ItemStackBuilder setLore(List<String> lore, Player player) {
    final ItemMeta meta = ITEM_STACK.getItemMeta();
    List<String> coloredLore = new ArrayList<String>();
    for (String s : lore) {
      s = PlaceholderAPI.setPlaceholders(player, s);
      coloredLore.add(Utils.format(s));
    }
    meta.setLore(coloredLore);
    ITEM_STACK.setItemMeta(meta);
    return this;
  }

  public ItemStackBuilder setLore(Player player, String... lore) {
    final ItemMeta meta = ITEM_STACK.getItemMeta();
    List<String> coloredLore = new ArrayList<>();
    for (String s : lore) {
      s = PlaceholderAPI.setPlaceholders(player, s);
      coloredLore.add(Utils.format(s));
    }
    meta.setLore(coloredLore);
    ITEM_STACK.setItemMeta(meta);
    return this;
  }

  public ItemStackBuilder setLore(String... lore) {
    final ItemMeta meta = ITEM_STACK.getItemMeta();
    List<String> coloredLore = new ArrayList<>();
    for (String s : lore) {
      coloredLore.add(Utils.format(s));
    }
    meta.setLore(coloredLore);
    ITEM_STACK.setItemMeta(meta);
    return this;
  }

  public ItemStackBuilder setLore(List<String> lore) {
    final ItemMeta meta = ITEM_STACK.getItemMeta();
    List<String> coloredLore = new ArrayList<String>();
    for (String s : lore) {
      coloredLore.add(Utils.format(s));
    }
    meta.setLore(coloredLore);
    ITEM_STACK.setItemMeta(meta);
    return this;
  }


  public ItemStackBuilder setDurability(int durability) {
    ITEM_STACK.setDurability((short) durability);
    return this;
  }

  public ItemStackBuilder setData(int data) {
    ITEM_STACK.setDurability((short) data);
    return this;
  }

  public ItemStackBuilder addEnchantment(Enchantment enchantment, final int level) {
    ITEM_STACK.addUnsafeEnchantment(enchantment, level);
    return this;
  }

  public ItemStackBuilder addEnchantment(Enchantment enchantment) {
    ITEM_STACK.addUnsafeEnchantment(enchantment, 1);
    return this;
  }

  public ItemStackBuilder setGlow() {
    final ItemMeta meta = ITEM_STACK.getItemMeta();
    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    ITEM_STACK.setItemMeta(meta);
    ITEM_STACK.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
    return this;
  }

  public ItemStackBuilder setType(Material material) {
    ITEM_STACK.setType(material);
    return this;
  }

  public ItemStackBuilder clearLore() {
    final ItemMeta meta = ITEM_STACK.getItemMeta();
    meta.setLore(new ArrayList<>());
    ITEM_STACK.setItemMeta(meta);
    return this;
  }

  public ItemStackBuilder clearEnchantments() {
    for (Enchantment enchantment : ITEM_STACK.getEnchantments().keySet()) {
      ITEM_STACK.removeEnchantment(enchantment);
    }
    return this;
  }

  public ItemStackBuilder setColor(Color color) {
    Material type = ITEM_STACK.getType();
    if (type == Material.LEATHER_BOOTS || type == Material.LEATHER_CHESTPLATE || type == Material.LEATHER_HELMET || type == Material.LEATHER_LEGGINGS) {
      LeatherArmorMeta meta = (LeatherArmorMeta) ITEM_STACK.getItemMeta();
      meta.setColor(color);
      ITEM_STACK.setItemMeta(meta);
      return this;
    } else {
      throw new IllegalArgumentException("setColor is only applicable for leather armor!");
    }
  }

  public ItemStack build() {
    return ITEM_STACK;
  }


}
