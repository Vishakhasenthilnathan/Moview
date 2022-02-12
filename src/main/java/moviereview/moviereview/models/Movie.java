package moviereview.moviereview.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.sql.Time;
import java.time.Duration;
import java.time.Year;
import java.util.Date;

@Data
@Entity
@Table(name = "movies", uniqueConstraints = {
        @UniqueConstraint(columnNames = "title")
})
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String title;
    private int durationInMinutes;
    private String genre;
    private double imdbRating;
    @NonNull
    private String director;
    private String leadActor;

}
