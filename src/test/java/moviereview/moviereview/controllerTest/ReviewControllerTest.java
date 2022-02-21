package moviereview.moviereview.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import moviereview.moviereview.controller.ReviewController;
import moviereview.moviereview.payload.MovieDTO;
import moviereview.moviereview.payload.ReviewDTO;
import moviereview.moviereview.services.CustomUserDetailsService;
import moviereview.moviereview.services.MovieService;
import moviereview.moviereview.services.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
@WithMockUser
public class ReviewControllerTest {
    @MockBean
    private CustomUserDetailsService userDetailsService;
    @MockBean
    private ReviewService reviewService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void ShouldReturnEmptyReviewsWhenNoReviewIsPresent() throws Exception {
        when(reviewService.getAllReviews(anyLong())).thenReturn(new ArrayList<ReviewDTO>());
        mockMvc.perform(get("/api/movies/1/reviews")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
        verify(reviewService, times(1)).getAllReviews(1L);
    }
    @Test
    public void ShouldReturnReviewsWhenReviewIsPresent() throws Exception {
        List<ReviewDTO> reviewDTOList = new ArrayList<ReviewDTO>();
        reviewDTOList.add(ReviewDTOBuild());
        when(reviewService.getAllReviews(anyLong())).thenReturn(reviewDTOList);
        mockMvc.perform(get("/api/movies/1/reviews")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
        verify(reviewService, times(1)).getAllReviews(1L);
    }
    @Test
    public void ShouldReturnMovieReviewWhenGivenMovieIdIsPresent() throws Exception {
        ReviewDTO anyMovie = ReviewDTOBuild();
        when(reviewService.getReviewById(anyLong(),anyLong())).thenReturn(anyMovie);
        mockMvc.perform(get("/api/movies/{movieId}/reviews/1",1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(anyMovie)));
        verify(reviewService).getReviewById(anyLong(),anyLong());
    }
    @Test
    public void ShouldCreateReviewWhenReviewIsValid() throws Exception {
        ReviewDTO reviewDTO = ReviewDTOBuild();
        when(reviewService.postReview(any(),anyLong())).thenReturn(reviewDTO);
        mockMvc.perform(post("/api/movies/{movieId}/reviews/post",1)
                        .content(objectMapper.writeValueAsString(reviewDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(reviewDTO)));
        verify(reviewService).postReview(any(),anyLong());
    }
    @Test
    public void ShouldReviewBeDeletedWhenMovieIdIsPresent() throws Exception {
        when(reviewService.getReviewById(1L,1L)).thenReturn(ReviewDTOBuild());
        mockMvc.perform(delete("/api/movies/{movieId}/reviews/{reviewId}",1,1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("review deleted successfully"));
    }

    @Test
    public void ShouldReviewBeUpdatedWhenIdIsPresent() throws Exception {
        ReviewDTO reviewDTO = ReviewDTOBuild();
        when(reviewService.updateReviewById(anyLong(),anyLong(),any())).thenReturn(reviewDTO);
        mockMvc.perform(put("/api/movies/{movieId}/reviews/{reviewId}",1,1)
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(reviewDTO)))
               .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(reviewDTO)));
    }
    public ReviewDTO ReviewDTOBuild(){
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setReviewComments("Nice movie");
        reviewDTO.setUsername("user");
        reviewDTO.setStarRating(4);
        return reviewDTO;
    }
}
