package com.thoughtworks.unit;

public enum Metal {

	SILVER("Silver"), GOLD("Gold"), IRON("Iron");

	private final String name;

	private Metal(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static Metal getMetal(String source) {
		source = source.trim();
		for (Metal metal : Metal.values()) {
			if (source.equalsIgnoreCase(metal.getName())) {
				return metal;
			}
		}
		return null;
	}

}
