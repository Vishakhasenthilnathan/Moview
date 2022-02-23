package moviereview.moviereview.serviceTest;

import moviereview.moviereview.models.Movie;
import moviereview.moviereview.payload.MovieDTO;
import moviereview.moviereview.repository.MovieRepository;
import moviereview.moviereview.services.CustomUserDetailsService;
import moviereview.moviereview.services.Implementation.MovieServiceImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;


@SpringBootTest
public class MovieServiceTest {
    @MockBean
    CustomUserDetailsService userService;
    @MockBean
    private MovieRepository movieRepository;

    @Autowired
    private MovieServiceImplementation movieService;
    @Test
    public void ShouldReturnNoMoviesWhenNoMovieIsPresent() throws Exception {
        List<Movie> expectedMovies = new ArrayList<>();
        Mockito.when(movieRepository.findAll()).thenReturn(expectedMovies);
        List<MovieDTO> movies = movieService.getAllMovies();
        assertEquals(expectedMovies,movies);
        Mockito.verify(movieRepository,Mockito.times(1)).findAll();
    }
    @Test
    public void ShouldReturnMoviesWhenMoviesArePresent(){
        List<Movie> expectedMovies = new ArrayList<>();
        Mockito.when(movieRepository.findAll()).thenReturn(expectedMovies);
        List<MovieDTO> movies = movieService.getAllMovies();
        assertEquals(expectedMovies,movies);
    }
    @Test
    public void ShouldReturnMovieWhenMovieWithIdIsPresent(){
        Movie movie = MovieBuild();
        Mockito.when(movieRepository.findById(anyLong())).thenReturn(Optional.ofNullable(movie));
        MovieDTO movieDTO = movieService.getMovieById(1L);
        assertEquals(movie.getTitle(),movieDTO.getTitle());
        Mockito.verify(movieRepository,Mockito.times(1)).findById(anyLong());
    }
    @Test
    public void ShouldThrowIdExceptionWhenIdIsNotPresent(){
        Mockito.when(movieRepository.findById(anyLong())).thenThrow(new RuntimeException("Id does not exists"));
         RuntimeException runtimeException = assertThrows(RuntimeException.class, ()->movieService.getMovieById(anyLong()));
         assertEquals(runtimeException.getMessage(),"Id does not exists");
    }
    @Test
    public void ShouldReturnMovieWhenMovieWithTitleIsPresent(){
        Movie movie = MovieBuild();
        Mockito.when(movieRepository.findByTitle(anyString())).thenReturn(Optional.ofNullable(movie));
        MovieDTO movieDTO = movieService.getMovieByTitle(anyString());
        assertEquals(movie.getTitle(),movieDTO.getTitle());
        Mockito.verify(movieRepository,Mockito.times(1)).findByTitle(anyString());
    }
    @Test
    public void ShouldThrowTitleExceptionWhenTitleIsNotPresent(){
        Mockito.when(movieRepository.findByTitle(anyString())).thenThrow(new RuntimeException("Title does not exist"));
        RuntimeException runtimeException = assertThrows(RuntimeException.class, ()->movieService.getMovieByTitle(anyString()));
        assertEquals(runtimeException.getMessage(),"Title does not exist");
    }
    @Test
    public void ShouldMovieBeCreatedWhenMovieIsValid(){
        Movie movie = MovieBuild();
        MovieDTO inputMovieDTO = MovieDTOBuild();
        Mockito.when(movieRepository.save(movie)).thenReturn(movie);
        MovieDTO createdMovie = movieService.createMovie(inputMovieDTO);

        assertEquals(movie.getTitle(),createdMovie.getTitle());
        assertEquals(movie.getDirector(),createdMovie.getDirector());
        assertEquals(movie.getImdbRating(),createdMovie.getImdbRating());
    }
    @Test
    public void ShouldMovieBeNotCreatedWhenMovieIsInvalid(){
        MovieDTO inputMovie = MovieDTOBuild();
        Mockito.when(movieRepository.save(MovieBuild())).thenThrow(new ConstraintViolationException(new HashSet<>()));
        Assertions.assertThrows(ConstraintViolationException.class,()-> movieService.createMovie(inputMovie));
    }
    @Test
    public void ShouldMovieBeDeletedWhenMovieIdIsPresent(){
        MovieDTO movie = MovieDTOBuild();
        Movie movie1 = MovieBuild();
        Mockito.when(movieRepository.findById(anyLong())).thenReturn(Optional.ofNullable(movie1));
        movieService.deleteMovieById(anyLong());
        Mockito.verify(movieRepository,Mockito.times(1)).delete(movie1);
    }
    public Movie MovieBuild(){
        Movie movie = new Movie();
        movie.setTitle("demon 1");
        movie.setDirector("Stanley Kubrick");
        movie.setLeadActor("Malcolm McDowell");
        movie.setImdbRating(8.3);
        movie.setGenre("Crime, Sci-Fi");
        movie.setDurationInMinutes(136);
        return movie;
    }
    public MovieDTO MovieDTOBuild(){
        MovieDTO movie = new MovieDTO();
        movie.setTitle("demon 1");
        movie.setDirector("Stanley Kubrick");
        movie.setLeadActor("Malcolm McDowell");
        movie.setImdbRating(8.3);
        movie.setGenre("Crime, Sci-Fi");
        movie.setDurationInMins(136);
        return movie;
    }
}
