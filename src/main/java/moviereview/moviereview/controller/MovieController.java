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

    @PostMapping("/create")
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movieDTO){
       MovieDTO newMovie = movieService.createMovie(movieDTO);
       return new ResponseEntity<MovieDTO>(movieDTO,HttpStatus.CREATED);
    }

    @PutMapping("update/id={id}")
    public ResponseEntity<MovieDTO> updateMovieById(@RequestBody MovieDTO movieDTO, @PathVariable(name = "id") Long id){
      MovieDTO updatedMovie =  movieService.updateMovieById(id,movieDTO);
      return new ResponseEntity<MovieDTO>(updatedMovie,HttpStatus.OK);
    }

    @DeleteMapping("delete/id={id}")
    public String deleteMovieById(@PathVariable(name = "id") Long id){
        movieService.deleteMovieById(id);
        return "Movie deleted successfully";
    }
    @DeleteMapping("delete/title={title}")
    public String deleteMovieByTitle(@PathVariable(name = "title") String title){
        movieService.deleteMovieByTitle(title);
        return "Movie deleted successfully";
    }
}
