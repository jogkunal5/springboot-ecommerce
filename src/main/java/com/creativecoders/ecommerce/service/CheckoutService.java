package com.creativecoders.ecommerce.service;

import com.creativecoders.ecommerce.dto.PaymentInfo;
import com.creativecoders.ecommerce.dto.Purchase;
import com.creativecoders.ecommerce.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {

	PurchaseResponse placeOrder(Purchase purchase);

	PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;

}
