package com.wust.iot.service.impl;

import com.wust.iot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("用户名:"+username);
        com.wust.iot.model.User myUser = userService.findUserByUsername(username);
        User user = null;
        if (myUser != null){
            List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            user = new User(myUser.getUsername(),myUser.getPassword(),true,true,true,true,grantedAuthorities);
        }
        return user;
    }
}
