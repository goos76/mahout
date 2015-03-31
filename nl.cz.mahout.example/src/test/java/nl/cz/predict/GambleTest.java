package nl.cz.predict;

import java.util.Random;

import org.junit.Test;

public class GambleTest {

	@Test
	public void testPrediction() {
		Predicter predicter = new Predicter();
		Dice dice1 = new Dice();
		Dice dice2 = new Dice();
		Random random = new Random();

		for (int i = 0; i < 1000000; i++) {

			int sum = dice1.roll() + dice2.roll();
			int prediction = random.nextInt(12) + 1;
			predicter.addObservation(sum, prediction);

		}
		System.out.println("Time for prediction");
		for (int i = 0; i < 1000;) {
			int prediction = predicter.predict();
			int sum = dice1.roll() + dice2.roll();

			if (prediction == sum) {
				System.out.println("predicted succesfully; prediction= " + prediction + " ;sum= " + sum);
				return;
			}

		}

	}
}
