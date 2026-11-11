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
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.jspecify.annotations.NonNull;

public class SetOwnerWolfCommand implements WolfCommand {

    @Override
    public String getLabel() {
        return "setowner";
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) throws CommandSyntaxException {
        Player player = PlayerSenderExtractor.findPlayer(commandSourceStack).orElseThrow(SenderNotPlayerException::new);
        Wolf wolf = WolfExtractor.findTargetedWolf(player, 10)
                .orElseThrow(() -> new InvalidTargetException("Please look at a tamed wolf to set its owner."));

        if (wolf.getOwner() == null) {
            throw new InvalidTargetException("This wolf is not tamed. You can only set the owner of a tamed wolf.");
        }

        Player newOwner = getNewOwner(args, player, wolf);

        wolf.setOwner(newOwner);
        player.sendMessage(
                Component.text("Owner of the wolf has been set to: ")
                        .append(Component.text(newOwner.getName(), NamedTextColor.GREEN))
        );
    }

    private static @NonNull Player getNewOwner(String[] args, Player player, Wolf wolf) throws InvalidTargetException {
        Server server = player.getServer();
        String newOwnerName = args.length > 1 ? args[1] : null;
        Player newOwner = newOwnerName == null ? player : server.getPlayerExact(newOwnerName);
        if (newOwner == null) {
            throw new InvalidTargetException("Player not found: " + newOwnerName);
        }

        if (!server.getOnlinePlayers().contains(newOwner)) {
            throw new InvalidTargetException("Player " + newOwnerName + " is not online.");
        }

        if (wolf.getOwner().equals(newOwner)) {
            if (player.equals(newOwner)) {
                throw new InvalidTargetException("You already own this wolf!");
            }

            throw new InvalidTargetException("This player is already the owner of the wolf.");
        }

        return newOwner;
    }

}
