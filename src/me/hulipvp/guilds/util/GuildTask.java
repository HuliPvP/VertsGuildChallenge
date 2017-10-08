package me.hulipvp.guilds.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.AllArgsConstructor;
import me.hulipvp.guilds.manager.GuildManager;

@AllArgsConstructor
public class GuildTask extends BukkitRunnable {
	
	private GuildManager guildManager;

	/**
	 * Save all Guilds periodically while the server is running
	 * 
	 * Be sure to make this task asynchronous
	 */
	@Override
	public void run() {
		Bukkit.broadcastMessage(ChatColor.YELLOW + "Saving all Guilds to the database. Expect lag.");
		long start = System.currentTimeMillis();
		guildManager.save();
		long end = System.currentTimeMillis();
		Bukkit.broadcastMessage(ChatColor.GREEN + "All Guilds have been saved. " + ChatColor.GRAY + "(" + (end - start) + "ms)");
	}
	
}