package me.hulipvp.guilds.command.leader;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.hulipvp.guilds.command.api.StringArgument;
import me.hulipvp.guilds.structure.Guild;
import me.hulipvp.guilds.structure.member.Role;

public class GuildInvite extends StringArgument {

	@Override
	public String[] aliases() {
		return new String[] { "invite", "i" };
	}

	@Override
	public String description() {
		return "Invite players to your Guild";
	}

	@Override
	public String permission() {
		return "guilds.cmd.invite";
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
			player.sendMessage(ChatColor.RED + "/" + label + " invite <name>");
			return;
		}
		
		Player target = Bukkit.getPlayer(args[1]);
		if (target == null) {
			player.sendMessage(ChatColor.RED + args[1] + " is not online.");
			return;
		}
		
		guild.getInvites().add(target.getUniqueId());
		guild.sendMessage(ChatColor.GREEN + args[1] + ChatColor.YELLOW + " has been invited to the Guild.");
		target.sendMessage(ChatColor.YELLOW + "You have been invited to join " + ChatColor.BLUE + guild.getName());
	}
	
	

}
