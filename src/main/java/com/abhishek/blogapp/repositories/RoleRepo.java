package com.abhishek.blogapp.repositories;

import com.abhishek.blogapp.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer> {

}
