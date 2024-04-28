// Package declaration for the Utilities class

package com.accgroupproject.realestate.util;

// Import statements for regular expressions and matchers
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Utility class for common operations
public class Utilities {

// Array of cities for city highlighting

	static String[] cities = { "toronto", "vancouver", "montreal", "calgary", "ottawa", "edmonton", "quebec city",
			"winnipeg", "hamilton", "kitchener", "london", "st. catharines", "halifax", "oshawa", "victoria", "windsor",
			"saskatoon", "regina", "sherbrooke", "barrie", "st. john's", "kelowna", "abbotsford", "greater sudbury",
			"kingston", "saguenay", "trois-rivières", "guelph", "moncton", "brantford", "thunder bay", "peterborough",
			"saint john", "red deer", "lethbridge", "nanaimo", "kamloops", "belleville", "chatham-kent", "fredericton",
			"prince george", "drummondville", "saint john's", "vernon", "sherwood park", "saint-jérôme", "jonquière",
			"red deer", "medicine hat", "norfolk", "drummondville", "newmarket", "châteauguay", "white rock",
			"st. albert", "granby", "airdrie", "halton hills", "aurora", "north bay", "belleville", "mirabel",
			"repentigny", "brossard", "penticton", "wood buffalo", "aurora", "prince albert", "orillia", "lasalle",
			"orangeville", "vaudreuil-dorion", "leamington", "terrace", "salaberry-de-valleyfield", "strathcona county",
			"clarence-rockland", "petawawa", "new tecumseth", "rimouski", "sylvan lake", "fort st. john", "joliette",
			"orillia", "grande prairie", "owen sound", "brooks", "chestermere", "cochrane", "lloydminster",
			"rouyn-noranda", "varennes", "elliot lake", "thetford mines", "port colborne", "yellowknife", "val-d'or",
			"timmins", "woodstock", "pembroke", "leduc", "weyburn", "brandon", "salmon arm", "camrose", "stouffville",
			"wetaskiwin", "spruce grove", "sarnia", "collingwood", "quinte west", "campbell river", "courtenay",
			"parksville", "brandon", "saint-constant", "mascouche", "beaconsfield", "kirkland", "sainte-thérèse",
			"thorold", "sept-îles", "sainte-julie", "moose jaw", "duncan", "swift current", "port alberni",
			"prince rupert", "brooks", "yellowknife", "norfolk county", "caledon", "chilliwack", "kamloops",
			"fort erie", "miramichi", "fort saskatchewan", "brandon", "saint-lazare", "dorval", "new glasgow",
			"prince albert", "bathurst", "baie-comeau", "sept-îles", "corner brook", "parksville", "petawawa",
			"lloydminster", "new tecumseth", "collingwood", "lacombe", "stony plain", "brockville", "centre wellington",
			"orangeville", "okotoks", "yorkton", "huntsville", "sidney", "langford", "joliette", "steinbach",
			"cranbrook", "portage la prairie", "fort st. john", "grimsby", "hawkesbury", "new york", "los angeles",
			"chicago", "houston", "phoenix", "philadelphia", "san antonio", "san diego", "dallas", "san jose", "austin",
			"jacksonville", "fort worth", "columbus", "san francisco", "charlotte", "indianapolis", "seattle", "denver",
			"washington", "boston", "el paso", "nashville", "detroit", "oklahoma city", "portland", "las vegas",
			"memphis", "louisville", "baltimore", "milwaukee", "albuquerque", "tucson", "fresno", "sacramento",
			"kansas city", "long beach", "mesa", "atlanta", "colorado springs", "virginia beach", "raleigh", "omaha",
			"miami", "oakland", "minneapolis", "tulsa", "wichita", "new orleans", "arlington", "cleveland",
			"bakersfield", "tampa", "aurora", "honolulu", "anaheim", "santa ana", "corpus christi", "riverside",
			"st. louis", "lexington", "stockton", "pittsburgh", "saint paul", "anchorage", "cincinnati", "henderson",
			"greensboro", "plano", "abu dhabi", "dubai", "sharjah", "ajman", "um al quwain", "fujairah",
			"ras al khaimah", "al ain", "al gharbia", "al reef", "al raha beach", "al reem island", "al samha",
			"al shahama", "bani yas", "barsha heights", "bur dubai", "business bay", "corniche area", "deira",
			"dubailand", "emirates hills", "garhoud", "greens", "international city", "jbr - jumeirah beach residence",
			"jlt - jumeirah lake towers", "jumeirah", "karama", "khalidiya", "khalifa city", "khor fakkan", "mankhool",
			"marina", "mirdif", "muhaisnah", "nadd al hamar", "nad al sheba", "naif", "old town", "oud metha",
			"palm jumeirah", "satwa", "sheikh zayed road", "silicon oasis", "sonapur", "sports city", "tcom",
			"technology park", "the greens", "the lagoons", "the lakes", "the meadows", "the palm", "the springs",
			"tourist club area", "umm suqeim", "wadi al safa", "zabeel" };

// Method to compute the Longest Prefix Suffix (LPS) array for the KMP algorithm
	public static int[] computeLPSArray(String pattern) {
		int m = pattern.length();
		int[] lps = new int[m];
		int length = 0;
		int i = 1;

		while (i < m) {
			if (Character.toLowerCase(pattern.charAt(i)) == Character.toLowerCase(pattern.charAt(length))) {
				length++;
				lps[i] = length;
				i++;
			} else {
				if (length != 0) {
					length = lps[length - 1];
				} else {
					lps[i] = 0;
					i++;
				}
			}
		}

		return lps;
	}

// Method for performing KMP search and replace to highlight city names
	public static String kmpSearchAndReplace(String text, String pattern) {
		int n = text.length();
		int m = pattern.length();

		int[] lps = computeLPSArray(pattern);

		StringBuilder result = new StringBuilder();
		int i = 0, j = 0;

		while (i < n) {
			if (Character.toLowerCase(pattern.charAt(j)) == Character.toLowerCase(text.charAt(i))) {
				i++;
				j++;
			}

			if (j == m) {
				result.append("<span id=\"cityNameFound\"> ").append(pattern).append(" </span>");
				j = 0;
			} else if (i < n && Character.toLowerCase(pattern.charAt(j)) != Character.toLowerCase(text.charAt(i))) {
				if (j != 0) {
					j = lps[j - 1];
				} else {
					result.append(text.charAt(i));
					i++;
				}
			}
		}

		result.append(text.substring(i));
		return result.toString();
	}
// Method to highlight cities and ZIP codes in the given title

