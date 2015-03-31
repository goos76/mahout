package nl.cz.recommend;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

class User {

	private Long id;
	private String name;
	private TreeMap<Long, Double> recommendations = new TreeMap<>();

	User(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	Long getId() {
		return id;
	}

	String getName() {
		return name;
	}

	void addRecommendation(Movie movie, Double rating) {
		this.recommendations.put(movie.getId(), rating);

	}

	List<String> writeAsCsv() {
		List<String> lines = new ArrayList<>();

		for (Long movieId : this.recommendations.keySet()) {
			StringBuilder csvRecord = new StringBuilder();
			csvRecord.append(this.id + ",");
			csvRecord.append(movieId + ",");
			csvRecord.append(this.recommendations.get(movieId));
			lines.add(csvRecord.toString());
		}
		return lines;
	}

}
