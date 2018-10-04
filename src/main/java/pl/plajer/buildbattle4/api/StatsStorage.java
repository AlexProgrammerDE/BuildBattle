/*
 * BuildBattle 4 - Ultimate building competition minigame
 * Copyright (C) 2018  Plajer's Lair - maintained by Plajer and Tigerpanzer
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

package pl.plajer.buildbattle4.api;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import pl.plajer.buildbattle4.Main;
import pl.plajer.buildbattle4.user.UserManager;
import pl.plajer.buildbattle4.utils.MessageUtils;
import pl.plajer.buildbattle4.utils.Utils;
import pl.plajerlair.core.utils.ConfigUtils;

/**
 * @author Plajer, TomTheDeveloper
 * @since 2.0.0
 * <p>
 * Class for accessing users statistics.
 */
public class StatsStorage {

  public static Main plugin;

  /**
   * Get all UUID's sorted ascending by Statistic Type
   *
   * @param stat Statistic type to get (kills, deaths etc.)
   * @return Map of UUID keys and Integer values sorted in ascending order of requested statistic type
   */
  public static Map<UUID, Integer> getStats(StatisticType stat) {
    Main.debug("BuildBattle API getStats(" + stat.getName() + ") run", System.currentTimeMillis());
    if (plugin.isDatabaseActivated()) {
      ResultSet set = plugin.getMySQLDatabase().executeQuery("SELECT UUID, " + stat.getName() + " FROM buildbattlestats ORDER BY " + stat.getName() + " ASC;");
      Map<java.util.UUID, java.lang.Integer> column = new LinkedHashMap<>();
      try {
        while (set.next()) {
          column.put(java.util.UUID.fromString(set.getString("UUID")), set.getInt(stat.getName()));
        }
      } catch (SQLException e) {
        e.printStackTrace();
        MessageUtils.errorOccurred();
        Bukkit.getConsoleSender().sendMessage("Cannot get contents from MySQL database!");
        Bukkit.getConsoleSender().sendMessage("Check configuration of mysql.yml file or disable mysql option in config.yml");
      }
      return column;
    }
    else {
      FileConfiguration config = ConfigUtils.getConfig(plugin, "stats");
      Map<UUID, Integer> stats = new TreeMap<>();
      for (String string : config.getKeys(false)) {
        stats.put(UUID.fromString(string), config.getInt(string + "." + stat.getName()));
      }
      return Utils.sortByValue(stats);
    }
  }

  /**
   * Get user statistic based on StatisticType
   *
   * @param player        Online player to get data from
   * @param statisticType Statistic type to get (blocks placed, wins etc.)
   * @return int of statistic
   * @see StatisticType
   */
  public static int getUserStats(Player player, StatisticType statisticType) {
    Main.debug("BuildBattle API getUserStats(" + player.getName() + ", " + statisticType.getName() + ") run", System.currentTimeMillis());
    return UserManager.getUser(player.getUniqueId()).getInt(statisticType.name);
  }

  /**
   * Available statistics to get.
   */
  public enum StatisticType {
    BLOCKS_PLACED("blocksplaced"), BLOCKS_BROKEN("blocksbroken"), GAMES_PLAYED("gamesplayed"), WINS("wins"), LOSES("loses"), HIGHEST_WIN("highestwin"), PARTICLES_USED("particles"),
    SUPER_VOTES("supervotes");

    String name;

    StatisticType(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }

}
