package com.bookmyshow.bmscore.controller;

import com.bookmyshow.bmscore.models.Movie;
import com.bookmyshow.bmscore.requestDTO.AddMovieDTO;
import com.bookmyshow.bmscore.requestDTO.DateRangeDTO;
import com.bookmyshow.bmscore.requestDTO.MovieIdName;
import com.bookmyshow.bmscore.service.MovieService;
import com.bookmyshow.bmscore.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/apis/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @GetMapping("/findByDateRange")
    public ResponseEntity<?> findByDateRange(@RequestBody DateRangeDTO dto){
        try{
            List<Movie> movies =  movieService.findByDateRange(dto);
            return new ResponseEntity<>(movies , HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Unable to fetch movies" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add-movie")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<String> addMovie(@RequestBody AddMovieDTO dto){
        try{
            String message = movieService.addMovie(dto);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllMovies")
    public List<MovieIdName> getAllMovies(){
        return movieService.findAllMovieIdAndName();
    }
    @GetMapping("/top-ten-movies")
    public ResponseEntity<List<Movie>> findTopTenShowsInLast30Days(LocalDateTime thirtyDaysAgo){
        try{
            List<Movie> movies =  movieService.findTopTenMovies();
            return new ResponseEntity<>(movies, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
