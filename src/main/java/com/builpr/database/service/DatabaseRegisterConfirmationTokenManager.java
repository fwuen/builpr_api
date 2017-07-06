package com.builpr.database.service;

import com.builpr.database.bridge.register_confirmation_token.RegisterConfirmationToken;
import com.builpr.database.bridge.register_confirmation_token.RegisterConfirmationTokenManager;

import java.util.Optional;

/**
 * Database manager that covers the register_confirmation_token-table
 */
public class DatabaseRegisterConfirmationTokenManager extends DatabaseManager<RegisterConfirmationTokenManager> {

    /**
     * connects the manager to the database
     */
    public DatabaseRegisterConfirmationTokenManager() {
        super(RegisterConfirmationTokenManager.class);
    }

    /**
     * saves the given RegisterConfirmationToken in the database
     *
     * @param token the token to be saved
     * @return the modified token (possibly with values given by the database)
     */
    public RegisterConfirmationToken persist(RegisterConfirmationToken token) {
        return getDao().persist(token);
    }

    /**
     * checks if a token that has the given attributes is present
     *
     * @param user_id the id of the user the token belongs to
     * @param token the token of the user
     * @return true if an entry is present, false if not
     */
    public boolean isPresent(int user_id, String token) {
        return getDao().stream().anyMatch(RegisterConfirmationToken.USER_ID.equal(user_id).and(RegisterConfirmationToken.TOKEN.equal(token)));
    }

    /**
     * gets a token with the given attributes from the database
     *
     * @param userID the id of the user the token belongs to
     * @param token the user's token
     * @return if present, the entry in the database, otherwise null
     */
    public RegisterConfirmationToken getTokenEntry(int userID, String token) {
        Optional<RegisterConfirmationToken> databaseToken = getDao().stream().filter(RegisterConfirmationToken.USER_ID.equal(userID).and(RegisterConfirmationToken.TOKEN.equal(token))).findFirst();
        return databaseToken.orElse(null);
    }

    /**
     * deletes a token from the database
     * @param token the token to be deleted
     * @return the deleted token
     */
    public RegisterConfirmationToken delete(RegisterConfirmationToken token) {
        return getDao().remove(token);
    }
}
