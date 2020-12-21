package com.network.localtoast.repos;

import com.network.localtoast.domain.Conversation;
import org.springframework.data.repository.CrudRepository;

public interface ConversationRepo extends CrudRepository<Conversation, Long> {
}
