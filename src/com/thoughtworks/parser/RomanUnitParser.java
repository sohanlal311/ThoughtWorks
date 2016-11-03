package com.thoughtworks.parser;

import java.text.ParseException;

import com.thoughtworks.unit.RomanUnit;

public class RomanUnitParser {

	public static int parse(String input) throws ParseException {
		if (input == null || input.length() == 0) {
			return -1;
		}

		int sum = 0, multiplier, count = 0;
		RomanUnit currentUnit, rightSideUnit = RomanUnit.NA, boundedUnit = RomanUnit.NA;
		char[] charArray = input.toCharArray();
		for (int i = charArray.length - 1; i >= 0; i--) {
			currentUnit = RomanUnit.getRomanUnitByRomanCode(charArray[i]);
			if (currentUnit == null || currentUnit.getValue() < boundedUnit.getValue()) {
				return -1;
			}
			if (currentUnit.getValue() < rightSideUnit.getValue()) {
				if (!currentUnit.isValidPrecedentOf(rightSideUnit) || currentUnit.getValue() == boundedUnit.getValue()) {
					return -1;
				}
				multiplier = -1;
			} else {
				if (currentUnit == rightSideUnit) {
					if (currentUnit.isOneOf(RomanUnit.V, RomanUnit.L, RomanUnit.D)
							|| (currentUnit.isOneOf(RomanUnit.I, RomanUnit.X, RomanUnit.C, RomanUnit.M) && ++count == 4)) {
						return -1;
					}
				} else {
					count = 1;
				}
				multiplier = 1;
			}
			sum += currentUnit.getValue() * multiplier;
			boundedUnit = rightSideUnit;
			rightSideUnit = currentUnit;
		}

		return sum;
	}
}
