package com.exercise.stripe;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Refund;
import com.stripe.model.Source;

@SpringBootApplication
public class StripeApplication {

	//Stripe API Identfier
	private static final String STRIPE_API_KEY =  "sk_test_4eC39HqLyjWDarjtT1zdp7dc";

	//Stripe objects
	private Stripe stripe;
	private Customer customer;
	private Source source;
	private Charge charge;
	
	public StripeApplication() {
		//Set Stripe API Key
		stripe.apiKey =  StripeApplication.STRIPE_API_KEY ;
	}
	
	
	public void createCustomerStripe(String email) throws StripeException {
		Map<String, Object> customerParams = new HashMap<String, Object>();
		customerParams.put("email", email);
		customerParams.put("source", "tok_visa");
		//Call Stripe API
		this.customer = Customer.create(customerParams);
	}
	
	
	public void createCreditCardSource() throws StripeException {
		
		Map<String, Object> sourceParams = new HashMap<String, Object>();
		sourceParams.put("type", "card");
		sourceParams.put("token", "tok_at");
		//Call Stripe API
		this.source = Source.create(sourceParams);
		//Set as default source for the customer 
		this.customer.setDefaultSource(source.getId());
	}
	
	public void createCharge(String chargeAmount) throws StripeException {
		
	    Map<String, Object> chargeParams = new HashMap<String, Object>();
	    chargeParams.put("amount", chargeAmount);
	    chargeParams.put("currency", "eur");
	    chargeParams.put("description", "Charge for " + this.customer.getEmail());
	    chargeParams.put("customer", this.customer.getId());
	    //Call Stripe API
	    this.charge =  Charge.create(chargeParams);
	}
	
	public void createRefund(String chargeID) throws StripeException {
		Map<String, Object> refundParams = new HashMap<>();
    	refundParams.put("charge", chargeID);
    	//Call Stripe API
    	Refund.create(refundParams);
	}
	
	
	public Customer getCustomer() {
		return this.customer;
	}
	
	public Charge getCharge() {
		return this.charge;
	}
}
