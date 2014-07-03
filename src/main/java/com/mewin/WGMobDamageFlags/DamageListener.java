package com.mewin.WGMobDamageFlags;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 *
 * @author mewin<mewin001@hotmail.de>
 */
public class DamageListener implements Listener {
    private WorldGuardPlugin wgPlugin;
    private WGMobDamageFlagsPlugin plugin;
    
    public DamageListener(WorldGuardPlugin wgPlugin, WGMobDamageFlagsPlugin plugin)
    {
        this.wgPlugin = wgPlugin;
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e)
    {
        if (e.getEntityType() == EntityType.PLAYER)
        {
            return;
        }
        if (!Utils.mobDamageAllowedAtLocation(wgPlugin, Utils.mobTypeForEntity(e.getEntityType()), e.getEntity().getLocation()))
        {
            e.setCancelled(true);
            
            if (e instanceof EntityDamageByEntityEvent)
            {
                EntityDamageByEntityEvent ebe = (EntityDamageByEntityEvent) e;
                
                if (ebe.getDamager() instanceof Player)
                {
                    if (((Player) ebe.getDamager()).isOp())
                    {
                        e.setCancelled(false);
                    }
                    else
                    {
                        ((Player) ebe.getDamager()).sendMessage(ChatColor.RED + "You may not hurt this " + e.getEntityType().name().toLowerCase().replace("_", " ") + ".");
                    }
                }
                else if (ebe.getDamager() instanceof Projectile && ((Projectile) ebe.getDamager()).getShooter() instanceof Player)
                {
                    if (((Player) (((Projectile) ebe.getDamager()).getShooter())).isOp())
                    {
                        e.setCancelled(false);
                    }
                    else
                    {
                        ((Player) (((Projectile) ebe.getDamager()).getShooter())).sendMessage(ChatColor.RED + "You may not hurt this " + e.getEntityType().name().toLowerCase().replace("_", " ") + ".");
                    }
                }
            }
        }
    }
}
