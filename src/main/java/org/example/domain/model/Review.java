package org.example.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Review {
    private Integer id;
    private String userId;
    private String review;
    private Boolean valid;
}
