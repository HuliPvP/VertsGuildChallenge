package me.hulipvp.guilds.command.api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lombok.Getter;

@Getter
public class CommandBase extends Command implements Comparable<CommandBase> {

    private Object obj; // TODO: Use classes? error:
                        // http://pastebin.com/Bc6CcFVf
    private CommandApi plugin;
    private boolean playerOnly, isPublic;
    private int uses;

    public CommandBase(CommandApi plugin, String name, String description, String usage, List<String> aliases, Object obj, boolean playerOnly, boolean isPublic) {
        super(name, description, usage, aliases);
        this.plugin = plugin;
        this.obj = obj;
        this.playerOnly = playerOnly;
        this.isPublic = isPublic;
    }

    private void executeCommand(CommandSender sender, String label, String[] args) {
        try {
            Method m = this.plugin.getCommandManager().getRegisteredCommands().get(this);
            m.invoke(obj, sender, label, args);
            uses++;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sender.sendMessage(ChatColor.RED + "An error occured while executing command '" + ChatColor.GRAY + label + ChatColor.RED + "'");
            sender.sendMessage(ChatColor.RED + "Stack trace: " + ChatColor.GRAY + ex.toString() + ": " + ex.getMessage());
            sender.sendMessage(ChatColor.RED + "Please send the stacktrace to " + ChatColor.GRAY + "HuliPvP");
            ex.printStackTrace();
        }
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (playerOnly && !(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
            return true;
        }

        if (sender.hasPermission("guilds.cmd.*")) {
            executeCommand(sender, label, args);
            return true;
        }

        if (!isPublic) {
            if (!sender.hasPermission(getPermission())) {
                sender.sendMessage(getPermissionMessage());
                return true;
            }
        }

        executeCommand(sender, label, args);
        return true;
    }

    @Override
    public int compareTo(CommandBase o) {
        return getName().compareTo(o.getName());
    }

}
