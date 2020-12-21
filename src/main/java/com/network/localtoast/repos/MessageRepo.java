package com.network.localtoast.repos;

import com.network.localtoast.domain.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepo extends CrudRepository<Message, Long> {
}
