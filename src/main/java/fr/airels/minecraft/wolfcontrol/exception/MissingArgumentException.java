package fr.airels.minecraft.wolfcontrol.exception;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class MissingArgumentException extends CommandSyntaxException {

    public MissingArgumentException(String message) {
        super(null, () -> message);
    }

}
