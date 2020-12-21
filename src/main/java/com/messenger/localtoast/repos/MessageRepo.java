package com.messenger.localtoast.repos;

import com.messenger.localtoast.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {
}
