package com.abhishek.blogapp.payloads;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtAuthRequest {
    private String username;
    private String password;
}















