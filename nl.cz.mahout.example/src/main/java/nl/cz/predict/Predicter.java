package nl.cz.predict;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.mahout.classifier.evaluation.Auc;
import org.apache.mahout.classifier.sgd.L1;
import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;

public class Predicter {

	private List<Observation> data = new LinkedList<Observation>();
	private double heldOutPercentage = 0.10;

	Predicter() {
		super();
	}

	public int predict() {

		for (int run = 0; run < 20; run++) {
			Collections.shuffle(data);

			int cutoff = (int) (heldOutPercentage * data.size());
			List<Observation> testData = data.subList(0, cutoff);
			List<Observation> trainData = data.subList(cutoff, data.size());
			System.out.println("testData size " + testData.size());
			System.out.println("trainData size " + trainData.size());
			if (testData.isEmpty()) {
				return 0;
			}
			OnlineLogisticRegression logisticRegression = new OnlineLogisticRegression(2, 1, new L1());
			logisticRegression.learningRate(4).alpha(1).lambda(0.000001).stepOffset(10000).decayExponent(0.2);
			for (int pass = 0; pass < 20; pass++) {
				for (Observation observation : trainData) {
					logisticRegression.train(observation.getTarget(), observation.getVector());
				}
				if (pass % 5 == 0) {
					Auc eval = new Auc(0.5);
					for (Observation test : testData) {
						eval.add(test.getTarget(), logisticRegression.classifyScalar(test.getVector()));
//						System.out.printf("%d, %.4f, %.4f\n", pass, logisticRegression.currentLearningRate(),
//								eval.auc());
						if (eval.auc() > 0.7) {
							System.out.printf("%d, %.4f, %.4f\n", pass, logisticRegression.currentLearningRate(),
									eval.auc());
							return test.getPrediction();
						}
					}

				}
			}

		}
		return 0;

	}

	public void addObservation(int sum, int prediction) {

		Observation testData = new Observation(sum, prediction);
		if (sum == prediction) {
			System.out.println("observation with nice prediction " + testData);
		}
		this.data.add(testData);
		if (data.size() % 100 == 0) {
			System.out.println("add already " + data.size() + " observations");
		}

	}

}
