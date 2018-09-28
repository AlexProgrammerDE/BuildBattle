/*
 * BuildBattle 3 - Ultimate building competition minigame
 * Copyright (C) 2018  Plajer's Lair - maintained by Plajer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package pl.plajer.buildbattle4.entities;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import pl.plajer.buildbattle4.Main;
import pl.plajer.buildbattle4.handlers.ChatManager;
import pl.plajer.buildbattle4.utils.Utils;
import pl.plajerlair.core.utils.ConfigUtils;

/**
 * Created by Tom on 23/08/2015.
 */
public class EntityItem {

  private Material material;
  private Byte data = null;
  private String[] lore;
  private String displayName;
  private int slot;
  private String name;

  private EntityItem(String name) {
    this.name = name;
  }

  public static void loadAll() {
    new EntityItem("Adult").load(ChatColor.GREEN + "Age", new String[]{ChatColor.GREEN + "Adult or baby? Click to change", ChatColor.GRAY + "Selected: " + ChatColor.GREEN + "Adult"}, Material.EGG, 2);
    new EntityItem("Baby").load(ChatColor.GREEN + "Age ", new String[]{ChatColor.GREEN + "Adult or baby? Click to change", ChatColor.GRAY + "Selected: " + ChatColor.RED + "Baby"}, Material.EGG, 2);
    new EntityItem("Move-On").load(ChatColor.GREEN + "Moving", new String[]{ChatColor.GRAY + "Click to disable moving", ChatColor.GRAY + "Selected: " + ChatColor.GREEN + "On"}, Material.ANVIL, 0);
    new EntityItem("Move-Off").load(ChatColor.GREEN + "Moving", new String[]{ChatColor.GRAY + "Click to enabled moving", ChatColor.GRAY + "Selected: " + ChatColor.RED + "Off"}, Material.ANVIL, 0);
    new EntityItem("Close").load(ChatColor.RED + "Close", new String[]{ChatColor.GRAY + "Click to close"}, Material.REDSTONE_BLOCK, 8);
    new EntityItem("Despawn").load(ChatColor.RED + "Destroy entity", new String[]{ChatColor.GRAY + "Click to destroy"}, Material.BEDROCK, 7);
    new EntityItem("Saddle-On").load(ChatColor.GREEN + "Saddled?", new String[]{ChatColor.GRAY + "Click to remove saddle", ChatColor.GRAY + "Selected: " + ChatColor.GREEN + "On"}, Material.SADDLE, 3);
    new EntityItem("Saddle-Off").load(ChatColor.GREEN + "Saddled?", new String[]{ChatColor.GRAY + "Click to enable saddle", ChatColor.GRAY + "Selected: " + ChatColor.RED + "Off"}, Material.SADDLE, 3);
    new EntityItem("Look-At-Me").load(ChatColor.GOLD + "Look", new String[]{ChatColor.GRAY + "Click to let mob look at you"}, Material.COMPASS, 1);
    new EntityItem("Profession-Villager-Selecting").load(ChatColor.GOLD + "Choose Profession", new String[]{ChatColor.GRAY + "Click to choose profession"}, Material.RED_ROSE, 3);
    new EntityItem("Profession.Librarian").load(ChatColor.GOLD + "Librarian", new String[]{ChatColor.GRAY + "Click to choose librarian"}, Material.BOOKSHELF, 1);
    new EntityItem("Profession.Butcher").load(ChatColor.GOLD + "Butcher", new String[]{ChatColor.GRAY + "Click to choose butcher"}, Material.COOKED_BEEF, 2);
    new EntityItem("Profession.Priest").load(ChatColor.GOLD + "Priest", new String[]{ChatColor.GRAY + "Click to choose priest"}, Material.FEATHER, 3);
    new EntityItem("Profession.Blacksmith").load(ChatColor.GOLD + "Blacksmith", new String[]{ChatColor.GRAY + "Click to choose blacksmith"}, Material.IRON_CHESTPLATE, 4);
    new EntityItem("Profession.Farmer").load(ChatColor.GOLD + "Farmer", new String[]{ChatColor.GRAY + "Click to choose farmer"}, Material.WHEAT, 0);

  }

  private void load(String displayName, String[] lore, Material material, int slot) {
    FileConfiguration config = ConfigUtils.getConfig(JavaPlugin.getPlugin(Main.class), "EntityMenu");
    if (!config.contains(name)) {
      config.set(name + ".data", 0);
      config.set(name + ".displayname", displayName);
      config.set(name + ".lore", Arrays.asList(lore));
      config.set(name + ".material", material.getId());
      config.set(name + ".slot", slot);
    }
    try {
      config.save(ConfigUtils.getFile(JavaPlugin.getPlugin(Main.class), "EntityMenu"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    EntityItem particleItem = new EntityItem(name);
    particleItem.setData(config.getInt(name + ".data"));
    particleItem.setMaterial(org.bukkit.Material.getMaterial(config.getInt(name + ".material")));
    particleItem.setLore(config.getStringList(name + ".lore"));
    particleItem.setDisplayName(config.getString(name + ".displayname"));
    particleItem.setSlot(config.getInt(name + ".slot"));
    EntityItemManager.addEntityItem(name, particleItem);

  }

  public Material getMaterial() {
    return material;
  }

  private void setMaterial(Material material) {
    this.material = material;
  }

  private byte getData() {
    return data;
  }

  private void setData(Integer data) {
    this.data = data.byteValue();
  }

  private void setLore(List<String> lore) {
    this.lore = lore.toArray(new String[0]);
  }

  private String getDisplayName() {
    return ChatManager.colorRawMessage(displayName);
  }

  private void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public int getSlot() {
    return slot;
  }

  private void setSlot(int slot) {
    this.slot = slot;
  }

  public ItemStack getItemStack() {
    ItemStack itemStack;
    if (data != null) {
      itemStack = new ItemStack(getMaterial(), 1, getData());
    } else {
      itemStack = new ItemStack(getMaterial());

    }
    Utils.setItemNameAndLore(itemStack, ChatManager.colorRawMessage(this.getDisplayName()), lore);
    return itemStack;
  }
}

