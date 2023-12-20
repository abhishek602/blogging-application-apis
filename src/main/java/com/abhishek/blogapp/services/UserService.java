package com.abhishek.blogapp.services;

import com.abhishek.blogapp.payloads.UserDto;

import java.util.List;

public interface UserService {
   UserDto registerNewUser(UserDto userDto);
   UserDto createUser(UserDto userDto);
   UserDto updateUser(UserDto userDto,Integer userId);

   void deleteUser(Integer userId);

   UserDto getUserById(Integer userId);

   List<UserDto> getAllUsers();

}
