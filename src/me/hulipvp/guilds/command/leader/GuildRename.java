package me.hulipvp.guilds.command.leader;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.hulipvp.guilds.command.api.StringArgument;
import me.hulipvp.guilds.structure.Guild;
import me.hulipvp.guilds.structure.Member;
import me.hulipvp.guilds.structure.Role;

public class GuildRename extends StringArgument {

	@Override
	public String[] aliases() {
		return new String[] { "rename" };
	}

	@Override
	public String description() {
		return "Rename your Guild";
	}

	@Override
	public String permission() {
		return "guild.cmd.rename";
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
		
		if (args.length != 2) {
			player.sendMessage(ChatColor.RED + "/" + label + " rename <name>");
			return;
		}
		
		if (plugin.getGuildManager().getGuildByString(args[1]) != null) { 
			player.sendMessage(ChatColor.RED + "A Guild by that name already exsists.");
			return;
		}
		
		Bukkit.broadcastMessage(ChatColor.BLUE + guild.getName() + ChatColor.YELLOW + " has renamed to " + ChatColor.GREEN + args[1]);
		guild.setName(args[1]);
	}

}
