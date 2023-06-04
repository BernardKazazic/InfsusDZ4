package org.example.application.comunda;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.client.ExternalTaskClient;
import org.example.domain.model.Review;
import org.example.domain.repository.ReviewRepository;
import org.example.interfaces.dto.CorrelationMessage;
import org.example.interfaces.dto.Variable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CamundaExternal {
    private static final String COMUNDA_MESSAGE_URL = "http://localhost:8080/engine-rest/message";
    @NonNull
    private ExternalTaskClient client;
    @NonNull
    private ReviewRepository reviewRepository;
    @NonNull
    private RestTemplate restTemplate;

    public void initialize() {
        client.subscribe("validate")
                .lockDuration(10000)
                .handler((externalTask, externalTaskService) -> {
                    String review = externalTask.<String>getVariable("review");
                    String userId = externalTask.getVariable("user");
                    Map<String, Object> variables = new HashMap<>();
                    if (!review.contains("bad word")) {
                        Review reviewModel = reviewRepository.createReview(userId, review, false);
                        Integer reviewId = reviewModel.getId();
                        variables.put("reviewValid", true);
                        variables.put("reviewId", reviewId);
                        externalTaskService.complete(externalTask, variables);
                    } else {
                        Review reviewModel = reviewRepository.createReview(userId, review, false);
                        Integer reviewId = reviewModel.getId();
                        variables.put("reviewValid", false);
                        variables.put("reviewId", reviewId);
                        externalTaskService.complete(externalTask, variables);
                    }
                })
                .open();
        client.subscribe("publish")
                .lockDuration(10000)
                .handler((externalTask, externalTaskService) -> {
                    Integer reviewId = externalTask.getVariable("reviewId");
                    reviewRepository.publishReview(reviewId);
                    externalTaskService.complete(externalTask);
                })
                .open();
        client.subscribe("delete")
                .lockDuration(10000)
                .handler((externalTask, externalTaskService) -> {
                    Integer reviewId = externalTask.getVariable("reviewId");
                    reviewRepository.deleteReview(reviewId);
                    externalTaskService.complete(externalTask);
                })
                .open();
    }

    public void assignReviewer(String reviewerName) {
        HttpMethod method = HttpMethod.POST;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HashMap<String, Variable> variableHashMap = new HashMap<>();
        variableHashMap.put("reviewer", new Variable(reviewerName));
        CorrelationMessage message = new CorrelationMessage("Support_req", "newReviewKey5", variableHashMap);
        RequestEntity<CorrelationMessage> request = new RequestEntity<>(message, httpHeaders, method, URI.create(COMUNDA_MESSAGE_URL));
        restTemplate.exchange(request, Void.class);
    }
}
