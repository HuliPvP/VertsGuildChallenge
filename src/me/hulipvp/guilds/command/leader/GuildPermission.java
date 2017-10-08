package me.hulipvp.guilds.command.leader;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.hulipvp.guilds.command.api.StringArgument;
import me.hulipvp.guilds.structure.Guild;
import me.hulipvp.guilds.structure.member.Role;

public class GuildPermission extends StringArgument {

	@Override
	public String[] aliases() {
		return new String[] { "permission", "perm", "p" };
	}

	@Override
	public String description() {
		return "Manage member's permissions";
	}

	@Override
	public String permission() {
		return "guilds.cmd.permission";
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
		
		if (args.length != 3) {
			player.sendMessage(ChatColor.RED + "/" + label + " permission <name> <type>");
			return;
		}
		
	}
	
}
