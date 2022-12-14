package com.shoponline.service;

import com.shoponline.model.ShippingAddress;

/**
 * This is the abstraction for Home Service
 * Operations related to home such as add address or payment etc
 * will be called from here.
 */
public interface HomeService {
	/**
     * this service operation will add the address
     * corresponding to logged in user in the database.
     * @param shippingAddress
     * @param userName
     */
	public void saveUserAddress(ShippingAddress shippingAddress, String userName);
	
	/**
     * this service operation will process payment of added products in the cart
     * corresponding to logged in user.
     * @param userName
     */
	public ShippingAddress getUserShippingAddress(String userName);
}