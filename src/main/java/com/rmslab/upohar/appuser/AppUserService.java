package com.rmslab.upohar.appuser;

import com.rmslab.upohar.registration.token.ConfirmationToken;
import com.rmslab.upohar.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MESSAGE = "user with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder ;
    private final ConfirmationTokenService confirmationTokenService ;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return appUserRepository
                .findByEmail(userName)
                .orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, userName)));
    }

    public String signUpUser(AppUser appUser) {
        boolean existsUser = appUserRepository.findByEmail(appUser.getEmail())
                .isPresent() ;
        if(existsUser)
            throw new IllegalStateException("email already taken") ;
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword()) ;
        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser) ;

        String token = UUID.randomUUID().toString() ;
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),LocalDateTime.now().plusMinutes(1),appUser) ;
        confirmationTokenService.saveConfirmationToken(confirmationToken) ;

        return token ;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email) ;
    }
}
