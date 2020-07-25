package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.ReqChangePassword;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
  @Autowired
    UserRepository userRepository;
  @Autowired
    PasswordEncoder passwordEncoder;

  public ApiResponse changePassword(ReqChangePassword reqChangePassword, User currentUser)
  {
    User user=userRepository.findById(currentUser.getId()).orElseThrow(
            () -> new UsernameNotFoundException("With this ID does not exist"));
    if (passwordEncoder.matches(reqChangePassword.getOldPassword(),user.getPassword()))
    {
      user.setPassword(passwordEncoder.encode(reqChangePassword.getPassword()));
      userRepository.save(user);
      return new ApiResponse("Password has been changed successfully",true);
    }
    else
      return new ApiResponse("Password has not been changed unfotunetly",false);
  }

}
