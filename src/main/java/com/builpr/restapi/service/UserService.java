package com.builpr.restapi.service;

import com.builpr.database.db.builpr.user.UserImpl;
import com.builpr.restapi.model.User.UserRegistrationErrorFields;
import com.builpr.restapi.utils.Connector;
import com.builpr.database.db.builpr.user.User;
import com.builpr.restapi.exception.User.UserNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service that manages database-access for user-objects
 */
public class UserService {

    /**
     * gets a user by his/her username
     *
     * @param username name of the user
     * @return the found user
     * @throws UserNotFoundException if the user doesn't exist
     * @throws Exception             if somehow more than one user can be found by the given username
     */
    public User getByUsername(String username) throws Exception {
        List<User> foundUsers = Connector.getUserManager().stream().filter(User.USERNAME.equal(username)).collect(Collectors.toList());

        if (foundUsers.isEmpty()) {
            throw new UserNotFoundException();
        }

        return foundUsers.get(0);
    }


    public UserRegistrationErrorFields saveUser(User userToSave) {
        Optional user = Connector.getUserManager().stream().filter(User.USERNAME.equal(userToSave.getUsername())
                .or(User.EMAIL.equal(userToSave.getEmail()))).findFirst();

        UserRegistrationErrorFields errorFields = new UserRegistrationErrorFields();

        if (user.isPresent()) {
            User alreadyExistentUser = (UserImpl) user.get();

            if (userToSave.getUsername().equals(alreadyExistentUser.getUsername())) {
                errorFields.setUsernameTaken(true);
            }
            if (userToSave.getEmail().equals(alreadyExistentUser.getEmail())) {
                errorFields.setEmailTaken(true);
            }
        } else {
            Connector.getUserManager().persist(userToSave);
        }

        return errorFields;
    }
}
