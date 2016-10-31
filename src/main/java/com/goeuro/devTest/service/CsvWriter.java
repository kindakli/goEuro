package com.goeuro.devTest.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goeuro.devTest.model.City;

public class CsvWriter {

	public static final String[] CSV_HEADER = { "id", "name", "type", "latitude", "longitude" };
	private static CSVFormat FORMAT = CSVFormat.DEFAULT.withHeader(CSV_HEADER).withDelimiter(',');

	private static Logger logger = Logger.getLogger("Writer");

	public static void jacksonWrite(String fileName, String json)
			throws JsonParseException, JsonMappingException, IOException {

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
				CSVPrinter printer = new CSVPrinter(writer, FORMAT)) {

			ObjectMapper objectMapper = new ObjectMapper();

			List<City> cities = objectMapper.readValue(json,
					objectMapper.getTypeFactory().constructCollectionType(List.class, City.class));

			for (City city : cities) {
				printer.print(city.get_id());
				printer.print(city.getName());
				printer.print(city.getType());

				printer.print(city.getGeo_position().getLatitude());
				printer.print(city.getGeo_position().getLongitude());

				printer.println();
			}
			printer.flush();

		} catch (IOException e) {
			logger.severe(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public static void write(String fileName, JSONArray jsonArray) {

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			CSVPrinter printer = new CSVPrinter(writer, FORMAT);

			for (Object city : jsonArray) {

				printer.print(((JSONObject) city).get("_id"));
				printer.print(((JSONObject) city).get("name"));
				printer.print(((JSONObject) city).get("type"));

				JSONObject position = (JSONObject) ((JSONObject) city).get("geo_position");
				printer.print(position.get("latitude"));
				printer.print(position.get("longitude"));

				printer.println();
			}

			printer.flush();
			printer.close();
		} catch (IOException e) {
			logger.severe(e.getMessage());
			throw new RuntimeException(e);
		}
	}
}
