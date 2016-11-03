package com.thoughtworks.parser;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.unit.Metal;
import com.thoughtworks.unit.RomanUnit;

public class GalaxyUnitParser {

	private static Map<String, String> map = new HashMap<String, String>();
	private static ParseException parseException = new ParseException("I have no idea what you are talking about", 0);

	public static void parse(String input) throws ParseException {
		try {
			parse0(input.trim().toUpperCase());
		} catch (ParseException e) {
			// swallow and print on console
			System.out.println(e.getMessage());
		}
	}

	private static void parse0(String input) throws ParseException {
		if (input == null || input.length() == 0) {
			throw parseException;
		}

		char lastChar = input.charAt(input.length() - 1);
		boolean isQue = lastChar == '?';
		if (isQue) {
			input = input.substring(0, input.length() - 1);
		}

		handle(input, isQue);
	}

	private static void handle(String input, boolean isQue) throws ParseException {
		String[] split = input.split(" IS ");
		if (split.length != 2) {
			throw parseException;
		}

		if (isQue) {
			handleQuestion(split);
		} else {
			handleInputs(split);
		}
	}

	private static void handleInputs(String[] split) throws ParseException {
		String unit;
		Metal metal = null;
		String str0 = split[0].trim();
		String[] split0 = str0.split(" ");
		String str1 = split[1].trim();
		String[] split1 = str1.split(" ");

		if (split0.length == 1 && split1.length == 1) {
			unit = extractConversionUnit(str1);
			if (unit.length() == 0) {
				throw parseException;
			}
			map.put(str0.toUpperCase(), str1);
		} else {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < split0.length; i++) {
				unit = map.get(split0[i]);
				if (unit == null) {
					metal = Metal.getMetal(split0[i]);
				} else {
					sb.append(unit);
				}
			}

			if (metal == null || split1.length > 2) {
				throw parseException;
			}

			try {
				double credit = Integer.parseInt(split1[0]);
				int num = RomanUnitParser.parse(sb.toString());
				if (num == -1) {
					throw parseException;
				}
				credit /= num;
				map.put(metal.getName().toUpperCase(), credit + "");
			} catch (NumberFormatException e) {
				throw parseException;
			}
		}
	}

	private static void handleQuestion(String[] split) throws ParseException {
		String unit;
		Metal metal = null;
		String str1 = split[1].trim();
		String[] split1 = str1.split(" ");

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < split1.length; i++) {
			unit = map.get(split1[i]);
			if (unit == null) {
				throw parseException;
			} else {
				metal = Metal.getMetal(split1[i]);
				if (metal == null) {
					sb.append(unit);
				}
			}
		}
		int num = RomanUnitParser.parse(sb.toString());
		if (num == -1) {
			throw parseException;
		}
		if (metal != null) {
			System.out.println(str1 + " is " + (Double.parseDouble(map.get(metal.getName().toUpperCase())) * num));
		} else {
			System.out.println(str1 + " is " + num);
		}
	}

	private static String extractConversionUnit(String str) throws ParseException {
		StringBuilder sb = new StringBuilder();
		for (char ch : str.toCharArray()) {
			if (RomanUnit.getRomanUnitByRomanCode(ch) == null) {
				throw parseException;
			}
			sb.append(ch);
		}
		return sb.toString();
	}
}
