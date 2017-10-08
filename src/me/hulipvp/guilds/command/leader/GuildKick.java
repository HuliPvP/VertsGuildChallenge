package me.hulipvp.guilds.command.leader;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.hulipvp.guilds.command.api.StringArgument;
import me.hulipvp.guilds.structure.Guild;
import me.hulipvp.guilds.structure.member.Role;

public class GuildKick extends StringArgument {

	@Override
	public String[] aliases() {
		return new String[] { "kick", "k" };
	}

	@Override
	public String description() {
		return "Kick a player from your Guild";
	}

	@Override
	public String permission() {
		return "guilds.cmd.kick";
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
			player.sendMessage(ChatColor.RED + "You must own a Guild to invite a player to your Guild.");
			return;
		}
		
		if (args.length != 2) {
			player.sendMessage(ChatColor.RED + "/" + label + " kick <name>");
			return;
		}
		
		OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
		UUID uuid = target.getUniqueId();
		if (!target.hasPlayedBefore() || plugin.getGuildManager().getGuildByPlayer(uuid) == null) {
			player.sendMessage(ChatColor.RED + args[1] + " is not in your Guild.");
			return;
		}
		
		guild.getMembers().removeIf(member -> member.getUuid() == uuid);
		if (target.isOnline()) {
			target.getPlayer().sendMessage(ChatColor.YELLOW + "You have been kicked from " + ChatColor.RED + guild.getName());
		}
		guild.sendMessage(ChatColor.RED + target.getName() + ChatColor.YELLOW + " has been kicked from the guild");
	}

}
