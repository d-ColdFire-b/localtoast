package com.messenger.localtoast.service;

import com.messenger.localtoast.domain.*;
import com.messenger.localtoast.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Value("${upload.path}")
    private String uploadPath;

    public List<Comment> getComments(Post post){
        List<Comment> comments = post.getComments();
        Collections.reverse(comments);
        return comments;
    }

    public void addComment(
            User user,
            Post post,
            Comment comment,
            Model model,
            MultipartFile file
    ) throws IOException {

        if (user.getActivationCode() != null){
            model.addAttribute("alert", "Please, check your email and activate your account.");

            model.addAttribute("comment", comment);


        } else{
            comment.setSender(user);

            comment.setPost(post);

            saveFile(comment, file);

            model.addAttribute("comment", null);

            LocalDate todayLocalDate = LocalDate.now();
            java.sql.Date sqlDate = java.sql.Date.valueOf(todayLocalDate);
            comment.setDate(sqlDate);

            //post.getComments().add(comment);

            commentRepo.save(comment);

        }

    }

    private void saveFile(Comment comment, MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            comment.setFilename(resultFilename);
        }
    }

}
