package com.builpr.database.service;

import com.builpr.database.bridge.register_confirmation_token.RegisterConfirmationToken;
import com.builpr.database.bridge.register_confirmation_token.RegisterConfirmationTokenManager;

/**
 *
 */
public class DatabaseRegisterConfirmationTokenManager extends DatabaseManager<RegisterConfirmationTokenManager> {

    public DatabaseRegisterConfirmationTokenManager() {
        super(RegisterConfirmationTokenManager.class);
    }

    public RegisterConfirmationToken persist(RegisterConfirmationToken token) {
        return getDao().persist(token);
    }

    public boolean isPresent(int user_id, String token) {
        return getDao().stream().anyMatch(RegisterConfirmationToken.USER_ID.equal(user_id).and(RegisterConfirmationToken.TOKEN.equal(token)));
    }

    public RegisterConfirmationToken getByUserIDandToken(int userID, String token) {
        if (isPresent(userID, token)) {
            return getDao().stream().filter(RegisterConfirmationToken.USER_ID.equal(userID).and(RegisterConfirmationToken.TOKEN.equal(token))).findFirst().get();
        }
        return null;
    }

    public void delete(RegisterConfirmationToken token) {
        getDao().remove(token);
    }
}
