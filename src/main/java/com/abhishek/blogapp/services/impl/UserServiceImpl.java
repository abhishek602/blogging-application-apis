package com.abhishek.blogapp.services.impl;

import com.abhishek.blogapp.configs.AppConstants;
import com.abhishek.blogapp.entities.Role;
import com.abhishek.blogapp.entities.User;
import com.abhishek.blogapp.exceptions.ResourceNotFoundException;
import com.abhishek.blogapp.payloads.UserDto;
import com.abhishek.blogapp.repositories.RoleRepo;
import com.abhishek.blogapp.repositories.UserRepo;
import com.abhishek.blogapp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user =  this.modelMapper.map(userDto,User.class);
     // encoded password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

     // roles
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);

        User newUser = this.userRepo.save(user);
        return this.modelMapper.map(newUser,UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }


    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User"," Id",userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updatedUser = this.userRepo.save(user);
        return this.userToDto(user);
    }


    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User"," Id",userId));
        this.userRepo.delete(user);
        System.out.println("User Deleted "+user.getName());
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User"," Id",userId));

        return this.userToDto(user);
    }



    @Override
    public List<UserDto> getAllUsers() {
       List<User>  users = this.userRepo.findAll();
        return users.stream().map(this::userToDto).toList();
    }


    public User dtoToUser(UserDto userDto){
        return this.modelMapper.map(userDto,User.class);
    }


    public UserDto userToDto(User user){
        // with the help of modelmapper.
        UserDto userDto = this.modelMapper.map(user,UserDto.class);

        // without modelmapper

//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());

        return userDto;
    }

}
