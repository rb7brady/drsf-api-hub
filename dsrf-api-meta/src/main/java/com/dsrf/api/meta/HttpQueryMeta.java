package com.dsrf.api.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpQueryMeta extends QueryMeta {

    public void putBaseEndpoint(String endpoint) {
        this.put("URL", endpoint);

    }

    public void putParamaterizedURI(String uri) {
        this.put("URI", uri);
    }

    public void putURIParamValue(Object value) {
//        List<String> requiredParams = (List<String>) this.getOrDefault("requiredParams", new ArrayList<String>());
//        requiredParams.add((String) value);
//        this.put("requiredParams", requiredParams);

        if (value != null) {
            Map<String, Object> requiredParams = (HashMap<String, Object>) this.getOrDefault("requiredParams", new HashMap<String, Object>());
            requiredParams.putIfAbsent(value.toString(), value);
            this.put("requiredParams", requiredParams);
        }
    }

    public void putParameterizedOptional(String name, Object value) {
        Map<String, Object> optionalParams  = (HashMap<String, Object>) this.getOrDefault("optionalParams", new HashMap<String, Object>());
        optionalParams.putIfAbsent(name, value);
        this.put("optionalParams",optionalParams);
    }


    public List<Object> getAllParamValues() {
       // List<Object> requiredParams = (List<String>) this.getOrDefault("requiredParams", new ArrayList<String>());

       return  Stream.concat(
                //((List<String>) this.getOrDefault("requiredParams", new ArrayList<String>())).stream()
       (((HashMap<String, String>) this.getOrDefault("requiredParams", new HashMap<String, String>())).values().stream().collect(Collectors.toList())).stream(),

        (((HashMap<String, String>) this.getOrDefault("optionalParams", new HashMap<String, String>())).values().stream().collect(Collectors.toList())).stream()
        ).collect(Collectors.toList());
    }

}
