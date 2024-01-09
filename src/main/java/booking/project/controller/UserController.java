package booking.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import booking.project.entity.User;
import booking.project.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class UserController {

    // injects an instance of the UserService, allowing the services to be used
    @Autowired
    private UserService Uservice;
    
    @GetMapping("/Login")
    public String Login() {
        return "Login";
    }

    @GetMapping("/logout")
    public String logout() {
        // Add your logout logic here if needed
        return "redirect:/Login"; // Redirect to the login page
    }

    @PostMapping("/Login")
    public String Login(HttpServletRequest request, HttpSession session) {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");

        User authenticatedUser = Uservice.authenticateUser(userName, password);

        if (authenticatedUser != null) {
            // Store the authenticated user in the session
            session.setAttribute("authenticatedUser", authenticatedUser);

            if ("admin".equals(authenticatedUser.getRole())) {
                return "redirect:/Admin_Home";
            } else if ("user".equals(authenticatedUser.getRole())) {
                return "redirect:/User_Home";
            }
        } else {
            // Add error message attribute to be displayed in the login page
            request.setAttribute("LogInError", "Invalid username or password!");
            return "Login";
        }
        return "redirect:/";
    }

    @GetMapping("/Register")
    public String Register() {
        return "Register";
    }

    @PostMapping("/Register")
    public String register(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String tel = request.getParameter("tel");
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmpassword");

        // Check if the username already exists in the database
        if (Uservice.alreadyExists(userName)) {
            request.setAttribute("RegisterError", "Username already exists!");
            return "Register";
        }

        // Check if password and confirm password match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("RegisterError", "Passwords do not match!");
            return "Register";
        }

        // Save the user to the database or perform other registration logic
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setTel(tel);
        newUser.setUserName(userName);
        newUser.setPassword(password);
        newUser.setRole("user"); // You may set the role as needed

        Uservice.save(newUser);

        // Add the success message to the Thymeleaf model
        model.addAttribute("SuccessMessage", "User has been successfully created!");

        // Redirect to the login page after successful registration
        return "Register";
    }

    @GetMapping("/Cinema")
    public String Cinema() {
        return "UserCinema";
    }
    @GetMapping("/Food&Berverage")
    public String FNB(){
        return "FNB";
    }
}
