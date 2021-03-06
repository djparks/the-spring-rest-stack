package com.jl.crm.web;

import com.jl.crm.services.*;
import org.apache.commons.logging.*;
import org.springframework.data.rest.repository.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RepositoryEventHandler (Customer.class)
public class CustomerEventHandler {

	private Log logger = LogFactory.getLog(getClass());

	@HandleBeforeCreate
	public void handleBeforeCreate(Customer customer) {
		if (StringUtils.hasText(customer.getFirstName())
		&& StringUtils.hasText(customer.getLastName()) && customer.getUser() != null){
			if (customer.getSignupDate() == null){
				customer.setSignupDate(new java.util.Date());
			}
		}
		else {
			throw new CustomerWriteException(customer, new RuntimeException("you must specify a 'firstName' and a 'lastName' and a valid user reference."));
		}
		logger.debug("handling before create for " + customer.toString());
	}

	@HandleAfterSave
	public void handleAfterSave(Customer customer) {
		logger.debug("saved customer #" + customer.getId());
	}

	@HandleAfterDelete
	public void handleAfterDelete(Customer customer) {
		logger.debug("deleted customer #" + customer.getId());
	}
}
