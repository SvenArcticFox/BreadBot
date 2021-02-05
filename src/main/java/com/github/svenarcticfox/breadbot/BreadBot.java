package com.github.svenarcticfox.breadbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.List;

public class BreadBot extends ListenerAdapter
{
    public static void main(String[] args)
    {
        String token = args[0];

        try
        {
            JDA jda = JDABuilder.createDefault(token)
                    .addEventListeners(new BreadBot())
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
            JDA jda = event.getJDA();
            long responseNumber = event.getResponseNumber();

            User author = event.getAuthor();
            Message message = event.getMessage();
            MessageChannel channel = event.getChannel();


            String msg = message.getContentDisplay();
            System.out.println(msg);

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
                PrivateChannel privateChannel = event.getPrivateChannel();

                System.out.printf("[PRIV]<%s>: %s\n", author.getName(), msg);
            }


        if (bot == false)
        {
            if (!msg.equals(":bread:") || (!msg.equals(":french_bread:") || !msg.equals(":baguette_bread:")))
            {
                try
                {
                    message.delete().queue();
                    channel.sendMessage("Bread ONLY! :bread:").queue();
                }
                catch (PermissionException e)
                {
                    e.printStackTrace();
                }

            }
        }

    }

}
