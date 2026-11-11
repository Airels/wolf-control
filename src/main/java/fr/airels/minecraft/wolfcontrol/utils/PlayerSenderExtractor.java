package fr.airels.minecraft.wolfcontrol.utils;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;

import java.util.Optional;

public class PlayerSenderExtractor {

    public static Optional<Player> findPlayer(CommandSourceStack commandSourceStack) {
        if (commandSourceStack.getSender() instanceof Player player) {
            return Optional.of(player);
        }

        return Optional.empty();
    }

    private PlayerSenderExtractor() {
        // not instantiable
    }

}
