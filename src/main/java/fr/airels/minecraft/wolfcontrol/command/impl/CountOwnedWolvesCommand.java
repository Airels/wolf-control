package fr.airels.minecraft.wolfcontrol.command.impl;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.airels.minecraft.wolfcontrol.command.WolfCommand;
import fr.airels.minecraft.wolfcontrol.exception.SenderNotPlayerException;
import fr.airels.minecraft.wolfcontrol.utils.PlayerSenderExtractor;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

import java.util.Collection;
import java.util.UUID;

public class CountOwnedWolvesCommand implements WolfCommand {

    @Override
    public String getLabel() {
        return "list";
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) throws CommandSyntaxException {
        Player player = PlayerSenderExtractor.findPlayer(commandSourceStack).orElseThrow(SenderNotPlayerException::new);
        UUID playerUuid = player.getUniqueId();

        long tamedWolfCount = Bukkit.getWorlds().stream()
                .map(world -> world.getEntitiesByClass(Wolf.class))
                .flatMap(Collection::stream)
                .filter(Wolf::isTamed)
                .filter(wolf -> wolf.getOwner() != null && wolf.getOwner().getUniqueId().equals(playerUuid))
                .count();

        Component message = Component.text("You own ")
                .append(Component.text(tamedWolfCount, NamedTextColor.GREEN))
                .append(Component.text(tamedWolfCount == 1 ? " wolf." : " wolves.", NamedTextColor.WHITE));

        player.sendMessage(message);
    }

}
