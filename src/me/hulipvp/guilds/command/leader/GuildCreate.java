package me.hulipvp.guilds.command.leader;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.hulipvp.guilds.command.api.StringArgument;
import me.hulipvp.guilds.structure.Role;

public class GuildCreate extends StringArgument {

	@Override
	public String[] aliases() {
		return new String[] { "create" };
	}

	@Override
	public String description() {
		return "Create a Guild";
	}

	@Override
	public String permission() {
		return "guilds.cmd.create";
	}

	@Override
	public boolean playerOnly() {
		return true;
	}
	
	@Override
	public Role requiredRole() {
		return null;
	}

	@Override
	public void onArgument(CommandSender sender, String label, String[] args) {
		Player player = (Player) sender;
		if (plugin.getGuildManager().getGuildByPlayer(player.getUniqueId()) == null) {
			player.sendMessage(ChatColor.RED + "You must leave your current Guild to create a Guild.");
			return;
		}
		
		if (args.length != 2) {
			player.sendMessage(ChatColor.RED + "/" + label + " create <name>");
			return;
		}
		
		plugin.getGuildManager().createGuild(args[1], player.getUniqueId());
		Bukkit.broadcastMessage(ChatColor.BLUE + args[1] + ChatColor.YELLOW + " Guild has been created by " + ChatColor.GREEN + player.getName());
	}

}
