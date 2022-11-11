package com.volmit.demobot.commands.slash;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import com.volmit.demobot.CESBot;
import com.volmit.demobot.util.VolmitEmbed;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BoothCommand extends SlashCommand {
    public enum TE {
        WEST(1),
        EAST(2),
        SOUTH(3),
        NORTH(4);
        private int value;

        private TE(int value) { // Do something with this for the What/Three/Words Garbage
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public BoothCommand() {
        this.name = "booth"; // This has to be lowercase
        this.help = "/booth <name> <building> <booth_number> <location> [<tag>...]";
        this.options = List.of(
                new OptionData(OptionType.STRING, "name", "The name of the booth", true),
                new OptionData(OptionType.STRING, "building", "The building the booth is in", true), // This is where the First Set of Options will be
                new OptionData(OptionType.STRING, "booth-number", "JUST THE NUMBER, NOTHING ELSE", true),
                new OptionData(OptionType.STRING, "what-three-words-location", "The location of the booth using the What/Three/Words Location API", true),
                new OptionData(OptionType.STRING, "tag0", "The tag of the booth", true),
                new OptionData(OptionType.STRING, "tag1", "The tag of the booth", false),
                new OptionData(OptionType.STRING, "tag2", "The tag of the booth", false),
                new OptionData(OptionType.STRING, "tag3", "The tag of the booth", false),
                new OptionData(OptionType.STRING, "tag4", "The tag of the booth", false),
                new OptionData(OptionType.STRING, "tag5", "The tag of the booth", false)
        );
        List<Command.Choice> tags = List.of(
                new Command.Choice("XR", "XR"),
                new Command.Choice("AR", "AR"),
                new Command.Choice("VR", "VR"),
                new Command.Choice("AI", "AI"),
                new Command.Choice("Transportation", "Transportation"),
                new Command.Choice("Robotics", "Robotics"),
                new Command.Choice("3D Printing", "3D Printing"),
                new Command.Choice("Cybersecurity", "Cybersecurity"),
                new Command.Choice("IoT", "IoT"),
                new Command.Choice("Distribution", "Distribution"),
                new Command.Choice("Health", "Health"),
                new Command.Choice("Education", "Education"),
                new Command.Choice("Manufacturing", "Manufacturing"),
                new Command.Choice("Energy", "Energy"),
                new Command.Choice("Wearable", "Wearable"),
                new Command.Choice("Software", "Software"),
                new Command.Choice("Hardware", "Hardware"),
                new Command.Choice("Accessibility", "Accessibility"),
                new Command.Choice("Home", "Home"),
                new Command.Choice("I DONT KNOW BUT MARIA NEEDS TO SEE THIS", "Maria"),
                new Command.Choice("Other", "Other")
        );

        options.get(1).addChoice(String.valueOf(TE.WEST), String.valueOf(TE.WEST.getValue())); // First Set of Options
        options.get(1).addChoice(String.valueOf(TE.EAST), String.valueOf(TE.EAST.getValue()));
        options.get(1).addChoice(String.valueOf(TE.SOUTH), String.valueOf(TE.SOUTH.getValue()));
        options.get(1).addChoice(String.valueOf(TE.NORTH), String.valueOf(TE.NORTH.getValue()));
        options.get(4).addChoices(tags);
        options.get(5).addChoices(tags);
        options.get(6).addChoices(tags);
        options.get(7).addChoices(tags);
        options.get(8).addChoices(tags);
        options.get(9).addChoices(tags);


        this.category = new Category("CES - 2022"); // This is where the command will show up in the help menu
    }

    @Override
    public void execute(SlashCommandEvent e) {
        // Sends a "<bot> is thinking..." response and allows you a delayed response.
        CESBot.info("Booth Command Executed");
        OptionMapping name = e.getOption("name");
        String boothName = name == null ? "No Name" : name.getAsString();
        OptionMapping building = e.getOption("building");
        String boothBuilding = building == null ? "No Building" : building.getAsString();
        OptionMapping boothNumber = e.getOption("booth-number");
        String boothNumberString = boothNumber == null ? "No Booth Number" : boothNumber.getAsString();
        Guild guild = e.getGuild();


        validateExhibitsChannel(e.getGuild());
        validateBoothChannel(e, e.getGuild(), boothName, boothNumberString, boothBuilding);
        e.deferReply().queue(
                m -> m.setEphemeral(true).editOriginal("Booth Page Created!").queueAfter(0, TimeUnit.SECONDS)
        );
    }

    private VolmitEmbed getChannelEmbed(String name, String boothNumber, String building, String location, String tag0, String tag1, String tag2, String tag3, String tag4, String tag5) {
        VolmitEmbed embed = new VolmitEmbed();
        embed.setTitle(name + " - Booth " + boothNumber);
        embed.setDescription("What3Words Location: " + location);
        embed.addField("Building", building, true);
        embed.addField("Booth Number", String.valueOf(boothNumber), true);
        embed.addField("Tags: ", tag0 + " " + tag1 + " " + tag2 + " " + tag3 + " " + tag4 + " " + tag5, true);
        return embed;
    }

    private void validateExhibitsChannel(Guild guild) {
        // Check if the channel is an exhibit channel
        if (guild.getCategoriesByName("exhibits", true).size() == 0) {
            // Create the channel
            guild.createCategory("exhibits")
                    //Perms or anything here
                    .queue();
        }
    }

    private String validateBoothChannel(SlashCommandEvent e, Guild guild, String boothName, String boothNumber, String buildingEnum) {
        //Makes the Booth channel if it doesn't exist
        OptionMapping location = e.getOption("what-three-words-location");
        String boothLocation = location == null ? "No Location" : location.getAsString();
        OptionMapping tag0 = e.getOption("tag0");
        String boothTag0 = tag0 == null ? "" : tag0.getAsString();
        OptionMapping tag1 = e.getOption("tag1");
        String boothTag1 = tag1 == null ? "" : tag1.getAsString();
        OptionMapping tag2 = e.getOption("tag2");
        String boothTag2 = tag2 == null ? "" : tag2.getAsString();
        OptionMapping tag3 = e.getOption("");
        String boothTag3 = tag3 == null ? "" : tag3.getAsString();
        OptionMapping tag4 = e.getOption("tag4");
        String boothTag4 = tag4 == null ? "" : tag4.getAsString();
        OptionMapping tag5 = e.getOption("tag5");
        String boothTag5 = tag5 == null ? "" : tag5.getAsString();

        String chatBooth = boothName + "-" + buildingEnum + "-" + boothNumber;
        if (guild.getTextChannelsByName(chatBooth, true).size() == 0) {
            // Create the channel
            guild.createTextChannel(chatBooth, guild.getCategoriesByName("exhibits", true).get(0))
                    //Perms or anything here
                    .queue( // This is where you can do something with the channel
                            channel -> channel.sendMessageEmbeds(getChannelEmbed(
                                    boothName,
                                    boothNumber,
                                    buildingEnum,
                                    boothLocation,
                                    boothTag0,
                                    boothTag1,
                                    boothTag2,
                                    boothTag3,
                                    boothTag4,
                                    boothTag5
                            ).build()).queue()
                    );
        }
        return chatBooth;
    }
}