package com.messenger.localtoast.repos;

import com.messenger.localtoast.domain.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepo extends JpaRepository<Conversation, Long> {
}
