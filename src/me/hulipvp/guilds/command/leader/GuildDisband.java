package me.hulipvp.guilds.command.leader;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.hulipvp.guilds.command.api.StringArgument;
import me.hulipvp.guilds.structure.Guild;
import me.hulipvp.guilds.structure.member.Role;

public class GuildDisband extends StringArgument {

	@Override
	public String[] aliases() {
		return new String[] { "disband" };
	}

	@Override
	public String description() {
		return "Disband your Guild";
	}

	@Override
	public String permission() {
		return "guild.cmd.disband";
	}

	@Override
	public boolean playerOnly() {
		return true;
	}

	@Override
	public Role requiredRole() {
		return Role.LEADER;
	}

	@Override
	public void onArgument(CommandSender sender, String label, String[] args) {
		Player player = (Player) sender;
		Guild guild = plugin.getGuildManager().getGuildByPlayer(player.getUniqueId());
		if (guild == null || (guild != null && guild.getLeader().getUuid() != player.getUniqueId())) { 
			player.sendMessage(ChatColor.RED + "You must be in a Guild to perform this action.");
			return;
		}
		
		if (args.length != 1) {
			player.sendMessage(ChatColor.RED + "/" + label + " disband");
			return;
		}
		
		plugin.getGuildManager().disbandGuild(guild);
		Bukkit.broadcastMessage(ChatColor.BLUE + guild.getName() + ChatColor.YELLOW + " has been disbanded by " + ChatColor.GREEN + player.getName());
	}
	
}
