package me.sorin.ignite;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class IgniteListener implements Listener
{
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
    
        Player player = event.getPlayer();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
        if(bPlayer == null) return;
        
        if(bPlayer.canBend(CoreAbility.getAbility(Ignite.class))) {
            new Ignite(player, event.getClickedBlock(), event.getBlockFace());
        }
    }
}
