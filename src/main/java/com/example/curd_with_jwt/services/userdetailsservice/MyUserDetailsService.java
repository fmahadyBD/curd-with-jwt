package com.example.curd_with_jwt.services.userdetailsservice;

import com.example.curd_with_jwt.entity.MyUser;
import com.example.curd_with_jwt.reposioty.MyUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final MyUserRepository myUserRepository;
    public  MyUserDetailsService(MyUserRepository myUserRepository){
        this.myUserRepository=myUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<MyUser> user = myUserRepository.findByEmail(email);
        if(user.isPresent()){
            var userObject = user.get();
            return User.builder().username(userObject.getEmail())
                    .password(userObject.getPassword())
                    .roles(String.valueOf(userObject.getRole())).build();
        }else{
            throw new UsernameNotFoundException(email);
        }
    }
}
