package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.ReqChangePassword;
import com.example.demo.security.CurrentUser;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController
{
    @Autowired
    UserService userService;
   @PostMapping("/changepassword")
   public HttpEntity<?> changepassword(@Valid @RequestBody ReqChangePassword reqChangePassword,
                                       @CurrentUser User user)
   {
       ApiResponse response= userService.changePassword(reqChangePassword,user);
       return ResponseEntity.status(response.isSuccess()? HttpStatus.ACCEPTED:HttpStatus.CONFLICT)
               .body(response);
   }

}
