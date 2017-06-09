package com.builpr.restapi.security;

import com.builpr.Constants;
import com.builpr.database.service.DatabaseUserManager;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private DatabaseUserManager databaseUserManager = new DatabaseUserManager();

    public UserDetails loadUserByUsername(String username) {
        if(username == null)
            throw new UsernameNotFoundException("Username ist null");

        if(!databaseUserManager.isPresent(username))
            throw new UsernameNotFoundException(username);

        com.builpr.database.bridge.user.User dbUser = databaseUserManager.getByUsername(username);

        if(dbUser == null)
            throw new UsernameNotFoundException(username);

        return new User(dbUser.getUsername(), dbUser.getPassword(), AuthorityUtils.createAuthorityList(Constants.SECURITY_ROLE_USER));
    }

}
