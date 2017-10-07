package me.hulipvp.guilds.manager;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.Getter;
import me.hulipvp.guilds.Guilds;
import me.hulipvp.guilds.structure.Guild;


public class GuildManager {
	
	private Guilds plugin;
	private File file;
	
	@Getter
	private Set<Guild> guilds;
	
	public GuildManager(Guilds plugin) {
		
		this.plugin = plugin;
		// TODO: Convert to JSON because YAML is a pain in the arse
		this.file = new File(plugin.getDataFolder(), "guilds.yml");
		if (!this.file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		guilds = new HashSet<>();
		
	}
	
	/**
	 * Will return <tt>null</tt> if no Guild with the provided String parameter is
	 * not found
	 * 
	 * @param guildName - The name of the Guild you wish to find
	 * @return Guild - the Guild with the same name as the provided parameter
	 */
	public Guild getGuildByString(String guildName) {
		return guilds.stream().filter(guild -> guild.getName().equalsIgnoreCase(guildName)).findFirst().orElse(null);
	}
	
	/**
	 * Will return <tt>null</tt> if no Guild with the provided UUID is
	 * not found in the collection of Members
	 * 
	 * @param uuid - The UUID of the player you wish to find
	 * @return Guild - the Guild that contains the Member with a matching UUID
	 */
	public Guild getGuildByPlayer(UUID uuid) {
		return guilds.stream().filter(guild -> guild.getMembers().stream().filter(member -> member.getUuid() == uuid).collect(Collectors.toList()).contains(uuid)).findFirst().orElse(null);
	}
	
	/**
	 * Creates a new Guild and then stores it
	 * 
	 * @param name - The name of the Guild
	 * @param uuid - The UUID of the Leader of the Guild
	 */
	public void createGuild(String name, UUID uuid) {
		Guild guild = new Guild(name, uuid);
		guilds.add(guild);
	}
	
	/**
	 * Removes a guild from the stored Guilds
	 * 
	 * @param guild - The Guild you wish to disband
	 */
	public void disbandGuild(Guild guild) {
		guilds.remove(guild);
	}

}
