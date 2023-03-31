package net.lubosovo.svatba.service;

import net.lubosovo.svatba.repository.domain.User;
import net.lubosovo.svatba.repository.dao.UserDao;

import java.util.Set;
import java.util.HashSet;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Service("simpleUserDetailsService")
public class SimpleUserDetailsService implements UserDetailsService {

    private UserDao userDao;

    @Autowired
    protected final void setUserDao(final UserDao userDao) {
        this.userDao = userDao;
    }

    //fiks write some tests; empty, null parameters, etc.
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        try {
            user = this.userDao.getByLogin(username);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("No such user: " + username);
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("bestman");
        if (!"".equals(user.getLogin())) {
            grantedAuthorities.add(simpleGrantedAuthority);
        }
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), grantedAuthorities);
    }
}
