package com.mobileapp.service;

import java.util.List;

import com.mobileapp.exceptions.EmptyCartException;
import com.mobileapp.exceptions.MobileNotFoundException;
import com.mobileapp.model.Mobile;

public interface ICartService {
	List<Mobile> showCart() throws EmptyCartException;
	void addCart(Mobile mobile) throws MobileNotFoundException;
	boolean removeCart(Mobile mobile) throws MobileNotFoundException;
}
