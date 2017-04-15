package com.builpr.restapi.configuration;

import com.timgroup.jgravatar.Gravatar;
import com.timgroup.jgravatar.GravatarDefaultImage;
import com.timgroup.jgravatar.GravatarRating;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GravatarConfiguration {

    @Bean
    public Gravatar gravatar() {
        Gravatar gravatar = new Gravatar();

        gravatar.setSize(50);
        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
        gravatar.setDefaultImage(GravatarDefaultImage.RETRO);

        return gravatar;
    }

}