	public static String highlightCitiesAndZipCodes(String title) {
		String result = title;

		try {
			for (String city : cities) {
				String cityRegex = "\\b" + city + "\\b";
				Pattern cityPattern = Pattern.compile(cityRegex, Pattern.CASE_INSENSITIVE);
				Matcher cityMatcher = cityPattern.matcher(result);

				while (cityMatcher.find()) {
					String foundCity = cityMatcher.group();
//					result = result.replace(foundCity, "<span id=\"cityNameFound\" >" + foundCity + "</span>");
					result = kmpSearchAndReplace(title, city);

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (checkCanadianPostalCode(result).length() > 0) {
			// System.out.println("Canadian postal code found in the input string.");
			result = checkCanadianPostalCode(result);
		} else if (checkUSZipCode(result).length() > 0) {
			// System.out.println("U.S. ZIP code found in the input string.");
			result = checkUSZipCode(result);
		} else if (checkDubaiPostalCode(result).length() > 0) {
			// System.out.println("Dubai postal code found in the input string.");
			result = checkDubaiPostalCode(result);
		} else {
			// System.out.println("No postal code found in the input string.");
		}

		return result;
	}
// Private method to check and highlight Canadian postal codes
	private static String checkCanadianPostalCode(String input) {
		String canadianPostalCodeRegex = "[A-Za-z]\\d[A-Za-z] ?\\d[A-Za-z]\\d"; // Allow for an optional space
		canadianPostalCodeRegex += "|[A-Za-z]\\d[A-Za-z]\\d[A-Za-z]\\d"; // No space allowed
		Pattern pattern = Pattern.compile(canadianPostalCodeRegex);
		Matcher matcher = pattern.matcher(input);

		while (matcher.find()) {
			String foundZipCode = matcher.group();
			input = input.replace(foundZipCode, "<span id=\"zipCodeFound\" > " + foundZipCode + " </span>");
		}
		return input;
	}

// Private method to check and highlight US ZIP codes
	private static String checkUSZipCode(String input) {
		String usZipCodeRegex = "\\b\\d{5}(?:-\\d{4})?\\b";
		Pattern pattern = Pattern.compile(usZipCodeRegex);
		Matcher matcher = pattern.matcher(input);

		while (matcher.find()) {
			String foundZipCode = matcher.group();
			input = input.replace(foundZipCode, "<span id=\"zipCodeFound\" > " + foundZipCode + " </span>");
		}
		return input;
	}
// Private method to check and highlight Dubai postal codes
	private static String checkDubaiPostalCode(String input) {
		String dubaiPostalCodeRegex = "\\b\\d{5}\\b"; // Adjust as needed for Dubai postal code format
		Pattern pattern = Pattern.compile(dubaiPostalCodeRegex);
		Matcher matcher = pattern.matcher(input);

		while (matcher.find()) {
			String foundZipCode = matcher.group();
			input = input.replace(foundZipCode, "<span id=\"zipCodeFound\" > " + foundZipCode + " </span>");
		}
		return input;
	}
// Main method for testing the utility functions

	public static void main(String[] args) { 
		String input = "628 Fleet St, Suite 920, Toronto, ON M5V 1A8";
		String result = highlightCitiesAndZipCodes(input);
		System.out.println(result);
	}

}
