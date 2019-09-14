package com.interview.parser;

import org.junit.platform.commons.util.StringUtils;

import java.util.Map;
import java.util.Optional;

public class NumberParser {

	private final Map<String, Integer> callingCodes;
	private final Map<String, String> prefixes;

	/**
	 * Constructor.
	 *
	 * @param callingCodes country code map.
	 * @param prefixes     country calling code map.
	 */
	public NumberParser(Map<String, Integer> callingCodes,
	                    Map<String, String> prefixes) {
		Validator.validateCountryCodes(callingCodes);
		this.callingCodes = callingCodes;
		this.prefixes = prefixes;
	}

	/**
	 * Parse dialled number in international format
	 *
	 * @param dialledNumber dialled Number.
	 * @param userNumber    user Number.
	 */
	public String parse(String dialledNumber,
	                    String userNumber) {
		Validator.validateInputNumbers(dialledNumber, userNumber);
		if (dialledNumber.startsWith("+")) {
			return dialledNumber;
		}

		Optional<String> userNumberCountry = findCountryCode(userNumber);

		if (!userNumberCountry.isPresent()) {
			throw new RuntimeException("Country code doesn't exist");
		}

		String userNumberCountryCode = userNumberCountry.get();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("+").append(callingCodes.get(userNumberCountryCode));

		String convertedDialledNumber =
				dialledNumber.substring(prefixes.get(userNumberCountryCode).length());

		return stringBuilder.append(convertedDialledNumber).toString();
	}

	private Optional<String> findCountryCode(String userNumber) {
		System.out.println(callingCodes);
		return callingCodes.entrySet().stream().filter(codesEntry ->
				userNumber.startsWith(codesEntry.getValue().toString(), 1))
				.findAny().map(Map.Entry::getKey);
	}

	private static class Validator {

		private static void validateInputNumbers(String dialledNumber,
		                                         String userNumber) {
			validateNotBlank(dialledNumber, userNumber);
			validateUserNumberFormat(userNumber);
		}

		private static void validateUserNumberFormat(String userNumber) {
			if (!userNumber.startsWith("+") || !userNumber.substring(1).matches("-?\\d+(\\.\\d+)?")) {
				throw new IllegalArgumentException("Incorrect international number format");
			}
		}

		private static void validateNotBlank(String dialledNumber,
		                                     String userNumber) {
			if (StringUtils.isBlank(dialledNumber) || StringUtils.isBlank(userNumber)) {
				throw new IllegalArgumentException("Dialled Number or userNumber is empty");
			}
		}

		private static void validateCountryCodes(Map<String, Integer> callingCodes) {
			callingCodes.values().stream().map(code -> Integer.toString(code))
					.filter(countryCode -> countryCode.isEmpty() ||
							countryCode.startsWith("0") ||
							countryCode.length() > 4)
					.findAny().ifPresent(countryCode -> {
				throw new NumberFormatException("Country code validation is failed");
			});
		}
	}
}
