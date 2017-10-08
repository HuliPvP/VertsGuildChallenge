package me.hulipvp.guilds.command.normal;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.hulipvp.guilds.command.api.StringArgument;
import me.hulipvp.guilds.structure.Guild;
import me.hulipvp.guilds.structure.Member;
import me.hulipvp.guilds.structure.Role;

public class GuildLeave extends StringArgument {

	@Override
	public String[] aliases() {
		return new String[] { "leave", "l" };
	}

	@Override
	public String description() {
		return "Leave your current guild";
	}

	@Override
	public String permission() {
		return "guilds.cmd.leave";
	}

	@Override
	public boolean playerOnly() {
		return true;
	}

	@Override
	public Role requiredRole() {
		return Role.MEMBER;
	}

	@Override
	public void onArgument(CommandSender sender, String label, String[] args) {
		Player player = (Player) sender;
		if (plugin.getGuildManager().getGuildByPlayer(player.getUniqueId()) != null) {
			player.sendMessage(ChatColor.RED + "You are not in a Guild.");
			return;
		}
		
		if (args.length != 1) {
			player.sendMessage(ChatColor.RED + "/" + label + " leave");
			return;
		}
		
		Guild guild = plugin.getGuildManager().getGuildByPlayer(player.getUniqueId());
		Member memberToRemove = guild.getMembers().stream().filter(member -> member.getUuid() == player.getUniqueId()).findFirst().orElse(null);
		guild.getMembers().remove(memberToRemove);
		if (memberToRemove.getRole() == Role.LEADER) {
			plugin.getGuildManager().disbandGuild(guild);
			guild.sendMessage(ChatColor.YELLOW + "Your leader has left so the Guild has been automatically disbanded");
			return;
		}
		guild.sendMessage(ChatColor.RED + player.getName() + ChatColor.YELLOW + " has left the Guild");
	}
	
}
