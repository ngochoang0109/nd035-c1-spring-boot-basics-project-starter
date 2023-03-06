package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.controller.request.Authenticated;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

@Service
public class AuthenticatedService implements AuthenticationProvider {

    private UserMapper userMapper;

    private HashService hashService;

    public AuthenticatedService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    @Transactional
    public boolean registerNewAccount(Authenticated registerInformation){
        boolean isRegister = false;
        Users user = userMapper.findUserByUsername(registerInformation.getUsername());
        if (user == null){
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            String encodedSalt = Base64.getEncoder().encodeToString(salt);
            String hashedPassword = hashService.getHashedValue(registerInformation.getPassword(), encodedSalt);
            registerInformation.setSalt(encodedSalt);
            registerInformation.setPassword(hashedPassword);
            int row = userMapper.insertUserDataset(registerInformation);
            if (row > 0){
                isRegister = true;
            }
        }
        return isRegister;
    }

    /* authenticate user information when login */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Users user = userMapper.findUserByUsername(username);
        if (user != null) {
            String encodedSalt = user.getSalt();
            String hashedPassword = hashService.getHashedValue(password, encodedSalt);
            if (user.getPassword().equals(hashedPassword)) {
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
