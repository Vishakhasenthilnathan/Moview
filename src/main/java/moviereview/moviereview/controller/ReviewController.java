package moviereview.moviereview.controller;

import moviereview.moviereview.models.Review;
import moviereview.moviereview.payload.ReviewDTO;
import moviereview.moviereview.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReviewController {
    @Autowired
    public ReviewService reviewService;

    @PostMapping("movies/{movieId}/reviews/post")
    public ResponseEntity<ReviewDTO> postReview(@PathVariable Long movieId,@RequestBody ReviewDTO reviewDTO){
       ReviewDTO createdReview = reviewService.postReview(reviewDTO,movieId);
       return new ResponseEntity<ReviewDTO>(createdReview, HttpStatus.CREATED);
    }
    @GetMapping("movies/{movieId}/reviews")
    public List<ReviewDTO> getAllReviews(@PathVariable Long movieId){
        return reviewService.getAllReviews(movieId);
    }
    @GetMapping("movies/{movieId}/reviews/{reviewId}")
    public ReviewDTO getReviewById(@PathVariable(value = "movieId") Long movieId,
                                   @PathVariable(value = "reviewId") Long reviewId){
        return reviewService.getReviewById(movieId,reviewId);
    }
    @PutMapping("movies/{movieId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDTO> updateReviewById(@PathVariable(value = "movieId") Long movieId,
                                   @PathVariable(value = "reviewId") Long reviewId,
                                   @RequestBody ReviewDTO reviewDTO){
        ReviewDTO review = reviewService.updateReviewById(movieId, reviewId, reviewDTO);
        return new ResponseEntity<ReviewDTO>(review,HttpStatus.OK);
    }
    @DeleteMapping("movies/{movieId}/reviews/{reviewId}")
    public String deleteReview(@PathVariable(value = "movieId") Long movieId,
                               @PathVariable(value = "reviewId") Long reviewId){
        reviewService.deleteReviewById(reviewId);
        return "review deleted successfully";
    }
}
