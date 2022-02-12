package moviereview.moviereview.services;

import moviereview.moviereview.models.Movie;
import moviereview.moviereview.models.Review;
import moviereview.moviereview.payload.ReviewDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ReviewService {
    ReviewDTO postReview(ReviewDTO reviewDTO, Long movieId);
    List<ReviewDTO> getAllReviews(Long movieId);
    ReviewDTO getReviewById(Long movieId,Long reviewId);
    ReviewDTO updateReviewById(Long movieId, Long reviewId, ReviewDTO reviewDTO);
    void deleteReviewById(Long reviewId);
}
