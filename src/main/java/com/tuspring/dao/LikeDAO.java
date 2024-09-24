package com.tuspring.dao;


import com.tuspring.dto.Like;

import java.util.List;

public interface LikeDAO {
    void save(Like like);
    List<Like> findByPostId(long postId);
    void deleteByPostId(long postId);
}
