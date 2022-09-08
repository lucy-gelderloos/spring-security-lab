package com.gelderloos.codefellowship.controllers;

//TODO: Step 2: Make a controller
// Don't forget @Controller anno

import com.gelderloos.codefellowship.models.AppUser;
import com.gelderloos.codefellowship.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class UserController {
    // Autowire user repo
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    HttpServletRequest request;

    @GetMapping("/")
    public String getHomePage(Principal p, Model m) {
        if (p != null) {
            String username = p.getName();
            AppUser appUser = appUserRepository.findByUsername(username);

            m.addAttribute("username", username);
            m.addAttribute("nickname", appUser.getNickname());
        }
        return "index";
    }

    @GetMapping("/test")
    public String getTestPage(Principal p, Model m) {
        if (p != null) {
            String username = p.getName();
            AppUser appUser = appUserRepository.findByUsername(username);

            m.addAttribute("username", username);
            m.addAttribute("nickname", appUser.getNickname());
        }
        return "test";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String getSignupPage() {
        return "signup";
    }

    @GetMapping("/sauce")
    public String getSecretSauce() {
        return "secretSauce";
    }

    @PostMapping("/test")
    public RedirectView testUser() {
        String hashedPassword = passwordEncoder.encode("password");
        AppUser newUser = new AppUser("Pippin", hashedPassword, "pip-pop", "Pippin", "Took", "1/15/18", "I'm a cat!");
        appUserRepository.save(newUser);
        return new RedirectView("/");
    }

    @GetMapping("/users/{id}")
    public String getUserInfo(Model m, Principal p, @PathVariable Long id) {
        if (p != null) { // not strictly required IF your WebSecurityConfig is correct
            String username = p.getName();
            AppUser appUser = appUserRepository.findByUsername(username);

            m.addAttribute("username", username);
            m.addAttribute("nickname", appUser.getNickname());
        }

        AppUser dbUser = appUserRepository.findById(id).orElseThrow();
        m.addAttribute("dbUserUsername", dbUser.getUsername());
        m.addAttribute("dbUserNickname", dbUser.getNickname());
        m.addAttribute("dbUserId", dbUser.getId());

        m.addAttribute("testDate", LocalDateTime.now());

        return "user-info";
    }

    @PostMapping("/signup")
    public RedirectView createUser(String username, String nickname, String password, String firstName, String lastName, String dateOfBirth, String userBio) {
        String hashedPassword = passwordEncoder.encode(password);
        AppUser newUser = new AppUser(username, hashedPassword, nickname, firstName, lastName, dateOfBirth, userBio);
        appUserRepository.save(newUser);
        // pre auth with HttpServletReq
        authWithHttpServletRequest(username, password);
        return new RedirectView("/");
    }

    @PutMapping("/users/{id}")
    public RedirectView editUserInfo(Model m, Principal p, @PathVariable Long id, String username, String nickname, RedirectAttributes redir) {
        if (p != null && p.getName().equals(username)) {
            AppUser newUser = appUserRepository.findById(id).orElseThrow();
            newUser.setUsername(username);
            newUser.setNickname(nickname);
            appUserRepository.save(newUser);
        } else {
            redir.addFlashAttribute("errorMessage", "Cannot edit another user's info");
        }
        return new RedirectView("/users/" + id);
    }

    public void authWithHttpServletRequest(String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
