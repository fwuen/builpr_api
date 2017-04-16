package com.builpr.restapi.configuration;

import com.builpr.restapi.utils.GravatarWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GravatarConfiguration {

    @Bean
    public GravatarWrapper gravatar() {
        return new GravatarWrapper();
    }

}
