package com.mobileapp.testcases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mobileapp.exceptions.EmptyCartException;
import com.mobileapp.exceptions.MobileNotFoundException;
import com.mobileapp.model.Mobile;
import com.mobileapp.service.CartDetails;
import com.mobileapp.service.ICartService;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class CartDetailsTest {

	@Mock
	ICartService cartService;

	@InjectMocks
	CartDetails cartDetails;
	Mobile mobile1, mobile2, mobile3, mobile4, mobile5, mobile6;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		mobile1 = new Mobile(1, "Samsung", "A53", 26000);
		mobile2 = new Mobile(2, "Xiaomi", "A3", 16000);
		mobile3 = new Mobile(3, "realme", "Neo2", 32000);
		mobile4 = new Mobile(4, "Xiaomi", "3s", 10000);
		mobile5 = new Mobile(5, "Xiaomi", "4a", 10000);
		mobile6 = new Mobile(6, "Samsung", "S21", 53000);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Add to cart")
	void testAddcart() throws MobileNotFoundException {
		Mockito.doNothing().when(cartService).addCart(mobile1);
		String actual = cartDetails.addCart(mobile1);
		String expected = "added successfully";
		assertEquals(expected, actual, "Invalid");
	}

	@Test
	@DisplayName("Add to cart exception")
	void testAddcartException() throws MobileNotFoundException {
		Mockito.doThrow(new MobileNotFoundException("invalid")).when(cartService).addCart(mobile3);
		assertThrows(MobileNotFoundException.class, () -> cartDetails.addCart(mobile3));
	}

	@Test
	@DisplayName("Show cart")
	void testShowCart() throws MobileNotFoundException, EmptyCartException {
		List<Mobile> expected = Arrays.asList(mobile1, mobile3, mobile6);
		doReturn(Arrays.asList(mobile1, mobile6, mobile3)).when(cartService).showCart();
		List<Mobile> actualMobiles = cartDetails.showCart();
		assertEquals(expected, actualMobiles, "not equal");
	}

	@Test
	@DisplayName("Show cart null")
	void testShowCartNull() throws MobileNotFoundException, EmptyCartException {

		doReturn(null).when(cartService).showCart();
		assertNull(cartDetails.showCart());

	}

	@Test
	@DisplayName("Show cart empty")
	void testShowCartEmpty() throws MobileNotFoundException, EmptyCartException {
		doThrow(new EmptyCartException()).when(cartService).showCart();
		assertThrows(EmptyCartException.class, () -> cartDetails.showCart());
	}
	
	@Test
	@DisplayName("Remove cart")
	void testRemoveCart() throws MobileNotFoundException {
		doReturn(true).when(cartService).removeCart(mobile1);
		assertEquals(true, cartDetails.removeFromCart(mobile1));
	}
	
	@Test
	@DisplayName("Remove cart exception")
	void testRemoveCartEmpty() throws MobileNotFoundException {
		doThrow(new MobileNotFoundException()).when(cartService).removeCart(mobile3);
		assertThrows(MobileNotFoundException.class, () -> cartDetails.removeFromCart(mobile3));
	}

}
