package org.example.infrastructure.configuration;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.client.ExternalTaskClient;
import org.example.application.comunda.CamundaExternal;
import org.example.domain.repository.ReviewRepository;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class ComundaConfiguration {
    @NonNull
    private DSLContext dslContext;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ReviewRepository getReviewRepository() {
        return new ReviewRepository(dslContext);
    }

    @Bean
    public ExternalTaskClient getExternalTaskClient() {
        return ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest")
                .build();
    }

    @Bean
    public CamundaExternal getCamundaExternal() {
        CamundaExternal camundaExternal = new CamundaExternal(getExternalTaskClient(), getReviewRepository(), getRestTemplate());
        camundaExternal.initialize();
        return camundaExternal;
    }
}
