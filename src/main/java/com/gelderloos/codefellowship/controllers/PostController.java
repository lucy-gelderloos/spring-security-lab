package com.gelderloos.codefellowship.controllers;

import com.gelderloos.codefellowship.models.AppPost;
import com.gelderloos.codefellowship.repositories.AppPostRepository;
import com.gelderloos.codefellowship.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    AppPostRepository appPostRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @GetMapping("/posts")
    public String getAllPosts(HttpServletRequest request, Model model) {

        if(request.getSession(false) == null) {
            return "index";
        } else {
            List<AppPost> postList = appPostRepository.findAll();
            model.addAttribute("postList",postList);
            HttpSession session = request.getSession();
            String postAuthorUserName = (String) session.getAttribute("username");
            model.addAttribute("username",postAuthorUserName);
            return "posts";
        }
    }

    @PostMapping("/posts")
    public RedirectView createPost(String postAuthorUserName, String postContent){
        AppPost newPost = new AppPost(postAuthorUserName,postContent);
        appPostRepository.save(newPost);
        return new RedirectView("/posts");
    }
}
