package com.messenger.localtoast.controller;

import com.messenger.localtoast.service.UserService;
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
    public String userList(
            @RequestParam(required = false, defaultValue = "") String filter,
            @RequestParam(required = false, defaultValue = "") String city,
            Model model){

        model.addAttribute("users", userService.filter(filter, city));

        model.addAttribute("filter", filter);
        model.addAttribute("city", city);

        return "userList";
    }

}
