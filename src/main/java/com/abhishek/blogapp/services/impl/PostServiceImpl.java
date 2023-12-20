package com.abhishek.blogapp.services.impl;

import com.abhishek.blogapp.entities.Category;
import com.abhishek.blogapp.entities.Post;
import com.abhishek.blogapp.entities.User;
import com.abhishek.blogapp.exceptions.ResourceNotFoundException;
import com.abhishek.blogapp.payloads.PostDto;
import com.abhishek.blogapp.payloads.PostResponse;
import com.abhishek.blogapp.repositories.CategoryRepo;
import com.abhishek.blogapp.repositories.PostRepo;
import com.abhishek.blogapp.repositories.UserRepo;
import com.abhishek.blogapp.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User ","UserId ",userId));

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category ","Category Id ",categoryId));

        Post post = this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost = this.postRepo.save(post);
        return this.modelMapper.map(newPost,PostDto.class);
    }


    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","post Id",postId));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post updatedPostDto = this.postRepo.save(post);
        return this.modelMapper.map(updatedPostDto,PostDto.class);
    }


    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post ","post id",postId));
        this.postRepo.delete(post);
    }


    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy, String sortDir) {
        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }else{
            sort = Sort.by(sortBy).descending();
        }

        Pageable p = PageRequest.of(pageNumber,pageSize, sort);
        Page<Post> pagePost = this.postRepo.findAll(p);
        List<Post> allPosts = pagePost.getContent();

        //convert Posts into PostDtos.
        List<PostDto> postDtos= allPosts.stream().map((post)-> this.modelMapper.map(post,PostDto.class))
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElement(pagePost.getTotalElements());

        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return  postResponse;
    }


    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post ","post id",postId));
        return this.modelMapper.map(post,PostDto.class);
    }


    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category ","Category id ",categoryId));
        List<Post> posts = this.postRepo.findByCategory(category);
        return posts.stream().map(post -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
    }


    @Override
    public List<PostDto> getPostsByUser(Integer postId) {
        User user = this.userRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("User ","User id ",postId));
        List<Post> posts = this.postRepo.findByUser(user);
        return posts.stream().map(post -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
    }


    @Override
    public List<PostDto> searchPosts(String keyword) {
//        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
        List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
        return posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
    }
}
