package com.builpr.restapi;

import com.builpr.database.BuilprApplication;
import com.builpr.restapi.utils.Connector;
import com.builpr.database.db.builpr.user.User;
import com.builpr.database.db.builpr.user.UserManager;
import com.builpr.restapi.exception.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private BuilprApplication builprApplication;
    private UserManager userManager;

    public UserService() {
        builprApplication = Connector.getConnection();
        userManager = builprApplication.getOrThrow(UserManager.class);
    }

    public User getByUsername(String username) throws Exception {
        List<User> foundUsers = userManager.stream().filter(User.USERNAME.equal(username)).collect(Collectors.toList());

        if(foundUsers.isEmpty()) {
            throw new UserNotFoundException("Der Nutzer existiert nicht!");
        }

        if(foundUsers.size() > 1) {
            throw new Exception("WTF");
        }

        return foundUsers.get(0);
    }

}
