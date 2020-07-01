package com.test.customer.service.util;

public class CustomerUtil {

	public static Long calculateRewards(final Long price) {
		if (price >= 50 && price <= 100) {
			return price - 50;
		} else if (price > 100) {
			return (2 * (price - 100) + 50);
		}
		return 0L;
	}
}
