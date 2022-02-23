package moviereview.moviereview.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
//    private String movieTitle;
    private String username;
    private String reviewComments;
    private double starRating;
}
