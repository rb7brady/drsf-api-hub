import net.minidev.json.JSONUtil;
import org.junit.Test;
import org.w3c.dom.ls.LSOutput;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

public class MonoTest {

    private static void accept(String m) {
        try {
            Files.write(Paths.get("app.log"), m.getBytes());
        } catch (IOException e) {
            System.out.println("IO Exception Logged.");
        }
    }

    @Test
    public void mono() {
        Mono.just("A")
                .log();
    }

    @Test
    public void monoWithSubscriber() {
        Mono.just("A")
                .log()
                .subscribe();
    }

    @Test
    public void monoWithConsumer() {
        Mono.just("A")
                .log()
                .subscribe(s -> System.out.println(s));
    }

    @Test
    public void monoWithDoOn() {
        Mono.just("A")
                .log()
                .doOnSubscribe(subs -> System.out.println("Subscribed: " + subs))
                .doOnRequest(request -> System.out.println("Request: " + request))
                .doOnSuccess(complete -> System.out.println("Complete: " + complete))
                .subscribe(System.out::println);
    }

    @Test
    public void emptyMono() {
        Mono.empty()
                .log()
                .subscribe(System.out::println);
    }

    @Test
    public void emptyCompleteConsumerMono() {
        Mono.empty()
                .log()
                .subscribe(System.out::println,null,() -> System.out.println("Done"));
    }

    @Test
    public void errorRuntimeExceptionMono() {
        Mono.error(new RuntimeException())
                .log()
                .subscribe();
    }

    @Test
    public void errorExceptionMono(){
        Mono.error(new Exception())
                .log()
                .subscribe();
    }

    @Test
    public void errorConsumerMono() {
        Mono.error(new Exception())
                .log()
                .subscribe(System.out::println,
                        e -> System.out.println("Error: " + e));
    }

    @Test
    public void errorMonoDoOnError() {
        Mono.error(new Exception())
                .log()
                .doOnError(e -> System.out.println("Error: " + e))
                .subscribe();
    }

    @Test
    public void errorOnErrorResumeMono() {
        Mono.error(new Exception())
                .onErrorResume(e -> {
                    System.out.println("Caught: " + e);
                    return Mono.just("B");
                })
                .subscribe();
    }


    /**
     * Author: rbrady
     */
    @Test
    public void monoWithFileWriteTryCatch() {
        Mono.just("A")
                .log()
                .doOnSubscribe(subs -> System.out.println("Subscribed: " + subs))
                .doOnRequest(request -> System.out.println("Request: " + request))
                .doOnSuccess(complete -> System.out.println("Complete: " + complete))
                .subscribe(m -> {
                    try {
                        Files.write(Paths.get("app.log"), m.getBytes());
                    } catch (IOException e) {

                    }
                });
    }

    /**
     * Author: rbrady
     */
    @Test
    public void monoWithFileWriteErrorHandler() {
        Mono.just("A")
                .log()
                .doOnSubscribe(subs -> System.out.println("Subscribed: " + subs))
                .doOnRequest(request -> System.out.println("Request: " + request))
                .doOnSuccess(complete -> System.out.println("Complete: " + complete))
                .subscribe(MonoTest::accept,null);
    }


}
