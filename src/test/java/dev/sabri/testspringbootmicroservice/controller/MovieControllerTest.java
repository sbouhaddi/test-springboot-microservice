package dev.sabri.testspringbootmicroservice.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sabri.testspringbootmicroservice.domain.Movie;
import dev.sabri.testspringbootmicroservice.service.MovieService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MovieControllerTest {

    @Mock
    private MovieService movieService;
    private Movie movie;
    @InjectMocks
    private MovieController movieController;
    private MockMvc mockMvc;


    @BeforeEach
    public void setUp() {
        movie = new Movie(1L, "Movie", "★★★★");
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }


    @Test
    void postMappingMovie() throws Exception {
        when(movieService.saveMovie(any())).thenReturn(movie);
        mockMvc.perform(post(MovieController.MOVIE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(asJsonString(movie))))
                .andExpect(status().isCreated());
        verify(movieService, times(1)).saveMovie(any());

    }


    @Test
    void getMappingById() throws Exception {
        when(movieService.getMovieById(movie.getId())).thenReturn(Optional.of(movie));

        var mvcResult = mockMvc.perform(get(MovieController.MOVIE_PATH + "/" + movie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(movie)))
                .andExpect(status().isOk())
                .andReturn();

        var contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    }

    private String asJsonString(final Object obj) {

        try {

            var s = new ObjectMapper().writeValueAsString(obj);
            return s;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @AfterEach
    void tearDown() {
        movie = null;
        mockMvc = null;
    }
}
