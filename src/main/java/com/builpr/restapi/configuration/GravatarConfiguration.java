package com.builpr.restapi.configuration;

import com.builpr.restapi.utils.GravatarWrapper;
import com.timgroup.jgravatar.GravatarDefaultImage;
import com.timgroup.jgravatar.GravatarRating;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GravatarConfiguration {

    @Bean
    public GravatarWrapper gravatar() {
        GravatarWrapper gravatar = new GravatarWrapper();

        gravatar.getGravatarProvider().setSize(50);
        gravatar.getGravatarProvider().setRating(GravatarRating.GENERAL_AUDIENCES);
        gravatar.getGravatarProvider().setDefaultImage(GravatarDefaultImage.RETRO);

        return gravatar;
    }

}
