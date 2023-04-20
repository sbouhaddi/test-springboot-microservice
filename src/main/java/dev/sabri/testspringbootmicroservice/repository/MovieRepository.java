package dev.sabri.testspringbootmicroservice.repository;

import dev.sabri.testspringbootmicroservice.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findMoviesByTitle(String title);

}
