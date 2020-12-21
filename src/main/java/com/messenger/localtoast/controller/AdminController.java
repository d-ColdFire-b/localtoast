package com.messenger.localtoast.controller;

import com.messenger.localtoast.domain.Role;
import com.messenger.localtoast.domain.User;
import com.messenger.localtoast.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class AdminController {
    @Autowired
    private UserService userService;


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit/{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/edit/{user}")
    public String userSave(
            @RequestParam String username,
            @RequestParam("role") String role,
            @RequestParam("userId") User user
    ){
        userService.saveUser(user, username, role);

        return "redirect:/user/edit/" + user.getId();
    }

}
