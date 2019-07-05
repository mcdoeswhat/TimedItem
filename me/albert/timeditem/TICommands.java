package me.albert.timeditem;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class TICommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        switch (args.length){
            case 0:
                sendHelp(sender);
                return true;
            case 1:
            case 2:
                if (args[0].equalsIgnoreCase("reload")){
                    Main.getInstance().reloadConfig();
                    Config.loadConfig();
                    sender.sendMessage(Config.prefix+Config.config_reload);
                    return true;
                }
                if (args[0].equalsIgnoreCase("set")){
                    sender.sendMessage(Config.invalid_usage);
                    return true;
                }
                sendHelp(sender);
                return true;
            case 3:
                if (args[0].equalsIgnoreCase("set")){
                    if (!(sender instanceof Player)){
                        sender.sendMessage(Config.prefix+"§aPlayer only!");
                        return true;
                    }
                    Player p = (Player)sender;
                    if (p.getInventory().getItemInMainHand() == null ||
                            p.getInventory().getItemInMainHand().getType() == Material.AIR){
                        p.sendMessage(Config.prefix+Config.no_item);
                        return true;
                    }
                    if (!Utils.isNumeric(args[1]) || !Arrays.asList(Utils.time).contains(args[2])){
                        p.sendMessage(Config.prefix+Config.invalid_usage);
                        return true;
                    }
                    int time = Integer.parseInt(args[1]);
                    ItemStack is =p.getInventory().getItemInMainHand();
                    if (Utils.isTIitem(is)){
                        Utils.removeTime(is);
                    }
                    Utils.addTime(is,time,args[2]);
                    p.sendMessage(Config.prefix+Config.time_set);
                    return true;
                }
                default:
                    sendHelp(sender);
                    return true;



        }
    }

    public void sendHelp(CommandSender sender){
        sender.sendMessage(Config.prefix);
        sender.sendMessage("§a/ti reload ");
        sender.sendMessage("§a/ti set [number] [time format]");

    }
}
