package com.abhishek.blogapp;

import com.abhishek.blogapp.configs.AppConstants;
import com.abhishek.blogapp.entities.Role;
import com.abhishek.blogapp.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@EnableAutoConfiguration
public class BlogAppApisApplication implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    public static void main(String[] args) {

        SpringApplication.run(BlogAppApisApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return  new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(this.passwordEncoder.encode("1234"));
        try {
            Role role1 = new Role();
            role1.setId(AppConstants.ADMIN_USER);
            role1.setName("ROLE_ADMIN");

            Role role2 = new Role();
            role2.setId(AppConstants.NORMAL_USER);
            role2.setName("ROLE_NORMAL");

            List<Role> roles = List.of(role1, role2);
            List<Role> savesRoles = this.roleRepo.saveAll(roles);

            savesRoles.forEach(rl-> System.out.println(rl.getName()));

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
