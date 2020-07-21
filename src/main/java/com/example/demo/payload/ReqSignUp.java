package com.example.demo.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReqSignUp
{
    private String username;
    private String fullname;
    private String password;
}
