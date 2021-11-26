package ru.realityfamily.party_server.Security.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.realityfamily.party_server.DB.DAO.PersonCredentialsDAO;
import ru.realityfamily.party_server.Models.PersonCredentials;

import java.util.Collections;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final PersonCredentialsDAO personCredentialsDAO;

    public MyUserDetailsService(PersonCredentialsDAO personCredentialsDAO) {
        this.personCredentialsDAO = personCredentialsDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        PersonCredentials personCredentials = personCredentialsDAO.findByLoginLike(userName);
        if (personCredentials != null) {
            return new User(
                    personCredentials.getLogin(),
                    personCredentials.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(personCredentials.getRole().toString()))
            );
        } else {
            return null;
        }
    }
}
