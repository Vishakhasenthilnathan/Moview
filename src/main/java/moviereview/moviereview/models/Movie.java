package moviereview.moviereview.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.persistence.*;
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
    @Column(nullable = false)
    @NotBlank
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
