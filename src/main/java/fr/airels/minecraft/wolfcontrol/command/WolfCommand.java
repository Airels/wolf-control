package fr.airels.minecraft.wolfcontrol.command;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface WolfCommand extends BasicCommand {

    String getLabel();

    default List<String> getAliases() {
        return Collections.emptyList();
    }

    default String getDescription() {
        StringBuilder desc = new StringBuilder("No description provided. Usage: /" + getLabel());

        List<String> aliases = getAliases();
        if (aliases != null && !aliases.isEmpty()) {
            desc.append(" (Aliases: ")
                    .append(String.join(", ", getAliases()))
                    .append(")");
        }

        return desc.toString();
    }

    default boolean isDeactivated() {
        return false;
    }

    @Override
    default Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return commandSourceStack.getSender().getServer().getOnlinePlayers().stream()
                .map(Player::getName)
                .filter(name -> name.startsWith(args[1]))
                .toList();
    }
}
