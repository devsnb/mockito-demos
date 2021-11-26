package com.mobileapp.testcases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
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

import com.mobileapp.exceptions.MobileNotFoundException;
import com.mobileapp.model.Mobile;
import com.mobileapp.service.IMobileService;
import com.mobileapp.service.OrderDetails;


@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class OrderDetailsTest {
	
	@Mock
	IMobileService mobileService;
	
//	create an object of orderDetails
	@InjectMocks
	OrderDetails orderDetails;
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
		orderDetails = new OrderDetails();
		orderDetails.setMobileService(mobileService);
		List<Mobile> mobileData = Arrays.asList(mobile1, mobile2, mobile3, mobile4, mobile5, mobile6);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Show mobiles")
	void testShowMobiles() throws MobileNotFoundException {
		String brand = "Samsung";
		List<Mobile> expectedMobiles = Arrays.asList(mobile6, mobile1);
//		use the proxy object to call the method of IMobileService with when then
		Mockito.when(mobileService.getByBrand("Samsung"))
					.thenReturn(Arrays.asList(mobile1, mobile6));
		List<Mobile> actualMobileList = orderDetails.showMobiles(brand);
		assertEquals(expectedMobiles, actualMobileList, "List not equal");
	
	}
	
	@Test
	@DisplayName("Show mobiles by brand invalid ")
	void testshowMobilesInvalid() throws MobileNotFoundException {
		Mockito.when(mobileService.getByBrand("vivo"))
			.thenThrow(MobileNotFoundException.class);
		assertThrows(MobileNotFoundException.class, () -> orderDetails.showMobiles("vivo"));
	}
	
	@Test
	@DisplayName("Show mobiles null")
	void testShowMobilesNull() throws MobileNotFoundException {
		String brand = "Samsung";

		Mockito.when(mobileService.getByBrand("Samsung"))
					.thenReturn(null);
		
		List<Mobile> actualMobileList = orderDetails.showMobiles(brand);
		assertNull(actualMobileList);
	
	}
	
	@Test
	@DisplayName("Show mobiles empty")
	void testShowMobilesEmpty() throws MobileNotFoundException {
		String brand = "Moto";

		Mockito.when(mobileService.getByBrand("Moto"))
					.thenReturn(new ArrayList<>());
		
		List<Mobile> actualMobileList = orderDetails.showMobiles(brand);
		assertEquals(0, actualMobileList.size(), "List should be empty");
	
	}
	
	@Test
	@DisplayName("Show mobiles sort by brand")
	void testShowMobilesSortByBrand() throws MobileNotFoundException {
		String brand = "Samsung";
		List<Mobile> expectedMobiles = Arrays.asList(mobile6, mobile1);
		
		Mockito.when(mobileService.getByBrand("Samsung"))
					.thenReturn(Arrays.asList(mobile1, mobile6));
		List<Mobile> actualMobileList = orderDetails.showMobiles(brand);
		
		assertEquals(expectedMobiles, actualMobileList, "List not equal");
	
	}
	
	@Test
	@DisplayName("Mobiles successful order")
	void testOrderValid() throws MobileNotFoundException {
		String expected = "Mobile Ordered";
		Mockito.when(mobileService.getById(1)).thenReturn(mobile1);
		String actualMobile = orderDetails.orderMobile(1);
		assertEquals(expected, actualMobile, "Not Same");
	}
	
	@Test
	@DisplayName("Invalid order")
	void testOrderInvalid() throws MobileNotFoundException {
		String expected = "Mobile Not Ordered";
		Mockito.when(mobileService.getById(100)).thenReturn(null);
		String actual = orderDetails.orderMobile(100);
		assertEquals(expected, actual, "values are different");
	}
	
	@Test
	@DisplayName("Checking with exception")
	void testOrderException() throws MobileNotFoundException {
		String expected = "Mobile Not Ordered";
		Mockito.when(mobileService.getById(100)).thenThrow(MobileNotFoundException.class);
		String actual = orderDetails.orderMobile(100);
		assertEquals(expected, actual, "values are different");
	}
	
	@Test
	@DisplayName("Checking with empty object")
	void testOrderEmpty() throws MobileNotFoundException {
		String expected = "Mobile Not Ordered";
		Mockito.when(mobileService.getById(100)).thenReturn(new Mobile());
		String actual = orderDetails.orderMobile(100);
		assertEquals(expected, actual, "Empty object expected");
	}

}
