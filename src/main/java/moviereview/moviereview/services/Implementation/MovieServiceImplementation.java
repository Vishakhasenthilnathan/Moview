package moviereview.moviereview.services.Implementation;

import moviereview.moviereview.models.Movie;
import moviereview.moviereview.payload.MovieDTO;
import moviereview.moviereview.repository.MovieRepository;
import moviereview.moviereview.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImplementation implements MovieService {
    @Autowired
    public MovieRepository movieRepository;

    @Override
    public List<MovieDTO> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream().map(movie -> MapMovieToDTOResponse(movie))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> getMovieByTitle(String title) {
       List<Movie> movieByTitle = movieRepository.findAllByTitle(title);
        return movieByTitle.stream().map(movie -> MapMovieToDTOResponse(movie))
                .collect(Collectors.toList());
    }

    @Override
    public MovieDTO createMovie(MovieDTO movieDTO) {
        Movie newMovie = MapDtoToMovie(movieDTO);
        movieRepository.save(newMovie);
        MovieDTO createdMovieDTO = MapMovieToDTOResponse(newMovie);
        return createdMovieDTO;
    }

    @Override
    public MovieDTO getMovieById(Long id) throws RuntimeException {
        Optional<Movie> movie = movieRepository.findById(id);
        if(movie.isPresent())
        return MapMovieToDTOResponse(movie.get());
        else
            throw new RuntimeException("Id does not exists");
    }

    private MovieDTO MapMovieToDTOResponse(Movie movie) {
        MovieDTO createdMovieDTO = new MovieDTO();
        createdMovieDTO.setTitle(movie.getTitle());
        createdMovieDTO.setDirector(movie.getDirector());
        createdMovieDTO.setLeadActor(movie.getLeadActor());
        createdMovieDTO.setImdbRating(movie.getImdbRating());
        createdMovieDTO.setGenre(movie.getGenre());
        createdMovieDTO.setDurationInMins(movie.getDurationInMinutes());
        return createdMovieDTO;
    }

    private Movie MapDtoToMovie(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle());
        movie.setDirector(movieDTO.getDirector());
        movie.setLeadActor(movieDTO.getLeadActor());
        movie.setImdbRating(movieDTO.getImdbRating());
        movie.setGenre(movieDTO.getGenre());
        movie.setDurationInMinutes(movieDTO.getDurationInMins());
        return movie;
    }

}
