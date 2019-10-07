package com.qa.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qa.data.TestData;
import com.qa.util.TestUtil;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class WeatherInfoTest {

	Response response;

//	@DataProvider
//	public Iterator<Object[]> getTestData() {
//
//		ArrayList<Object[]> citiesWeatherData = TestUtil.getDataExcel(TestData.WEATHER_INFO_SHEETNAME);
//		return citiesWeatherData.iterator();
//	}

	@DataProvider
	public Object[][] getTestData() {

		Object[][] citiesWeatherData = TestUtil.getDataExcelCustomer(TestData.WEATHER_INFO_SHEETNAME);
		return citiesWeatherData;
	}

	@Test(dataProvider = "getTestData")
	public void weatherInfoTest(String city, String temperature, String humidity, String weatherdescription,
			String windspeed, String winddirectiondegree) {

		response = TestUtil.getHTTPMethod(city);
		System.out.println(response.getBody().asString());
		// 1. VALIDATE RESPONSE CODE
		Assert.assertEquals(response.getStatusCode(), TestData.RESPONSE_CODE_200);

		// 2. VALIDATE OTHER ATTRIBUTES OF JSON RESPONSE

		JsonPath jPath = response.jsonPath();
		Assert.assertEquals(jPath.get("City"), city);
		Assert.assertEquals(jPath.get("Temperature"), temperature);
		Assert.assertEquals(jPath.get("Humidity"), humidity);
		Assert.assertEquals(jPath.get("WeatherDescription"), weatherdescription);
		Assert.assertEquals(jPath.get("WindSpeed"), windspeed);
		Assert.assertEquals(jPath.get("WindDirectionDegree"), winddirectiondegree);

	}

}
