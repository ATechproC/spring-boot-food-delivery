package com.atechproc.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class ContactInfo {

    @NotBlank(message = "Res email is required")
    private String email;

    @NotBlank(message = "Res mobile phone is required")
    private String mobile = "";

    private String twitter = "";

    private String instagram = "";
}
