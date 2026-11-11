package fr.airels.minecraft.wolfcontrol.command.impl;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.airels.minecraft.wolfcontrol.command.WolfCommand;
import fr.airels.minecraft.wolfcontrol.exception.InvalidColorArgumentException;
import fr.airels.minecraft.wolfcontrol.exception.InvalidTargetException;
import fr.airels.minecraft.wolfcontrol.exception.MissingArgumentException;
import fr.airels.minecraft.wolfcontrol.exception.SenderNotPlayerException;
import fr.airels.minecraft.wolfcontrol.utils.PlayerSenderExtractor;
import fr.airels.minecraft.wolfcontrol.utils.WolfExtractor;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

import java.util.Collection;
import java.util.Collections;

public class RenameWolfCommand implements WolfCommand {

    private static final int MAX_DISTANCE_IN_BLOCKS = 10;
    private static final NamedTextColor DEFAULT_NAME_COLOR = NamedTextColor.WHITE;

    @Override
    public String getLabel() {
        return "rename";
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) throws CommandSyntaxException {
        Player player = PlayerSenderExtractor.findPlayer(commandSourceStack).orElseThrow(SenderNotPlayerException::new);
        Wolf wolf = WolfExtractor.findTargetedWolf(player, MAX_DISTANCE_IN_BLOCKS)
                .orElseThrow(() -> new InvalidTargetException("Please look at a tamed wolf to rename it."));

        if (!(wolf.getOwner() != null && wolf.getOwner().getUniqueId().equals(player.getUniqueId()))) {
            throw new InvalidTargetException("You can only rename your own tamed wolves.");
        }

        if (args.length < 2) {
            throw new MissingArgumentException("Please provide a new name for the wolf.");
        }

        String newName = args[1];
        NamedTextColor nameColor = DEFAULT_NAME_COLOR; // Default color

        if (args.length >= 3) {
            String rawColor = args[2].toLowerCase();
            nameColor = NamedTextColor.NAMES.value(rawColor);
            if (nameColor == null) {
                throw new InvalidColorArgumentException(rawColor, NamedTextColor.NAMES.keys().stream().toList());
            }
        }

        Component finalName = Component.text(newName, nameColor);
        wolf.customName(finalName);
        player.sendMessage(
                Component.text("Wolf renamed to: ", NamedTextColor.GREEN)
                        .append(finalName)
        );
    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        if (args.length > 3) return Collections.emptyList();

        String rawColor = args.length == 3 ? args[1] : "";
        return NamedTextColor.NAMES.keys().stream()
                .filter(name -> name.startsWith(rawColor))
                .toList();
    }

}
