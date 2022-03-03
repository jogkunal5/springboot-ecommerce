package com.creativecoders.ecommerce.service;

import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.creativecoders.ecommerce.dao.CustomerRepository;
import com.creativecoders.ecommerce.dto.Purchase;
import com.creativecoders.ecommerce.dto.PurchaseResponse;
import com.creativecoders.ecommerce.entity.Customer;
import com.creativecoders.ecommerce.entity.Order;
import com.creativecoders.ecommerce.entity.OrderItem;

@Service
public class CheckoutServiceImpl implements CheckoutService {

	private CustomerRepository customerRepository;

	public CheckoutServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	@Transactional
	public PurchaseResponse placeOrder(Purchase purchase) {

		// retrive order info from dto
		Order order = purchase.getOrder();

		// generate tracking number
		String orderTrackingNumber = generateOrderTrackingNumber();
		order.setOrderTrackingNumber(orderTrackingNumber);

		// populate order with orderItems
		Set<OrderItem> orderItems = purchase.getOrderItems();
		orderItems.forEach(item -> order.add(item));

		// populate order with billingaddress and shipping address
		order.setShippingAddress(purchase.getShippingAddress());
		order.setBillingAddress(purchase.getBillingAddress());
		
		// populate customer with order
		Customer customer = purchase.getCustomer();
		
		// check if this is an existing customer
		String theEmail = customer.getEmail();
		Customer customerFromDB = customerRepository.findByEmail(theEmail);
		
		if(customerFromDB != null) {
			customer = customerFromDB;
		}
		
		customer.add(order);

		// save to db
		customerRepository.save(customer);

		// return a response
		return new PurchaseResponse(orderTrackingNumber);
	}

	private String generateOrderTrackingNumber() {
		// generate random UUID (UUID version-4)		
		return UUID.randomUUID().toString();	
	}

}
