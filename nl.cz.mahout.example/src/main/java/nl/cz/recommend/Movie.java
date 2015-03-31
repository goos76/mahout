package nl.cz.recommend;

class Movie {
	private Long id;
	private String name;

	Movie(Long id, String name) {
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

}
