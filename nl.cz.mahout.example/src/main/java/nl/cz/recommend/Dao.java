package nl.cz.recommend;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

class Dao {
	static final Dao INSTANCE = new Dao();
	private final TreeMap<Long, User> userMap = new TreeMap<>();
	private final TreeMap<String, User> userMapByName = new TreeMap<>();
	private final TreeMap<Long, Movie> movieMap = new TreeMap<>();
	private final TreeMap<String, Movie> movieByName = new TreeMap<>();

	private Dao() {
		super();
		addUser(1L, "Miepje");
		addUser(2L, "Roosje");
		addUser(3L, "Keesje");
		addUser(4L, "Jantje");

		addMovie(10L, "vikings");
		addMovie(11L, "game of thrones");
		addMovie(12L, "pretty little liars");
		addMovie(13L, "dexter");
		addMovie(14L, "breaking bad");
		addMovie(15L, "suits");
		addMovie(16L, "house of cards");
		addMovie(17L, "happy valley");
		addMovie(18L, "californication");
		addRecommendations();

	}

	void addRecommendations() {
		InputStream inputStream = null;
		try {
			inputStream = this.getClass().getResourceAsStream("dataset.csv");
			List<String> recommendations = IOUtils.readLines(inputStream);
			for (String recommendation : recommendations) {
				String[] fields = StringUtils.split(recommendation, ',');
				User user = this.getUser(Long.parseLong(fields[0]));
				Movie movie = this.getMovie(Long.parseLong(fields[1]));
				Double rating = Double.parseDouble(fields[2]);
				user.addRecommendation(movie, rating);
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}

	}

	User getUser(String name) {
		return this.userMapByName.get(name);

	}

	Movie getMovie(String name) {
		return this.movieByName.get(name);
	}

	User getUser(Long id) {
		return this.userMap.get(id);
	}

	Movie getMovie(Long id) {
		return this.movieMap.get(id);
	}

	File getRecommendationsAsCsv() {
		OutputStream output = null;
		try {
			File dataSet = new File("src/main/java/nl/cz/recommend/datasetnew.csv");
			output = new FileOutputStream(dataSet);
			Collection<User> users = this.userMap.values();
			for (User user : users) {
				List<String> csvRecords = user.writeAsCsv();
				if (!csvRecords.isEmpty()) {
					IOUtils.writeLines(csvRecords, "\n", output);
				}
			}

			return dataSet;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(output);
		}
	}

	private void addMovie(Long id, String name) {
		Movie movie = new Movie(id, name);
		this.movieMap.put(id, movie);
		this.movieByName.put(name, movie);

	}

	private void addUser(Long id, String name) {
		User user = new User(id, name);
		this.userMap.put(id, user);
		this.userMapByName.put(name, user);

	}

}
