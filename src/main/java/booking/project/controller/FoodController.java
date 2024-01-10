package booking.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import booking.project.entity.Food;
import booking.project.service.FoodService;
import jakarta.servlet.http.HttpSession;

@Controller
public class FoodController {
    @Autowired
    private FoodService foodService;

    @GetMapping("/Food&Beverage")
    public String FoodAndBeverage(HttpSession session, Model model) {
        // Check if the user is authenticated
        if (session.getAttribute("authenticatedUser") == null) {
            // Redirect to the login page if not authenticated
            return "redirect:/Login";
        }

        List<Food> foodList = foodService.getAllFoods();

        model.addAttribute("foodList", foodList);

        return "FNB";
    }
}
