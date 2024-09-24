package com.tuspring.dao;


import com.tuspring.dto.User;

import java.util.List;

public interface UserDAO {
    void save(User user);
    void saveAll(List<User> users);
    User findById(long id);
    List<User> findAll();
    List<String> findUserNamesWithMoreThanFriendsNumberAnd100LikesInTheLasthMonth(long friendShips);
    void deleteById(long id);
}
