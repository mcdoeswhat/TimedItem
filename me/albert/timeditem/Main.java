package me.albert.timeditem;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    CommandSender cs = Bukkit.getConsoleSender();
    private static Main instance;
    @Override
    public void onEnable(){
        cs.sendMessage("§7[§bTimedItem§7] §aLoaded!");
        Bukkit.getServer().getPluginManager().registerEvents(new TIListener(),this);
        getCommand("timeditem").setExecutor(new TICommands());
        new MetricsLite(this);
        saveDefaultConfig();
        instance = this;
    }

    public static Main getInstance(){
        return instance;
    }

}
