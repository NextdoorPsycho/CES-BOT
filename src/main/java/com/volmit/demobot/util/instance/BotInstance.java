package com.volmit.demobot.util.instance;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.volmit.demobot.Core;
import com.volmit.demobot.CESBot;
import com.volmit.demobot.commands.slash.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.util.Objects;


public class BotInstance {

    private final JDA jda;
    public BotInstance(String s) throws LoginException, InterruptedException {
        if (Objects.equals(Core.get().botToken, "")) {
            CESBot.error("YOU NEED TO GIVE A VALID BOT TOKEN");
            System.exit(0);
        }
        CommandClientBuilder builder = new CommandClientBuilder();// [DONT TOUCH]
        // Slash Commands Below:
        builder.addSlashCommand(new PingCommand());
        builder.addSlashCommand(new BoothCommand());

        // End of Slash Commands
        builder.forceGuildOnly(Core.get().discordGuildID);
        builder.setOwnerId(Core.get().botOwnerID);
        CommandClient commandClient = builder.build();
        jda = JDABuilder.createDefault(s)
                .setChunkingFilter(ChunkingFilter.ALL) // enable member chunking for all guilds
                .setMemberCachePolicy(MemberCachePolicy.ALL) // ignored if chunking enabled
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES)
                .addEventListeners(commandClient)
                .build().awaitReady();
        jda.getPresence().setStatus(OnlineStatus.IDLE);
        jda.getPresence().setActivity(Activity.watching(Core.get().botActivityMessage));
        CESBot.info(Core.get().botInstanceMessage);
    }

    public void close() {
        CESBot.info("Terminating Bot Instance");
        jda.shutdown();
    }

    public JDA getJDA() {
        return jda;
    }
}
