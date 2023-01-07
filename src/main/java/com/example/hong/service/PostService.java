package com.example.hong.service;

import com.example.hong.domain.Post;
import com.example.hong.domain.PostEditor;
import com.example.hong.exception.PostNotFound;
import com.example.hong.repository.PostRepository;
import com.example.hong.request.PostCreate;
import com.example.hong.request.PostEdit;
import com.example.hong.request.PostSearch;
import com.example.hong.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }
    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);
//                        new IllegalArgumentException("존재하지 않는 글입니다."));

        return  PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostResponse> getList(PostSearch postSearch) {
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponse edit(Long id, PostEdit postEdit){
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);


        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();
//         값 변경시 이전 값 유지를 원한다면.
//          if(boardEdit.getTitle() != null){
//                      editorBuilder.title(boardEdit.getTitle());
//                  }
//                  if(boardEdit.getContent() != null){
//                      editorBuilder.content(boardEdit.getContent());
//                  }
//
//                  post.edit(editorBuilder.build());

        PostEditor postEditor = editorBuilder.title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

        post.edit(postEditor);

        return new PostResponse(post);
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);
        postRepository.delete(post);
    }
}

