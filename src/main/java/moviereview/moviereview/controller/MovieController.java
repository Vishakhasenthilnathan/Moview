package moviereview.moviereview.controller;

import moviereview.moviereview.models.Movie;
import moviereview.moviereview.payload.MovieDTO;
import moviereview.moviereview.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    @Autowired
    public MovieService movieService;

    @GetMapping
    public List<MovieDTO> getAllMovies(){
        return movieService.getAllMovies();
    }

    @GetMapping("/title={title}")
    public List<MovieDTO> getAllMoviesByTitle(@PathVariable(name = "title") String title){
        return movieService.getMovieByTitle(title);
    }

    @GetMapping("/id={id}")
    public MovieDTO getAllMoviesById(@PathVariable(name = "id") Long id){
        return movieService.getMovieById(id);
    }

    @PostMapping("/createMovie")
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movieDTO){
       MovieDTO newMovie = movieService.createMovie(movieDTO);
       return new ResponseEntity<MovieDTO>(movieDTO,HttpStatus.CREATED);
    }
}
