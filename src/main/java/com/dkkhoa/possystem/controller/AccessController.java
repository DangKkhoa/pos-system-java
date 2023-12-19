package com.dkkhoa.possystem.controller;

import com.dkkhoa.possystem.model.users.SessionUser;
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
                SessionUser sessionUser = new SessionUser(authenticatingUser.getUserId(), authenticatingUser.getUsername(), authenticatingUser.getFullname(), authenticatingUser.isAdmin(), authenticatingUser.getProfilePicture());
//                System.out.println(sessionUser);
                session.setAttribute("user", sessionUser);
                if(authenticatingUser.isFirstLogin() && !authenticatingUser.isAdmin()) {
                    return "redirect:/set_password";
                }
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
