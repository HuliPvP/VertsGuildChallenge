package me.hulipvp.guilds.command.api;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;

import lombok.Getter;
import me.hulipvp.guilds.command.api.annotation.Command;

@Getter
public class CommandManager {

    private CommandApi instance;
    // alphabetically sorted hashmap
    private TreeMap<CommandBase, Method> registeredCommands = new TreeMap<>();
    private CommandMap cmap;

    public CommandManager(CommandApi instance) {
        this.instance = instance;
        setupCommandMap();
    }

    /**
     * Setup the command map for usage
     */
    private void setupCommandMap() {
        try {
            Field field = instance.getPlugin().getServer().getPluginManager().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            cmap = (CommandMap) field.get(instance.getPlugin().getServer().getPluginManager());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Register all methods with the @Command annotation to the Bukkit command
     * map in the given object's class
     * 
     * @param obj
     *            - The object
     */
    public void registerCommands(Object obj) {
        if (obj instanceof Listener) {
            Listener listener = (Listener) obj;
            instance.getPlugin().getServer().getPluginManager().registerEvents(listener, this.instance.getPlugin());
        }

        List<Method> commandMethods = new ArrayList<>();

        for (Method method : obj.getClass().getMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                commandMethods.add(method);
            }
        }
        
        for (Method method : commandMethods) {
            Command command = method.getAnnotation(Command.class);

            String commandName = command.name().toLowerCase();
            CommandBase commandBase = new CommandBase(this.instance, commandName, command.desc(), command.usage(), Arrays.asList(command.aliases()), obj, command.playerOnly(), command.isPublic());

            commandBase.setPermission("guilds.cmd." + commandName);
            commandBase.setPermissionMessage(ChatColor.RED + "No permission.");

            registeredCommands.put(commandBase, method);
            cmap.register(instance.getPlugin().getDescription().getName(), commandBase);
        }

    }
}