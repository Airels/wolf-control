package fr.airels.minecraft.wolfcontrol;

import fr.airels.minecraft.wolfcontrol.command.WolfCommand;
import fr.airels.minecraft.wolfcontrol.command.WolfCommandHandlerCommand;
import fr.airels.minecraft.wolfcontrol.event.WolfDeathEventHandler;
import fr.airels.minecraft.wolfcontrol.event.WolfTargetEventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class WolfControlPlugin extends JavaPlugin {

    private final WolfCommand rootCommand = new WolfCommandHandlerCommand();
    private final List<Listener> listeners = List.of(
            new WolfDeathEventHandler(),
            new WolfTargetEventHandler()
    );

    @Override
    public void onEnable() {
        listeners.forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
        registerCommand(
                rootCommand.getLabel(),
                rootCommand.getDescription(),
                rootCommand.getAliases(),
                rootCommand
        );

        getLogger().info("WolfControl has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("WolfControlPlugin has been disabled!");
    }
}
