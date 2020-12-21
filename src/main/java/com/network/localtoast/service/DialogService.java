package com.network.localtoast.service;

import com.network.localtoast.domain.Conversation;
import com.network.localtoast.domain.Message;
import com.network.localtoast.domain.User;
import com.network.localtoast.repos.ConversationRepo;
import com.network.localtoast.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class DialogService {

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private ConversationRepo conversationRepo;

    @Value("${upload.path}")
    private String uploadPath;

    public Conversation defineConversation(User currentUser, User user){

        boolean conversationExists = false;

        Conversation targetConversation = new Conversation();

        for (Conversation userConversation : currentUser.getConversations()) {
            Set<User> participants = userConversation.getParticipants();

            if (participants.contains(user) && participants.size() == 2){
                conversationExists = true;
                targetConversation = userConversation;
            }
        }

        if (conversationExists) {
            return targetConversation;
        } else {
            Conversation newConversation = new Conversation();

            newConversation.getParticipants().add(currentUser);
            newConversation.getParticipants().add(user);

            conversationRepo.save(newConversation);

            return newConversation;
        }

    }

    public Model addMessage(
            User user,
            Conversation conversation,
            Message message,
            Model model,
            MultipartFile file
    ) throws IOException {

        if (user.getActivationCode() != null){
            model.addAttribute("alert", "Please, check your email and activate your account.");

            model.addAttribute("message", message);


        } else{
            message.setAuthor(user);

            saveFile(message, file);

            model.addAttribute("message", null);

            LocalDate todayLocalDate = LocalDate.now();
            java.sql.Date sqlDate = java.sql.Date.valueOf(todayLocalDate);
            message.setDate(sqlDate);

            conversation.getMessages().add(message);
            conversation.setLast(message);

            messageRepo.save(message);

        }

        List<Message> messages = conversation.getMessages();
        Collections.reverse(messages);

        model.addAttribute("messages", messages);

        return model;

    }

    private void saveFile(Message message, MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            message.setFilename(resultFilename);
        }
    }

}
