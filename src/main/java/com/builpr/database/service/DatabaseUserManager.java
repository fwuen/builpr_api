package com.builpr.database.service;

import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserManager;
import com.builpr.database.bridge.user.generated.GeneratedUser;

import java.util.List;
import java.util.Optional;

public class DatabaseUserManager extends DatabaseManager<UserManager> {

    public DatabaseUserManager() {
        super(UserManager.class);
    }

    public User getByUsername(String name) {
        Optional<User> user = getDao().stream().filter(User.USERNAME.equal(name)).findFirst();

        return user.orElse(null);
    }

    public User getByEmail(String email) {
        Optional<User> user = getDao().stream().filter(User.EMAIL.equal(email)).findFirst();

        return user.orElse(null);
    }

    public User getByID(int id) {
        Optional<User> user = getDao().stream().filter(User.USER_ID.equal(id)).findFirst();

        return user.orElse(null);
    }

    public void deleteByUsername(String name) {
        getDao().remove(
            this.getByUsername(name)
        );
    }

    public boolean isPresent(String name) {
        return getByUsername(name) != null;
    }

    public void persist(User user) {
        getDao().persist(user);
    }

    public void update(User user) {
        getDao().update(user);
    }

}
