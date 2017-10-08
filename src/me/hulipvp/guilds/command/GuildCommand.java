package me.hulipvp.guilds.command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import me.hulipvp.guilds.command.api.StringArgument;
import me.hulipvp.guilds.command.api.annotation.Command;
import me.hulipvp.guilds.command.leader.GuildCreate;
import me.hulipvp.guilds.command.leader.GuildDisband;
import me.hulipvp.guilds.command.leader.GuildInvite;
import me.hulipvp.guilds.command.leader.GuildKick;
import me.hulipvp.guilds.command.leader.GuildRename;
import me.hulipvp.guilds.command.normal.GuildJoin;
import me.hulipvp.guilds.command.normal.GuildLeave;

public class GuildCommand {
	
	// The list of StringArguments for the Guild command
	private List<StringArgument> arguments;
	
	public GuildCommand() {
		
		arguments = new ArrayList<>();
		
		Stream.of(
				new GuildCreate(),
				new GuildDisband(),
				new GuildInvite(),
				new GuildKick(),
				new GuildRename(),
				new GuildJoin(),
				new GuildLeave()
				)
		.forEach(command -> arguments.add(command));
		
	}
	
	/**
	 * Just the main method to handle the Guild command
	 * 
	 * @param sender - The Player who executed the command
	 * @param label - The command that the player used, since there are aliases
	 * @param args - The arguments provided when the command was executed
	 */
	@Command(name = "guild", aliases = { "g", "guilds", "t", "teams", "f", "factions" }, desc = "Main command for Guilds", isPublic = true, playerOnly = false)
	public void guildCommand(CommandSender sender, String label, String[] args) {
		if (args.length == 0) {
        	sender.sendMessage(ChatColor.RED + "Available Guild Commands:");
            arguments.stream().filter(argument -> sender.hasPermission(argument.getPermission())).forEach(argument -> sender.sendMessage(ChatColor.RED + "/" + label + " " + argument.getAliases()[0] + " - " + argument.getDescription()));
        } else {
            String pre = args[0];

            arguments.stream().filter(argument -> Stream.of(argument.getAliases()).collect(Collectors.toList()).contains(pre.toLowerCase())).forEach(argument -> {
            	if (sender.hasPermission(argument.getPermission())) {
            		// TODO: Check for the required role of the argument
            		argument.executeArgument(sender, label, args);
                    return;
                }
            });

            sender.sendMessage(ChatColor.RED + "Invalid argument provided: " + pre);
            sender.sendMessage(ChatColor.RED + "Use '/" + label + "' for a list of valid arguments.");
        }
	}

}