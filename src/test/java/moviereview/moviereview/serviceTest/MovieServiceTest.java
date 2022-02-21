package moviereview.moviereview.serviceTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import moviereview.moviereview.models.Movie;
import moviereview.moviereview.payload.MovieDTO;
import moviereview.moviereview.repository.MovieRepository;
import moviereview.moviereview.services.CustomUserDetailsService;
import moviereview.moviereview.services.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieService.class)
@ComponentScan
public class MovieServiceTest {
    @MockBean
    CustomUserDetailsService userService;
    @Autowired
    private MovieRepository movieRepository;

    private MovieService movieService;

    @Test
    public void ShouldReturnNoMoviesWhenNoMovieIsPresent() throws Exception {
//        Movie movie =  MovieBuild();
//        movieRepository.save(movie);
//
//        List<MovieDTO> movies = movieService.getAllMovies();
//
//        assertEquals(1, movies.size());
//        assertEquals("A Clockwork Orange", movies.get(0).getTitle());

    }
    public Movie MovieBuild(){
        Movie movie = new Movie();
        movie.setTitle("A Clockwork Orange");
        movie.setDirector("Stanley Kubrick");
        movie.setLeadActor("Malcolm McDowell");
        movie.setImdbRating(8.3);
        movie.setGenre("Crime, Sci-Fi");
        movie.setDurationInMinutes(136);
        return movie;
    }
}
