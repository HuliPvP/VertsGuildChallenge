package me.hulipvp.guilds.command.normal;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.hulipvp.guilds.command.api.StringArgument;
import me.hulipvp.guilds.structure.Role;

public class GuildMessage extends StringArgument {

	@Override
	public String[] aliases() {
		return new String[] { "message", "m" };
	}

	@Override
	public String description() {
		return "Send a quick message to the online members in your Guild";
	}

	@Override
	public String permission() {
		return "guild.cmd.message";
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
		if (plugin.getGuildManager().getGuildByPlayer(player.getUniqueId()) != null) {
			player.sendMessage(ChatColor.RED + "You are not in a Guild.");
			return;
		}
		
		if (args.length <= 1) {
			player.sendMessage(ChatColor.RED + "/" + label + " message <message>");
			return;
		}
		
		String message = ChatColor.AQUA + "Guild |" + player.getName() + " > " + ChatColor.YELLOW + String.join(" ", args);
		plugin.getGuildManager().getGuildByPlayer(player.getUniqueId()).sendMessage(message);
	}

}
