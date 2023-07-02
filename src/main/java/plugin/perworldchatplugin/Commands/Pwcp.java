package plugin.perworldchatplugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Pwcp implements CommandExecutor, TabCompleter {

    private final JavaPlugin plugin;
    public Pwcp(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("reload");
            return list;
        } else {
            return null;
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("pwcp")) {
            if (args.length != 1) return false;
            plugin.reloadConfig();
            sender.sendMessage("Plugin reloaded successfully.");
            return true;
        }
        return false;
    }

}
