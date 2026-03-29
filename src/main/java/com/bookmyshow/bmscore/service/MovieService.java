package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.customExceptions.MovieAlreadyExistsException;
import com.bookmyshow.bmscore.models.Movie;
import com.bookmyshow.bmscore.repository.MovieRepository;
import com.bookmyshow.bmscore.requestDTO.AddMovieDTO;
import com.bookmyshow.bmscore.requestDTO.MovieIdName;
import com.bookmyshow.bmscore.utilities.CommonUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepo;
    @Autowired
    private CommonUtilities utilities;

    public String addMovie(AddMovieDTO dto){
        String movieName = utilities.normalizeString(dto.getName());

        if(movieRepo.existsByName(movieName)){
            throw new MovieAlreadyExistsException("Movie already exists.");
        }

        Movie movie = new Movie();
        movie.setName(movieName);
        movie.setDuration(dto.getDuration());
        movie.setRating(dto.getRating());
        movie.setReleaseDate(dto.getReleaseDate());
        if(movieRepo.existsByName(movieName)){
            throw new MovieAlreadyExistsException("Movie already exists.");
        }
        movieRepo.save(movie);

        return "Movie added successfully";
    }

    public List<MovieIdName> findAllMovieIdAndName(){
        return movieRepo.findAllIdAndName();
    }
}
//fetch by movie name
//shows on particular date

