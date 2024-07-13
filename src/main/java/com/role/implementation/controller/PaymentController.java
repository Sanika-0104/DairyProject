package com.role.implementation.controller;

import com.razorpay.*;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @PostMapping("/newInvoice")
    public String generateNewInvoice(
            @RequestParam(name="t1") String name,
            @RequestParam(name="t2") String contact,
            @RequestParam(name="t3") String email,
            @RequestParam(name="a1") String line1,
            @RequestParam(name="a2") String line2,
            @RequestParam(name="a3") String zipcode,
            @RequestParam(name="a4") String city,
            @RequestParam(name="a5") String state,
            @RequestParam(name="a6") int amount
    ) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient("rzp_test_0EBCNVwrFmCs9Y", "nOqTWvy2zJO3pk58jsWbszsh");

        JSONObject invoiceRequest = new JSONObject();
        invoiceRequest.put("type", "invoice");
        invoiceRequest.put("description", "Invoice for the selected plan");
        invoiceRequest.put("partial_payment", true);

        JSONObject customer = new JSONObject();
        customer.put("name", name);
        customer.put("contact", contact);
        customer.put("email", email);

        JSONObject billingAddress = new JSONObject();
        billingAddress.put("line1", line1);
        billingAddress.put("line2", line2);
        billingAddress.put("zipcode", zipcode);
        billingAddress.put("city", city);
        billingAddress.put("state", state);
        billingAddress.put("country", "IN");
        customer.put("billing_address", billingAddress);

        JSONObject shippingAddress = new JSONObject();
        shippingAddress.put("line1", line1);
        shippingAddress.put("line2", line2);
        shippingAddress.put("zipcode", zipcode);
        shippingAddress.put("city", city);
        shippingAddress.put("state", state);
        shippingAddress.put("country", "IN");
        customer.put("shipping_address", shippingAddress);

        invoiceRequest.put("customer", customer);
        List<Object> lines = new ArrayList<>();
        JSONObject lineItems = new JSONObject();

        lineItems.put("name", "Selected Plan");
        lineItems.put("description", "Subscription plan selected by the user");
        lineItems.put("amount", amount * 100); // Razorpay requires amount in paise
        lineItems.put("currency", "INR");
        lineItems.put("quantity", 1);

        lines.add(lineItems);
        invoiceRequest.put("line_items", lines);
        invoiceRequest.put("email_notify", 1);
        invoiceRequest.put("sms_notify", 1);
        invoiceRequest.put("currency", "INR");
        invoiceRequest.put("expire_by", 2180479824L);

        Invoice invoice = razorpay.invoices.create(invoiceRequest);
        return "Invoice created successfully";
    }
}
