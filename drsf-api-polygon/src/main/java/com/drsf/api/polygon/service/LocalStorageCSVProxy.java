package com.drsf.api.polygon.service;

import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LocalStorageCSVProxy {

    public static void save(Mono<String> publisher, String filePath) {
        publisher.subscribe(m -> {
            try {
                Files.write(Paths.get(filePath), ((String)m).getBytes());
            } catch (IOException e) {

            }
        });
    }

//    public static void save(String> publisher, String filePath) {
//        publisher.subscribe(m -> {
//            try {
//                Files.write(Paths.get(filePath), ((String)m).getBytes());
//            } catch (IOException e) {
//
//            }
//        });
//    }

}
