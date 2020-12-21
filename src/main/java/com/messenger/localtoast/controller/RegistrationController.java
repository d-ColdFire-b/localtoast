package com.messenger.localtoast.controller;

import com.messenger.localtoast.domain.User;
import com.messenger.localtoast.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired //подгрузка
    private UserService userService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration") //смотрит форму, проверяет поля на заполнение
    public String addUser(
            @RequestParam("password2") String passwordConfirm,
            @RequestParam(required = false) String nickname,
            @Valid User user,
            BindingResult bindingResult,
            Model model

    ){

        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        if (isConfirmEmpty){ //подтверждение пароля пусто
            model.addAttribute("password2Error", "Password confirmation cannot be empty");
        }

        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm)){
            model.addAttribute("passwordError", "Passwords are different");
        }//если подтверждение и сам пароль разные

        if (isConfirmEmpty || bindingResult.hasErrors()){
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "registration";
        }

        if (nickname == null || StringUtils.isEmpty(nickname)){
            user.setNickname(null);
        } else{
            user.setNickname(nickname);
        }

        if (!userService.addUser(user)){
            if (!userService.isUsernameUnique(user.getUsername())){
                model.addAttribute("usernameError", "Name exists!");
            }

            if (!userService.isEmailUnique(user.getEmail())){
                model.addAttribute("emailError", "Email exists!");
            }

            return "registration";
        }

        if (userService.sendMessage(user)){
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Activation link was sent to your e-mail");
        }
        else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Error sending activation link to your e-mail");
        }

        return "login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActivated = userService.activateUser(code);

        if (isActivated){
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated. Please re-login to finish the activation.");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found!");
        }

        return  "login";
    }
}
