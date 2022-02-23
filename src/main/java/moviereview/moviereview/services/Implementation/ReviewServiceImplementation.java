package moviereview.moviereview.services.Implementation;

import moviereview.moviereview.models.Movie;
import moviereview.moviereview.models.Review;
import moviereview.moviereview.payload.ReviewDTO;
import moviereview.moviereview.repository.MovieRepository;
import moviereview.moviereview.repository.ReviewRepository;
import moviereview.moviereview.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImplementation implements ReviewService {
    @Autowired
    public ReviewRepository reviewRepository;
    @Autowired
    public MovieRepository movieRepository;
    @Override
    public ReviewDTO postReview(ReviewDTO reviewDTO, Long movieId) {
        Review review = MapReviewDtoToReviewEntity(reviewDTO);
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new RuntimeException("Movie does not exists"));
        review.setMovie(movie);
        Review createdReview = reviewRepository.save(review);
        ReviewDTO createdreviewDTO = MapReviewToDTO(review);
        return createdreviewDTO;
    }

    @Override
    public List<ReviewDTO> getAllReviews(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new RuntimeException("Movie does not exists"));
            List<Review> reviews = reviewRepository.findByMovieId(movieId);
            List<ReviewDTO> reviewsCreated = reviews.stream().map(review -> MapReviewToDTO(review)).collect(Collectors.toList());
            return reviewsCreated;
    }

    @Override
    public ReviewDTO getReviewById(Long movieId, Long reviewId){
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new RuntimeException("Movie does not exists"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new RuntimeException("Review does not exists"));
        ReviewDTO reviewDTO = MapReviewToDTO(review);
        return reviewDTO;
    }

    @Override
    public ReviewDTO updateReviewById(Long movieId, Long reviewId, ReviewDTO reviewDTO) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new RuntimeException("Movie does not exists"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new RuntimeException("Review does not exists"));

        review = UpdateReview(review,reviewDTO);

        return MapReviewToDTO(review);
    }


    @Override
    public void deleteReviewById(Long reviewId) {
     Review review =  reviewRepository.findById(reviewId).orElseThrow(()->new RuntimeException("Review does not exists"));
     reviewRepository.delete(review);
    }

    private ReviewDTO MapReviewToDTO(Review review) {
        ReviewDTO createdreviewDTO = new ReviewDTO();
        createdreviewDTO.setUsername(review.getUsername());
        createdreviewDTO.setReviewComments(review.getReviewComments());
        createdreviewDTO.setStarRating(review.getStarRating());
        return createdreviewDTO;
    }

    private Review MapReviewDtoToReviewEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setUsername(reviewDTO.getUsername());
        review.setReviewComments(reviewDTO.getReviewComments());
        review.setStarRating(reviewDTO.getStarRating());
        return review;
    }
    private Review UpdateReview(Review review, ReviewDTO reviewDTO) {
        if(!Objects.isNull(reviewDTO.getUsername()))
            review.setUsername(reviewDTO.getUsername());
        if(!Objects.isNull(reviewDTO.getReviewComments()))
            review.setReviewComments(reviewDTO.getReviewComments());
        if(!Objects.isNull(reviewDTO.getStarRating()))
            review.setStarRating(reviewDTO.getStarRating());

        return review;
    }

}
