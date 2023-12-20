package com.abhishek.blogapp.payloads;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtAuthResponse {
    private String token;
    private String username;
}
