package nl.cz.predict;

import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;

class Observation {
	private int prediction;
	private int sum;

	Observation(int sum, int prediction) {
		this.prediction = prediction;
		this.sum = sum;
	}

	Vector getVector() {
		Vector vector = new RandomAccessSparseVector(1);
		vector.set(0, sum);
		return vector;
	}

	int getPrediction() {

		return this.prediction;
	}

	int getTarget() {
		return sum == prediction ? 1 : 0;
	}

	@Override
	public String toString() {
		return "Observation [prediction=" + prediction + ", sum=" + sum + "]";
	}

}
