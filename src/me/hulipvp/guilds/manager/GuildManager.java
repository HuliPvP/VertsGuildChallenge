package me.hulipvp.guilds.manager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import lombok.Getter;
import me.hulipvp.guilds.Guilds;
import me.hulipvp.guilds.structure.Guild;
import me.hulipvp.guilds.structure.Member;
import me.hulipvp.guilds.structure.Role;


public class GuildManager {
	
	private Guilds plugin;
	private File file;
	private FileConfiguration config;
	
	@Getter
	private Set<Guild> guilds;
	
	public GuildManager(Guilds plugin) {
		
		this.plugin = plugin;
		// TODO: Convert to JSON because YAML is a pain in the arse
		this.file = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "guilds.yml");
		plugin.getDataFolder().mkdirs();
		if (!this.file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		config = YamlConfiguration.loadConfiguration(file);
		
		guilds = new HashSet<>();
		
		load();
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
		return guilds.stream().filter(guild -> guild.getMembers().stream().filter(member -> member.getUuid() == uuid).collect(Collectors.toList()).size() >= 1).findFirst().orElse(null);
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
	
	
	/**
	 * Turns a ConfigurationSection into a Guild
	 * 
	 * @param configurationSection - The section which holds all of the Guild's data
	 * @return Guild - A new Guild which was instantiated with the data provided from the ConfigurationSection
	 */
	public Guild guildFromSection(ConfigurationSection configurationSection) {
		UUID id = UUID.fromString(configurationSection.getString("id"));
		String name = configurationSection.getString("name");
		String motd = configurationSection.getString("motd");
		Set<Member> members = (Set<Member>) configurationSection.get("members");
		return new Guild(name, members.stream().filter(member -> member.getRole() == Role.LEADER).findFirst().orElse(null).getUuid(), id, motd);
	}
	
	
	/**
	 * Turns a Guild into a ConfigurationSection so you can save it into the YamlConfiguration
	 * 
	 * @param guild - The Guild you wish to convert into a ConfigurationSection
	 * @return configurationSection - The section which holds all of the Guild's data
	 */
	public ConfigurationSection guildToSection(Guild guild) {
		ConfigurationSection configurationSection = new YamlConfiguration();
		configurationSection.set("id", guild.getId().toString());
		configurationSection.set("name", guild.getName());
		configurationSection.set("motd", guild.getMotd());
		configurationSection.set("members", guild.getMembers());
		return configurationSection;
	}
	
	/**
	 * Allows you to save all of the Guilds which are loaded onto the server
	 */
	public void save() {
		Map<String, ConfigurationSection> configurationSections = new HashMap<>();
		guilds.stream().forEach(guild -> configurationSections.put(guild.getId().toString(), guildToSection(guild)));
		config.set("guilds", configurationSections);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Allows you to load all of the Guilds that have been previously saved
	 */
	public void load() {
		guilds.clear();
		ConfigurationSection configurationSection = config.getConfigurationSection("guilds");
		if (configurationSection != null) {
			configurationSection.getKeys(false).stream().forEach(sectionKey -> {
				guilds.add(guildFromSection(configurationSection.getConfigurationSection(sectionKey)));
			});
		} else {
			Bukkit.getLogger().log(Level.WARNING, "There seems to be no Guilds setup!");
		}
	}
	
}
