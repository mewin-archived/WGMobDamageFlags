/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mewin.WGMobDamageFlags;

import com.mewin.WGCustomFlags.WGCustomFlagsPlugin;
import com.mewin.WGCustomFlags.flags.CustomSetFlag;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.EnumFlag;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author mewin<mewin001@hotmail.de>
 */
public class WGMobDamageFlagsPlugin extends JavaPlugin {
    private static final EnumFlag MOB_FLAG = new EnumFlag("mobs", MobType.class);
    public static final CustomSetFlag MOB_DAMAGE_ALLOW_FLAG = new CustomSetFlag("mob-damage-allow", MOB_FLAG);
    public static final CustomSetFlag MOB_DAMAGE_DENY_FLAG = new CustomSetFlag("mob-damage-deny", MOB_FLAG);
    
    private WGCustomFlagsPlugin custPlugin;
    private WorldGuardPlugin wgPlugin;
    private DamageListener listener;
    
    @Override
    public void onEnable()
    {
        custPlugin = getCustPlugin();
        wgPlugin = getWGPlugin();
        
        if (custPlugin == null)
        {
            getLogger().warning("This plugin requires WorldGuard Custom Flags, disabling.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        if (wgPlugin == null)
        {
            getLogger().warning("This plugin requires WorldGuard, disabling.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        listener = new DamageListener(wgPlugin, this);
        getServer().getPluginManager().registerEvents(listener, this);
        
        custPlugin.addCustomFlag(MOB_DAMAGE_ALLOW_FLAG);
        custPlugin.addCustomFlag(MOB_DAMAGE_DENY_FLAG);
    }
    
    private WorldGuardPlugin getWGPlugin()
    {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
        
        if (plugin == null || ! (plugin instanceof WorldGuardPlugin))
        {
            return null;
        }
        
        return (WorldGuardPlugin) plugin;
    }
    
    private WGCustomFlagsPlugin getCustPlugin()
    {
        Plugin plugin = getServer().getPluginManager().getPlugin("WGCustomFlags");
        
        if (plugin == null || ! (plugin instanceof WGCustomFlagsPlugin))
        {
            return null;
        }
        
        return (WGCustomFlagsPlugin) plugin;
    }
}
