package com.test.customer.service;


import static com.test.customer.service.util.CustomerUtil.calculateRewards;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.customer.entity.Customer;
import com.test.customer.entity.Customer.Transaction;
import com.test.customer.entity.Customer.Transaction.Audit;
import com.test.customer.entity.CustomerReport;

@Service
public class CustomerService {

	@Autowired
	List<Customer> customers;

	public CustomerReport getRewards(final Long durationInMonths) {
		LocalDateTime localDateTime = LocalDateTime.now().minusMonths(durationInMonths);
		final Long durationNeededInMillis = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

		Map<String, List<Transaction>> result = new LinkedHashMap<>();
		final AtomicLong sum = new AtomicLong();
		customers.stream().filter(customer -> {
			// Period Check
			List<Transaction> transactions = customer.getTransactions().stream()
					.filter(transaction -> transaction.getAudit().getDate() >= durationNeededInMillis)
					.collect(Collectors.toList());

			if (!transactions.isEmpty()) {
				result.put(customer.getId(), transactions);
				sum.getAndAdd(transactions.
//						stream().
						parallelStream().
				mapToLong(t -> t.getRewards())
				.sum());
//				reduce((a, b) -> a + b).getAsLong());
				return true;
			} else {
				return false;
			}

		}).count();
		CustomerReport customerReport = new CustomerReport();
		customerReport.setCustomers(result);
		customerReport.setTotal(sum.get());
		return customerReport;
	}

	
	public CustomerReport getRewardsById(final String id, final Long durationInMonths) {
		LocalDateTime localDateTime = LocalDateTime.now().minusMonths(durationInMonths);
		final Long durationNeededInMillis = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

		Map<String, List<Transaction>> result = new LinkedHashMap<>();
		final AtomicLong sum = new AtomicLong();
		customers.stream().filter(customer -> {
			
			// Period Check
			if(id.equalsIgnoreCase(customer.getId())) {
			List<Transaction> transactions = customer.getTransactions().stream()
					.filter(transaction -> transaction.getAudit().getDate() >= durationNeededInMillis)
					.collect(Collectors.toList());

			if (!transactions.isEmpty()) {
				result.put(customer.getId(), transactions);
				sum.getAndAdd(transactions.
						stream().
//						parallelStream().
				mapToLong(t -> t.getRewards())
//				.sum());
				.reduce((a, b) -> a + b).getAsLong());
				return true;
			} else {
				return false;
			}
			}else {
				return false;
			}

		}).count();
		CustomerReport customerReport = new CustomerReport();
		customerReport.setCustomers(result);
		customerReport.setTotal(sum.get());
		return customerReport;
	}
	
	public Optional<Customer> addTransaction(final String customerId, final Double price,
			final Optional<String> createdBy) {
		Optional<Customer> optCustomer = customers.stream()
				.filter(customer -> customerId.equalsIgnoreCase(customer.getId())).findFirst();
		if (optCustomer.isPresent()) {
			Transaction transaction = new Transaction();
			transaction.setPrice(price);
			transaction.setRewards(calculateRewards(price.longValue()));

			Audit audit = new Audit();
			audit.setDate(Calendar.getInstance().getTimeInMillis());
			createdBy.ifPresent(val -> audit.setCreatedBy(val));
			transaction.setAudit(audit);
			optCustomer.get().getTransactions().add(transaction);
		}
		return optCustomer;
	}
}
