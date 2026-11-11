package fr.airels.minecraft.wolfcontrol.exception;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class InvalidTargetException extends CommandSyntaxException {

    public InvalidTargetException(String message) {
        super(null, () -> message);
    }
}
