package com.network.localtoast.controller;

import com.network.localtoast.domain.User;
import com.network.localtoast.service.MainPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private MainPageService service;

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/error")
    public String greeting(
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
        }

        return "error";
    }

    @GetMapping("/main")
    public String main(
            @AuthenticationPrincipal User currentUser,
            Model model
    ){
        model.addAttribute("conversations", service.getConversations(currentUser));

        return "main";
    }

}
