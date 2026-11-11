package fr.airels.minecraft.wolfcontrol.event;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WolfDeathEventHandler implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Wolf wolf && wolf.isTamed()) {
            sendDeathNotificationToOwner(wolf);
        }
    }

    private void sendDeathNotificationToOwner(Wolf wolf) {
        if (wolf.getOwner() instanceof Player owner) sendDeathMessage(owner, wolf);
    }

    private void sendDeathMessage(Player owner, Wolf wolf) {
        Component deathMessage = Optional.ofNullable(wolf.getLastDamageCause())
                .map(EntityDamageEvent::getDamageSource)
                .map(DamageSource::getCausingEntity)
                .map(Entity::getName)
                .map(killerName -> createDeathMessage(Component.text(wolf.getName()), killerName))
                .orElseGet(() -> createDeathMessage(Component.text(wolf.getName()), null));

        owner.sendMessage(deathMessage);
    }

    private Component createDeathMessage(Component wolfName, @Nullable String killerName) {
        Component deathMessage = Component.text("Your wolf ");

        if (wolfName != null) {
            deathMessage = deathMessage.append(wolfName)
                    .append(Component.text(" "));
        }

        deathMessage = deathMessage.append(Component.text("has died."));

        if (killerName != null) {
            deathMessage = deathMessage.append(Component.text(" ("))
                    .append(Component.text("Killed by: "))
                    .append(Component.text(killerName))
                    .append(Component.text(")"));
        }

        return deathMessage.color(NamedTextColor.RED);
    }
}
