package fr.airels.minecraft.wolfcontrol.command;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.airels.minecraft.wolfcontrol.config.CommandConfig;
import fr.airels.minecraft.wolfcontrol.exception.UnknownCommandException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WolfCommandHandlerCommand implements WolfCommand {

    private static final String COMMAND = "wc";
    private static final List<String> ALIASES = List.of("wolfcontrol", "wolf");

    @Override
    public void execute(@NonNull CommandSourceStack commandSourceStack, String @NonNull [] args) throws CommandSyntaxException {
        var sender = commandSourceStack.getSender();

        if (args.length == 0) {
            sendAvailableCommands(sender);
            return;
        }

        String command = args[0];
        for (var wolfCommand : CommandConfig.WOLF_COMMANDS) {
            if (!wolfCommand.isDeactivated() && wolfCommand.getLabel().equalsIgnoreCase(command)) {
                wolfCommand.execute(commandSourceStack, args);
                return;
            }
        }

        throw new UnknownCommandException(command);
    }

    @Override
    public Collection<String> suggest(@NonNull CommandSourceStack commandSourceStack, String[] args) {
        if (args.length < 2) {
            String prefixCommand = args.length > 0 ? args[0] : "";
            return CommandConfig.WOLF_COMMANDS.stream()
                    .filter(wolfCommand -> !wolfCommand.isDeactivated())
                    .map(WolfCommand::getLabel)
                    .filter(label -> label.startsWith(prefixCommand.toLowerCase()))
                    .toList();
        }

        String command = args[0];
        for (var wolfCommand : CommandConfig.WOLF_COMMANDS) {
            if (!wolfCommand.isDeactivated() && (wolfCommand.getLabel().equalsIgnoreCase(command))) {
                return wolfCommand.suggest(commandSourceStack, args);
            }
        }

        return Collections.emptyList();
    }

    @Override
    public String getLabel() {
        return COMMAND;
    }

    @Override
    public List<String> getAliases() {
        return ALIASES;
    }

    private void sendAvailableCommands(CommandSender sender) {
        Component response = Component.text("Available commands:");

        for (WolfCommand wolfCommand : CommandConfig.WOLF_COMMANDS) {
            if (!wolfCommand.isDeactivated()) {
                response = response.appendNewline()
                        .appendSpace()
                        .append(Component.text("- " + wolfCommand.getLabel()));

            }
        }

        sender.sendMessage(response.color(NamedTextColor.GREEN));
    }

}
