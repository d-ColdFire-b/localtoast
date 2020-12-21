package com.messenger.localtoast.service;

import com.messenger.localtoast.domain.Post;
import com.messenger.localtoast.domain.Role;
import com.messenger.localtoast.domain.User;
import com.messenger.localtoast.repos.PostRepo;
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
public class FeedService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserService userService;

    @Value("${upload.path}")
    private String uploadPath;

    public List<Post> getAll(){
        List<Post> posts = postRepo.findAll();
        Collections.reverse(posts);
        return posts;
    }

    public List<Post> filterByAccess(List<Post> posts, User currentUser){
        if (posts == null || posts.isEmpty()) return new ArrayList<>();

        List<Post> result = new ArrayList<>();
        for (Post post : posts){
            if (isAvailable(post, currentUser)) result.add(post);
        }

        return result;
    }

    public boolean isAvailable(Post post, User currentUser){
        if (post.getAccess() != null && post.getAccess().toLowerCase().equals("private")){
            return post.getCreator().getFriends().contains(currentUser) ||
                    post.getCreator().getId() == currentUser.getId() ||
                    currentUser.getRoles().contains(Role.ADMIN) ;
        }
        else return true;
    }

    public List<Post> getByUser(User user){
        List<Post> posts = user.getPosts();
        Collections.reverse(posts);
        return posts;
    }

    public void delete(Post post){
        User user = post.getCreator();
        user.getPosts().remove(post);
        userService.save(user);
        postRepo.deleteById(post.getId());
    }

    public void addPost(
            User user,
            Post post,
            Model model,
            MultipartFile file
    ) throws IOException {

        if (user.getActivationCode() != null){
            model.addAttribute("alert", "Please, check your email and activate your account.");

            model.addAttribute("post", post);


        } else{
            post.setCreator(user);

            saveFile(post, file);

            model.addAttribute("post", null);

            LocalDate todayLocalDate = LocalDate.now();
            java.sql.Date sqlDate = java.sql.Date.valueOf(todayLocalDate);
            post.setDate(sqlDate);

            /*if (isPrivate != null && isPrivate.toLowerCase() == "private"){
                post.setAccess("private");
            }
            else if (isPrivate != null && isPrivate.toLowerCase() == "public"){
                post.setAccess("public");
            }*/

            postRepo.saveAndFlush(post);

        }

    }

    private void saveFile(Post post, MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            post.setFilename(resultFilename);
        }
    }

}
