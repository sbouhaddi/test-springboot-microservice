package dev.sabri.testspringbootmicroservice.service;


import dev.sabri.testspringbootmicroservice.domain.Movie;
import dev.sabri.testspringbootmicroservice.repository.MovieRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private MovieService movieService;
    private Movie movie;

    @BeforeEach
    public void setUp() {
        movie = new Movie("Movie", "★★★★");
    }


    @Test
    void saveMovieShouldReturnGoodValue() {
        when(movieRepository.save(any())).thenReturn(movie);
        var movie1 = movieService.saveMovie(movie);
        verify(movieRepository, times(1)).save(any());
        assertEquals(movie.getTitle(), movie1.getTitle());
    }


    @AfterEach
    void tearDown() {
        movie = null;
    }
}
