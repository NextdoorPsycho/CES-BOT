//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.volmit.demobot.commands.slash;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import com.volmit.demobot.CESBot;
import com.volmit.demobot.util.VolmitEmbed;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class BoothCommand extends SlashCommand {
    public BoothCommand() {
        this.name = "booth";
        this.help = "/booth <name> <building> <booth_number> <location> [<tag>...]";
        this.options = List.of(new OptionData(OptionType.STRING, "name", "The name of the booth", true), new OptionData(OptionType.STRING, "building", "The building the booth is in", true), new OptionData(OptionType.STRING, "booth-number", "JUST THE NUMBER, NOTHING ELSE", true), new OptionData(OptionType.STRING, "what-three-words-location", "The location of the booth using the What/Three/Words Location API", true), new OptionData(OptionType.STRING, "tag0", "The tag of the booth", true), new OptionData(OptionType.STRING, "tag1", "The tag of the booth", false), new OptionData(OptionType.STRING, "tag2", "The tag of the booth", false), new OptionData(OptionType.STRING, "tag3", "The tag of the booth", false), new OptionData(OptionType.STRING, "tag4", "The tag of the booth", false), new OptionData(OptionType.STRING, "tag5", "The tag of the booth", false));
        List<Command.Choice> tags = List.of(new Command.Choice("XR", "XR"), new Command.Choice("AR", "AR"), new Command.Choice("VR", "VR"), new Command.Choice("AI", "AI"), new Command.Choice("Transportation", "Transportation"), new Command.Choice("Robotics", "Robotics"), new Command.Choice("3D Printing", "3D Printing"), new Command.Choice("Cybersecurity", "Cybersecurity"), new Command.Choice("IoT", "IoT"), new Command.Choice("Distribution", "Distribution"), new Command.Choice("Health", "Health"), new Command.Choice("Education", "Education"), new Command.Choice("Manufacturing", "Manufacturing"), new Command.Choice("Energy", "Energy"), new Command.Choice("Wearable", "Wearable"), new Command.Choice("Software", "Software"), new Command.Choice("Hardware", "Hardware"), new Command.Choice("Accessibility", "Accessibility"), new Command.Choice("Home", "Home"), new Command.Choice("I DONT KNOW BUT MARIA NEEDS TO SEE THIS", "Maria"), new Command.Choice("Other", "Other"));
        this.options.get(1).addChoice(String.valueOf(BoothCommand.TE.WEST), String.valueOf(BoothCommand.TE.WEST.getValue()));
        this.options.get(1).addChoice(String.valueOf(BoothCommand.TE.EAST), String.valueOf(BoothCommand.TE.EAST.getValue()));
        this.options.get(1).addChoice(String.valueOf(BoothCommand.TE.SOUTH), String.valueOf(BoothCommand.TE.SOUTH.getValue()));
        this.options.get(1).addChoice(String.valueOf(BoothCommand.TE.NORTH), String.valueOf(BoothCommand.TE.NORTH.getValue()));
        this.options.get(4).addChoices(tags);
        this.options.get(5).addChoices(tags);
        this.options.get(6).addChoices(tags);
        this.options.get(7).addChoices(tags);
        this.options.get(8).addChoices(tags);
        this.options.get(9).addChoices(tags);
        this.category = new com.jagrosh.jdautilities.command.Command.Category("CES - 2022");
    }

    public void execute(SlashCommandEvent e) {
        CESBot.info("Booth Command Executed");
        OptionMapping name = e.getOption("name");
        String boothName = name == null ? "No Name" : name.getAsString();
        OptionMapping building = e.getOption("building");
        String boothBuilding = building == null ? "No Building" : building.getAsString();
        OptionMapping boothNumber = e.getOption("booth-number");
        String boothNumberString = boothNumber == null ? "No Booth Number" : boothNumber.getAsString();
        this.validateExhibitsChannel(e.getGuild());
        this.validateBoothChannel(e, e.getGuild(), boothName, boothNumberString, boothBuilding);
        e.deferReply().queue((m) -> m.setEphemeral(true).editOriginal("Booth Page Created!").queueAfter(0L, TimeUnit.SECONDS));
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
        if (guild.getCategoriesByName("exhibits", true).size() == 0) {
            guild.createCategory("exhibits").queue();
        }

    }

    private String validateBoothChannel(SlashCommandEvent e, Guild guild, String boothName, String boothNumber, String buildingEnum) {
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
            guild.createTextChannel(chatBooth, guild.getCategoriesByName("exhibits", true).get(0)).queue((channel) -> {
                channel.sendMessageEmbeds(this.getChannelEmbed(boothName, boothNumber, buildingEnum, boothLocation, boothTag0, boothTag1, boothTag2, boothTag3, boothTag4, boothTag5).build(), new MessageEmbed[0]).queue();
            });
        }

        return chatBooth;
    }

    public static enum TE {
        WEST(1),
        EAST(2),
        SOUTH(3),
        NORTH(4);

        private int value;

        private TE(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}
