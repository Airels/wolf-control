package fr.airels.minecraft.wolfcontrol.utils;

import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

import java.util.Optional;

public class WolfExtractor {

    public static Optional<Wolf> findTargetedWolf(Player player, final int maxDistance) {
        if (player.getTargetEntity(maxDistance) instanceof Wolf wolf) {
            return Optional.of(wolf);
        }

        return Optional.empty();
    }

    private WolfExtractor() {
        // not instantiable
    }

}
