package com.mewin.WGMobDamageFlags;

import com.mewin.util.Util;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

/**
 *
 * @author  mewin
 */
public final class Utils {
    
    public static boolean mobDamageAllowedAtLocation(WorldGuardPlugin wgp, MobType mobType, Location loc)
    {
        return Util.flagAllowedAtLocation(wgp, mobType, loc, WGMobDamageFlagsPlugin.MOB_DAMAGE_ALLOW_FLAG, WGMobDamageFlagsPlugin.MOB_DAMAGE_DENY_FLAG, MobType.ANY);
    }
    
    public static MobType mobTypeForEntity(EntityType entityType)
    {
        try
        {
            return MobType.valueOf(entityType.name());
        }
        catch(IllegalArgumentException ex)
        {
            return MobType.OTHER;
        }
    }
}