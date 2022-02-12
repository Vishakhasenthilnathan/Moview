package moviereview.moviereview.payload;

import lombok.Data;

import java.sql.Time;
import java.time.Year;
import java.util.Date;
@Data
public class MovieDTO {
    private String title;
    private int durationInMins;
    private String genre;
    private double imdbRating;
    private String director;
    private String leadActor;
}
