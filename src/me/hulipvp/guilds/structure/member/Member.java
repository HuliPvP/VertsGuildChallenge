package me.hulipvp.guilds.structure.member;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import me.hulipvp.guilds.structure.permission.PermissionType;

@Getter
public class Member {
	
	private UUID uuid;
	private Role role;
	private List<PermissionType> validPermissions;
	
	/**
	 * The constructor to create a default Member
	 * 
	 * @param uuid - The player's UUID
	 */
	public Member(UUID uuid) {
		this(uuid, Role.MEMBER);
	}
	
	/**
	 * The main constructor to instantiate a new Member
	 * 
	 * @param uuid - The player's UUID
	 * @param role - The Role you wish to set
	 */
	public Member(UUID uuid, Role role) {
		this.uuid = uuid;
		this.role = role;
		this.validPermissions = new ArrayList<>();
	}

}
