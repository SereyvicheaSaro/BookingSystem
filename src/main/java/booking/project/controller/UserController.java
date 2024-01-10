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
    @GetMapping("/Food&Beverage")
    public String FNB(){
        return "FNB";
    }

    @GetMapping("/User_List")
    public String User_List(Model model, HttpSession session) {
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

        // Fetch all users from the database
        List<User> userList = Uservice.getAllUsers();

        // Add the user list to the Thymeleaf model
        model.addAttribute("userList", userList);

        return "UserList";
    }

    @GetMapping("/User_Insert")
    public String Product_Insert(HttpSession session, Model model) {
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
        model.addAttribute("user", new User());

        return "UserInsert";
    }

    @PostMapping("/User_Insert")
    public String User_Insert(@ModelAttribute("user") User newUser, HttpServletRequest request, Model model) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String email = request.getParameter("email");
        String tel = request.getParameter("tel");

        if (Uservice.alreadyExists(userName)) {
            model.addAttribute("RegisterError", "Username already exists!");
            return "UserInsert";
        }

        // Set the username, password, and role, and then save the user to the database
        newUser.setUserName(userName);
        newUser.setPassword(password);
        newUser.setRole(role);
        newUser.setEmail(email);
        newUser.setTel(tel);

        // Save the user
        Uservice.save(newUser);  

        // Add the success message to the Thymeleaf model
        model.addAttribute("SuccessMessage", "User has been successfully inserted!");

        // Return the view name
        return "UserInsert";
    }
    
    @GetMapping("/User_Update")
    public String showUpdateForm(@RequestParam("userId") Long userId, Model model, HttpSession session) {
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
        
        Optional<User> optionalUser = Uservice.getUserById(userId);
        
        // Check if the movie exists, otherwise throw an exception
        User user = optionalUser.orElseThrow(() -> new NoSuchElementException("User not found for id: " + userId));
        
        model.addAttribute("user", user);
        return "UserUpdate";
    }

    @PostMapping("/User_Update")
    public String updateUser(@ModelAttribute("user") User updatedUser, Model model) {
    
        // Get the ID of the movie to be updated
        Long userId = updatedUser.getId();
    
        // Check if the movie ID is not null
        if (userId != null) {
            // Retrieve the existing movie from the database
            Optional<User> existingUserOptional = Uservice.getUserById(userId);
    
            // Check if the movie with the given ID exists
            if (existingUserOptional.isPresent()) {
                User existingUser = existingUserOptional.get();
    
                // Update the properties of the existing movie with the values from the updated movie
                existingUser.setUserName(updatedUser.getUserName());
                existingUser.setPassword(updatedUser.getPassword());
                existingUser.setRole(updatedUser.getRole());
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setTel(updatedUser.getTel());
                // Repeat for other properties...
    
                // Save the updated movie
                Uservice.save(existingUser);
    
                // Add success message to the model
                model.addAttribute("SuccessMessage", "User has been successfully updated!");
    
                // Return the view name
                return "UserUpdate";
            }
        }
    
        // Handle the case where the movie ID is null or the movie doesn't exist
        // You can redirect or show an error message as needed
        return "redirect:/User_List";
    }    
}
