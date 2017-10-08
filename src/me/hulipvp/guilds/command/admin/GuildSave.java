package me.hulipvp.guilds.command.admin;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import me.hulipvp.guilds.command.api.StringArgument;
import me.hulipvp.guilds.structure.member.Role;

public class GuildSave extends StringArgument {

	@Override
	public String[] aliases() {
		return new String[] { "save" };
	}

	@Override
	public String description() {
		return "Save all Guild data";
	}

	@Override
	public String permission() {
		return "guilds.cmd.save";
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
		plugin.getGuildManager().save();
		sender.sendMessage(ChatColor.GREEN + "Successfully saved all Guild data.");
	}

}
