package nl.cz.recommend;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecommenderTest {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(RecommenderTest.class);

	@Test
	public void testRecommend() {
		new Recommender("dataset.csv").recommend(2L, 3);
	}

	@Test
	public void testRecommendRoosje() {
		new Recommender("dataset.csv").recommend("Roosje", 3);
	}

	@Test
	public void testRecommendKeesje() {
		new Recommender("dataset.csv").recommend("Keesje", 2);
	}

	@Test
	public void testRecommendWithDynamicData() {
		System.out.println("Voor data manipulatie...");
		new Recommender().recommend("Roosje", 2);
		new Recommender().recommend("Miepje", 2);
		new Recommender().recommend("Jantje", 2);
		new Recommender().recommend("Keesje", 3);

		System.out.println("\nBeetje data manipuleren...");
		Movie vikings = Dao.INSTANCE.getMovie("vikings");
		Movie gameOfThrones = Dao.INSTANCE.getMovie("game of thrones");
		Movie dexter = Dao.INSTANCE.getMovie("dexter");
		User roosje = Dao.INSTANCE.getUser("Roosje");
		roosje.addRecommendation(vikings, 5.0);
		roosje.addRecommendation(gameOfThrones, 5.0);
		roosje.addRecommendation(dexter, 5.0);


		User miepje = Dao.INSTANCE.getUser("Miepje");
		miepje.addRecommendation(vikings, 5.0);
		miepje.addRecommendation(gameOfThrones, 5.0);
		miepje.addRecommendation(dexter, 5.0);

		User keesje = Dao.INSTANCE.getUser("Keesje");
		keesje.addRecommendation(vikings, 5.0);
		keesje.addRecommendation(gameOfThrones, 5.0);
		keesje.addRecommendation(dexter, 5.0);

		User jantje = Dao.INSTANCE.getUser("Jantje");
		jantje.addRecommendation(vikings, 5.0);
		jantje.addRecommendation(gameOfThrones, 5.0);
		jantje.addRecommendation(dexter, 5.0);

		System.out.println("\nNa data manipulatie...");
		new Recommender().recommend("Roosje", 3);
		new Recommender().recommend("Miepje", 3);
		new Recommender().recommend("Jantje", 3);
		new Recommender().recommend("Keesje", 3);
	}

}
