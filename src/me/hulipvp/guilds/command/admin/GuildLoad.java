package me.hulipvp.guilds.command.admin;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import me.hulipvp.guilds.command.api.StringArgument;
import me.hulipvp.guilds.structure.member.Role;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class GuildLoad extends StringArgument {
	
	@Override
	public String[] aliases() {
		return new String[] { "load" };
	}

	@Override
	public String description() {
		return "Load all Guild data";
	}

	@Override
	public String permission() {
		return "guilds.cmd.load";
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
		plugin.getGuildManager().load();
		sender.sendMessage(ChatColor.GREEN + "Successfully loaded all Guild data.");
	}

}
