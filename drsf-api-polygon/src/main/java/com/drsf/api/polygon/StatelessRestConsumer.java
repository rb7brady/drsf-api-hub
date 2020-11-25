package com.drsf.api.polygon;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class StatelessRestConsumer {

    static {

    }


    public static Object testGenericConsumer(RestTemplate restTemplate, String url, Class resultType){
        //log.info(historicQuotes.toString());
        return restTemplate.getForObject(url, resultType);
    }

    public static String getPolygonResponseRaw(String url, RestTemplate restTemplate, HttpHeaders defaultHeaders) {
        ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(defaultHeaders), String.class);
        return response.getBody().toString();
    }
    public static <T> Object getPolygonResponse(String url, RestTemplate restTemplate, HttpHeaders defaultHeaders, ParameterizedTypeReference<T> classType) {

        ResponseEntity response = getHTTPResponse(url, restTemplate, defaultHeaders, classType);

        if (response.getStatusCodeValue() == 200 && response.getBody() != null)  {
            return response.getBody();
        }
        return null;
    }

    public static <T> Object getPolygonResponseCasted(String url, RestTemplate restTemplate, HttpHeaders defaultHeaders, T classType) {
        //String responseBodyString = getPolygonResponseRaw(url, restTemplate, defaultHeaders);
        Object responseBodyObject = getPolygonResponse(url, restTemplate, defaultHeaders, new ParameterizedTypeReference<Object>() {});
        //objects.add(responseBodyObject);
        //Object responseObjectAsList = Collections.singletonList(responseBodyObject);
        try {
            //return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false).convertValue(responseBodyObject, new ParameterizedTypeReference<T>(){});
        } catch (Exception e) {
            e.printStackTrace();
        }
        //((List<?>)responseObjectAsList).stream().forEach(System.out::println);

//        return ((List<T>)responseObjectAsList)
//                        .stream()
//                        .filter(o -> new ObjectMapper().configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false).convertValue(o, classType.getClass())!=null)
//                        .map(o -> new ObjectMapper().configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false).convertValue(o, classType.getClass()))
//                        .collect(Collectors.toList());

//        try {
//
//        } catch (Exception e) {
//            return null;
//        }
        return null;
    }


    public static String getHTTPResponseAsString(String url, RestTemplate restTemplate, HttpHeaders headers) {
        return null;
    }

    public static <T> ResponseEntity<T> getHTTPResponse(String url, RestTemplate restTemplate, HttpHeaders headers,ParameterizedTypeReference<T> classType) {
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), classType);
    }
}
