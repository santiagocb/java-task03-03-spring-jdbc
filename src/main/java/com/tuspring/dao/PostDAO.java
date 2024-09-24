package com.tuspring.dao;


import com.tuspring.dto.Post;

import java.util.List;

public interface PostDAO {
    void save(Post post);
    List<Post> findByUserId(long userId);
    void deleteById(long id);
}
