package dev.sabri.testspringbootmicroservice.repository;

import dev.sabri.testspringbootmicroservice.domain.Movie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class MovieRepositoryTest {


    @Autowired
    private MovieRepository movieRepository;
    private Movie movie;


    @BeforeEach
    public void SetUp() {
        movie = new Movie("Movie", "★★★★");
    }


    @Test
    void addingProductShouldReturnSameProduct() {
        var savedMovie = movieRepository.save(movie);
        assertEquals(movie.getRating(), savedMovie.getRating());
        assertNotNull(savedMovie.getId());
    }


    @Test
    void shouldFindMovieListByTitle() {
        var savedMovie = movieRepository.save(movie);
        var movieFound = movieRepository.findMoviesByTitle("Movie");

        assertFalse(movieFound.isEmpty());
        assertTrue(movieFound.size() > 0);
    }


    @AfterEach
    public void tearDown() {
        movieRepository.deleteAll();
        movie = null;
    }


}
