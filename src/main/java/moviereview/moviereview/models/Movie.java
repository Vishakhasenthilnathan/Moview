package moviereview.moviereview.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.time.Duration;
import java.time.Year;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

}
