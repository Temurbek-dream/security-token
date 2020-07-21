package com.example.demo.controller;

import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.JwtResponse;
import com.example.demo.payload.ReqSignIn;
import com.example.demo.payload.ReqSignUp;
import com.example.demo.security.AuthService;
import com.example.demo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public HttpEntity<?> login(@Valid @RequestBody ReqSignIn reqSignIn){
        return getApiToken(reqSignIn.getUsername(), reqSignIn.getPassword());
    }

    @PostMapping("/register")
    public HttpEntity<?> register(@Valid @RequestBody ReqSignUp reqSignUp)
    {
        ApiResponse response = authService.register(reqSignUp);
        if (response.isSuccess())
        {
            return getApiToken(reqSignUp.getUsername(),
                    reqSignUp.getPassword());
        }
        else
            {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    private HttpEntity<?> getApiToken(String username, String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

}
