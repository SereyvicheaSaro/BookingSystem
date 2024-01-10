package booking.project.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import booking.project.entity.Movie;
import booking.project.entity.User;
import booking.project.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;
    
    @GetMapping("/Ticket")
    public String Ticket(){
        return "UserTicket";
    }
    @GetMapping("/Admin_Home")
    public String Admin_Home(HttpSession session, Model model) {
        // Check if the user is authenticated
        if (session.getAttribute("authenticatedUser") == null) {
            // Redirect to the login page if not authenticated
            return "redirect:/Login";
        }

        // Check the role of the authenticated user
        User authenticatedUser = (User) session.getAttribute("authenticatedUser");
        if (!"admin".equals(authenticatedUser.getRole())) {
            // Redirect to the appropriate home page based on the user's role
            if ("user".equals(authenticatedUser.getRole())) {
                return "redirect:/Login";
            }
        }

        List<Movie> movieList = movieService.getAllMovies();

        model.addAttribute("movieList", movieList);

        return "AdminHome";
    }

    @GetMapping("/User_Home")
    public String User_Home(HttpSession session, Model model) {
        // Check if the user is authenticated
        User authenticatedUser = (User) session.getAttribute("authenticatedUser");
        if (authenticatedUser == null) {
            // Redirect to the login page if not authenticated
            return "redirect:/Login";
        }

        List<Movie> movieList = movieService.getAllMovies();
        
        model.addAttribute("movieList", movieList);
    
        return "UserHome";
    }    

    @GetMapping("/Movie_List")
    public String Movie_List(Model model, HttpSession session) {
        // Check if the user is authenticated
        if (session.getAttribute("authenticatedUser") == null) {
            // Redirect to the login page if not authenticated
            return "redirect:/Login";
        }

        // Check the role of the authenticated user
        User authenticatedUser = (User) session.getAttribute("authenticatedUser");
        if (!"admin".equals(authenticatedUser.getRole())) {
            // Redirect to the appropriate home page based on the user's role
            if ("user".equals(authenticatedUser.getRole())) {
                return "redirect:/Login";
            }
        }

        // Fetch all products from the database
        List<Movie> movieList = movieService.getAllMovies();

        // Add the product list to the Thymeleaf model
        model.addAttribute("movieList", movieList);


        return "MovieList";
    }

    @GetMapping("/Movie_Insert")
    public String Movie_Insert(HttpSession session, Model model) {
        // Check if the user is authenticated
        if (session.getAttribute("authenticatedUser") == null) {
            // Redirect to the login page if not authenticated
            return "redirect:/Login";
        }

        // Check the role of the authenticated user
        User authenticatedUser = (User) session.getAttribute("authenticatedUser");
        if (!"admin".equals(authenticatedUser.getRole())) {
            // Redirect to the appropriate home page based on the user's role
            if ("user".equals(authenticatedUser.getRole())) {
                return "redirect:/User_Home";
            }
        }

        // Prepare a new product object for the form
        model.addAttribute("movie", new Movie());

        return "MovieInsert";
    }

    @PostMapping("/Movie_Insert")
    public String Movie_Insert(@ModelAttribute("movie") Movie newMovie, HttpServletRequest request, Model model) {
        String title = request.getParameter("title");
        String duration = request.getParameter("duration");
        String releaseDate = request.getParameter("releaseDate");
        String hall = request.getParameter("hall");
        String genre = request.getParameter("genre");
        String extension = request.getParameter("extension");
        String showingDate = request.getParameter("showingDate");
        String showingTime = request.getParameter("showingTime");
        String type = request.getParameter("type");

        // Set the username, password, and role, and then save the user to the database
        newMovie.setTitle(title);
        newMovie.setDuration(duration);
        newMovie.setReleaseDate(releaseDate);
        newMovie.setHall(hall);
        newMovie.setGenre(genre);
        newMovie.setExtension(extension);
        newMovie.setShowingDate(showingDate);
        newMovie.setShowingTime(showingTime);
        newMovie.setType(type);

        // Save the user
        movieService.save(newMovie);

        // Add the success message to the Thymeleaf model
        model.addAttribute("SuccessMessage", "Movie has been successfully inserted!");

        // Return the view name
        return "MovieInsert";
    }

    @GetMapping("/Movie_Update")
    public String showUpdateForm(@RequestParam("movieId") Long movieId, Model model, HttpSession session) {
        // Check if the user is authenticated
        if (session.getAttribute("authenticatedUser") == null) {
            // Redirect to the login page if not authenticated
            return "redirect:/Login";
        }

        // Check the role of the authenticated user
        User authenticatedUser = (User) session.getAttribute("authenticatedUser");
        if (!"admin".equals(authenticatedUser.getRole())) {
            // Redirect to the appropriate home page based on the user's role
            if ("user".equals(authenticatedUser.getRole())) {
                return "redirect:/User_Home";
            }
        }
        
        Optional<Movie> optionalMovie = movieService.getMovieById(movieId);
        
        // Check if the movie exists, otherwise throw an exception
        Movie movie = optionalMovie.orElseThrow(() -> new NoSuchElementException("Movie not found for id: " + movieId));
        
        model.addAttribute("movie", movie);
        return "MovieUpdate";
    }

    @PostMapping("/Movie_Update")
    public String updateMovie(@ModelAttribute("movie") Movie updatedMovie, Model model) {
    
        // Get the ID of the movie to be updated
        Long movieId = updatedMovie.getId();
    
        // Check if the movie ID is not null
        if (movieId != null) {
            // Retrieve the existing movie from the database
            Optional<Movie> existingMovieOptional = movieService.getMovieById(movieId);
    
            // Check if the movie with the given ID exists
            if (existingMovieOptional.isPresent()) {
                Movie existingMovie = existingMovieOptional.get();
    
                // Update the properties of the existing movie with the values from the updated movie
                existingMovie.setTitle(updatedMovie.getTitle());
                existingMovie.setDuration(updatedMovie.getDuration());
                existingMovie.setReleaseDate(updatedMovie.getReleaseDate());
                existingMovie.setHall(updatedMovie.getHall());
                existingMovie.setGenre(updatedMovie.getGenre());
                existingMovie.setExtension(updatedMovie.getExtension());
                existingMovie.setShowingDate(updatedMovie.getShowingDate());
                existingMovie.setShowingTime(updatedMovie.getShowingTime());
                existingMovie.setType(updatedMovie.getType());
                existingMovie.setDescription(updatedMovie.getDescription());
                // Repeat for other properties...
    
                // Save the updated movie
                movieService.save(existingMovie);
    
                // Add success message to the model
                model.addAttribute("SuccessMessage", "Movie has been successfully updated!");
    
                // Return the view name
                return "MovieUpdate";
            }
        }
    
        // Handle the case where the movie ID is null or the movie doesn't exist
        // You can redirect or show an error message as needed
        return "redirect:/Movie_List";
    }    

    @GetMapping("/Movie_Delete")
    public String showDeleteForm(@RequestParam("movieId") Long movieId, Model model, HttpSession session) {
        // Check if the user is authenticated
        if (session.getAttribute("authenticatedUser") == null) {
            // Redirect to the login page if not authenticated
            return "redirect:/Login";
        }

        // Check the role of the authenticated user
        User authenticatedUser = (User) session.getAttribute("authenticatedUser");
        if (!"admin".equals(authenticatedUser.getRole())) {
            // Redirect to the appropriate home page based on the user's role
            if ("user".equals(authenticatedUser.getRole())) {
                return "redirect:/User_Home";
            }
        }
        
        Optional<Movie> optionalMovie = movieService.getMovieById(movieId);
        
        // Check if the movie exists, otherwise throw an exception
        Movie movie = optionalMovie.orElseThrow(() -> new NoSuchElementException("Movie not found for id: " + movieId));
        
        model.addAttribute("movie", movie);
        return "MovieDelete";
    }

    @PostMapping("/Movie_Delete")
    public String deleteMovie(@ModelAttribute("movie") Movie movie, Model model) {
        // Get the ID of the movie to be deleted
        Long movieId = movie.getId();

        // Check if the movie ID is not null
        if (movieId != null) {
            // Delete the movie from the database
            movieService.deleteMovieById(movieId);

            // Redirect to the movie list page or another appropriate page
            return "redirect:/Movie_List";
        }

        // Handle the case where the movie ID is null
        return "redirect:/Movie_List"; // Redirect to the movie list page, for example
    }

    @GetMapping("/Booking")
    public String showBooking(@RequestParam(name = "movieId", required = false) Long movieId, Model model, HttpSession session) {
        // Check if the user is authenticated
        if (session.getAttribute("authenticatedUser") == null) {
            // Redirect to the login page if not authenticated
            return "redirect:/Login";
        }
        
        Optional<Movie> optionalMovie = movieService.getMovieById(movieId);
        
        // Check if the movie exists, otherwise throw an exception
        Movie movie = optionalMovie.orElseThrow(() -> new NoSuchElementException("Movie not found for id: " + movieId));
        
        model.addAttribute("movie", movie);
        return "Booking";
    }    
}
