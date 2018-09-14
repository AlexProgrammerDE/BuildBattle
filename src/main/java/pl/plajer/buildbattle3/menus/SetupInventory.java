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

package pl.plajer.buildbattle3.menus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import pl.plajer.buildbattle3.Main;
import pl.plajer.buildbattle3.arena.Arena;
import pl.plajerlair.core.utils.ConfigUtils;
import pl.plajerlair.core.utils.ItemBuilder;
import pl.plajerlair.core.utils.LocationUtils;

/**
 * Created by Tom on 15/06/2015.
 */
public class SetupInventory {

  private static Main plugin = JavaPlugin.getPlugin(Main.class);
  private Inventory inventory;

  public SetupInventory(Arena arena) {
    this.inventory = Bukkit.createInventory(null, 9 * 2, "BB Arena: " + arena.getID());
    addItem(new ItemBuilder(new ItemStack(Material.REDSTONE_BLOCK))
            .name(ChatColor.GOLD + "► Set" + ChatColor.RED + " ending " + ChatColor.GOLD + "location")
            .lore(ChatColor.GRAY + "Click to set the ending location")
            .lore(ChatColor.GRAY + "on the place where you are standing.")
            .lore(ChatColor.DARK_GRAY + "(location where players will be teleported")
            .lore(ChatColor.DARK_GRAY + "after the game)")
            .lore(isOptionDoneBool("instances." + arena.getID() + ".Endlocation"))
            .build());
    addItem(new ItemBuilder(new ItemStack(Material.LAPIS_BLOCK))
            .name(ChatColor.GOLD + "► Set" + ChatColor.WHITE + " lobby " + ChatColor.GOLD + "location")
            .lore(ChatColor.GRAY + "Click to set the lobby location")
            .lore(ChatColor.GRAY + "on the place where you are standing")
            .lore(isOptionDoneBool("instances." + arena.getID() + ".lobbylocation"))
            .build());
    int min = ConfigUtils.getConfig(plugin, "arenas").getInt("instances." + arena.getID() + ".minimumplayers");
    addItem(new ItemBuilder(new ItemStack(Material.COAL, min == 0 ? 1 : min))
            .name(ChatColor.GOLD + "► Set" + ChatColor.DARK_GREEN + " minimum players " + ChatColor.GOLD + "size")
            .lore(ChatColor.GRAY + "LEFT click to decrease")
            .lore(ChatColor.GRAY + "RIGHT click to increase")
            .lore(ChatColor.DARK_GRAY + "(how many players are needed")
            .lore(ChatColor.DARK_GRAY + "for game to start lobby countdown)")
            .lore(ChatColor.RED + "Set it minimum 3 when using TEAM game type!!!")
            .lore(isOptionDone("instances." + arena.getID() + ".minimumplayers"))
            .build());
    int max = ConfigUtils.getConfig(plugin, "arenas").getInt("instances." + arena.getID() + ".maximumplayers");
    addItem(new ItemBuilder(new ItemStack(Material.REDSTONE, max == 0 ? 1 : max))
            .name(ChatColor.GOLD + "► Set" + ChatColor.GREEN + " maximum players " + ChatColor.GOLD + "size")
            .lore(ChatColor.GRAY + "LEFT click to decrease")
            .lore(ChatColor.GRAY + "RIGHT click to increase")
            .lore(ChatColor.DARK_GRAY + "(how many players arena can hold)")
            .lore(isOptionDone("instances." + arena.getID() + ".maximumplayers"))
            .build());
    if (!plugin.isBungeeActivated()) {
      addItem(new ItemBuilder(new ItemStack(Material.SIGN))
              .name(ChatColor.GOLD + "► Add game" + ChatColor.AQUA + " sign")
              .lore(ChatColor.GRAY + "Target a sign and click this.")
              .lore(ChatColor.DARK_GRAY + "(this will set target sign as game sign)")
              .build());
    }
    addItem(new ItemBuilder(new ItemStack(Material.WATCH))
            .name(ChatColor.GOLD + "► Set game type")
            .lore(ChatColor.GRAY + "Set game mode of build battle arena.")
            .lore(ChatColor.GRAY + "Valid types: SOLO, TEAM")
            .lore(ChatColor.GRAY + "SOLO - 1 player per plot")
            .lore(ChatColor.GRAY + "TEAM - 2 players per plot")
            .lore(isOptionDone("instances." + arena.getID() + ".gametype"))
            .build());
    addItem(new ItemBuilder(new ItemStack(Material.NAME_TAG))
            .name(ChatColor.GOLD + "► Set" + ChatColor.RED + " map name " + ChatColor.GOLD + "(currently: " + arena.getMapName() + ")")
            .lore(ChatColor.GRAY + "Replace this name tag with named name tag.")
            .lore(ChatColor.GRAY + "It will be set as arena name.")
            .lore(ChatColor.RED + "" + ChatColor.BOLD + "Drop name tag here don't move")
            .lore(ChatColor.RED + "" + ChatColor.BOLD + "it and replace with new!!!")
            .build());
    addItem(new ItemBuilder(new ItemStack(Material.BARRIER))
            .name(ChatColor.GOLD + "► Add game plot")
            .lore(ChatColor.GRAY + "Select your plot with our built-in")
            .lore(ChatColor.GRAY + "selector (select minimum and maximum")
            .lore(ChatColor.GRAY + "plot opposite selections with built-in wand)")
            .lore(ChatColor.GRAY + "And click this.")
            .lore(ChatColor.GRAY + "Command for wand is: " + ChatColor.YELLOW + "/bba plotwand")
            .lore(ChatColor.GREEN + "PLEASE SELECT FLOOR TOO!")
            .lore(isOptionDoneList("instances." + arena.getID() + ".plots"))
            .build());
    addItem(new ItemBuilder(new ItemStack(Material.GRASS))
            .name(ChatColor.GOLD + "► Add floor changer NPC")
            .lore(ChatColor.GRAY + "Add floor changer NPC to your plot.")
            .lore(ChatColor.RED + "Requires Citizens plugin!")
            .build());
    addItem(new ItemBuilder(new ItemStack(Material.FIREWORK))
            .name(ChatColor.GOLD + "► " + ChatColor.GREEN + "Register arena")
            .lore(ChatColor.GRAY + "Click this when you're done with configuration.")
            .lore(ChatColor.GRAY + "It will validate and register arena.")
            .build());
  }

