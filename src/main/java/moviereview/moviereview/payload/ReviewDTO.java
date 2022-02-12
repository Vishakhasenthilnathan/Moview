package moviereview.moviereview.payload;

import lombok.Data;
import moviereview.moviereview.models.Movie;

@Data
public class ReviewDTO {
//    private String movieTitle;
    private String username;
    private String reviewComments;
    private double starRating;
}
