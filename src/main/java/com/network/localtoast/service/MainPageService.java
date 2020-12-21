package com.network.localtoast.service;

import com.network.localtoast.domain.Conversation;
import com.network.localtoast.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MainPageService {

    public List<Conversation> getConversations(User currentUser){
        List<Conversation> conversations = currentUser.getConversations();

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
