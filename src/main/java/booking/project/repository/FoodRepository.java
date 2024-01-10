package booking.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import booking.project.entity.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    
}
