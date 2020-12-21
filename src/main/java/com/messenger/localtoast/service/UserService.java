package com.messenger.localtoast.service;

import com.messenger.localtoast.domain.Role;
import com.messenger.localtoast.domain.User;
import com.messenger.localtoast.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private  MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${upload.path}")
    private String uploadPath;

    private static final String domain = "localhost:8080";

    public void save(User user){
        userRepo.saveAndFlush(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public boolean isUsernameUnique(String username){
        return userRepo.findByUsername(username) == null;
    }

    public boolean isEmailUnique(String email){
        return userRepo.findByEmail(email) == null;
    }

    public boolean addUser(User user){

        if (!isUsernameUnique(user.getUsername()) || !isEmailUnique(user.getEmail())){
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        return true;
    }

    public boolean sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())){
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to LocalToast social network. Please, visit next link: http://%s/activate/%s",
                    user.getUsername(),
                    domain,
                    user.getActivationCode()
            );

            try {
                mailSender.send(user.getEmail(), "Activation code", message);
                return true;
            }
            catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null){
            return false;
        }

        user.setActivationCode(null);

        userRepo.save(user);

        return true;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public List<User> filter(String filter, String city) {
        List<User> users = findAll();

        List<User> result = new ArrayList<>();

        if (filter != null && !filter.isEmpty()){

            String[] criteria = filter.split(" ");

            String name, lastname, nickname;

            for (User user : users) {
                name = user.getName().toLowerCase();
                lastname = user.getLastname().toLowerCase();
                nickname = user.getNickname();


                for (String criterion : criteria){
                    if ((name.contains(criterion.toLowerCase())
                            || lastname.contains(criterion.toLowerCase())
                            || nickname != null && nickname.toLowerCase().contains(criterion.toLowerCase()))
                            && !result.contains(user)){
                        result.add(user);
                    }
                }

            }
        }

        if (city != null && !city.isEmpty()){
            for (User user : users) {
                String userCity = user.getCity();
                if (userCity != null && userCity.toLowerCase().contains(city.toLowerCase())
                        && !result.contains(user)){
                    result.add(user);
                }
            }
        }

        if (!result.isEmpty()){
            return result;
        } else {
            return users;
        }

    }

    public void saveUser(User user, String username, String role) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        if (roles.contains(role)){
            user.getRoles().clear();
            user.getRoles().add(Role.valueOf(role));
        }
        /*
        user = userRepo.findByUsername(user.getUsername());
        user.setUsername(username);

        switch(role.toLowerCase()){
            case "user":{
                user.getRoles().clear();
                user.getRoles().add(Role.USER);
                break;
            }
            case "admin":{
                user.getRoles().clear();
                user.getRoles().add(Role.ADMIN);
                break;
            }
        }
        */

        userRepo.save(user);
    }

    public void updateProfile(
            User user,
            MultipartFile file,
            String email,
            String nickname,
            String city,
            String interests
    ) throws IOException {
        String userEmail = user.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if (isEmailChanged){
            user.setEmail(email);

            if(!StringUtils.isEmpty(email)){
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        String userNickname = user.getNickname();

        boolean nicknameChanged = nickname != null && !nickname.equals(userNickname) ||
                userNickname != null && !userNickname.equals(nickname);

        if (nicknameChanged){
            if (nickname == null || nickname.equals("")){
                user.setNickname(null);
            }
            else user.setNickname(nickname);
        }

        String userCity = user.getCity();

        boolean cityChanged = city != null && !city.equals(userCity) ||
                userCity != null && !userCity.equals(city);

        if (cityChanged){
            if (city == null || city.equals("")){
                user.setCity(null);
            }
            else user.setCity(city);
        }

        String userInterests = user.getInterests();

        boolean interestsChanged = interests != null && !interests.equals(userInterests) ||
                userInterests != null && !userInterests.equals(interests);

        if (interestsChanged){
            if (interests == null || interests.equals("")){
                user.setInterests(null);
            }
            else user.setInterests(interests);
        }

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            user.setPicname(resultFilename);
        }

        userRepo.save(user);

        if (isEmailChanged) {
            sendMessage(user);
        }

    }

    public void changePassword(
            User user,
            String password
    ){
        if (!StringUtils.isEmpty(password)){
            user.setPassword(passwordEncoder.encode(password));
        }

        userRepo.save(user);
    }
}
