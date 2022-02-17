package moviereview.moviereview.models;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
@Builder
@Getter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String username;
    @NonNull
    private String reviewComments;
    @NonNull
    private double starRating;
    @ManyToOne
    private Movie movie;
}
