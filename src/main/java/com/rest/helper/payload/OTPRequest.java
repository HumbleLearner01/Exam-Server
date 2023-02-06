package com.rest.helper.payload;

import lombok.Data;

import java.util.Date;

@Data
public class OTPRequest {
    private String otpRequest;
    private Date otpRequestedTime;
}