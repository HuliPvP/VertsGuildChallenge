package me.hulipvp.guilds.command.api;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;

@Getter
public class CommandApi {

    private JavaPlugin plugin;
    private CommandManager commandManager;

    public CommandApi(JavaPlugin plugin) {
        this.plugin = plugin;
        commandManager = new CommandManager(this);
    }

    public void error(String msg) {
        plugin.getLogger().log(Level.SEVERE, msg);
    }
}