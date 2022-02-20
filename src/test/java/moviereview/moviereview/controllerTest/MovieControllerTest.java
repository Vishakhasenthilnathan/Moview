package moviereview.moviereview.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import moviereview.moviereview.controller.MovieController;
import moviereview.moviereview.payload.MovieDTO;
import moviereview.moviereview.services.CustomUserDetailsService;
import moviereview.moviereview.services.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
@WithMockUser
public class MovieControllerTest {
    @MockBean
    CustomUserDetailsService userService;
    @MockBean
    private MovieService movieService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void ShouldReturnEmptyMoviesWhenNoMovieIsPresent() throws Exception {
        when(movieService.getAllMovies()).thenReturn(new ArrayList<MovieDTO>());
        mockMvc.perform(get("/api/movies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
        verify(movieService, times(1)).getAllMovies();
    }
    @Test
    public void ShouldReturnOneMovieWhenOneMovieIsPresent() throws Exception {
        List<MovieDTO> movies = new ArrayList<MovieDTO>();
        MovieDTO firstMovie = MovieDTOBuild();
        movies.add(firstMovie);
        when(movieService.getAllMovies()).thenReturn(movies);
        mockMvc.perform(get("/api/movies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
        verify(movieService).getAllMovies();
    }

    @Test
    public void ShouldReturnMovieWhenGivenMovieIdIsPresent() throws Exception {
        MovieDTO anyMovie = MovieDTOBuild();
        when(movieService.getMovieById(anyLong())).thenReturn(anyMovie);
        mockMvc.perform(get("/api/movies/id=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(anyMovie)));
        verify(movieService).getMovieById(anyLong());
    }

    @Test
    public void ShouldReturnMovieTitleWhenGivenMovieTitleIsPresent() throws Exception {
        MovieDTO anyMovie = MovieDTOBuild();
        when(movieService.getMovieByTitle(anyString())).thenReturn(anyMovie);
        mockMvc.perform(get("/api/movies/title=Hi").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("A Clockwork Orange"));
        verify(movieService).getMovieByTitle(anyString());
    }

    @Test
    public void shouldMovieServiceBeCalledOnceWhenGetMovieByIdIsInvoked() throws Exception {
      //Arrange Act Assert
        when(movieService.getMovieById(1L)).thenReturn(new MovieDTO());
        mockMvc.perform(get("/api/movies/id=1")
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
        verify(movieService,times(1)).getMovieById(1L);
    }
    @Test
    public void ShouldCreateMovieWhenMovieIsValid() throws Exception {
        MovieDTO movie = MovieDTOBuild();
        when(movieService.createMovie(any())).thenReturn(movie);
        mockMvc.perform(post("/api/movies/create")
                .content(objectMapper.writeValueAsString(movie))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(movie)));
        verify(movieService).createMovie(any());
    }
    @Test
    public void ShouldNotCreateMovieWhenMovieIsInvalid() throws Exception {
        MovieDTO movie = MovieDTOBuild();
        movie.setTitle(null);
        when(movieService.createMovie(any()))
               .thenThrow(new ConstraintViolationException(new HashSet<>()));
        mockMvc.perform(post("/api/movies/create")
                        .content(objectMapper.writeValueAsString(movie))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(movieService).createMovie(any());
    }
    public MovieDTO MovieDTOBuild(){
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle("A Clockwork Orange");
        movieDTO.setDirector("Stanley Kubrick");
        movieDTO.setLeadActor("Malcolm McDowell");
        movieDTO.setImdbRating(8.3);
        movieDTO.setGenre("Crime, Sci-Fi");
        movieDTO.setDurationInMins(136);
        return movieDTO;
    }
}
