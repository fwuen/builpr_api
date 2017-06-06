package com.builpr.restapi.service;

import com.builpr.database.db.builpr.user.User;
import com.builpr.database.db.builpr.user.UserManager;
import com.builpr.restapi.utils.Connector;
import java.util.Optional;

/**
 * user service
 */
public class UserService {

    private UserManager userManager;

    public UserService() {
        userManager = Connector.getConnection().getOrThrow(UserManager.class);
    }

    public User getByUsername(String name) {
        Optional<User> user = userManager.stream().filter(User.USERNAME.equal(name)).findFirst();

        return user.orElse(null);
    }

    public User getByEmail(String email) {
        Optional<User> user = userManager.stream().filter(User.EMAIL.equal(email)).findFirst();

        return user.orElse(null);
    }

    public User getByID(int id) {
        Optional<User> user = userManager.stream().filter(User.USER_ID.equal(id)).findFirst();

        return user.orElse(null);
    }

    public boolean isPresent(String name) {
        return getByUsername(name) != null;
    }

    public void persist(User user) {
        userManager.persist(user);
    }
}
