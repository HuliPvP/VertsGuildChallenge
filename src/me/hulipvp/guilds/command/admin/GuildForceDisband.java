package me.hulipvp.guilds.command.admin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import me.hulipvp.guilds.command.api.StringArgument;
import me.hulipvp.guilds.structure.Guild;
import me.hulipvp.guilds.structure.member.Role;

public class GuildForceDisband extends StringArgument {

	@Override
	public String[] aliases() {
		return new String[] { "forcedisband", "remove" };
	}

	@Override
	public String description() {
		return "Forcefully disband/remove a Guild";
	}

	@Override
	public String permission() {
		return "guilds.cmd.forcedisband";
	}

	@Override
	public boolean playerOnly() {
		return false;
	}

	@Override
	public Role requiredRole() {
		return null;
	}

	@Override
	public void onArgument(CommandSender sender, String label, String[] args) {
		if (args.length != 2) {
			sender.sendMessage(ChatColor.RED + "/" + label + " forcedisband <name>");
			return;
		}

		Guild guild = null;
		guild = plugin.getGuildManager().getGuildByString(args[1]);
		if (guild == null) {
			OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
			if (offlinePlayer.hasPlayedBefore()) {
				guild = plugin.getGuildManager().getGuildByPlayer(offlinePlayer.getUniqueId());
			}
		}

		if (guild == null) {
			sender.sendMessage(ChatColor.RED + "No guild by that name or with that player was found.");
			return;
		}
		
		Bukkit.broadcastMessage(ChatColor.BLUE + guild.getName() + ChatColor.YELLOW + " has been forcefully disbanded by " + ChatColor.GREEN + sender.getName());
		plugin.getGuildManager().disbandGuild(guild);
	}

}
