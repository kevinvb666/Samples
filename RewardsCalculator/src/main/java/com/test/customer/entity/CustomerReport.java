package com.test.customer.entity;

import java.util.List;
import java.util.Map;

import com.test.customer.entity.Customer.Transaction;

import lombok.Data;

@Data
public class CustomerReport {

	Map<String, List<Transaction>> customers;
	private Long total;
	
}
