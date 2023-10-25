package com.eternalcode.discordapp.leveling.experience.listener;

import com.eternalcode.discordapp.leveling.experience.ExperienceConfig;
import com.eternalcode.discordapp.leveling.experience.ExperienceService;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ExperienceReactionListener extends ListenerAdapter {

    private final ExperienceConfig experienceConfig;
    private final ExperienceService experienceService;

    public ExperienceReactionListener(ExperienceConfig experienceConfig, ExperienceService experienceService) {
        this.experienceConfig = experienceConfig;
        this.experienceService = experienceService;
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        long userId = event.getUserIdLong();
        double points = this.experienceConfig.basePoints * this.experienceConfig.reactionExperience.multiplier;

        if (event.getUser().isBot()) {
            return;
        }

        this.modifyPoints(event.getUser(), userId, points);
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        long userId = event.getUserIdLong();
        double points = this.experienceConfig.basePoints * this.experienceConfig.reactionExperience.multiplier;

        if (event.getUser().isBot()) {
            return;
        }

        this.modifyPoints(event.getUser(), userId, points);
    }

    private void modifyPoints(User event, long userId, double points) {
        event.openPrivateChannel().queue(channel -> this.experienceService.modifyPoints(userId, points, true, channel.getIdLong())
            .whenComplete((experience, throwable) -> {

            if (throwable != null) {
                throwable.printStackTrace();
            }
        }));
    }

}
