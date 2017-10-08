package me.hulipvp.guilds.structure.permission;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionType {
	
	USE_CHESTS(1, "useChests", "Allows Members to access chests", "use chests"),
	ACCESS_BANK(2, "accessBank", "Allows Members to access the Guild bank", "access the bank"),
	INVITE_PLAYERS(3, "invitePlayers", "Allows Members to invite other players", "invite players"),
	KICK_PLAYERS(4, "kickPlayers", "Allows Members to kick other players", "kick players"),
	SET_MOTD(5, "setMotd", "Allows Members to set the MOTD of the Guild", "set the MOTD"),
	MANAGE_PERMISSIONS(6, "managerPermissions", "Allows Members to edit other Member's permissions", "manage permissions");
	
	private Integer id;
	private String name;
	private String description;
	private String friendlyName;
	
	public static PermissionType getPermissionType(Object obj) {
		PermissionType permissionType = null;
		if (obj instanceof String) {
			permissionType = Arrays.stream(PermissionType.values()).filter(type -> type.getName().equalsIgnoreCase((String)obj)).findFirst().orElse(null);
		} else if (obj instanceof Integer) {
			permissionType = Arrays.stream(PermissionType.values()).filter(type -> type.getId() == (int)obj).findFirst().orElse(null);
		}
		return permissionType;
	}
	
}
