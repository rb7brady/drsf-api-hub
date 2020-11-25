package com.drsf.api.polygon.service;

import com.drsf.api.polygon.annotations.persist;
import com.drsf.api.polygon.model.DividendResult;
import com.drsf.api.polygon.model.Dividends;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.BaseStream;
import java.util.stream.Collectors;

public class DataStoreService {



    public static void merge(String key, Dividends dividends) {

        List<String> orderedCsv = dividends.getResults().stream().map(r -> convertDividendResultToCSVString(r)).collect(Collectors.toList());

        try {
            String pathString = determineStoragePath(dividends.getClass()).toString();

            Path filePath = Files.createFile(Paths.get(pathString + key+ "/dividends.csv"));
            //filePath.
            Files.write(filePath, orderedCsv);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Flux<String> fromPath(Path path) {
        return Flux.using(() -> Files.lines(path),
                Flux::fromStream,
                BaseStream::close
        );
    }

//   printStackTrace public static File getFileIfExists(Path path) {
//        Files.createFile()
//    }

    public static Path determineStoragePath(Class input) {
        try {
            return Files.createDirectories(Paths.get("src/main/resources/dataStore/" + input.getSimpleName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertDividendResultToCSVString(DividendResult result) {
        List<Field> persistableFields = Arrays.asList(DividendResult.class.getDeclaredFields()).stream().filter(f -> f.isAnnotationPresent(persist.class)).collect(Collectors.toList());

        String csvRow = persistableFields.stream().map(f -> {
            try {

                Object value = Arrays
                        .stream(result.getClass().getMethods())
                        .filter(m -> m.getName().equalsIgnoreCase("get" + f.getName()))
                        .findFirst()
                        .get()
                        .invoke(result);
                return (value != null) ? value.toString() : "" ;
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.joining(","));

        return csvRow;
    }



}

