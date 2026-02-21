package com.atechproc.dto;

import com.atechproc.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class FavResDto {
    private Long id;
    private String title;
    private String description;
    private List<String> images;
}
