package booking.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import booking.project.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    
}
