package com.example.circuitbreakerpoc;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class TestController {

    @Autowired
    @Qualifier(value="hystrixRestTemplate")
    RestTemplate hystrixRestTemplate;


    @HystrixCommand(fallbackMethod = "createHystrixFallback", commandProperties={
            @HystrixProperty(name ="execution.isolation.strategy", value = "THREAD"),
            @HystrixProperty(name ="execution.isolation.thread.timeoutInMilliseconds", value = "500"),
            @HystrixProperty(name ="metrics.rollingStats.timeInMilliseconds", value = "100000"),
            @HystrixProperty(name ="circuitBreaker.requestVolumeThreshold", value = "1"),
            @HystrixProperty(name ="circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name ="circuitBreaker.sleepWindowInMilliseconds", value = "50000")
    })
    @GetMapping(value = "/hystrix/{userId}")
    public ResponseEntity<?> createHystrixAccounts(@PathVariable("userId") String userId) {
        System.out.println("createHystrixAccounts:::::::");
        ResponseEntity<String> responseEntity = hystrixRestTemplate.getForEntity("https://xxxyyyy/111", String.class);
        System.out.println("responseEntity:::::::"+responseEntity);
        return new ResponseEntity<>(responseEntity,  HttpStatus.OK);
    }


    public ResponseEntity<?> createHystrixFallback(String userId){

        String response = "fallback response for walletId : "+userId;

        return new ResponseEntity<>(response,  HttpStatus.OK);
    }
}
