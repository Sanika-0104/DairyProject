package com.role.implementation.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RazorpayClientConfiguration {

    @PostConstruct
    public void init() {
        System.setProperty("razorpay.timeout.connect", "10000");
        System.setProperty("razorpay.timeout.read", "30000");
    }
    @Bean
    public RazorpayClient razorpayClient() throws RazorpayException {
        // Provide your Razorpay key and secret here
        return new RazorpayClient("rzp_test_0EBCNVwrFmCs9Y", "nOqTWvy2zJO3pk58jsWbszsh");
    }
}
