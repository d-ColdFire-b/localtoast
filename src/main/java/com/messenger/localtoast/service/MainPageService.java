package com.messenger.localtoast.service;

import com.messenger.localtoast.domain.Conversation;
import com.messenger.localtoast.domain.User;
import com.messenger.localtoast.repos.ConversationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MainPageService {

    @Autowired
    private ConversationRepo conversationRepo;

    public List<Conversation> getConversations(User currentUser){

        List<Conversation> allConversations = conversationRepo.findAll();

        List<Conversation> conversations = new ArrayList<>();

        for (Conversation conversation : allConversations){
            if (conversation.getParticipants().contains(currentUser)){
                conversations.add(conversation);
            }
        }

        if (conversations.isEmpty()) {
            return conversations;
        }

        List<Conversation> result = new ArrayList<>();

        for (Conversation conversation : conversations){
            if (!conversation.getMessages().isEmpty()){
                result.add(conversation);
            }
        }

        Collections.reverse(result);

       return result;
    }

}
