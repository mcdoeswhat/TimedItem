package me.albert.timeditem;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Date;


public class TIListener implements Listener {
    @EventHandler (ignoreCancelled = true)
    public void onClick(InventoryClickEvent e){
        if (e.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)){
            return;
        }
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR){
            return;
        }
        ItemStack is = e.getCurrentItem();
        Player p = (Player)e.getWhoClicked();
        checkItem(is,p,Config.on_click);


    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
            if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                return;
            }
            if (e.getItem() == null || e.getItem().getType() == Material.AIR) {
                return;
            }
            ItemStack is = e.getItem();
            Player p = e.getPlayer();
            checkItem(is, p, true);
    }

    @EventHandler (ignoreCancelled = true)
    public void onDamage(EntityDamageEvent e){
        if (!(e.getEntity() instanceof Player)){
            return;
        }
        Player p = (Player) e.getEntity();
        if (p.getGameMode().equals(GameMode.CREATIVE)){
            return;
        }
        PlayerInventory inv = p.getInventory();
        ItemStack[] armors = inv.getArmorContents();
        for (ItemStack is : armors){
            if (is != null && is.getType() != Material.AIR){
                checkItem(is,p,true);
            }

        }


    }
    public void checkItem(ItemStack is, Player p,Boolean b){
        if (!Utils.isTIitem(is)){
            return;
        }
        String type = Utils.getTItype(is);
        switch (type) {
            case "expire":
                Date date = new Date();
                if (Utils.getDate(is).before(date)) {
                    playSound(p);
                    is.setAmount(0);
                    p.setMaxHealth(20);
                    p.sendMessage(Config.prefix + Config.expire);
                }
                return;
            case "change":
                if (!b){
                    return;
                }
                Utils.changeItem(is);
        }

    }
    public void playSound(Player p){
        if (Config.Sounds){
            p.playSound(p.getLocation(),Sounds.BLAZE_DEATH.bukkitSound(),10,1.2F);
        }

    }



}
