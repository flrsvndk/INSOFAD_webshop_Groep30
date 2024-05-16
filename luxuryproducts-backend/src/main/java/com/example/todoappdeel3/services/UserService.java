package com.example.todoappdeel3.services;


import com.example.todoappdeel3.config.JWTUtil;
import com.example.todoappdeel3.dao.UserRepository;
import com.example.todoappdeel3.models.CustomUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final HttpServletRequest request;
    private final JWTUtil jwtUtil;

    public UserService(UserRepository userRepository, HttpServletRequest request, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.request = request;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomUser customUser = userRepository.findByEmail(email);

        return new User(
                email,
                customUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(customUser.getRole())));
    }

    public CustomUser getUser(){
        String authHeader = request.getHeader("Authorization");
        String jwt = authHeader.substring(7);
        String userEmail = this.jwtUtil.validateTokenAndRetrieveSubject(jwt);

        CustomUser user = userRepository.findByEmail(userEmail);
        return user;
    }

    public CustomUser getUserByJWT(){
        String authHeader = request.getHeader("Authorization");
        String jwt = authHeader.substring(7);
        String userEmail = this.jwtUtil.validateTokenAndRetrieveSubject(jwt);

        CustomUser user = userRepository.findByEmail(userEmail);
        return user;
    }
}
