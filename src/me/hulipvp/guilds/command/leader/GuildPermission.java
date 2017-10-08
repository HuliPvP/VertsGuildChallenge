package me.hulipvp.guilds.command.leader;

import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.hulipvp.guilds.command.api.StringArgument;
import me.hulipvp.guilds.structure.Guild;
import me.hulipvp.guilds.structure.member.Member;
import me.hulipvp.guilds.structure.member.Role;
import me.hulipvp.guilds.structure.permission.PermissionType;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;

public class GuildPermission extends StringArgument {

	@Override
	public String[] aliases() {
		return new String[] { "permission", "perm", "p" };
	}

	@Override
	public String description() {
		return "Manage member's permissions";
	}

	@Override
	public String permission() {
		return "guilds.cmd.permission";
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
		Guild playerGuild = plugin.getGuildManager().getGuildByPlayer(player.getUniqueId());
		if (playerGuild == null || (playerGuild != null && playerGuild.getLeader().getUuid() != player.getUniqueId())) {
			player.sendMessage(ChatColor.RED + "You must own a Guild to perform this action.");
			return;
		}

		if (args.length != 4) {
			player.sendMessage(ChatColor.RED + "/" + label + " permission <name> <type> <remove|add>");
			return;
		}

		Guild guild = null;
		OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
		UUID uuid = null;
		if (offlinePlayer.hasPlayedBefore()) {
			guild = plugin.getGuildManager().getGuildByPlayer(offlinePlayer.getUniqueId());
			uuid = offlinePlayer.getUniqueId();
		} else if (offlinePlayer.isOnline()) {
			guild = plugin.getGuildManager().getGuildByPlayer(offlinePlayer.getPlayer().getUniqueId());
			uuid = offlinePlayer.getPlayer().getUniqueId();
		}

		if (guild == null) {
			player.sendMessage(ChatColor.RED + "No guild by that player was found.");
			return;
		}
		
		if (playerGuild == guild) {
			player.sendMessage(ChatColor.RED + "That player is not in your Guild.");
			return;
		}

		PermissionType permissionType = PermissionType.getPermissionType(StringUtils.isAlphanumeric(args[2]) ? args[2] : Integer.parseInt(args[2]));
		if (permissionType == null) {
			player.sendMessage(ChatColor.RED + "Invalid Permission Type - Please use one of the following:");
			Arrays.stream(PermissionType.values()).forEach(type -> player.sendMessage(ChatColor.RED.toString() + type.getId() + ". "+ type.getName() + " - " + type.getDescription()));
			return;
		}
		
		String action = args[3].toLowerCase();
		switch(action) {
		case "add": {
			Member member = plugin.getGuildManager().getMemberByUuid(uuid);
			if (member.getValidPermissions().contains(permissionType)) {
				player.sendMessage(ChatColor.YELLOW + args[1] + " already has permission to " + permissionType.getFriendlyName());
				return;
			}
			member.getValidPermissions().add(permissionType);
			player.sendMessage(ChatColor.GREEN + args[1] + ChatColor.YELLOW + " now has the permission to " + ChatColor.GREEN + permissionType.getFriendlyName());
			break;
		}
		case "remove": {
			Member member = plugin.getGuildManager().getMemberByUuid(uuid);
			if (!member.getValidPermissions().contains(permissionType)) {
				player.sendMessage(ChatColor.YELLOW + args[1] + " does not have permission to " + permissionType.getFriendlyName());
				return;
			}
			member.getValidPermissions().remove(permissionType);
			break;
		}
		default: {
			player.sendMessage(ChatColor.RED + "Invalid permission action.");
			player.sendMessage(ChatColor.RED + "Valid permission actions: add, remove");
		}
		}
	}

}