  private static String isOptionDone(String path) {
    if (ConfigUtils.getConfig(plugin, "arenas").isSet(path)) {
      return ChatColor.GOLD + "" + ChatColor.BOLD + "Done: " + ChatColor.GREEN + "Yes " + ChatColor.GRAY + "(value: " + ConfigUtils.getConfig(plugin, "arenas").getString(path) + ")";
    }
    return ChatColor.GOLD + "" + ChatColor.BOLD + "Done: " + ChatColor.RED + "No";
  }

  private String isOptionDoneList(String path) {
    if (ConfigUtils.getConfig(plugin, "arenas").isSet(path)) {
      return ChatColor.GOLD + "" + ChatColor.BOLD + "Done: " + ChatColor.GREEN + "Yes " + ChatColor.GRAY + "(value: " + ConfigUtils.getConfig(plugin, "arenas").getConfigurationSection(path).getKeys
              (false).size() + ")";
    }
    return ChatColor.GOLD + "" + ChatColor.BOLD + "Done: " + ChatColor.RED + "No";
  }

  private String isOptionDoneBool(String path) {
    if (ConfigUtils.getConfig(plugin, "arenas").isSet(path)) {
      if (Bukkit.getServer().getWorlds().get(0).getSpawnLocation().equals(LocationUtils.getLocation(ConfigUtils.getConfig(plugin, "arenas").getString(path)))) {
        return ChatColor.GOLD + "" + ChatColor.BOLD + "Done: " + ChatColor.RED + "No";
      }
      return ChatColor.GOLD + "" + ChatColor.BOLD + "Done: " + ChatColor.GREEN + "Yes";
    }
    return ChatColor.GOLD + "" + ChatColor.BOLD + "Done: " + ChatColor.RED + "No";
  }

  public void addItem(ItemStack itemStack) {
    inventory.addItem(itemStack);
  }

  public Inventory getInventory() {
    return inventory;
  }

  public void openInventory(Player player) {
    player.openInventory(inventory);
  }

}
