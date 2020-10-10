package com.drsf.polygon;

import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestConsumer {

    @Bean
    public static Object testGenericConsumer(RestTemplate restTemplate, String url, Class resultType){
        return restTemplate.getForObject(url, resultType);
    }

    public String getPolygonResponseRaw(String url, RestTemplate restTemplate, HttpHeaders defaultHeaders) {
        ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(defaultHeaders), String.class);
        return response.getBody().toString();
    }
    public Object getPolygonResponse(String url, RestTemplate restTemplate, HttpHeaders defaultHeaders) {
        ResponseEntity response = getHTTPResponse(url, restTemplate, defaultHeaders, new ParameterizedTypeReference<String>(){});

        if (response.getStatusCodeValue() == 200 && response.getBody() != null)  {
            return response.getBody();
        }
        return null;
    }


    public String getHTTPResponseAsString(String url, RestTemplate restTemplate, HttpHeaders headers) {
        return null;
    }


    public <T> ResponseEntity<T> getHTTPResponse(String url, RestTemplate restTemplate, HttpHeaders headers,ParameterizedTypeReference<T> classType) {
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), classType);
    }

}
