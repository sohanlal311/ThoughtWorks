package com.thoughtworks.unit;

import java.util.HashMap;
import java.util.Map;

public enum RomanUnit {

	I('I', 1), V('V', 5), X('X', 10), L('L', 50), C('C', 100), D('D', 500), M('M', 1000), NA('\0', 0);

	private static final Map<Character, RomanUnit> romanCodeToRomanUnit = new HashMap<Character, RomanUnit>();

	static {
		for (RomanUnit u : values()) {
			romanCodeToRomanUnit.put(u.getRomanCode(), u);
		}
	}

	public static RomanUnit getRomanUnitByRomanCode(char romanCode) {
		return romanCodeToRomanUnit.get(romanCode);
	}

	private final char romanCode;
	private final int value;

	private RomanUnit(char romanCode, int value) {
		this.romanCode = romanCode;
		this.value = value;
	}

	public char getRomanCode() {
		return romanCode;
	}

	public int getValue() {
		return value;
	}

	public boolean isOneOf(RomanUnit... romanUnits) {
		boolean result = false;
		if (romanUnits != null) {
			for (int i = 0; i < romanUnits.length; i++) {
				if (this == romanUnits[i]) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	public boolean isValidPrecedentOf(RomanUnit right) {
		boolean result = false;
		switch (this) {
		case I:
			result = right == RomanUnit.V || right == RomanUnit.X;
			break;
		case X:
			result = right == RomanUnit.L || right == RomanUnit.C;
			break;
		case C:
			result = right == RomanUnit.D || right == RomanUnit.M;
			break;
		default:
			break;
		}
		return result;
	}

}
