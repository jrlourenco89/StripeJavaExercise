package com.exercise.stripe;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Objects;
import java.util.Scanner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.slf4j.Logger;
import com.stripe.exception.StripeException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class StripeApplicationTests {
	
	/*Utils*/
	private static final Logger log = LoggerFactory.getLogger(StripeApplicationTests.class);
	private Scanner scanner;
	
	/*Exercise Application*/
	private StripeApplication stripeApplication;
	
	@Before
    public void init() {
		this.stripeApplication = new StripeApplication();
		this.scanner =  new Scanner(System.in);
	 }
	 
	
	@Test
	public void goUrbanTask() {
		// Step 1 & 2 - Get user email address from console
		
	    System.out.println("Please enter your email");
	    String email = this.scanner.nextLine(); 
	    
	    //Check if is a valid email address
	    assertTrue("Valid email",Utils.emailValidator(email));
	    
	  
	    try {
	    	//Step 3 - Create customer at stripe with the entered email address
			stripeApplication.createCustomerStripe(email);
			
			//Check for the returned Customer ID
			assertNotNull("Customer must have an ID", stripeApplication.getCustomer().getId());
			
			//Step 4 - Create a credit card source in stripe with token tok_at and type card and attach it as default source to the created customer.
			stripeApplication.createCreditCardSource();
			
			//Step 5 - Ask user for the amount of the charge in Euro.
			System.out.println("Please enter charge amount in Euro");
		    String chargeAmount = scanner.nextLine();
		    //Check if the value is a valid decimal
		    assertTrue("Valid charge amount", Utils.decimalValidator( chargeAmount) );
		   
		    //Step 6 - Create a charge for the requested amount
		    stripeApplication.createCharge(chargeAmount);

		    //Step 7 - Ask the user if he wants to chapture the charge or refund it
		    System.out.println("Do you want to chapture the charge(press A) or refund it(press B) ? ");
		    Boolean chaptureCharge = scanner.nextLine().equalsIgnoreCase("A") ?  true : false;

		    
		    //Check if a refund is needed
		    if(!chaptureCharge) {
		    	this.stripeApplication.createRefund( stripeApplication.getCharge().getId());
		    }
		
		    
		} catch (Exception e) {
			//Log error message
			log.error(e.getMessage());
			
			//Check if there is any Charge and then do the refund
			if(Objects.nonNull(stripeApplication.getCharge())){
				try {
					this.stripeApplication.createRefund( stripeApplication.getCharge().getId());
				} catch (StripeException e1) {
					log.error(e1.getMessage());
				}
				
			}
		}
			
	}
	
	
	@After
	public void end() {
		//Close scanner stream
		scanner.close();	
	}
	
	
}
