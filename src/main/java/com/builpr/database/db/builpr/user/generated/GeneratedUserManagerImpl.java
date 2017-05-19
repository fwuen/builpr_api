package com.builpr.database.db.builpr.user.generated;

import com.builpr.database.db.builpr.user.User;
import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.manager.AbstractManager;
import com.speedment.runtime.field.Field;
import java.util.stream.Stream;

/**
 * The generated base implementation for the manager of every {@link
 * com.builpr.database.db.builpr.user.User} entity.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public abstract class GeneratedUserManagerImpl extends AbstractManager<User> implements GeneratedUserManager {
    
    private final TableIdentifier<User> tableIdentifier;
    
    protected GeneratedUserManagerImpl() {
        this.tableIdentifier = TableIdentifier.of("builpr", "builpr", "User");
    }
    
    @Override
    public TableIdentifier<User> getTableIdentifier() {
        return tableIdentifier;
    }
    
    @Override
    public Stream<Field<User>> fields() {
        return Stream.of(
            User.USER_ID,
            User.USERNAME,
            User.PASSWORD,
            User.EMAIL,
            User.REGTIME,
            User.BIRTHDAY,
            User.FIRSTNAME,
            User.LASTNAME,
            User.AVATAR,
            User.SKYPE,
            User.TWITTER,
            User.FACEBOOK,
            User.INSTAGRAM,
            User.DESCRIPTION,
            User.SHOW_NAME,
            User.SHOW_BIRTHDAY,
            User.SHOW_EMAIL
        );
    }
    
    @Override
    public Stream<Field<User>> primaryKeyFields() {
        return Stream.of(
            User.USER_ID
        );
    }
}