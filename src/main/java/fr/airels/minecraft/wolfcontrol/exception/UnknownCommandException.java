package fr.airels.minecraft.wolfcontrol.exception;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class UnknownCommandException extends CommandSyntaxException {

    public UnknownCommandException(String command) {
        super(null, () -> "Unknown command: " + command);
    }

}
