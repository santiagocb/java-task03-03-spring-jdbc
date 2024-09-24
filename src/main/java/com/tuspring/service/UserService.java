package com.tuspring.service;

import com.tuspring.dao.FriendshipDAO;
import com.tuspring.dao.UserDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDAO userDAO;
    private final FriendshipDAO friendshipDAO;

    public UserService(@Qualifier("userDAO1") UserDAO userDAO, FriendshipDAO friendshipDAO) {
        this.userDAO = userDAO;
        this.friendshipDAO = friendshipDAO;
    }

    public List<String> getUserNamesWithMoreThanFriendsNumberAnd100LikesInTheLasthMonth(long userFriendShips) {
        return userDAO.findUserNamesWithMoreThanFriendsNumberAnd100LikesInTheLasthMonth(userFriendShips);
    }

    public long getFriendshipNumberAverage() {
        var users = userDAO.findAll();

        return (long) users
                .stream()
                .map(u -> friendshipDAO.findByUserId(u.getId()).size()).mapToDouble(Integer::doubleValue)
                .average()
                .orElse(0.0);
    }
}