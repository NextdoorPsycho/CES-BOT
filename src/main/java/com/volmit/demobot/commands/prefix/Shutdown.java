package com.volmit.demobot.commands.prefix;

import com.volmit.demobot.CESBot;
import com.volmit.demobot.commands.VolmitCommand;
import com.volmit.demobot.Core;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;


public class Shutdown extends VolmitCommand {

    // Constructor
    public Shutdown() {
        super(
                "stop",
                new String[]{"stop", "kill", "s"},
                new String[]{Core.get().adminControllerRole},
                "Stops the Bot boi",
                false,
                null
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, MessageReceivedEvent e) {
        CESBot.warn("Terminating the Bot");
        String oidcheck = e.getMessage().getAuthor().getId();
        if (oidcheck.equals(Core.get().botOwnerID)) {
            CESBot.warn("KILLING BOT");
            CESBot.shutdown();
        } else {
            e.getMessage().reply("uR noT my DAddY!").queue();
        }
    }
}
