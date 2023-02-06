package com.rest.helper.payload;

import lombok.Data;

@Data
public class JwtRequest {
    public String username;
    public String password;
}