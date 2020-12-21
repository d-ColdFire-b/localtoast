package com.network.localtoast.controller;

import com.network.localtoast.domain.Conversation;
import com.network.localtoast.domain.Message;
import com.network.localtoast.domain.User;
import com.network.localtoast.service.DialogService;
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
@RequestMapping("/main/dialog")
public class DialogController {

    @Autowired
    private DialogService dialogService;

    @GetMapping
    public String initConversation(
            @AuthenticationPrincipal User currentUser,
            @RequestParam User user,
            Model model
    ){
        Conversation currentConversation = dialogService.defineConversation(currentUser, user);

        model.addAttribute("conversation", currentConversation);

        return "redirect:/main/dialog/" + currentConversation.getId();

    }

    @GetMapping("{conversation}")
    public String showConversation(
            @PathVariable Conversation conversation,
            Model model){

        List<Message> messages = conversation.getMessages();

        model.addAttribute("messages", messages);

        return "dialog";
    }

    @PostMapping("{conversation}")
    public String add(
            @AuthenticationPrincipal User user,
            @PathVariable Conversation conversation,
            @Valid Message message,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        if (bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);

            List<Message> messages = conversation.getMessages();

            model.addAttribute("messages", messages);
        } else {

            dialogService.addMessage(user, conversation, message, model, file);
        }

        return "dialog";

    }



}
