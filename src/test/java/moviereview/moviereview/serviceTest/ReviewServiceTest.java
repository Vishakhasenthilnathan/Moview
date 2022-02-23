package moviereview.moviereview.serviceTest;

import moviereview.moviereview.models.Movie;
import moviereview.moviereview.models.Review;
import moviereview.moviereview.payload.ReviewDTO;
import moviereview.moviereview.repository.MovieRepository;
import moviereview.moviereview.repository.ReviewRepository;
import moviereview.moviereview.services.ReviewService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReviewServiceTest {

    @MockBean
    ReviewRepository reviewRepository;
    @MockBean
    MovieRepository movieRepository;
    @Autowired
    ReviewService reviewService;
    @Test
    public void ShouldReturnReviewsWhenReviewIsPresent(){
        List<Review> expectedReviews = new ArrayList<>();
        expectedReviews.add(new Review(1,"user","nice movie",7.6,MovieBuild()));
        when(reviewRepository.findByMovieId(anyLong())).thenReturn(expectedReviews);
        List<ReviewDTO> reviews = reviewService.getAllReviews(anyLong());
        Assertions.assertEquals(expectedReviews.get(0).getReviewComments(),reviews.get(0).getReviewComments());
    }
    @Test
    public void ShouldReturnEmptyReviewsWhenReviewIsNotPresent(){
        List<Review> expectedReviews = new ArrayList<>();
        when(reviewRepository.findByMovieId(anyLong())).thenReturn(expectedReviews);
        List<ReviewDTO> reviews = reviewService.getAllReviews(anyLong());
        Assertions.assertEquals(expectedReviews.size(),reviews.size());
    }
    @Test
    public void ShouldThrowMovieNotFoundErrorWhenMovieIsNotValid(){
        when(movieRepository.findById(anyLong())).thenThrow(new RuntimeException("Movie does not exists"));
        Assertions.assertThrows(RuntimeException.class,()->reviewService.getAllReviews(anyLong()));
    }
    @Test
    public void ShouldThrowReviewNotFoundErrorWhenReviewIdIsNotValid(){
        when(reviewRepository.findById(anyLong())).thenThrow(new RuntimeException("Review does not exists"));
        Assertions.assertThrows(RuntimeException.class,()->reviewService.getReviewById(anyLong(),anyLong()));
    }
    @Test
    public void ShouldReviewBeCreatedBeWhenReviewIsValid(){
        Movie movie = MovieBuild();
        Review review = new Review(1,"user","nice movie",7.6,movie);
        when(reviewRepository.save(review)).thenReturn(review);
        when(movieRepository.findById(anyLong())).thenReturn(Optional.ofNullable(movie));
        ReviewDTO reviewDTO = new ReviewDTO("user","nice movie",7.6);
        ReviewDTO reviewCreated = reviewService.postReview(reviewDTO,anyLong());

        Assertions.assertEquals(review.getUsername(),reviewCreated.getUsername());
        Assertions.assertEquals(review.getReviewComments(),reviewCreated.getReviewComments());
        Assertions.assertEquals(review.getStarRating(),reviewCreated.getStarRating());
        verify(reviewRepository,times(1)).save(any());
    }
    @Test
    public void ShouldReviewBeDeletedWhenReviewIdIsPresent(){
        Movie movie = MovieBuild();
        Review review = new Review(1,"user","nice movie",7.6,movie);
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));

        reviewService.deleteReviewById(anyLong());
        verify(reviewRepository,times(1)).delete(review);
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
}
