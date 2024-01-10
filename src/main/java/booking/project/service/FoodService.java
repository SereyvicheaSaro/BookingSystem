package booking.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import booking.project.entity.Food;
import booking.project.repository.FoodRepository;

@Service
public class FoodService {
    @Autowired
    private FoodRepository foodRepo;

    public List<Food> getAllFoods(){
        return foodRepo.findAll();
    }
}
