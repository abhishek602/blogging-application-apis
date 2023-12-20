package com.abhishek.blogapp.payloads;


import com.abhishek.blogapp.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

// UserDto = user data transfer object, with necessary data to expose .

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private Integer id;

    @NotEmpty
    @Size(min = 4,message = "Username must be minimum of 4 characters.")
    private String name;

    @Email(message = "Email address is not valid.")
    private  String email;

    @NotEmpty
    @Size(min = 3,max = 10,message = "password should be b/w  3  and 10 characters.")
    private  String password;

    @NotEmpty
    private  String about;

    private Set<RoleDto> roles = new HashSet<>();
}
