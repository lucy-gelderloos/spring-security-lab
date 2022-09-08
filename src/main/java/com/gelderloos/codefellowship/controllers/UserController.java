package com.gelderloos.codefellowship.controllers;

//TODO: Step 2: Make a controller
// Don't forget @Controller anno

import com.gelderloos.codefellowship.models.AppPost;
import com.gelderloos.codefellowship.models.AppUser;
import com.gelderloos.codefellowship.repositories.AppPostRepository;
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
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UserController {
    // Autowire user repo
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AppPostRepository appPostRepository;

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

    @GetMapping(value = {"/users"})
    public String usersGet(Principal p, Model model) {
        if (p != null) { // not strictly required IF your WebSecurityConfig is correct
            String username = p.getName();
            AppUser appUser = appUserRepository.findByUsername(username);

            model.addAttribute("username", username);
            model.addAttribute("nickname", appUser.getNickname());
        }
        List<AppUser> userList = appUserRepository.findAll();
        model.addAttribute("userList",userList);
        return "users";
    }

    @GetMapping("/users/{id}")
    public String getUserInfo(Model m, Principal p, @PathVariable Long id) {
        if (p != null) { // not strictly required IF your WebSecurityConfig is correct
            String username = p.getName();
            AppUser appUser = appUserRepository.findByUsername(username);

            m.addAttribute("username", username);
            m.addAttribute("nickname", appUser.getNickname());
            m.addAttribute("userId", appUser.getId());
        }

        AppUser dbUser = appUserRepository.findById(id).orElseThrow();
        m.addAttribute("dbUserUsername", dbUser.getUsername());
        m.addAttribute("dbUserNickname", dbUser.getNickname());
        m.addAttribute("dbUserId", dbUser.getId());
        m.addAttribute("dbUserFirstName", dbUser.getFirstName());
        m.addAttribute("dbUserLastName", dbUser.getLastName());
        m.addAttribute("dbUserDateOfBirth", dbUser.getDateOfBirth());
        m.addAttribute("dbUserBio", dbUser.getBio());

//        List<AppPost> postList = appPostRepository.findAllByAuthor(dbUser.getUsername());
        m.addAttribute("testDate", LocalDateTime.now());

        return "profile";
    }

    @GetMapping("/posts")
    public String getAllPosts(Model m, Principal p) {

        if (p != null) {
            String username = p.getName();
            AppUser appUser = appUserRepository.findByUsername(username);

            m.addAttribute("username", username);
            m.addAttribute("appUserId", appUser.getId());
            m.addAttribute("nickname", appUser.getNickname());
        }

        List<AppPost> postList = appPostRepository.findAll();
        m.addAttribute("postList",postList);
        return "posts";
    }

    @PostMapping("/posts")
    public RedirectView createPost(String postAuthorUserName, String postContent){
        AppPost newPost = new AppPost(postAuthorUserName,postContent);
        appPostRepository.save(newPost);
        return new RedirectView("/posts");
    }

    @PostMapping("/test")
    public RedirectView testUser() {
        String hashedPassword = passwordEncoder.encode("password");
        AppUser newUser = new AppUser("Pippin", hashedPassword, "pip-pop", "Pippin", "Took", "1/15/18", "I'm a cat!");
        appUserRepository.save(newUser);
        return new RedirectView("/");
    }

    @PostMapping("/signup")
    public RedirectView createUser(String username, String nickname, String password, String firstName, String lastName, String dateOfBirth, String userBio) {
        String hashedPassword = passwordEncoder.encode(password);
        AppUser newUser = new AppUser(username, hashedPassword, nickname, firstName, lastName, dateOfBirth, userBio);
        appUserRepository.save(newUser);
        // pre auth with HttpServletReq
        authWithHttpServletRequest(username, password);
        AppUser appUser = appUserRepository.findByUsername(username);
        return new RedirectView("/users");
    }

    @PostMapping("/login")
    public RedirectView loginUser(String username, String password) {
        authWithHttpServletRequest(username, password);
        AppUser appUser = appUserRepository.findByUsername(username);
        Long id = appUser.getId();
        return new RedirectView("/users" + 1);
    }

    @PutMapping("/users/{id}")
    public RedirectView editUserInfo(Model m, Principal p, @PathVariable Long id, String username, String nickname, String firstName, String lastName, String dateOfBirth, String userBio, RedirectAttributes redir) {
        if (p != null && p.getName().equals(username)) {
            AppUser newUser = appUserRepository.findById(id).orElseThrow();
            newUser.setUsername(username);
            newUser.setNickname(nickname);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setDateOfBirth(dateOfBirth);
            newUser.setBio(userBio);
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
