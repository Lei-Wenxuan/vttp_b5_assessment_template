package vttp.batch5.sdf.task01;

// Use this class as the entry point of your program

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import vttp.batch5.sdf.task01.models.BikeEntry;

public class Main {

	public static final String[] POSITION = { "highest", "second highest", "third highest", "fourth highest", "fifth highest" };
	public static final String[] WEATHER = { "Clear, Few clouds, Partly cloudy, Partly cloudy",
			"Mist + Cloudy, Mist + Broken clouds, Mist + Few clouds, Mist",
			"Light Snow, Light Rain + Thunderstorm + Scattered clouds, Light Rain + Scattered clouds",
			"Heavy Rain + Ice Pallets + Thunderstorm + Mist, Snow + Fog" };

	public static void main(String[] args) throws IOException {

		Map<Integer, BikeEntry> bikeMap = new HashMap<>();

		Path p = null;
		// p = Paths.get("vttp_b5_assessment_template/task01/day.csv");
		// System.out.println("To remove hardcoded path");
		// System.out.println("To uncomment args ifelse");

		if (args.length > 0) {
			p = Paths.get(args[0]);
		} else {
			System.err.println("No file provided");
			System.exit(0);
		}

		File file = p.toFile();

		if (!file.exists()) {
			System.err.println("File does not exist");
			System.exit(0);
		}

		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		br.readLine();

		String line;
		int idx = 0;

		while ((line = br.readLine()) != null) {
			String[] cols = line.split(",");
			BikeEntry bikeEntry = BikeEntry.toBikeEntry(cols);

			bikeMap.put(idx, bikeEntry);

			idx++;
		}

		br.close();

		Map<Integer, BikeEntry> bikeMapSorted = new LinkedHashMap<>();
		bikeMapSorted = sortByComparator(bikeMap, false);

		int top5 = 0;

		for (Entry<Integer, BikeEntry> entry : bikeMapSorted.entrySet()) {
			if (top5 < 5) {
				BikeEntry bikeEntry = entry.getValue();

				String position = POSITION[top5];
				String season = Utilities.toSeason(bikeEntry.getSeason());
				String day = Utilities.toWeekday(bikeEntry.getWeekday());
				String month = Utilities.toMonth(bikeEntry.getMonth());
				int total = bikeEntry.getCasual() + bikeEntry.getRegistered();
				String weather = toWeather(bikeEntry.getWeather());
				String holidayText = toHoliday(bikeEntry.isHoliday());

				System.out.printf(
						"The %s (position) recorded number of cyclists was in %s (season), on a %s (day) in the month of %s (month).\nThere were a total of %d (total) cyclists. The weather was %s (weather).\n%s (day) was %s.\n\n",
						position, season, day, month, total, weather, day, holidayText);

			}
			top5++;
		}

	}

	// order: True - ASC; False - DESC
	private static Map<Integer, BikeEntry> sortByComparator(Map<Integer, BikeEntry> unsortMap, final boolean order) {

		List<Entry<Integer, BikeEntry>> list = new LinkedList<Entry<Integer, BikeEntry>>(unsortMap.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<Integer, BikeEntry>>() {
			public int compare(Entry<Integer, BikeEntry> o1, Entry<Integer, BikeEntry> o2) {
				Integer int1 = o1.getValue().getCasual() + o1.getValue().getRegistered();
				Integer int2 = o2.getValue().getCasual() + o2.getValue().getRegistered();
				if (order) {
					return int1.compareTo(int2);
				} else {
					return int2.compareTo(int1);
				}
			}
		});

		// Maintaining insertion order with the help of LinkedList
		Map<Integer, BikeEntry> sortedMap = new LinkedHashMap<Integer, BikeEntry>();
		for (Entry<Integer, BikeEntry> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;

	}

	public static void printMap(Map<Integer, BikeEntry> map) {
		for (Entry<Integer, BikeEntry> entry : map.entrySet()) {
			BikeEntry bikeEntry = entry.getValue();
			System.out.println(
					"Key : " + entry.getKey() + " Value : " + (bikeEntry.getCasual() + bikeEntry.getRegistered()));
		}
	}

	public static String toWeather(int weather) {
		// season (1:clear, 2:mist + cloudy, 3:light snow, light rain, 4:heavy rain)
		switch (weather) {
			case 1:
			case 2:
			case 3:
				return WEATHER[weather - 1];
			default:
				return "funny weather";
		}
	}

	public static String toHoliday(boolean isHoliday) {
		String holidayText;

		if (isHoliday) {
			holidayText = "a holiday";
		} else {
			holidayText = "not a holiday";
		}

		return holidayText;
	}
}
