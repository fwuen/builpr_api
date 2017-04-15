package com.builpr.restapi.utils;

import com.google.common.base.Preconditions;
import com.timgroup.jgravatar.Gravatar;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.validator.routines.EmailValidator;

public class GravatarWrapper {

    @Getter
    private Gravatar gravatarProvider = new Gravatar();



    public String getUrl(@NonNull String email) {
        Preconditions.checkArgument(email.length() > 0);
        Preconditions.checkArgument(EmailValidator.getInstance().isValid(email));

        return gravatarProvider
                .getUrl(email)
                .replace("?d=404", "");
    }

}
