package com.test.customer.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Customer {
	private String id;
	private String name;
	private Address address;
	
	private List<Transaction> transactions = new ArrayList<>();
	
	
	@Data
	public static class Address {
		private String address1;
		private String address2;
		private String city;
		private String zip;
		private String state;
	}
	
	@Data
	public static class Transaction {
		private Double price;
		private Long rewards;
		private Audit audit;
		
		@Data
		public static class Audit {
			private Long date;
			private String createdBy;
		}
	}
}
