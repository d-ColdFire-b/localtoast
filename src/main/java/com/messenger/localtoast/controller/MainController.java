package com.messenger.localtoast.controller;

import com.messenger.localtoast.domain.User;
import com.messenger.localtoast.service.MainPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private MainPageService service;

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/error")
    public String error() { return "error"; }

    @GetMapping("/main")
    public String main(
            @AuthenticationPrincipal User currentUser,
            Model model
    ){

        model.addAttribute("conversations", service.getConversations(currentUser));

        return "main";
    }

}
