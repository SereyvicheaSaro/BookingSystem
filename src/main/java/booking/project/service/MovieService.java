package booking.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import booking.project.entity.Movie;
import booking.project.repository.MovieRepository;
import jakarta.transaction.Transactional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepo;

    public List<Movie> getAllMovies(){
        return movieRepo.findAll();
    }

    public void save(Movie movie) {
        movieRepo.save(movie);
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepo.findById(id);
    } 

    public void deleteMovieById(Long id) {
        movieRepo.deleteById(id);
    }

    @Transactional
    public void updateMovie(Long id, Movie updatedMovie) {
        // Fetch the existing movie from the database
        Optional<Movie> existingMovieOptional = movieRepo.findById(id);

        // Check if the movie with the given id exists
        if (existingMovieOptional.isPresent()) {
            // Copy properties from updatedMovie to existingMovie, ignoring null values
            Movie existingMovie = existingMovieOptional.get();
            BeanUtils.copyProperties(updatedMovie, existingMovie, "id");

            // Save the updated movie
            movieRepo.save(existingMovie);
        } else {
            throw new RuntimeException("Movie not found with id: " + id);
        }
    }
}
