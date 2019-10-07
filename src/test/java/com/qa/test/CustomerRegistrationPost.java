package com.qa.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qa.data.TestData;
import com.qa.util.TestUtil;
import io.restassured.response.Response;

public class CustomerRegistrationPost {

	@DataProvider
	public Object[][] getTestDataCustomer() {

		Object[][] customerArr = TestUtil.getDataExcelCustomer(TestData.CUSTOMER_INFO_SHEETNAME);
		return customerArr;
	}

	@Test(dataProvider = "getTestDataCustomer")
	public void createCustomerTest(String FirstName, String LastName, String UserName, String Password, String Email) {

		Response response = TestUtil.postHTTPMethod(FirstName, LastName, UserName, Password, Email);
		

		// TestObjects tObj = response.as(TestObjects.class); //DESERIALIZATION
		if (response.getBody().asString().contains("USER_ALREADY_EXISTS")) {

			Assert.assertEquals(response.getStatusCode(), TestData.RESPONSE_CODE_200);
//			Assert.assertEquals(tObj.FaultId, TestData.FAULT_ID_TXT);
//			Assert.assertEquals(tObj.fault, TestData.FAULT_TXT);
			System.out.println(response.jsonPath().get("FaultId"));

			Assert.assertEquals(response.jsonPath().get("FaultId"), TestData.FAULT_ID_TXT);

		}

	}

}
