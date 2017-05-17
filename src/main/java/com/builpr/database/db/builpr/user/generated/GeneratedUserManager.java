package com.builpr.database.db.builpr.user.generated;

import com.builpr.database.db.builpr.user.User;
import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.core.manager.Manager;

/**
 * The generated base interface for the manager of every {@link
 * com.builpr.database.db.builpr.user.User} entity.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public interface GeneratedUserManager extends Manager<User> {
    
    @Override
    default Class<User> getEntityClass() {
        return User.class;
    }
}