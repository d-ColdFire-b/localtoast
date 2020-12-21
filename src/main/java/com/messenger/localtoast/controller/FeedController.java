package com.messenger.localtoast.controller;

import com.messenger.localtoast.domain.Post;
import com.messenger.localtoast.domain.User;
import com.messenger.localtoast.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    private FeedService feedService;

    @GetMapping
    public String showFeed(
            @AuthenticationPrincipal User user,
            Model model
    ){
        List<Post> posts = feedService.getAll();
        posts = feedService.filterByAccess(posts, user);
        model.addAttribute("posts", posts);
        return "feed";
    }

    @PostMapping
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Post post,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        if (bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("post", post);
        } else {
            feedService.addPost(user, post, model, file);
        }

        List<Post> posts = feedService.getAll();

        model.addAttribute("posts", posts);

        return "feed";

    }



}
