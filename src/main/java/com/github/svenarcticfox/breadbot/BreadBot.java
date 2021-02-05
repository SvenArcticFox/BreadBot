package com.github.svenarcticfox.breadbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

import java.util.Random;

public class BreadBot extends ListenerAdapter
{
    public static void main(String[] args)
    {
        String token = null;
        try
        {
            token = args[0];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Enter the token as an argument!");
            e.printStackTrace();
            System.exit(-1);
        }


        try
        {
            JDA jda = JDABuilder.createDefault(token)
                    .addEventListeners(new BreadBot())
                    .setActivity(Activity.playing("with bread"))
                    .build();
            jda.awaitReady();
            System.out.println("Finished Building JDA");
        } catch (LoginException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
            User author = event.getAuthor();
            Message message = event.getMessage();
            MessageChannel channel = event.getChannel();

            String msg = message.getContentDisplay();

            boolean bot = author.isBot();

            if (event.isFromType(ChannelType.TEXT))
            {
                Guild guild = event.getGuild();
                TextChannel textChannel = event.getTextChannel();
                Member member = event.getMember();

                String name;
                if (message.isWebhookMessage())
                    name = author.getName();
                else
                    name = member.getEffectiveName();

                System.out.printf("(%s)[%s]<%s>: %s\n", guild.getName(), textChannel.getName(), name, msg);
            }
            else if (event.isFromType(ChannelType.PRIVATE))
            {
                System.out.printf("[PRIVATE]<%s>: %s\n", author.getName(), msg);
            }

        if (message.isFromGuild() && !bot)
        {
            if (!(msg.equals("\uD83C\uDF5E") || (msg.equals("\uD83E\uDD56"))))
            {
                try
                {
                    Random random = new Random();
                    int randomInt = random.nextInt(50);
                    message.delete().queue();
                    if (randomInt == 21)
                        channel.sendMessage(":french_bread:");
                    else
                        channel.sendMessage(":bread:").queue();
                }
                catch (PermissionException e)
                {
                    e.printStackTrace();
                }

            }
        }
    }
}
