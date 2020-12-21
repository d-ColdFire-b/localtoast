package com.network.localtoast.controller;

import com.network.localtoast.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user-search")
public class SearchController {

    @Autowired
    private UserService userService;


    @GetMapping
    public String userList(@RequestParam(required = false, defaultValue = "") String filter, Model model){

        model.addAttribute("users", userService.filter(filter));

        model.addAttribute("filter", filter);

        return "userList";
    }

}
