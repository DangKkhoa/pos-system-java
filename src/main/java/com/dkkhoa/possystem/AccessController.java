package com.dkkhoa.possystem;

import com.dkkhoa.possystem.model.users.User;
import com.dkkhoa.possystem.model.users.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

public class AccessController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    private String login() {
        return "login";
    }

    @PostMapping("/login")
//    @ResponseBody
    private String login_auth(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        User authenticatingUser = userRepo.findByUsername(username);

        System.out.println(username);
        System.out.println(authenticatingUser);
        if(authenticatingUser != null) {
            String passwordHashed = passwordEncoder.encode(password);
            System.out.println(passwordHashed);
            if (passwordEncoder.matches(password, authenticatingUser.getPassword())) {
                session.setAttribute("user", authenticatingUser);
                return "redirect:/";
            }
            else {
                model.addAttribute("username", username);
                model.addAttribute("error", "Wrong username or password");
                return "login";
            }


        }
        model.addAttribute("username", username);
        model.addAttribute("error", "Account does not exist");
        return "login";
    }
}
