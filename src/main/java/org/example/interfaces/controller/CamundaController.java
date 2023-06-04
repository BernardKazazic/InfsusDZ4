package org.example.interfaces.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.infrastructure.service.ComundaService;
import org.example.interfaces.dto.ReviewerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CamundaController {
    @NonNull
    private ComundaService service;

    @PostMapping("/assign-reviewer")
    public ResponseEntity<Void> postAssignReviewer(@RequestBody ReviewerDto reviewerDto) {
        service.assignReviewer(reviewerDto.reviewerName());
        return ResponseEntity.ok(null);
    }
}
