package com.creativecoders.ecommerce.service;

import com.creativecoders.ecommerce.dto.Purchase;
import com.creativecoders.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {
	PurchaseResponse placeOrder(Purchase purchase);
}
