package com.messenger.localtoast.controller;

import com.messenger.localtoast.domain.Conversation;
import com.messenger.localtoast.domain.Message;
import com.messenger.localtoast.domain.User;
import com.messenger.localtoast.service.DialogService;
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
            @AuthenticationPrincipal User user,
            @PathVariable Conversation conversation,
            Model model){
        if (!conversation.getParticipants().contains(user)){
            model.addAttribute("alert", "Access denied");
            return "error";
        }

        List<Message> messages = dialogService.getMessages(conversation);

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
        if (!conversation.getParticipants().contains(user)){
            model.addAttribute("alert", "Access denied");
            return "error";
        }

        if (bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            dialogService.addMessage(user, conversation, message, model, file);
        }

        List<Message> messages = dialogService.getMessages(conversation);

        model.addAttribute("messages", messages);

        return "dialog";

    }



}
