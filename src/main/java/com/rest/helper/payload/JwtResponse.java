package com.rest.helper.payload;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class JwtResponse {
    public String token;
}