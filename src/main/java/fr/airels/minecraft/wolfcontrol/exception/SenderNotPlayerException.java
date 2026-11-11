package fr.airels.minecraft.wolfcontrol.exception;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class SenderNotPlayerException extends CommandSyntaxException {

    public static final String SENDER_PLAYER_ONLY_MESSAGE = "This command can only be executed by a player.";

    public SenderNotPlayerException() {
        super(null, () -> SENDER_PLAYER_ONLY_MESSAGE);
    }

}