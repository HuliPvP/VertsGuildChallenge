package me.hulipvp.guilds.command.api;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lombok.Getter;
import me.hulipvp.guilds.Guilds;
import me.hulipvp.guilds.structure.member.Role;

@Getter
public abstract class StringArgument {

	public Guilds plugin;
	
    private String[] aliases;
    private String description;
    private String permission;
    private boolean playerOnly;
    private Role requiredRole;

    public StringArgument() {
    	this.plugin = Guilds.getInstance();
        this.aliases = aliases();
        this.description = description();
        this.permission = permission();
        this.playerOnly = playerOnly();
        this.requiredRole = requiredRole();
    }

    public void executeArgument(CommandSender sender, String label, String[] args) {
        if (this.playerOnly && !(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Player only argument.");
            return;
        } 
        
        if (this.requiredRole != null && plugin.getGuildManager().getMemberByUuid(((Player)sender).getUniqueId()).getRole() != this.requiredRole) {
            sender.sendMessage(ChatColor.RED + "You do not have the required role for this Guild command.");
            return;
        } 
        
        onArgument(sender, label, args);
    }
    
    public abstract String[] aliases();
    
    public abstract String description();
    
    public abstract String permission();
    
    public abstract boolean playerOnly();
    
    public abstract Role requiredRole();

    public abstract void onArgument(CommandSender sender, String label, String[] args);

}