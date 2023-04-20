package dev.sabri.testspringbootmicroservice.service;

import dev.sabri.testspringbootmicroservice.domain.Movie;
import dev.sabri.testspringbootmicroservice.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Optional<Movie> updateMovie(Long id, Movie movie) {
        AtomicReference<Optional<Movie>> atomicReference = new AtomicReference<>();

        movieRepository.findById(id).ifPresentOrElse(foundMovie -> {
            foundMovie.setTitle(movie.getTitle());
            foundMovie.setRating(movie.getRating());
            atomicReference.set(Optional.of(movieRepository.save(foundMovie)));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }


    public Boolean deleteMovie(Long id) {

        var byId = movieRepository.findById(id);
        if (byId.isPresent()) {
            movieRepository.delete(byId.get());
            return true;
        }
        return false;
    }

    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }
}
