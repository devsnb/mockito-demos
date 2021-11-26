package com.mobileapp.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.mobileapp.exceptions.EmptyCartException;
import com.mobileapp.exceptions.MobileNotFoundException;
import com.mobileapp.model.Mobile;

public class CartDetails {
	ICartService cartService;

	public void setCartService(ICartService cartService) {
		this.cartService = cartService;
	}

	public List<Mobile> showCart() throws EmptyCartException {
		List<Mobile> mobileList;

		try {
			mobileList = cartService.showCart();
		} catch (EmptyCartException e) {
			throw e;
		}

		if (mobileList != null) {
			return mobileList.stream().sorted(Comparator.comparing(Mobile::getMobileId)).collect(Collectors.toList());
		}

		return mobileList;
	}

	public String addCart(Mobile mobile) throws MobileNotFoundException {
		cartService.addCart(mobile);

		return "added successfully";
	}

	public boolean removeFromCart(Mobile mobile) throws MobileNotFoundException {
		Boolean result = false;
		try {
			result = cartService.removeCart(mobile);
		} catch (MobileNotFoundException e) {
			throw e;
		}

		return result;
	}
}
