package  com.drsf.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

public class DeserializerTest {

    @Test
    public void testJacksonDeserializer() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        Files.lines(Paths.get("src/test/resources/payloads/polygonDividends.json"));
        System.out.println("src/main/test");
    }


}
