package com.gelderloos.codefellowship.controllers;

//TODO: Step 2: Make a controller
// Don't forget @Controller anno

import com.gelderloos.codefellowship.models.AppUser;
import com.gelderloos.codefellowship.repositories.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class UserController {
    // Autowire user repo
    @Autowired
    AppUserRepo appUserRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    HttpServletRequest request;

    // GET ROUTES

    // Step 8: Make a home page
    @GetMapping("/")
    public String getHomePage(Principal p, Model m)
    {
        if (p != null)
        {
            String username = p.getName();
            AppUser dinoUser = appUserRepo.findByUsername(username);

            m.addAttribute("username", username);
            m.addAttribute("nickname", dinoUser.getNickname());
        }

        return "index.html";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/signup")
    public String getSignupPage(){
        return "signup";
    }

    @GetMapping("/sauce")
    public String getSecretSauce(){
        return "secretSauce";
    }


    @PostMapping("/test")
    public RedirectView testUser(){
        String hashedPassword = passwordEncoder.encode("password");
        AppUser newUser = new AppUser("lemi", hashedPassword, "Pound Cake");
        appUserRepo.save(newUser);
        return new RedirectView("/");
    }

    //POST ROUTES
    // signup
    @PostMapping("/signup")
    public RedirectView createUser(String username, String nickname, String password){
        String hashedPassword = passwordEncoder.encode(password);
        AppUser newUser = new AppUser(username, hashedPassword, nickname);
        appUserRepo.save(newUser);
        // pre auth with HttpServletReq
        authWithHttpServletRequest(username, password);
        return new RedirectView("/");
    }

    public void authWithHttpServletRequest(String username, String password){
        try {
            request.login(username, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
