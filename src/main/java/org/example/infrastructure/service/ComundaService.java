package org.example.infrastructure.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.application.comunda.CamundaExternal;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComundaService {
    @NonNull
    private CamundaExternal external;

    public void assignReviewer(String reviewerName) {
        external.assignReviewer(reviewerName);
    }
}
