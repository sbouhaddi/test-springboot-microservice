package dev.sabri.testspringbootmicroservice.controller;

import dev.sabri.testspringbootmicroservice.domain.Movie;
import dev.sabri.testspringbootmicroservice.service.MovieService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MovieController {

    public static final String MOVIE_PATH = "/api/v1/movie";
    public static final String MOVIE_PATH_ID = MOVIE_PATH + "/{movieId}";

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(MOVIE_PATH)
    public ResponseEntity getAll() {
        return ResponseEntity.ok(movieService.findAllMovies());
    }


    @DeleteMapping(MOVIE_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("movieId") Long movieId) {

        if (!movieService.deleteMovie(movieId)) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(MOVIE_PATH)
    public ResponseEntity save(@RequestBody Movie movie) {

        Movie savedMovie = movieService.saveMovie(movie);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", MOVIE_PATH + "/" + savedMovie.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping(MOVIE_PATH_ID)
    public ResponseEntity updateById(@PathVariable("movieId") Long movieId, @RequestBody Movie movie) {

        if (movieService.updateMovie(movieId, movie).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @GetMapping(MOVIE_PATH_ID)
    public ResponseEntity<Movie> getById(@PathVariable("movieId") Long movieId) {

        return ResponseEntity.ok(movieService.getMovieById(movieId).orElseThrow(NotFoundException::new));
    }


}