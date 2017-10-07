package me.hulipvp.guilds.command.leader;

import org.bukkit.command.CommandSender;

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
		return Role.LEADER;
	}

	@Override
	public void onArgument(CommandSender sender, String label, String[] args) {
		
	}

}
