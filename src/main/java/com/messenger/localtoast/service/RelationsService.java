package com.messenger.localtoast.service;

import com.messenger.localtoast.domain.Subscription;
import com.messenger.localtoast.domain.User;
import com.messenger.localtoast.repos.SubscriptionRepo;
import com.messenger.localtoast.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RelationsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SubscriptionRepo subscriptionRepo;


    public boolean subscribe(User subscriber, User channel){

        Subscription subscription = getSubscription(subscriber, channel);

        if (subscription != null || subscriber.getId() == channel.getId()) return false;

        if (isFriendTo(subscriber, channel)) return false;

        subscription = new Subscription(subscriber, channel);
        subscriptionRepo.saveAndFlush(subscription);

        return true;

    }

    public boolean unsubscribe(User subscriber, User channel){

        Subscription subscription = getSubscription(subscriber, channel);

        if (subscription == null) return false;

        subscriptionRepo.delete(subscription);

        return true;

    }

    public Subscription getSubscription(User subscriber, User channel){

        List<Subscription> subscriptionList = subscriptionRepo.findAll();

        if (subscriptionList == null || subscriptionList.isEmpty()) return null;

        for (Subscription subscription : subscriptionList){
            if (subscription.getSubscriber().getId() == subscriber.getId() &&
                    subscription.getChannel().getId() == channel.getId()
            ){
                return subscription;
            }
        }

        return null;
    }

    public boolean subscriptionExists(User subscriber, User channel){
        return getSubscription(subscriber, channel) != null;
    }


    public boolean acceptRequest(User subscriber, User acceptingUser){

        if (isFriendTo(acceptingUser, subscriber)) return false;

        if (!unsubscribe(subscriber, acceptingUser)) return  false;

        /*User acceptingUserFromDB = userRepo.findByUsername(acceptingUser.getUsername());
        acceptingUserFromDB.getFriends().add(subscriber);
        userRepo.saveAndFlush(acceptingUserFromDB);

        User subscriberFromDB = userRepo.findByUsername(subscriber.getUsername());
        subscriberFromDB.getFriends().add(acceptingUser);
        userRepo.saveAndFlush(subscriberFromDB);*/

        addFriend(acceptingUser, subscriber);
        addFriend(subscriber, acceptingUser);

        return true;
    }

    public boolean deleteFriend(User deleter, User userToDelete){

        if (!isFriendTo(deleter, userToDelete)) return false;

        removeFriend(deleter, userToDelete);
        removeFriend(userToDelete, deleter);

        // Тот, кого удаляют из друзей, становится подписчиком удаляющего
        // Возвращается результат попытки удаления подписки
        return subscribe(userToDelete, deleter);

    }

    public List<User> getFriends(User user){ //заявка в друзья
        for (User tempUser : userRepo.findAll()){
            if (tempUser.getId() == user.getId()) return  tempUser.getFriends();
        }
        return new ArrayList<>();
    }

    private void addFriend(User user, User userToAdd){//добавить друга
        for (User tempUser : userRepo.findAll()){
            if (tempUser.getId() == user.getId()) {
                tempUser.getFriends().add(userToAdd);
                userRepo.save(tempUser);
                return;
            }
        }
    }

    private void removeFriend(User user, User userToRemove){

        /*User userFromDB = userRepo.findByUsername(user.getUsername());
        userFromDB.getFriends().remove(userToRemove);
        userRepo.saveAndFlush(userFromDB);*/

        for (User tempUser : userRepo.findAll()){
            if (tempUser.getId() == user.getId()) {
                tempUser.getFriends().remove(userToRemove);
                userRepo.save(tempUser);
                return;
            }
        }
    }

    public boolean isFriendTo(User currentUser, User user){
        return getFriends(currentUser).contains(user) || getFriends(user).contains(currentUser);
    }

    public List<User> getOutgoingRequests(User user){
        List<User> result = new ArrayList<>();
        for (Subscription subscription : subscriptionRepo.findAll()){
            if (subscription.getSubscriber().getId() == user.getId())
                result.add(subscription.getChannel());
        }
        return result;
    }

    public List<User> getPendingRequests(User user){
        List<User> result = new ArrayList<>();
        for (Subscription subscription : subscriptionRepo.findAll()){
            if (subscription.getChannel().getId() == user.getId())
                result.add(subscription.getSubscriber());
        }
        return result;
    }

}
