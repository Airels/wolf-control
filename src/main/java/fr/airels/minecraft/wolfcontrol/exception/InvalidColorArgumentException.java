package fr.airels.minecraft.wolfcontrol.exception;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import java.util.List;

public class InvalidColorArgumentException extends CommandSyntaxException {

    public InvalidColorArgumentException(String color) {
        super(null, () -> "Invalid color argument: " + color);
    }

    public InvalidColorArgumentException(String color, List<String> validColors) {
        super(null, () -> "Invalid color argument: " + color + ". Valid colors are: " + String.join(", ", validColors));
    }

}
