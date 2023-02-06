package com.rest.helper.payload;

import lombok.Data;
import net.bytebuddy.utility.RandomString;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Data
@ResponseBody
public class OTPResponse {
    private String otp;

    public OTPResponse() {
        otp = UUID.randomUUID() + RandomString.make(2);
    }
}