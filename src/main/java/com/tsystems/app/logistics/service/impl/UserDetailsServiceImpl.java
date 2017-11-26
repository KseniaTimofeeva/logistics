package com.tsystems.app.logistics.service.impl;

import com.tsystems.app.logistics.dao.impl.UserDao;
import com.tsystems.app.logistics.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ksenia on 08.10.2017.
 */
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = getUserByLogin(s);
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), roles);
    }

    private User getUserByLogin(String login) {
        if (null != login) {
            List<User> users = userDao.getUserByLogin(login);
            if (users.size() > 1 || users.isEmpty()) {
                throw new UsernameNotFoundException("Not authorized");
            }
            return users.get(0);
        }
        throw new UsernameNotFoundException("Not authorized");
    }
}
