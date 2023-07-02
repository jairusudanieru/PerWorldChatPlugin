package plugin.perworldchatplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.perworldchatplugin.Commands.Pwcp;
import plugin.perworldchatplugin.Events.*;

public final class PerWorldChatPlugin extends JavaPlugin {

    public void checkAuthMePlugin() {
        Plugin authMe = Bukkit.getServer().getPluginManager().getPlugin("AuthMe");
        if (authMe != null) {
            Bukkit.getPluginManager().registerEvents(new AuthMeEvent(this),this);
        } else {
            Bukkit.getPluginManager().registerEvents(new PlayerJoin(this),this);
        }
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        checkAuthMePlugin();
        PluginCommand command = Bukkit.getPluginCommand("pwcp");
        if (command != null) command.setExecutor(new Pwcp(this));
        Bukkit.getPluginManager().registerEvents(new ChangeWorld(this),this);
        Bukkit.getPluginManager().registerEvents(new PlayerChat(this),this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(this),this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(this),this);
        Bukkit.getLogger().info("[PerWorldChatPlugin] Plugin Successfully Enabled!");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("[PerWorldChatPlugin] Plugin Successfully Disabled!");
    }
}
