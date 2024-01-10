package booking.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import booking.project.entity.Movie;
import booking.project.repository.MovieRepository;

@Service
public class FoodService {
    @Autowired
    private MovieRepository movieRepo;

    public List<Movie> getAllMovies(){
        return movieRepo.findAll();
    }
}
