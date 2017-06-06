package com.builpr.restapi.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    public UserDetails loadUserByUsername(String username) {
        if(username == null)
            throw new UsernameNotFoundException(username);

        return new User("admin", "password", AuthorityUtils.createAuthorityList("USER"));
    }

}
