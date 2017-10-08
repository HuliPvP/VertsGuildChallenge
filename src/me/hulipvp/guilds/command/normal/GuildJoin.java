package me.hulipvp.guilds.command.normal;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.hulipvp.guilds.command.api.StringArgument;
import me.hulipvp.guilds.structure.Guild;
import me.hulipvp.guilds.structure.member.Member;
import me.hulipvp.guilds.structure.member.Role;

public class GuildJoin extends StringArgument {

	@Override
	public String[] aliases() {
		return new String[] { "join", "j", "accept" };
	}

	@Override
	public String description() {
		return "Join a Guild that you were invited to";
	}

	@Override
	public String permission() {
		return "guild.cmd.join";
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
		if (plugin.getGuildManager().getGuildByPlayer(player.getUniqueId()) != null) {
			player.sendMessage(ChatColor.RED + "You are already in a Guild.");
			return;
		}
		
		if (args.length != 2) {
			player.sendMessage(ChatColor.RED + "/" + label + " join <name>");
			return;
		}
		
		Guild guild = null;
		guild = plugin.getGuildManager().getGuildByString(args[1]);
		if (guild == null) {
			guild = plugin.getGuildManager().getGuildByPlayer(player.getUniqueId());
		}
		
		if (guild == null) {
			player.sendMessage(ChatColor.RED + "No guild by that name or with that player was found.");
			return;
		}
		
		if (guild.getInvites().contains(player.getUniqueId())) {
			guild.getMembers().add(new Member(player.getUniqueId()));
			guild.sendMessage(ChatColor.GREEN + player.getName() + ChatColor.YELLOW + " has joined the Guild");
		}
	}

}
