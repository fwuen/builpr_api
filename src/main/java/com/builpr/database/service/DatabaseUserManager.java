package com.builpr.database.service;

import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserManager;

import java.util.Optional;

/**
 * @author Marco Geißler
 * Database manager that covers the user-table
 */
public class DatabaseUserManager extends DatabaseManager<UserManager> {

    /**
     * connects the manager to the database
     */
    public DatabaseUserManager() {
        super(UserManager.class);
    }

    /**
     * gets a user by its username
     *
     * @param name the username
     * @return if present, the user, else null
     */
    public User getByUsername(String name) {
        Optional<User> user = getDao().stream().filter(User.USERNAME.equal(name)).findFirst();

        return user.orElse(null);
    }

    /**
     * gets a user by its email-address
     *
     * @param email the user's email address
     * @return if present, the user, else null
     */
    public User getByEmail(String email) {
        Optional<User> user = getDao().stream().filter(User.EMAIL.equal(email)).findFirst();

        return user.orElse(null);
    }

    /**
     * gets a user by its ID
     *
     * @param id the userID
     * @return if present, the user, else null
     */
    public User getByID(int id) {
        Optional<User> user = getDao().stream().filter(User.USER_ID.equal(id)).findFirst();

        return user.orElse(null);
    }

    /**
     * deletes a user with the given username
     *
     * @param name the username
     * @return the deleted user
     */
    public User deleteByUsername(String name) {
        return getDao().remove(
            this.getByUsername(name)
        );
    }

    /**
     * deletes a user with the given userID
     *
     * @param id the userID
     * @return the deleted user
     */
    public User deleteByID(int id) {
        return getDao().remove(
                this.getByID(id)
        );
    }

    /**
     * checks if a user with the given username is present in the database
     *
     * @param name the username
     * @return if present, true, if not, false
     */
    public boolean isPresent(String name) {
        return getByUsername(name) != null;
    }

    /**
     * checks if a user with the given userID is present in the database
     *
     * @param userID the userID
     * @return if present, true, if not, false
     */
    public boolean isPresent(int userID) {
        return getByID(userID) != null;
    }

    /**
     * persists a user in the database
     *
     * @param user the user to be persisted
     * @return the persisted user (possibly with values given by the database)
     */
    public User persist(User user) {
        return getDao().persist(user);
    }

    /**
     * updates a user in the database
     *
     * @param user the user to be updated
     * @return the updated user (possibly with values given by the database)
     */
    public User update(User user) {
        return getDao().update(user);
    }

}
