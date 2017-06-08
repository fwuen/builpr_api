package com.builpr.restapi.security;

import com.builpr.Constants;
import com.builpr.restapi.service.UserService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private UserService userService = new UserService();

    public UserDetails loadUserByUsername(String username) {
        if(username == null)
            throw new UsernameNotFoundException(username);

        if(!userService.isPresent(username))
            throw new UsernameNotFoundException(username);

        com.builpr.database.db.builpr.user.User dbUser = userService.getByUsername(username);

        if(dbUser == null)
            throw new UsernameNotFoundException(username);

        return new User(String.valueOf(dbUser.getUserId()), dbUser.getPassword(), AuthorityUtils.createAuthorityList(Constants.ROLE_USER));
    }

}
