package com.builpr.database.bridge.user.generated;

import com.builpr.database.bridge.user.User;
import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.core.util.OptionalUtil;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * The generated base implementation of the {@link
 * com.builpr.database.bridge.user.User}-interface.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public abstract class GeneratedUserImpl implements User {
    
    private int userId;
    private String username;
    private String password;
    private String email;
    private Timestamp regtime;
    private Date birthday;
    private String firstname;
    private String lastname;
    private String avatar;
    private String description;
    private boolean showName;
    private boolean showBirthday;
    private boolean showEmail;
    private boolean activated;
    private boolean deleted;
    
    protected GeneratedUserImpl() {
        
    }
    
    @Override
    public int getUserId() {
        return userId;
    }
    
    @Override
    public String getUsername() {
        return username;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public String getEmail() {
        return email;
    }
    
    @Override
    public Timestamp getRegtime() {
        return regtime;
    }
    
    @Override
    public Date getBirthday() {
        return birthday;
    }
    
    @Override
    public String getFirstname() {
        return firstname;
    }
    
    @Override
    public String getLastname() {
        return lastname;
    }
    
    @Override
    public Optional<String> getAvatar() {
        return Optional.ofNullable(avatar);
    }
    
    @Override
    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }
    
    @Override
    public boolean getShowName() {
        return showName;
    }
    
    @Override
    public boolean getShowBirthday() {
        return showBirthday;
    }
    
    @Override
    public boolean getShowEmail() {
        return showEmail;
    }
    
    @Override
    public boolean getActivated() {
        return activated;
    }
    
    @Override
    public boolean getDeleted() {
        return deleted;
    }
    
    @Override
    public User setUserId(int userId) {
        this.userId = userId;
        return this;
    }
    
    @Override
    public User setUsername(String username) {
        this.username = username;
        return this;
    }
    
    @Override
    public User setPassword(String password) {
        this.password = password;
        return this;
    }
    
    @Override
    public User setEmail(String email) {
        this.email = email;
        return this;
    }
    
    @Override
    public User setRegtime(Timestamp regtime) {
        this.regtime = regtime;
        return this;
    }
    
    @Override
    public User setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }
    
    @Override
    public User setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }
    
    @Override
    public User setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }
    
    @Override
    public User setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }
    
    @Override
    public User setDescription(String description) {
        this.description = description;
        return this;
    }
    
    @Override
    public User setShowName(boolean showName) {
        this.showName = showName;
        return this;
    }
    
    @Override
    public User setShowBirthday(boolean showBirthday) {
        this.showBirthday = showBirthday;
        return this;
    }
    
    @Override
    public User setShowEmail(boolean showEmail) {
        this.showEmail = showEmail;
        return this;
    }
    
    @Override
    public User setActivated(boolean activated) {
        this.activated = activated;
        return this;
    }
    
    @Override
    public User setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }
    
    @Override
    public String toString() {
        final StringJoiner sj = new StringJoiner(", ", "{ ", " }");
        sj.add("userId = "       + Objects.toString(getUserId()));
        sj.add("username = "     + Objects.toString(getUsername()));
        sj.add("password = "     + Objects.toString(getPassword()));
        sj.add("email = "        + Objects.toString(getEmail()));
        sj.add("regtime = "      + Objects.toString(getRegtime()));
        sj.add("birthday = "     + Objects.toString(getBirthday()));
        sj.add("firstname = "    + Objects.toString(getFirstname()));
        sj.add("lastname = "     + Objects.toString(getLastname()));
        sj.add("avatar = "       + Objects.toString(OptionalUtil.unwrap(getAvatar())));
        sj.add("description = "  + Objects.toString(OptionalUtil.unwrap(getDescription())));
        sj.add("showName = "     + Objects.toString(getShowName()));
        sj.add("showBirthday = " + Objects.toString(getShowBirthday()));
        sj.add("showEmail = "    + Objects.toString(getShowEmail()));
        sj.add("activated = "    + Objects.toString(getActivated()));
        sj.add("deleted = "      + Objects.toString(getDeleted()));
        return "UserImpl " + sj.toString();
    }
    
    @Override
    public boolean equals(Object that) {
        if (this == that) { return true; }
        if (!(that instanceof User)) { return false; }
        final User thatUser = (User)that;
        if (this.getUserId() != thatUser.getUserId()) {return false; }
        if (!Objects.equals(this.getUsername(), thatUser.getUsername())) {return false; }
        if (!Objects.equals(this.getPassword(), thatUser.getPassword())) {return false; }
        if (!Objects.equals(this.getEmail(), thatUser.getEmail())) {return false; }
        if (!Objects.equals(this.getRegtime(), thatUser.getRegtime())) {return false; }
        if (!Objects.equals(this.getBirthday(), thatUser.getBirthday())) {return false; }
        if (!Objects.equals(this.getFirstname(), thatUser.getFirstname())) {return false; }
        if (!Objects.equals(this.getLastname(), thatUser.getLastname())) {return false; }
        if (!Objects.equals(this.getAvatar(), thatUser.getAvatar())) {return false; }
        if (!Objects.equals(this.getDescription(), thatUser.getDescription())) {return false; }
        if (this.getShowName() != thatUser.getShowName()) {return false; }
        if (this.getShowBirthday() != thatUser.getShowBirthday()) {return false; }
        if (this.getShowEmail() != thatUser.getShowEmail()) {return false; }
        if (this.getActivated() != thatUser.getActivated()) {return false; }
        if (this.getDeleted() != thatUser.getDeleted()) {return false; }
        return true;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Integer.hashCode(getUserId());
        hash = 31 * hash + Objects.hashCode(getUsername());
        hash = 31 * hash + Objects.hashCode(getPassword());
        hash = 31 * hash + Objects.hashCode(getEmail());
        hash = 31 * hash + Objects.hashCode(getRegtime());
        hash = 31 * hash + Objects.hashCode(getBirthday());
        hash = 31 * hash + Objects.hashCode(getFirstname());
        hash = 31 * hash + Objects.hashCode(getLastname());
        hash = 31 * hash + Objects.hashCode(getAvatar());
        hash = 31 * hash + Objects.hashCode(getDescription());
        hash = 31 * hash + Boolean.hashCode(getShowName());
        hash = 31 * hash + Boolean.hashCode(getShowBirthday());
        hash = 31 * hash + Boolean.hashCode(getShowEmail());
        hash = 31 * hash + Boolean.hashCode(getActivated());
        hash = 31 * hash + Boolean.hashCode(getDeleted());
        return hash;
    }
}