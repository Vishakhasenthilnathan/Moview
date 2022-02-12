package moviereview.moviereview.services;

import moviereview.moviereview.models.Movie;
import moviereview.moviereview.payload.MovieDTO;

import java.util.List;

public interface MovieService {
    List<MovieDTO> getAllMovies();
    List<MovieDTO> getMovieByTitle(String title);
    MovieDTO createMovie(MovieDTO movieDTO);

    MovieDTO getMovieById(Long id);
}
