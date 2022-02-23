package moviereview.moviereview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("main.controllers, main.repositories")
//@EnableJpaRepositories("main.repositories")
public class MoviereviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviereviewApplication.class, args);
	}

}
