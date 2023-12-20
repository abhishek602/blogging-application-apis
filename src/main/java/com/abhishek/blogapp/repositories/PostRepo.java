package com.abhishek.blogapp.repositories;

import com.abhishek.blogapp.entities.Category;
import com.abhishek.blogapp.entities.Post;
import com.abhishek.blogapp.entities.User;
import com.abhishek.blogapp.payloads.PostDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.xml.transform.sax.SAXResult;
import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);

//    by native queryies
    @Query("select p from Post p where p.title like :key")
    List<Post> searchByTitle(@Param("key") String title);

}
