package ma.najeh.ibnouzouhr.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import ma.najeh.ibnouzouhr.model.User;
import ma.najeh.ibnouzouhr.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author hicham-abdedaime et jaouad el aoud
 */
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User u = userRepository.getByUsername(username);

        if (u == null) {
            throw new UsernameNotFoundException("user with " + username + " not found.");
        }
        return u.getUserDetailsDto();
    }


}