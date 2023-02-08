package me.sorin.ignite;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.FireAbility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Fire;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class Ignite extends FireAbility implements AddonAbility
{
    
    private Listener listener;
    private Permission perm;
    
    private Block fire;
    
    public Ignite(Player player, Block block, BlockFace blockFace)
    {
        super(player);
        
        bPlayer.addCooldown(this);
        setOnFire(block, blockFace);
        start();
    }
    
    private void setOnFire(Block block, BlockFace blockFace)
    {
        fire = block.getRelative(blockFace);
        createTempFire(fire.getLocation(), 3000);
        FireAbility.playFirebendingSound(fire.getLocation());
    }
    
    @Override
    public void progress()
    {
        Location loc = fire.getLocation();
        loc.add(0.5, 0.5, 0.5);
    
        if(fire.getType() != Material.FIRE && fire.getType() != Material.SOUL_FIRE) {
            remove();
        }
        
        if(bPlayer.hasSubElement(Element.BLUE_FIRE)) {
            loc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, loc, 10, 0.1, 0.01, 0.1, 0.05);
        } else {
            loc.getWorld().spawnParticle(Particle.FLAME, loc, 10, 0.1, 0.01, 0.1, 0.05);
        }
        
        if(getStartTime() + 3000 < System.currentTimeMillis()) {
            if(fire.getType() == Material.FIRE) {fire.setType(Material.AIR); }
            remove();
        }
        
    }
    
    @Override
    public Location getLocation()
    {
        return player.getLocation();
    }
    
    @Override
    public long getCooldown()
    {
        return 1500;
    }
    
    @Override
    public String getDescription() {
        return "Focus on a spot and set it ablaze\nUsage: Right Click a Block";
    }
    
    @Override
    public void load()
    {
        // register a listener
        listener = new IgniteListener();
        ProjectKorra.plugin.getServer().getPluginManager().registerEvents(listener, ProjectKorra.plugin);
        // register a permission
        perm = new Permission("bending.ability.ignite");
        perm.setDefault(PermissionDefault.OP);
        ProjectKorra.plugin.getServer().getPluginManager().addPermission(perm);
        // initialize default config
    }
    
    @Override
    public void stop()
    {
        // remove listener
        HandlerList.unregisterAll(listener);
        // remove permission
        ProjectKorra.plugin.getServer().getPluginManager().removePermission(perm);
    }
    
    @Override
    public boolean isSneakAbility()
    {
        return false;
    }
    
    @Override
    public boolean isHarmlessAbility()
    {
        return false;
    }
    
    @Override
    public String getAuthor()
    {
        return "Soringaming";
    }
    
    @Override
    public String getVersion()
    {
        return "1.0.0";
    }
    
    @Override
    public String getName()
    {
        return "Ignite";
    }
}
