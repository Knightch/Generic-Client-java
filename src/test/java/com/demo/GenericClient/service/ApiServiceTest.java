package com.demo.GenericClient.service;

import com.demo.GenericClient.model.Generic;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Flux;

import java.time.Duration;

@SpringBootTest
public class ApiServiceTest {

    @Autowired
    ApiService apiService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${genericApiGetUrl}")
    String genericApiGetUrl;
    @Value("${genericApiPostUrl}")
    String genericApiPostUrl;
    @Value("${genericApiPutUrl}")
    String genericApiPutUrl;
    @Value("${genericApiDeleteUrl}")
    String genericApiDeleteUrl;
    @Value("${genericRateLimitedApiGetUrl}")
    String genericRateLimitedApiGetUrl;
    @Value("${genericRateLimitedApiPostUrl}")
    String genericRateLimitedApiPostUrl;
    @Value("${genericRateLimitedApiPutUrl}")
    String genericRateLimitedApiPutUrl;
    @Value("${genericRateLimitedApiDeleteUrl}")
    String genericRateLimitedApiDeleteUrl;
    Generic generic = new Generic(1,"kdlkkdjkldkfks","fdnkjsnfkj","jhfdkjskfj","fdkjhkfsj","dmfkjdhk","jdhfkjhk","jdhjfghkej","jdsbjfhbe","jdsbjfhajd","hbdshbfjd");


    @Test
    public void fetchDataForGeneric() throws InterruptedException {

        Duration interval = Duration.ofMillis(10); // Interval of 10 milliseconds for approximately 100 requests per second

        // Create Flux instances for each HTTP method
        Flux<Generic> getGenericFlux = Flux.interval(Duration.ZERO, interval) .flatMap(i -> apiService.fetchDataFromApi(genericApiGetUrl, HttpMethod.GET, null)
                .onErrorContinue((throwable, obj) -> System.err.println("Error occurred in GET request: " + throwable.getMessage())));

        Flux<Generic> postGenericFlux = Flux.interval(Duration.ZERO, interval) .flatMap(i -> apiService.fetchDataFromApi(genericApiPostUrl, HttpMethod.POST, generic)
                .onErrorContinue((throwable, obj) -> System.err.println("Error occurred in POST request: " + throwable.getMessage())));

        Flux<Generic> putGenericFlux = Flux.interval(Duration.ZERO, interval) .flatMap(i -> apiService.fetchDataFromApi(genericApiPutUrl, HttpMethod.PUT, generic)
                .onErrorContinue((throwable, obj) -> System.err.println("Error occurred in PUT request: " + throwable.getMessage())));


        Flux<Generic> responseFlux = Flux.merge(getGenericFlux, postGenericFlux, putGenericFlux);

        // Subscribe to the Flux and handle the response
        responseFlux.subscribe(
                response -> {
                    // Log each response
                    System.out.println("Response: " + response);
                },
                error -> {
                    // Log the error (if any)
                    System.err.println("Error occurred: " + error);
                },
                () -> {
                    // Log when all responses are received
                    System.out.println("All responses received");
                });

        // Wait for a certain duration before ending the test
        while (true) {
            Thread.sleep(1000); // Sleep for 1 second between iterations
        }

    }


    @Test
    public void fetchDataForGenericRateLimited() throws InterruptedException {
        Duration interval = Duration.ofMillis(10); // Interval of 10 milliseconds for approximately 100 requests per second

        Flux<Generic> getGenericRateLimitedFlux = Flux.interval(Duration.ZERO, interval) .flatMap(i -> apiService.fetchDataFromApi(genericRateLimitedApiGetUrl, HttpMethod.GET, null)
                .onErrorContinue((throwable, obj) -> System.err.println("Error occurred in GET request: " + throwable.getMessage())));

        Flux<Generic> postGenericRateLimitedFlux = Flux.interval(Duration.ZERO, interval) .flatMap(i -> apiService.fetchDataFromApi(genericRateLimitedApiPostUrl, HttpMethod.POST, generic)
                .onErrorContinue((throwable, obj) -> System.err.println("Error occurred in POST request: " + throwable.getMessage())));

        Flux<Generic> putGenericRateLimitedFlux = Flux.interval(Duration.ZERO, interval) .flatMap(i -> apiService.fetchDataFromApi(genericRateLimitedApiPutUrl, HttpMethod.PUT, generic)
                .onErrorContinue((throwable, obj) -> System.err.println("Error occurred in PUT request: " + throwable.getMessage())));

        Flux<Generic> responseFlux = Flux.merge(getGenericRateLimitedFlux, postGenericRateLimitedFlux, putGenericRateLimitedFlux);

        // Subscribe to the Flux and handle the response
        responseFlux.subscribe(
                response -> {
                    // Log each response
                    System.out.println("Response: " + response);
                },
                error -> {
                    // Log the error (if any)
                    System.err.println("Error occurred: " + error);
                },
                () -> {
                    // Log when all responses are received
                    System.out.println("All responses received");
                });

        // Wait for a certain duration before ending the test
        while (true) {
            Thread.sleep(1000); // Sleep for 1 second between iterations
        }
    }

}



