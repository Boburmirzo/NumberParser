package com.interview.parser;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class NumberParserTest {

	//TODO: Cover with more test cases for failures

	@Test
	public void checkNumbersMatch() {
		assertEquals(1, 1);
		Map<String, Integer> countryCodes = new HashMap<>();
		Map<String, String> prefixes = new HashMap<>();
		countryCodes.put("GB", 44);
		countryCodes.put("US", 1);
		prefixes.put("GB", "0");
		prefixes.put("US", "1");
		NumberParser numberParser = new NumberParser(countryCodes, prefixes);

		assertEquals("+442079460056", numberParser.parse("02079460056", "+441614960148"));
		assertEquals("+442079460056", numberParser.parse("+442079460056", "+441614960148"));
		assertEquals("+12079460056", numberParser.parse("12079460056", "+11614960148"));
	}
}
