package com.wust.iot.filter;

import com.wust.iot.enums.RecordEnums;
import com.wust.iot.enums.ResultEnums;
import com.wust.iot.exception.SimpleException;
import com.wust.iot.model.User;
import com.wust.iot.service.UserService;
import com.wust.iot.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;

public class WebAuthenticationFilter implements AuthenticationProvider {


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = null;

        try{
            user = userService.findUserByUsername(username);
            if (user == null){
                throw new SimpleException(ResultEnums.USER_NOT_EXIST);
            }

            String encodedPassword = user.getPassword();
            //校验密码
            boolean authenticated = Md5Util.verifyPassword(password,encodedPassword);
            if (!authenticated){
                //认证失败
                throw new SimpleException(ResultEnums.PASSWORD_NOT_RIGHT);
            }
            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
            org.springframework.security.core.userdetails.User portalAuthUser = new org.springframework.security.core.userdetails.User(username, password, true, true, true, true, grantedAuths);
            return new UsernamePasswordAuthenticationToken(portalAuthUser,null,grantedAuths);
        } catch (Exception e){

        }
        return new UsernamePasswordAuthenticationToken(null,null,null);
    }

    public boolean supports(Class<?> aClass) {
        return true;
    }

}
