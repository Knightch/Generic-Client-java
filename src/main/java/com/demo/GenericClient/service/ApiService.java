package com.demo.GenericClient.service;

import com.demo.GenericClient.model.Generic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ApiService {

    @Autowired
    WebClient webClient;

    public Mono<Generic> fetchDataFromApi(String apiUrl, HttpMethod method, Generic requestBody) {
        WebClient.RequestBodySpec requestSpec = webClient.method(method)
                .uri(apiUrl);

        if (requestBody != null) {
            requestSpec.body(BodyInserters.fromValue(requestBody));
        }

        return requestSpec
                .retrieve()
                .bodyToMono(Generic.class);
    }
}
