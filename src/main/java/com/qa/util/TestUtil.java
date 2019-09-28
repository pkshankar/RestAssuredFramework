package com.qa.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import com.qa.base.TestBase;
import com.qa.data.TestData;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestUtil {

	static TestBase testBase = new TestBase();

	public static ArrayList<Object[]> getDataExcel(String sheetName) {

		ArrayList<Object[]> weatherDataList = new ArrayList<Object[]>();

		try {
			FileInputStream fis = new FileInputStream(TestData.WEATHER_TEST_DATA_SHEET_LOCATION);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sh = wb.getSheet(sheetName);
			int lastRowNum = sh.getLastRowNum();
			for (int rw = 1; rw <= lastRowNum; rw++) {

				String city = sh.getRow(rw).getCell(0).getStringCellValue();
				String temperature = sh.getRow(rw).getCell(1).getStringCellValue();
				String humidity = sh.getRow(rw).getCell(2).getStringCellValue();
				String weatherdescription = sh.getRow(rw).getCell(3).getStringCellValue();
				String windspeed = sh.getRow(rw).getCell(4).getStringCellValue();
				String winddirectiondegree = sh.getRow(rw).getCell(5).getStringCellValue();
				weatherDataList.add(new Object[] { city, temperature, humidity, weatherdescription, windspeed,
						winddirectiondegree });

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return weatherDataList;
	}

	public static Response getHTTPMethod(String cityName) {

		RestAssured.baseURI = TestBase.prop.getProperty("baseUrl") + "/" + cityName;
		RequestSpecification httpRequest = RestAssured.given();
		return httpRequest.request(Method.GET);

	}

	@SuppressWarnings("unchecked")
	public static Response postHTTPMethod(String FirstName, String LastName, String UserName, String Password,
			String Email) {

		RestAssured.baseURI = TestBase.prop.getProperty("baseUrlPost") + TestData.SERVICE_URL;
		RequestSpecification httpRequest = RestAssured.given();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(TestData.FIRST_NAME, FirstName);
		jsonObj.put(TestData.LAST_NAME, LastName);
		jsonObj.put(TestData.USER_NAME, UserName);
		jsonObj.put(TestData.PASSWORD, Password);
		jsonObj.put(TestData.EMAIL, Email);

		// ADD THE JSON PAYLOAD TO THE REQUEST BODY

		httpRequest.body(jsonObj.toJSONString());
		return httpRequest.post();

	}

	public static Object[][] getDataExcelCustomer(String sheetName) {

		Object[][] objectArr = null;

		try {
			FileInputStream fis = new FileInputStream(TestData.CUSTOMER_DATA_SHEET_LOCATION);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sh = wb.getSheet(sheetName);
			int lastRowNum = sh.getLastRowNum();
			int lastCellNum = sh.getRow(0).getLastCellNum();
			objectArr = new Object[lastRowNum][lastCellNum];
			for (int rw = 0; rw < lastRowNum; rw++) {

				for (int cl = 0; cl < lastCellNum; cl++) {

					objectArr[rw][cl] = sh.getRow(rw + 1).getCell(cl).getStringCellValue();

				}

			}
			return objectArr;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return objectArr;

	}

}
