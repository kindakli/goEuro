package com.goeuro.devTest;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.goeuro.devTest.service.CsvWriter;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@Test
	public void testDefinedCity() throws Exception {
		String url = App.buildUrl("berlin");
		String result = App.callAPI(url);
		Assert.assertTrue(!result.isEmpty() && result.length() > 0);
	}

	@Test
	public void testUndefinedCity() throws Exception {
		String url = App.buildUrl("xyz");
		String result = App.callAPI(url);
		Assert.assertTrue(result.equals("[]"));
	}

	@Test
	public void testEmptyInput() throws Exception {
		String url = App.buildUrl("");
		String result = App.callAPI(url);
		Assert.assertTrue(result.isEmpty());
	}

	@Test
	public void testCsvWriter() throws Exception {
		StringBuilder berlinJson = new StringBuilder();
		berlinJson.append(
				"[{\"_id\":376217,\"key\":null,\"name\":\"Berlin\",\"fullName\":\"Berlin, Germany\",\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Germany\",\"geo_position\":{\"latitude\":52.52437,\"longitude\":13.41053},\"locationId\":8384,\"inEurope\":true,\"countryId\":56,\"countryCode\":\"DE\",\"coreCountry\":true,\"distance\":null,\"names\":{\"it\":\"Berlino\",\"pt_BR\":\"Berlim\",\"fi\":\"Berliini\",\"es\":\"Berlín\",\"pt\":\"Berlim\",\"ru\":\"Берлин\",\"is\":\"Berlín\",\"zh\":\"柏林\",\"cs\":\"Berlín\",\"ca\":\"Berlín\",\"nl\":\"Berlijn\"},\"alternativeNames\":{}}]");

		CsvWriter.jacksonWrite("test.csv", berlinJson.toString());

		File csvFile = new File("test.csv");
		Assert.assertTrue(csvFile.exists());
	}
}
