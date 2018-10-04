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

package pl.plajer.buildbattle4.commands;

/**
 * @author Plajer
 * <p>
 * Created at 08.06.2018
 */
public class CommandData {

  private String text;
  private String command;
  private String description;

  public CommandData(String text, String command, String description) {
    this.text = text;
    this.command = command;
    this.description = description;
  }

  public String getText() {
    return text;
  }

  public String getCommand() {
    return command;
  }

  public String getDescription() {
    return description;
  }
}