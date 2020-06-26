package com.tomankiewicz.rafal.castawayspringboot.species;

public enum Fish {
	
	ASP("Asp", 40, 15),
	BARBEL("Barbel", 40, 30),
	BREAM("Bream", 25, 21),
	BROWN_TROUT("Brown trout", 30, 40),
	CARP("Carp", 30, 5),
	CATFISH("Catfish", 70.0, 1),
	CHUB("Chub", 25, 33),
	EEL("Eel", 40, 12),
	GRAYLING("Grayling", 31, 214),
	PERCH("Perch", 15.0, 40),
	PIKE("Pike", 50.0, 7),
	RAINBOW_TROUT("Rainbow trout", 30, 19),
	ROACH("Roach", 20, 100),
	RUDD("Rudd", 20, 120),
	SALMON("Salmon", 30, 5),
	SILVER_BREAM("Silver bream", 25, 50),
	STURGEON("Sturgeon", 50, 3),
	TENCH("Tench", 25, 48),
	ZANDER("Zander", 50.0, 8);


	

	private final String displayValue;
	private final double minLength;
	private final int pointFactor;
	
	private Fish(String displayValue, double minLength, int pointFactor) {
		this.displayValue = displayValue;
		this.minLength = minLength;
		this.pointFactor = pointFactor;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public double getMinLength() {
		return minLength;
	}

	public int getPointFactor() {
		return pointFactor;
	}
	
	public static Fish[] getAllSpeciesArray() {
		
		return Fish.values();
		
	}
	
}
