package com.builpr.database.bridge.user.generated;

import com.builpr.database.bridge.user.User;
import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.util.OptionalUtil;
import com.speedment.runtime.field.BooleanField;
import com.speedment.runtime.field.ComparableField;
import com.speedment.runtime.field.IntField;
import com.speedment.runtime.field.StringField;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.runtime.typemapper.integer.PrimitiveIntegerZeroOneToBooleanMapper;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;

/**
 * The generated base for the {@link
 * com.builpr.database.bridge.user.User}-interface representing entities of the
 * {@code user}-table in the database.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public interface GeneratedUser {
    
    /**
     * This Field corresponds to the {@link User} field that can be obtained
     * using the {@link User#getUserId()} method.
     */
    IntField<User, Integer> USER_ID = IntField.create(
        Identifier.USER_ID,
        User::getUserId,
        User::setUserId,
        TypeMapper.primitive(), 
        true
    );
    /**
     * This Field corresponds to the {@link User} field that can be obtained
     * using the {@link User#getUsername()} method.
     */
    StringField<User, String> USERNAME = StringField.create(
        Identifier.USERNAME,
        User::getUsername,
        User::setUsername,
        TypeMapper.identity(), 
        true
    );
    /**
     * This Field corresponds to the {@link User} field that can be obtained
     * using the {@link User#getPassword()} method.
     */
    StringField<User, String> PASSWORD = StringField.create(
        Identifier.PASSWORD,
        User::getPassword,
        User::setPassword,
        TypeMapper.identity(), 
        false
    );
    /**
     * This Field corresponds to the {@link User} field that can be obtained
     * using the {@link User#getEmail()} method.
     */
    StringField<User, String> EMAIL = StringField.create(
        Identifier.EMAIL,
        User::getEmail,
        User::setEmail,
        TypeMapper.identity(), 
        true
    );
    /**
     * This Field corresponds to the {@link User} field that can be obtained
     * using the {@link User#getRegtime()} method.
     */
    ComparableField<User, Timestamp, Timestamp> REGTIME = ComparableField.create(
        Identifier.REGTIME,
        User::getRegtime,
        User::setRegtime,
        TypeMapper.identity(), 
        false
    );
    /**
     * This Field corresponds to the {@link User} field that can be obtained
     * using the {@link User#getBirthday()} method.
     */
    ComparableField<User, Date, Date> BIRTHDAY = ComparableField.create(
        Identifier.BIRTHDAY,
        User::getBirthday,
        User::setBirthday,
        TypeMapper.identity(), 
        false
    );
    /**
     * This Field corresponds to the {@link User} field that can be obtained
     * using the {@link User#getFirstname()} method.
     */
    StringField<User, String> FIRSTNAME = StringField.create(
        Identifier.FIRSTNAME,
        User::getFirstname,
        User::setFirstname,
        TypeMapper.identity(), 
        false
    );
    /**
     * This Field corresponds to the {@link User} field that can be obtained
     * using the {@link User#getLastname()} method.
     */
    StringField<User, String> LASTNAME = StringField.create(
        Identifier.LASTNAME,
        User::getLastname,
        User::setLastname,
        TypeMapper.identity(), 
        false
    );
    /**
     * This Field corresponds to the {@link User} field that can be obtained
     * using the {@link User#getAvatar()} method.
     */
    StringField<User, String> AVATAR = StringField.create(
        Identifier.AVATAR,
        o -> OptionalUtil.unwrap(o.getAvatar()),
        User::setAvatar,
        TypeMapper.identity(), 
        false
    );
    /**
     * This Field corresponds to the {@link User} field that can be obtained
     * using the {@link User#getDescription()} method.
     */
    StringField<User, String> DESCRIPTION = StringField.create(
        Identifier.DESCRIPTION,
        o -> OptionalUtil.unwrap(o.getDescription()),
        User::setDescription,
        TypeMapper.identity(), 
        false
    );
    /**
     * This Field corresponds to the {@link User} field that can be obtained
     * using the {@link User#getShowName()} method.
     */
    BooleanField<User, Integer> SHOW_NAME = BooleanField.create(
        Identifier.SHOW_NAME,
        User::getShowName,
        User::setShowName,
        new PrimitiveIntegerZeroOneToBooleanMapper(), 
        false
    );
    /**
     * This Field corresponds to the {@link User} field that can be obtained
     * using the {@link User#getShowBirthday()} method.
     */
    BooleanField<User, Integer> SHOW_BIRTHDAY = BooleanField.create(
        Identifier.SHOW_BIRTHDAY,
        User::getShowBirthday,
        User::setShowBirthday,
        new PrimitiveIntegerZeroOneToBooleanMapper(), 
        false
    );
    /**
     * This Field corresponds to the {@link User} field that can be obtained
     * using the {@link User#getShowEmail()} method.
     */
    BooleanField<User, Integer> SHOW_EMAIL = BooleanField.create(
        Identifier.SHOW_EMAIL,
        User::getShowEmail,
        User::setShowEmail,
        new PrimitiveIntegerZeroOneToBooleanMapper(), 
        false
    );
    /**
     * This Field corresponds to the {@link User} field that can be obtained
     * using the {@link User#getActivated()} method.
     */
    BooleanField<User, Integer> ACTIVATED = BooleanField.create(
        Identifier.ACTIVATED,
        User::getActivated,
        User::setActivated,
        new PrimitiveIntegerZeroOneToBooleanMapper(), 
        false
    );
    /**
     * This Field corresponds to the {@link User} field that can be obtained
     * using the {@link User#getDeleted()} method.
     */
    BooleanField<User, Integer> DELETED = BooleanField.create(
        Identifier.DELETED,
        User::getDeleted,
        User::setDeleted,
        new PrimitiveIntegerZeroOneToBooleanMapper(), 
        false
    );
    
    /**
     * Returns the userId of this User. The userId field corresponds to the
     * database column builpr.builpr.user.user_id.
     * 
     * @return the userId of this User
     */
    int getUserId();
    
    /**
     * Returns the username of this User. The username field corresponds to the
     * database column builpr.builpr.user.username.
     * 
     * @return the username of this User
     */
    String getUsername();
    
    /**
     * Returns the password of this User. The password field corresponds to the
     * database column builpr.builpr.user.password.
     * 
     * @return the password of this User
     */
    String getPassword();
    
    /**
     * Returns the email of this User. The email field corresponds to the
     * database column builpr.builpr.user.email.
     * 
     * @return the email of this User
     */
    String getEmail();
    
    /**
     * Returns the regtime of this User. The regtime field corresponds to the
     * database column builpr.builpr.user.regtime.
     * 
     * @return the regtime of this User
     */
    Timestamp getRegtime();
    
    /**
     * Returns the birthday of this User. The birthday field corresponds to the
     * database column builpr.builpr.user.birthday.
     * 
     * @return the birthday of this User
     */
    Date getBirthday();
    
    /**
     * Returns the firstname of this User. The firstname field corresponds to
     * the database column builpr.builpr.user.firstname.
     * 
     * @return the firstname of this User
     */
    String getFirstname();
    
    /**
     * Returns the lastname of this User. The lastname field corresponds to the
     * database column builpr.builpr.user.lastname.
     * 
     * @return the lastname of this User
     */
    String getLastname();
    
    /**
     * Returns the avatar of this User. The avatar field corresponds to the
     * database column builpr.builpr.user.avatar.
     * 
     * @return the avatar of this User
     */
    Optional<String> getAvatar();
    
    /**
     * Returns the description of this User. The description field corresponds
     * to the database column builpr.builpr.user.description.
     * 
     * @return the description of this User
     */
    Optional<String> getDescription();
    
    /**
     * Returns the showName of this User. The showName field corresponds to the
     * database column builpr.builpr.user.show_name.
     * 
     * @return the showName of this User
     */
    boolean getShowName();
    
    /**
     * Returns the showBirthday of this User. The showBirthday field corresponds
     * to the database column builpr.builpr.user.show_birthday.
     * 
     * @return the showBirthday of this User
     */
    boolean getShowBirthday();
    
    /**
     * Returns the showEmail of this User. The showEmail field corresponds to
     * the database column builpr.builpr.user.show_email.
     * 
     * @return the showEmail of this User
     */
    boolean getShowEmail();
    
    /**
     * Returns the activated of this User. The activated field corresponds to
     * the database column builpr.builpr.user.activated.
     * 
     * @return the activated of this User
     */
    boolean getActivated();
    
    /**
     * Returns the deleted of this User. The deleted field corresponds to the
     * database column builpr.builpr.user.deleted.
     * 
     * @return the deleted of this User
     */
    boolean getDeleted();
    
    /**
     * Sets the userId of this User. The userId field corresponds to the
     * database column builpr.builpr.user.user_id.
     * 
     * @param userId to set of this User
     * @return       this User instance
     */
    User setUserId(int userId);
    
    /**
     * Sets the username of this User. The username field corresponds to the
     * database column builpr.builpr.user.username.
     * 
     * @param username to set of this User
     * @return         this User instance
     */
    User setUsername(String username);
    
    /**
     * Sets the password of this User. The password field corresponds to the
     * database column builpr.builpr.user.password.
     * 
     * @param password to set of this User
     * @return         this User instance
     */
    User setPassword(String password);
    
    /**
     * Sets the email of this User. The email field corresponds to the database
     * column builpr.builpr.user.email.
     * 
     * @param email to set of this User
     * @return      this User instance
     */
    User setEmail(String email);
    
    /**
     * Sets the regtime of this User. The regtime field corresponds to the
     * database column builpr.builpr.user.regtime.
     * 
     * @param regtime to set of this User
     * @return        this User instance
     */
    User setRegtime(Timestamp regtime);
    
    /**
     * Sets the birthday of this User. The birthday field corresponds to the
     * database column builpr.builpr.user.birthday.
     * 
     * @param birthday to set of this User
     * @return         this User instance
     */
    User setBirthday(Date birthday);
    
    /**
     * Sets the firstname of this User. The firstname field corresponds to the
     * database column builpr.builpr.user.firstname.
     * 
     * @param firstname to set of this User
     * @return          this User instance
     */
    User setFirstname(String firstname);
    
    /**
     * Sets the lastname of this User. The lastname field corresponds to the
     * database column builpr.builpr.user.lastname.
     * 
     * @param lastname to set of this User
     * @return         this User instance
     */
    User setLastname(String lastname);
    
    /**
     * Sets the avatar of this User. The avatar field corresponds to the
     * database column builpr.builpr.user.avatar.
     * 
     * @param avatar to set of this User
     * @return       this User instance
     */
    User setAvatar(String avatar);
    
    /**
     * Sets the description of this User. The description field corresponds to
     * the database column builpr.builpr.user.description.
     * 
     * @param description to set of this User
     * @return            this User instance
     */
    User setDescription(String description);
    
    /**
     * Sets the showName of this User. The showName field corresponds to the
     * database column builpr.builpr.user.show_name.
     * 
     * @param showName to set of this User
     * @return         this User instance
     */
    User setShowName(boolean showName);
    
    /**
     * Sets the showBirthday of this User. The showBirthday field corresponds to
     * the database column builpr.builpr.user.show_birthday.
     * 
     * @param showBirthday to set of this User
     * @return             this User instance
     */
    User setShowBirthday(boolean showBirthday);
    
    /**
     * Sets the showEmail of this User. The showEmail field corresponds to the
     * database column builpr.builpr.user.show_email.
     * 
     * @param showEmail to set of this User
     * @return          this User instance
     */
    User setShowEmail(boolean showEmail);
    
    /**
     * Sets the activated of this User. The activated field corresponds to the
     * database column builpr.builpr.user.activated.
     * 
     * @param activated to set of this User
     * @return          this User instance
     */
    User setActivated(boolean activated);
    
    /**
     * Sets the deleted of this User. The deleted field corresponds to the
     * database column builpr.builpr.user.deleted.
     * 
     * @param deleted to set of this User
     * @return        this User instance
     */
    User setDeleted(boolean deleted);
    
    enum Identifier implements ColumnIdentifier<User> {
        
        USER_ID       ("user_id"),
        USERNAME      ("username"),
        PASSWORD      ("password"),
        EMAIL         ("email"),
        REGTIME       ("regtime"),
        BIRTHDAY      ("birthday"),
        FIRSTNAME     ("firstname"),
        LASTNAME      ("lastname"),
        AVATAR        ("avatar"),
        DESCRIPTION   ("description"),
        SHOW_NAME     ("show_name"),
        SHOW_BIRTHDAY ("show_birthday"),
        SHOW_EMAIL    ("show_email"),
        ACTIVATED     ("activated"),
        DELETED       ("deleted");
        
        private final String columnName;
        private final TableIdentifier<User> tableIdentifier;
        
        Identifier(String columnName) {
            this.columnName      = columnName;
            this.tableIdentifier = TableIdentifier.of(    getDbmsName(), 
                getSchemaName(), 
                getTableName());
        }
        
        @Override
        public String getDbmsName() {
            return "builpr";
        }
        
        @Override
        public String getSchemaName() {
            return "builpr";
        }
        
        @Override
        public String getTableName() {
            return "user";
        }
        
        @Override
        public String getColumnName() {
            return this.columnName;
        }
        
        @Override
        public TableIdentifier<User> asTableIdentifier() {
            return this.tableIdentifier;
        }
    }
}