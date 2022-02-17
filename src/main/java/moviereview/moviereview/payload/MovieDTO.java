package moviereview.moviereview.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.Year;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private String title;
    private int durationInMins;
    private String genre;
    private double imdbRating;
    private String director;
    private String leadActor;


}
