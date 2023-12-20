package com.abhishek.blogapp.payloads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private  Integer categoryId;
    @NotEmpty
    @Size(min = 4,message = "Must be minimum 4 characters in title")
    private  String categoryTitle;

    @NotEmpty
    @Size(min = 10,message = "Must be minimum 10 characters in description")
    private  String categoryDescription;
}
