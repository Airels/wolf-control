package fr.airels.minecraft.wolfcontrol.config;

import fr.airels.minecraft.wolfcontrol.command.WolfCommand;
import fr.airels.minecraft.wolfcontrol.command.impl.CountOwnedWolvesCommand;
import fr.airels.minecraft.wolfcontrol.command.impl.RenameWolfCommand;
import fr.airels.minecraft.wolfcontrol.command.impl.owneroperations.GetOwnerWolfCommand;
import fr.airels.minecraft.wolfcontrol.command.impl.owneroperations.SetOwnerWolfCommand;

import java.util.List;

public class CommandConfig {

    public static final List<WolfCommand> WOLF_COMMANDS = List.of(
            new GetOwnerWolfCommand(),
            new SetOwnerWolfCommand(),
            new CountOwnedWolvesCommand(),
            new RenameWolfCommand()
    );

    private CommandConfig() {
        // not instantiable
    }

}
