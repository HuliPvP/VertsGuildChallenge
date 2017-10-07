package me.hulipvp.guilds;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import me.hulipvp.guilds.command.GuildCommand;
import me.hulipvp.guilds.command.api.CommandApi;
import me.hulipvp.guilds.manager.GuildManager;

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
		
	}
	
	/**
	 * Access the ONLY instance allowed in Guilds
	 * 
	 * @return instanc - The Guilds instance
	 */
	public static Guilds getInstance() {
		return instance;
	}
	
}
