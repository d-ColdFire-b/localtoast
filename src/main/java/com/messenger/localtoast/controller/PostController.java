package com.messenger.localtoast.controller;

import com.messenger.localtoast.domain.Comment;
import com.messenger.localtoast.domain.Post;
import com.messenger.localtoast.domain.Role;
import com.messenger.localtoast.domain.User;
import com.messenger.localtoast.service.CommentService;
import com.messenger.localtoast.service.FeedService;
import com.messenger.localtoast.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private FeedService feedService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @GetMapping("{post}")
    public String showPost(
            @AuthenticationPrincipal User user,
            @PathVariable Post post,
            Model model
    ){
        if (!feedService.isAvailable(post, user)){
            model.addAttribute("alert", "Access denied");
            model.addAttribute("post", null);
        }
        else model.addAttribute("post", post);


        return "post";
    }

    @RequestMapping(value="{post}", params = "delete", method=RequestMethod.POST)
    public String delete(
            @AuthenticationPrincipal User user,
            @PathVariable Post post,
            Model model
    ){
        if (user.getId() == post.getCreator().getId() || user.getRoles().contains(Role.ADMIN)){
            feedService.delete(post);
            return "deleted";
        }
        else{
            model.addAttribute("alert", "Access denied");
            model.addAttribute("post", post);
            return "post";
        }
    }

    @RequestMapping(value="{post}", params = "addComment", method=RequestMethod.POST)
    public String addComment(
            @AuthenticationPrincipal User user,
            @PathVariable Post post,
            @Valid Comment comment,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        if (bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("post", post);
        } else {
            commentService.addComment(user, post, comment, model, file);
        }

        List<Comment> comments = commentService.getComments(post);

        model.addAttribute("comments", comments);

        return "post";

    }



}
