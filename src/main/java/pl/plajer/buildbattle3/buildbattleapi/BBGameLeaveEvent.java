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

package pl.plajer.buildbattle3.buildbattleapi;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import pl.plajer.buildbattle3.arena.Arena;

/**
 * Called when player leaves arena
 */
public class BBGameLeaveEvent extends BBEvent {

  private static final HandlerList handlers = new HandlerList();
  private Player player;

  public BBGameLeaveEvent(Player player, Arena arena) {
    super(arena);
    this.player = player;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  /**
   * Get player associated with this event
   *
   * @return player
   */
  public Player getPlayer() {
    return player;
  }
}
