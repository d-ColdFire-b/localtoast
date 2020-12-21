package com.messenger.localtoast.repos;

import com.messenger.localtoast.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {
}
