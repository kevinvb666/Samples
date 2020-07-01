package com.test.customer;

import static com.test.customer.service.util.CustomerUtil.calculateRewards;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import com.test.customer.entity.Customer;
import com.test.customer.entity.Customer.Transaction;
import com.test.customer.entity.Customer.Transaction.Audit;

@Configuration
public class CustomerConfiguration {

	@Bean("customers")
	@Scope(value = WebApplicationContext.SCOPE_APPLICATION)
	public List<Customer> customers() {
		return createCustomers();
	}

	private List<Customer> createCustomers() {
		List<Customer> customers = new ArrayList<Customer>();

		Customer customer1 = new Customer();
		customer1.setId("C1");
		customer1.setName("Sam");
		customers.add(customer1);

		Transaction transaction = new Transaction();
		transaction.setPrice(120.00);
		transaction.setRewards(calculateRewards(transaction.getPrice().longValue()));

		Audit audit = new Audit();
		LocalDateTime localDateTime = LocalDateTime.now().minusMonths(6);
		final Long durationNeededInMillis = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		audit.setDate(durationNeededInMillis);
		audit.setCreatedBy("ADHOC");
		transaction.setAudit(audit);

		customer1.getTransactions().add(transaction);

		Customer customer2 = new Customer();
		customer2.setId("C2");
		customer2.setName("Tom");
		customers.add(customer2);

		Customer customer3 = new Customer();
		customer3.setId("C3");
		customer3.setName("Jack");
		customers.add(customer3);

		return customers;

	}

}
