package com.role.implementation.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
public class OrderCreationController {

    private final RazorpayClient client;

    public OrderCreationController() throws RazorpayException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        this.client = new RazorpayClient("rzp_test_0EBCNVwrFmCs9Y", "nOqTWvy2zJO3pk58jsWbszsh") {
            //@Override
            protected Response executeRequest(Request request) throws RazorpayException {
                try {
                    return okHttpClient.newCall(request).execute();
                } catch (IOException e) {
                    throw new RazorpayException(e.getMessage(), e);
                }
            }
        };
    }

    @GetMapping("/RZP_Java/OrderCreation")
    public String createOrder() {
        String orderId = null;
        try {
            JSONObject options = new JSONObject();
            options.put("amount", "100");
            options.put("currency", "INR");
            options.put("receipt", "zxr456");
            options.put("payment_capture", true);
            Order order = client.orders.create(options);
            orderId = order.get("id");
        } catch (RazorpayException e) {
            e.printStackTrace();
            return "Error creating order: " + e.getMessage();
        }
        return orderId;
    }

    @PostMapping("/RZP_Java/OrderCreation")
    public String verifyPayment(@RequestParam("razorpay_payment_id") String paymentId,
                                @RequestParam("razorpay_order_id") String orderId,
                                @RequestParam("razorpay_signature") String signature) {
        try {
            JSONObject options = new JSONObject();
            options.put("razorpay_payment_id", paymentId);
            options.put("razorpay_order_id", orderId);
            options.put("razorpay_signature", signature);
            boolean sigRes = Utils.verifyPaymentSignature(options, "Secret");
            if (sigRes) {
                return "Payment successful and Signature Verified";
            } else {
                return "Payment failed and Signature not Verified";
            }
        } catch (RazorpayException e) {
            e.printStackTrace();
            return "Error in processing payment verification: " + e.getMessage();
        }
    }
}
