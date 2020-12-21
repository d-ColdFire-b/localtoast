package com.messenger.localtoast.controller;

import com.messenger.localtoast.domain.Post;
import com.messenger.localtoast.domain.User;
import com.messenger.localtoast.service.FeedService;
import com.messenger.localtoast.service.RelationsService;
import com.messenger.localtoast.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private FeedService feedService;

    @Autowired
    private RelationsService relationsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("{user}")
    public String getProfile(
            Model model,
            @PathVariable User user,
            @AuthenticationPrincipal User currentUser){
        prepareProfile(user, currentUser, model);
        return "userProfile";
    }

    public void prepareProfile(User user, User currentUser, Model model){
        model.addAttribute("targetUser", user);
        model.addAttribute("user", currentUser);

        List<Post> posts = feedService.getByUser(user);
        posts = feedService.filterByAccess(posts, currentUser);

        model.addAttribute("friendsCount", relationsService.getFriends(user).size());

        model.addAttribute("posts", posts);

        if (currentUser.getId() != user.getId()){
            boolean isSubscription = relationsService.subscriptionExists(currentUser, user);
            boolean isFriend = relationsService.isFriendTo(currentUser, user);
            boolean isSubscriber = relationsService.subscriptionExists(user, currentUser);
            boolean isStranger = !isFriend && !isSubscriber && !isSubscription;

            model.addAttribute("isSubscription", isSubscription);
            model.addAttribute("isSubscriber", isSubscriber);
            model.addAttribute("isFriend", isFriend);
            model.addAttribute("isStranger", isStranger);
        }
        else{
            model.addAttribute("isSubscription", false);
            model.addAttribute("isSubscriber", false);
            model.addAttribute("isFriend", false);
            model.addAttribute("isStranger", false);
        }
    }

    // Profile editing

    @GetMapping("edit")
    public String editProfile(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("user", user);

        return "profileEdit";
    }

    @RequestMapping(value="/edit", params = "update", method=RequestMethod.POST)
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam("file") MultipartFile file,
            @Valid User tempUser,
            BindingResult bindingResult,
            Model model
    ) {
        model.addAttribute("user", user);

        Map<String, String> errors;

        // Костыль, который не описать по-русски(пытаемся изменить профиль но не ввели никаких данных)
        // Editing form does not contain inputs for username and password,
        // so this eventually will cause errors because both fields are mapped as NotBlank in User class
        // Now remove these two errors leaving just what we need
        if (bindingResult.hasErrors()){
            errors = ControllerUtils.getErrors(bindingResult);
            errors.remove("passwordError");
            errors.remove("usernameError");
            // If we still get some errors we report them
            if (!errors.isEmpty()){
                model.mergeAttributes(errors);
                model.addAttribute("messageType", "danger");
                model.addAttribute("message", "Error updating user");
                return "profileEdit";
            }
        }

        try {
            userService.updateProfile(user, file,
                    tempUser.getEmail(), tempUser.getNickname(),
                    tempUser.getCity(), tempUser.getInterests());
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully updated");
            return "profileEdit";
        }
        catch (IOException e){
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Error updating user");
            return "profileEdit";
        }
    }

    @RequestMapping(value="/edit", params = "pass", method=RequestMethod.POST)
    public String changePassword(
            @AuthenticationPrincipal User user,
            @RequestParam String newpassword,
            @RequestParam("password2") String passwordConfirm,
            Model model
    ){
        model.addAttribute("user", user);

        boolean isEmptyPassword = StringUtils.isEmpty(newpassword);
        if (isEmptyPassword){
            model.addAttribute("newpasswordError", "Password cannot be empty");
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Error changing password");
            return "profileEdit";
        }

        boolean isPasswordMismatch = !newpassword.equals(passwordConfirm);
        if (isPasswordMismatch){
            model.addAttribute("password2Error", "Passwords are different");
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Error changing password");
            return "profileEdit";
        }

        userService.changePassword(user, newpassword);

        model.addAttribute("messageType", "success");
        model.addAttribute("message", "Password successfully changed");
        return "profileEdit";
    }

    // Relations

    @RequestMapping(value="/{user}", params = "subscribe", method=RequestMethod.POST)
    public String subscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model
    ){

        if (currentUser.getId() == user.getId()){
            model.addAttribute("alert", "Error");

            prepareProfile(user, currentUser, model);

            return "userProfile";
        }

        if (relationsService.subscribe(currentUser, user)){
            model.addAttribute("success", "Request was sent");
        }
        else model.addAttribute("alert", "Error");

        prepareProfile(user, currentUser, model);

        return "userProfile";

    }

    @RequestMapping(value="/{user}", params = "unsubscribe", method=RequestMethod.POST)
    public String unsubscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model
    ){

        if (currentUser.getId() == user.getId()){
            model.addAttribute("alert", "Error");

            prepareProfile(user, currentUser, model);

            return "userProfile";
        }

        if (relationsService.unsubscribe(currentUser, user)){
            model.addAttribute("success", "Request cancelled");
        }
        else model.addAttribute("alert", "Error");

        prepareProfile(user, currentUser, model);

        return "userProfile";

    }

    @RequestMapping(value="/{user}", params = "acceptRequest", method=RequestMethod.POST)
    public String accept(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model
    ){

        if (currentUser.getId() == user.getId()){
            model.addAttribute("alert", "Error");

            prepareProfile(user, currentUser, model);

            return "userProfile";
        }

        if (relationsService.acceptRequest(user, currentUser)){
            model.addAttribute("success", "Request accepted");
        }
        else model.addAttribute("alert", "Error");

        prepareProfile(user, currentUser, model);

        return "userProfile";

    }

    @RequestMapping(value="/{user}", params = "denyRequest", method=RequestMethod.POST)
    public String deny(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model
    ){

        if (currentUser.getId() == user.getId()){
            model.addAttribute("alert", "Error");

            prepareProfile(user, currentUser, model);

            return "userProfile";
        }

        if (relationsService.unsubscribe(user, currentUser)){
            model.addAttribute("success", "Request denied");
        }
        else model.addAttribute("alert", "Error");

        prepareProfile(user, currentUser, model);

        return "userProfile";

    }

    @RequestMapping(value="/{user}", params = "deleteFriend", method=RequestMethod.POST)
    public String deleteFriend(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model
    ){

        if (currentUser.getId() == user.getId()){
            model.addAttribute("alert", "Error");

            prepareProfile(user, currentUser, model);

            return "userProfile";
        }

        if (relationsService.deleteFriend(currentUser, user)){
            model.addAttribute("success", "Friend was deleted");
        }
        else model.addAttribute("alert", "Error");

        prepareProfile(user, currentUser, model);

        return "userProfile";

    }

}
