package me.hulipvp.guilds.manager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.Getter;
import me.hulipvp.guilds.Guilds;
import me.hulipvp.guilds.structure.Guild;
import me.hulipvp.guilds.structure.member.Member;
import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.GsonBuilder;
import net.minecraft.util.com.google.gson.reflect.TypeToken;

public class GuildManager {
	
	private Guilds plugin;
	
	private File file;
	private Gson gson;
	
	@Getter
	private Set<Guild> guilds;
	
	public GuildManager(Guilds plugin) {
		
		this.plugin = plugin;
		this.file = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "guilds.json");
		plugin.getDataFolder().mkdirs();
		if (!this.file.exists()) {
			plugin.saveResource("guilds.json", false);
		}
		gson = new GsonBuilder().setPrettyPrinting().create();
		
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
	 * Use this to get a Member object which has the same UUID as the provided UUID in
	 * the parameter
	 *
	 * @param uuid - The UUID of the Member you wish to find
	 * @return Member - the Member object which has the same UUID as the provided UUID
	 */
	public Member getMemberByUuid(UUID uuid) {
		return getGuildByPlayer(uuid).getMembers().stream().filter(member -> member.getUuid() == uuid).findFirst().orElse(null);
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
	 * Allows you to save all of the Guilds which are loaded onto the server
	 */
	public void save() {
		try {
			Map<String, Set<Guild>> guildsMap = new HashMap<>();
			guildsMap.put("guilds", guilds);
			String json = gson.toJson(guildsMap);
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(json);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Allows you to load all of the Guilds that have been previously saved
	 */
	public void load() {
		guilds.clear();
		new BukkitRunnable() {
			public void run() {
				try {
					guilds = null;
					Type type = new TypeToken<Map<String, HashSet<Guild>>>(){}.getClass();
					Map<String, HashSet<Guild>> guildsMap = gson.fromJson(new FileReader(file), type);
					if (guildsMap.get("guilds") != null) {
						guilds = guildsMap.get("guilds");
					}
				} catch (Exception exception) {
					exception.printStackTrace();
					Bukkit.getLogger().severe("There were no Guilds found in the Guilds file.");
				}
				if (guilds == null) {
					guilds = new HashSet<Guild>();
				}
			}
		}.runTaskAsynchronously(plugin);
	}
	
}
