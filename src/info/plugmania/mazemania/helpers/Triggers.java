/*
    MazeMania; a minecraft bukkit plugin for managing a maze as an arena.
    Copyright (C) 2012 Plugmania (Sorroko,korikisulda) and contributors.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package info.plugmania.mazemania.helpers;

import info.plugmania.mazemania.ConfigUtil;
import info.plugmania.mazemania.MazeMania;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Triggers {

	public HashMap<String, List<String>> triggers = new HashMap<String, List<String>>();

	public YamlConfiguration trigConf;

	MazeMania plugin;

	public Triggers(MazeMania instance) {
		plugin = instance;

		ConfigUtil.loadConfig("triggers");
		trigConf = ConfigUtil.getConfig("triggers");

		if (trigConf.isSet("triggers")) {
			ConfigurationSection trigSec = trigConf.getConfigurationSection("triggers");
			for (String s : trigSec.getKeys(false)) {
				List<String> events = trigConf.getStringList("triggers." + s + ".events");
				if (events == null) events = new ArrayList<String>();

				triggers.put(s, events);
			}
		}
	}

	public void handle(Location loc, Player player) {
		String uid = loc.getWorld().getName() + loc.getBlockX() + "" + loc.getBlockY() + "" + loc.getBlockZ();
		if (!triggers.containsKey(uid)) return;

		List<String> events = triggers.get(uid);
		for (String s : events) {
			String[] ar = s.split(":");
			if (ar[0].equalsIgnoreCase("lightning")) {
				lightningHandle(loc, player);
			} else if (ar[0].equalsIgnoreCase("fire")) {
				fireHandle(loc, player);
			} else if (ar[0].equalsIgnoreCase("poison")) {
				poisonHandle(loc, player);
			} else if (ar[0].equalsIgnoreCase("blind")) {
				blindnessHandle(loc, player);
			} else if (ar[0].equalsIgnoreCase("confuse")) {
				confuseHandle(loc, player);
			} else if (ar[0].equalsIgnoreCase("slow")) {
				slowHandle(loc, player);
			} else if (ar[0].equalsIgnoreCase("speed")) {
				speedHandle(loc, player);
			} else if (ar[0].equalsIgnoreCase("night")) {
				nightHandle(loc, player);
			} else if (ar[0].equalsIgnoreCase("invisible")) {
				invisibleHandle(loc, player);
			} else if (ar[0].equalsIgnoreCase("zombie")) {
				zombieHandle(loc, player);
			} else if (ar[0].equalsIgnoreCase("spider")) {
				spiderHandle(loc, player);
			} else if (ar[0].equalsIgnoreCase("cavespider")) {
				cavespiderHandle(loc, player);
			}
		}
	}

	private void lightningHandle(Location loc, Player player) {
		loc.getWorld().strikeLightningEffect(loc);
		player.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 5, 1));
	}

	private void fireHandle(Location loc, Player player) {
		player.setFireTicks(4 * 20);
	}

	private void poisonHandle(Location loc, Player player) {
		player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 4 * 20, 1));
	}

	private void blindnessHandle(Location loc, Player player) {
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 4 * 20, 1));
	}

	private void confuseHandle(Location loc, Player player) {
		player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 4 * 20, 1));
	}

	private void slowHandle(Location loc, Player player) {
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 4 * 20, 1));
	}	

	private void speedHandle(Location loc, Player player) {
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 4 * 20, 1));
	}	

	private void nightHandle(Location loc, Player player) {
		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 4 * 20, 1));
	}	

	private void invisibleHandle(Location loc, Player player) {
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 4 * 20, 1));
	}	

	private void zombieHandle(Location loc, Player player) {
		loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
	}

	private void spiderHandle(Location loc, Player player) {
		loc.getWorld().spawnEntity(loc, EntityType.SPIDER);
	}

	private void cavespiderHandle(Location loc, Player player) {
		loc.getWorld().spawnEntity(loc, EntityType.CAVE_SPIDER);
	}
}
