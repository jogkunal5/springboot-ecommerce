package com.creativecoders.ecommerce.dto;

public class PurchaseResponse {

	private final String orderTrackingNumber;

	public String getOrderTrackingNumber() {
		return orderTrackingNumber;
	}

	public PurchaseResponse(String orderTrackingNumber) {
		this.orderTrackingNumber = orderTrackingNumber;
	}

}
