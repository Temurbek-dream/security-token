package com.example.demo.security;

import com.example.demo.entity.User;
import com.example.demo.entity.enums.RoleName;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.ReqSignUp;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

//import com.temurbek.demo.payload.ApiResponse;
//import com.temurbek.demo.payload.ReqSignUp;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("This username does not exist: "
                                +username));
    }
    public UserDetails loadUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User id not found: " +userId));
    }

    public ApiResponse register(ReqSignUp reqSignUp){
        Optional<User> optionalUser = userRepository.findByUsername(reqSignUp.getUsername());

        if (optionalUser.isPresent()){
            return new ApiResponse("This username is already existed!",false);
        }
        else
            {
            userRepository.save(
                    new User(
                            reqSignUp.getUsername(),
                            reqSignUp.getFullname(),
                            passwordEncoder.encode(reqSignUp.getPassword()),
                            roleRepository.findAllByName(RoleName.ROLE_USER)));
            return new ApiResponse("You have taken .", true);
        }
    }

}
