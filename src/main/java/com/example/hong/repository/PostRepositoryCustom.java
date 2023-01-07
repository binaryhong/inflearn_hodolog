package com.example.hong.repository;

import com.example.hong.domain.Post;
import com.example.hong.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
