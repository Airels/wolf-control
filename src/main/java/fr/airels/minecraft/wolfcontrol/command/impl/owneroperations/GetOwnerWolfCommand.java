package fr.airels.minecraft.wolfcontrol.command.impl.owneroperations;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.airels.minecraft.wolfcontrol.command.WolfCommand;
import fr.airels.minecraft.wolfcontrol.exception.InvalidTargetException;
import fr.airels.minecraft.wolfcontrol.exception.SenderNotPlayerException;
import fr.airels.minecraft.wolfcontrol.utils.PlayerSenderExtractor;
import fr.airels.minecraft.wolfcontrol.utils.WolfExtractor;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class GetOwnerWolfCommand implements WolfCommand {

    @Override
    public String getLabel() {
        return "getowner";
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) throws CommandSyntaxException {
        Player player = PlayerSenderExtractor.findPlayer(commandSourceStack).orElseThrow(SenderNotPlayerException::new);
        Wolf wolf = WolfExtractor.findTargetedWolf(player, 10)
                .orElseThrow(() -> new InvalidTargetException("Please look at a wolf first."));

        AnimalTamer owner = wolf.getOwner();
        if (wolf.getOwner() == null) {
            player.sendMessage(Component.text("This wolf does not have an owner.", NamedTextColor.BLUE));
            return;
        }

        Component responseText = Component.text("The wolf");

        if (wolf.customName() != null) {
            responseText = responseText.append(Component.text(" named "))
                    .append(wolf.customName().color(NamedTextColor.YELLOW));
        }

        responseText = responseText.append(Component.text(" belongs to "));

        String ownerName = owner.getName();
        if (ownerName == null) {
            responseText = responseText.append(Component.text("an unknown player.", NamedTextColor.RED));
        } else if (owner.equals(player)) {
            responseText = responseText.append(Component.text("you", NamedTextColor.GREEN))
                    .append(Component.text("!", NamedTextColor.WHITE));
        } else {
            responseText = responseText.append(Component.text(ownerName, NamedTextColor.YELLOW));
        }

        player.sendMessage(responseText);
    }

}
