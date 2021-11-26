package com.mobileapp.service;

import java.util.List;

import com.mobileapp.exceptions.MobileNotFoundException;
import com.mobileapp.model.Mobile;

public interface IMobileService {
//	return null, exception, mobile
	Mobile getById(int id) throws MobileNotFoundException;
	List<Mobile> getByBrand(String brand) throws MobileNotFoundException;
}
