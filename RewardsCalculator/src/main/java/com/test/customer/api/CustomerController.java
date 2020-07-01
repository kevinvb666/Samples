package com.test.customer.api;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.customer.entity.Customer;
import com.test.customer.entity.CustomerReport;
import com.test.customer.service.CustomerService;

@RestController
@RequestMapping("/")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@PostConstruct
	public void init() {
		System.out.println(1);
	}
	
	
	@GetMapping("/getRewards/{durationInMonths}")
	public CustomerReport getRewards(@PathVariable final Long durationInMonths) {
		return customerService.getRewards(durationInMonths);
	}

	
	@GetMapping("/getRewards/{id}/{durationInMonths}")
	public CustomerReport getRewardsforCustomer(@PathVariable final String id, 
			@PathVariable final Long durationInMonths) {
		return customerService.getRewardsById(id, durationInMonths);
	}
	
	@PatchMapping("/addTransaction/{id}/{price}/{createdBy}")
	public Customer addTransaction(@PathVariable final String id,
			@PathVariable final Double price,
			@PathVariable(required=false) final String createdBy) {
		return customerService.addTransaction(id, price, Optional.ofNullable(createdBy)).orElse(null);
	}
}
