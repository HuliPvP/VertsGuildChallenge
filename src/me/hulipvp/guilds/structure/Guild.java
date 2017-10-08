package me.hulipvp.guilds.structure;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Guild {
	
	private UUID id;
	
	private String name;
	private String motd;
	
	private Set<Member> members;
	
	private Set<UUID> invites;
	
	/**
	 * Instantiates a new Guild
	 * 
	 * @param name - The name of the Guild
	 * @param uuid - The UUID of the Leader
	 */
	public Guild(String name, UUID uuid) {
		
		this.id = UUID.randomUUID();
	
		this.name = name;
		this.motd = null;
		this.members = new HashSet<>();
		
		this.invites = new HashSet<>();
		
		members.add(new Member(uuid, Role.LEADER));
	}
	
	/**
	 * Instantiated a new Guild where the Guild is fully setup
	 * 
	 * @param name - The name of the Guild
	 * @param leaderUuid - The UUID of the leader
	 * @param guildUuid - The UUID of the Guild
	 */
	public Guild(String name, UUID leaderUuid, UUID guildUuid, String motd) {
		this(name, leaderUuid);
		this.id = guildUuid;
		this.motd = motd;
	}
	
	/**
	 * Will return <tt>null</tt> if no leader is found, which shouldn't happen since every Guild will
	 * have a Leader
	 * 
	 * @return Member - the Leader of the Guild
	 */
	public Member getLeader() {
		return this.members.stream().filter(member -> member.getRole() == Role.LEADER).findFirst().orElse(null);
	}
	
	/**
	 * Will return an empty <tt>Set</tt> if there are no players online
	 * in the Guild
	 * 
	 * @return players - A set of players that are online in the Guild
	 */
	public Set<Player> getOnlinePlayers() {
		Set<Player> players = new HashSet<>();
		this.members.stream().filter(member -> Bukkit.getPlayer(member.getUuid()) != null).forEach(member -> players.add(Bukkit.getPlayer(member.getUuid())));
		return players;
	}
	
	/**
	 * Will send a message to all online players in the Guild
	 * 
	 * @param message - the message to send to all online players
	 */
	public void sendMessage(String message) {
		this.getOnlinePlayers().stream().forEach(player -> player.sendMessage(message));
	}

}
