package com.dsrf.api.meta;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpQueryMeta extends QueryMeta {
    public static final String EXPECTED_CLASS = "expectedClass";
    public static final String URL = "URL";
    public static final String URI = "URI";
    public static final String REQUIRED_PARAMS = "requiredParams";
    public static final String OPTIONAL_PARAMS = "optionalParams";
    public static final String HEADERS = "headers";
    public static final String BODY = "body";


    public HttpQueryMeta putExpectedClass(Class expectedClass) {
        this.put(EXPECTED_CLASS, expectedClass);
        return this;
    }

    public HttpQueryMeta putBaseEndpoint(String endpoint) {
        this.put(URL, endpoint);
        return this;
    }

    public HttpQueryMeta putParamaterizedURI(String uri) {
        this.put(URI, uri);
        return this;
    }

    public HttpQueryMeta putURIParamValue(Object value) {
        if (value != null) {
            Map<String, Object> requiredParams = (HashMap<String, Object>) this.getOrDefault(REQUIRED_PARAMS, new HashMap<String, Object>());
            requiredParams.putIfAbsent(value.toString(), value);
            this.put(REQUIRED_PARAMS, requiredParams);
        }
        return this;
    }

    public HttpQueryMeta putParameterizedOptional(String name, Object value) {
        Map<String, Object> optionalParams = (HashMap<String, Object>) this.getOrDefault(OPTIONAL_PARAMS, new HashMap<String, Object>());
        optionalParams.putIfAbsent(name, value);
        this.put(OPTIONAL_PARAMS, optionalParams);
        return this;
    }

    public HttpQueryMeta putParameterizedOptionalNullExcluded(String name, Object value) {
        if (StringUtils.isNotEmpty(name) && value != null) {
            return putParameterizedOptional(name, value);
        }
        return this;
    }

    public HttpQueryMeta putHeader(String name, String value) {
        Map<String, Object> headers  = (HashMap<String, Object>) this.getOrDefault(HEADERS, new HashMap<String, Object>());
        headers.putIfAbsent(name, value);
        this.put(HEADERS, headers);
        return this;
    }

    public HttpQueryMeta putBodyAttribute(String name, String value) {
        Map<String, String> body = (HashMap<String, String>) this.getOrDefault(BODY, new HashMap<String, String>());
        body.putIfAbsent(name, value);
        this.put(BODY,body);
        return this;
    }

    public HttpQueryMeta clearAll() {
        this.clear();
        return this;
    }

    public List<Object> getAllParamValues() {

       return Stream
               .concat(
                       (((HashMap<String, String>) this.getOrDefault(REQUIRED_PARAMS, new HashMap<String, String>())).values().stream().collect(Collectors.toList())).stream(),
                       (((HashMap<String, String>) this.getOrDefault(OPTIONAL_PARAMS, new HashMap<String, String>())).values().stream().collect(Collectors.toList())).stream()
               ).collect(Collectors.toList());
    }

    public boolean hasHeaders () {
        return this.get(HEADERS) != null;
    }

    public boolean hasBody () {
        return this.get(BODY) != null;
    }

    public boolean hasURI() { return this.get(URI) != null; }

    public boolean hasRequiredParams() { return this.get(REQUIRED_PARAMS) !=null; }

    public boolean hasOptionalParams() { return this.get(OPTIONAL_PARAMS) != null; }

    public boolean hasExpectedClass() {
        return this.get(EXPECTED_CLASS) != null;
    }


}
