package moviereview.moviereview.repository;

import moviereview.moviereview.models.Movie;
import moviereview.moviereview.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    Optional<Movie> findByTitle(String title);
    List<Movie> findAllByTitle(String title);
    Boolean existsByTitle(String title);
    Optional<Movie> findByDirector(String director);
    Boolean existsByDirector(String director);

}
