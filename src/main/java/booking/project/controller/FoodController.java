package booking.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import booking.project.service.FoodService;

@Controller
public class FoodController {
        @Autowired
    private FoodService foodService;
    
    @GetMapping("/Food&Beverage")
    public String FNB(){
        return "FNB";
    }
}
