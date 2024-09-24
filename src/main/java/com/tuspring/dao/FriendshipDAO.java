package com.tuspring.dao;

import com.tuspring.dto.Friendship;

import java.util.List;

public interface FriendshipDAO {
    void save(Friendship friendship);
    List<Friendship> findByUserId(long userId);
    void deleteByUserId(long userId);
}
