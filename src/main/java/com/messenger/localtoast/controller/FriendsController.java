package com.messenger.localtoast.controller;

import com.messenger.localtoast.domain.User;
import com.messenger.localtoast.service.RelationsService;
import com.messenger.localtoast.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/friends")
public class FriendsController {

    @Autowired
    private UserService userService;

    @Autowired
    private RelationsService relationsService;

    @GetMapping
    public String showFriends(
            @AuthenticationPrincipal User user,
            Model model
    ){

        model.addAttribute("users", relationsService.getFriends(user));

        model.addAttribute("outgoing", relationsService.getOutgoingRequests(user).size());
        model.addAttribute("pending", relationsService.getPendingRequests(user).size());

        return "friends";
    }

    @GetMapping("outgoing-requests")
    public String outgoingRequests(
            @AuthenticationPrincipal User user,
            Model model
    ){
        model.addAttribute("users", relationsService.getOutgoingRequests(user));

        return "outgoingRequests";
    }

    @RequestMapping(value="outgoing-requests", params = "unsubscribe", method = RequestMethod.POST)
    public String unsubscribe(
            @RequestParam("unsubscribe") User targetUser,
            @AuthenticationPrincipal User user
    ){
        relationsService.unsubscribe(user, targetUser);
        return "redirect:/friends/outgoing-requests";
    }

    @GetMapping("pending-requests")
    public String pendingRequests(
            @AuthenticationPrincipal User user,
            Model model
    ){
        model.addAttribute("users", relationsService.getPendingRequests(user));

        return "pendingRequests";
    }

    @RequestMapping(value="pending-requests", params = "accept", method = RequestMethod.POST)
    public String accept(
            @RequestParam("accept") User targetUser,
            @AuthenticationPrincipal User user
    ){
        relationsService.acceptRequest(targetUser, user);
        return "redirect:/friends/pending-requests";
    }

    @RequestMapping(value="pending-requests", params = "deny", method = RequestMethod.POST)
    public String deny(
            @RequestParam("deny") User targetUser,
            @AuthenticationPrincipal User user
    ){
        relationsService.unsubscribe(targetUser, user);
        return "redirect:/friends/pending-requests";
    }

}
