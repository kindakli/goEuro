package com.goeuro.devTest;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

import com.goeuro.devTest.service.CsvWriter;

public class App {

	public static final String URL_API = "http://api.goeuro.com/api/v2/position/suggest/en/";

	public static final String CSV_NAME = "result.csv";

	private static Logger logger = Logger.getLogger("Main");

	public static String buildUrl(String city) {
		return URL_API + city;
	}

	public static String callAPI(String url) {

		try {
			return IOUtils.toString(new URL(url).openStream());
		} catch (IOException e) {
			logger.warning(e.getMessage());
			return "";
		}
	}

	public static void main(String[] args) throws Exception {
		String url;
		String api_output;

		logger.setLevel(Level.INFO);

		switch (args.length) {
		case 0:
			logger.warning("city name is missing");
			System.exit(0);

		case 1:
			url = buildUrl(args[0]);
			logger.info("checking result for " + args[0]);
			api_output = callAPI(url);
			CsvWriter.jacksonWrite(CSV_NAME, api_output);
			break;

		case 2:
			url = buildUrl(args[0]);
			logger.info("checking result for " + args[0]);
			api_output = callAPI(url);
			CsvWriter.jacksonWrite(args[1], api_output);
			break;

		default:
			break;
		}
		logger.info("Done.");
	}
}
