package com.abhishek.blogapp.controllers;


import com.abhishek.blogapp.payloads.ApiResponse;
import com.abhishek.blogapp.payloads.CommentDto;
import com.abhishek.blogapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
                                                    @PathVariable("postId") Integer postId){
        CommentDto createdComment =  this.commentService.createComment(commentDto,postId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }


    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer cmtID){
        this.commentService.deleteComment(cmtID);
        return new ResponseEntity<>(new ApiResponse("Comment deleted successfully!",true), HttpStatus.CREATED);
    }
}
