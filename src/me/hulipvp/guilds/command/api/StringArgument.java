package me.hulipvp.guilds.command.api;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lombok.Getter;
import me.hulipvp.guilds.structure.Role;

@Getter
public abstract class StringArgument {

    private String[] aliases;
    private String description;
    private String permission;
    private boolean playerOnly;

    public StringArgument() {
        this.aliases = aliases();
        this.description = description();
        this.permission = permission();
        this.playerOnly = playerOnly();
    }

    public void executeArgument(CommandSender sender, String label, String[] args) {
        if (playerOnly && !(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Player only argument.");
        } else {
            onArgument(sender, label, args);
        }
    }
    
    public abstract String[] aliases();
    
    public abstract String description();
    
    public abstract String permission();
    
    public abstract boolean playerOnly();
    
    public abstract Role requiredRole();

    public abstract void onArgument(CommandSender sender, String label, String[] args);

}