package me.hulipvp.guilds;

import java.util.concurrent.TimeUnit;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import me.hulipvp.guilds.command.GuildCommand;
import me.hulipvp.guilds.command.api.CommandApi;
import me.hulipvp.guilds.manager.GuildManager;
import me.hulipvp.guilds.util.GuildTask;

@Getter
public class Guilds extends JavaPlugin {
	
	// Only one instance is allowed to be accessed
	private static Guilds instance;
	
	// The main manager to handle all Guilds
	private GuildManager guildManager;
	
	// Self-explanatory
	private CommandApi commandApi;
	
	public void onEnable() {
		
		instance = this;
		
		guildManager = new GuildManager(this);
		
		commandApi = new CommandApi(this);
		commandApi.getCommandManager().registerCommands(new GuildCommand());
		
		new GuildTask(guildManager).runTaskTimerAsynchronously(this, TimeUnit.MINUTES.toSeconds(30L) * 20L, TimeUnit.MINUTES.toSeconds(30L) * 20L);
		
	}
	
	public void onDisable() {
		
		guildManager.save();
		
	}
	
	/**
	 * Access the ONLY instance allowed in Guilds
	 * 
	 * @return instance - The Guilds instance
	 */
	public static Guilds getInstance() {
		return instance;
	}
	
}
