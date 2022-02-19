package moviereview.moviereview.services;

import moviereview.moviereview.payload.MovieDTO;

import java.util.List;

public interface MovieService {
    List<MovieDTO> getAllMovies();
    MovieDTO getMovieByTitle(String title);
    MovieDTO createMovie(MovieDTO movieDTO);
    MovieDTO getMovieById(Long id);
    MovieDTO updateMovieById(Long id, MovieDTO movieDTO);

    void deleteMovieById(Long id);

    void deleteMovieByTitle(String title);
}
