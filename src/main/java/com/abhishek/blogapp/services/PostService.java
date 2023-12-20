package com.abhishek.blogapp.services;

import com.abhishek.blogapp.entities.Category;
import com.abhishek.blogapp.entities.Post;
import com.abhishek.blogapp.payloads.PostDto;
import com.abhishek.blogapp.payloads.PostResponse;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface PostService {

    // create
    PostDto createPost(PostDto postDto, Integer userId,Integer categoryId);



    // update
    PostDto updatePost(PostDto postDto, Integer postId);

    // delete
    void deletePost(Integer postId);

    // get all
    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);

    // get single post
    PostDto getPostById(Integer postId);

    // get post by category
    List<PostDto> getPostsByCategory(Integer categoryId);

    // get post by user
    List<PostDto> getPostsByUser(Integer postId);

    // search post by keyword
    List<PostDto> searchPosts(String keyword);
}
