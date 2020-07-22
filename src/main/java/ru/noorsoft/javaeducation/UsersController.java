package ru.noorsoft.javaeducation;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

/***
 * CRUD controller for User
 */
@Controller
@RequestMapping("/user")
public class UsersController {
    private UserRepository userRepository;
    public final static Logger logger = Logger.getLogger(UsersController.class);
    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World")
                                   String name, Model model) {
        model.addAttribute("name", name);
        return "users";
    }

    @GetMapping("/userlist")
    public String getAllUsers(Model model)
    {
        ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
        model.addAttribute("userList", users);
        return "userlist";
    }

    @PostMapping("/userlist")
    public String addUser(@ModelAttribute("user") User user) {
        //user = new User("Test", "User", "TestNumber", "TestEmail" );
        logger.info(userRepository.save(user));
        //logger.info(userRepository.findAll());
        return "redirect:/user/userlist";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam(name = "id") Long id)
    {
        userRepository.deleteById(id);
        logger.info("User with id " + Long.toString(id) + " has been deleted !");
        return "redirect:/user/userlist";
    }
}