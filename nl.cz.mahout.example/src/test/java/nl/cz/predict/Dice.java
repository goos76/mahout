package nl.cz.predict;

import java.util.Random;

class Dice {
	private final Random random = new Random();

	Dice() {
		super();
	}

	int roll() {
		return random.nextInt(6) + 1;
	}

}
