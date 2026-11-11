package fr.airels.minecraft.wolfcontrol.event;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class WolfTargetEventHandler implements Listener {

    @EventHandler
    public void onWolfTarget(EntityTargetLivingEntityEvent event) {
        LivingEntity target = event.getTarget();

        if (event.getEntity() instanceof Wolf wolf && isEntityTamedByPlayer(wolf) && (isPlayer(target) || isEntityTamedByPlayer(target))) {
            event.setCancelled(true);
        }
    }

    private boolean isPlayer(LivingEntity target) {
        return target instanceof Player;
    }

    private boolean isEntityTamedByPlayer(LivingEntity target) {
        if (target instanceof Wolf wolf) {
            return wolf.isTamed() && wolf.getOwner() instanceof Player;
        }

        return false;
    }

}
