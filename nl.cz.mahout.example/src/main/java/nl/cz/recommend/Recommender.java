package nl.cz.recommend;

import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class Recommender {
	private UserBasedRecommender recommender;

	public Recommender() {

		this(Dao.INSTANCE.getRecommendationsAsCsv());

	}

	public Recommender(String dataSetName) {

		this(new File(Recommender.class.getResource(dataSetName).getFile()));

	}

	public Recommender(File file) {

		try {

			DataModel model = new FileDataModel(file);
			UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
			UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
			this.recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void recommend(Long userId, int numberOfRecommendations) {
		try {

			List<RecommendedItem> recommendations = recommender.recommend(userId, numberOfRecommendations);
			String userName = Dao.INSTANCE.getUser(userId).getName();
			System.out.println("Voor " + userName + " zijn de volgende aanbevelingen:");
			for (RecommendedItem recommendation : recommendations) {
				String movieName = Dao.INSTANCE.getMovie(recommendation.getItemID()).getName();
				System.out.println(movieName);
			}
		} catch (TasteException e) {
			throw new RuntimeException(e);
		}

	}

	public void recommend(String name, int numberOfRecommendations) {
		User user = Dao.INSTANCE.getUser(name);
		recommend(user.getId(), numberOfRecommendations);

	}

}
