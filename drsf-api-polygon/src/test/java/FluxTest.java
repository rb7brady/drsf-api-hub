import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;
import reactor.util.context.Context;

import java.time.Duration;
import java.util.Arrays;
import java.util.Random;

public class FluxTest {


    @Test
    public void firstFlux() {
        Flux.just("A","B","C")
                .log()
                .subscribe();
    }

    @Test
    public void fluxFromIterable() {
        Flux.fromIterable(Arrays.asList("A","B","C"))
                .log()
                .subscribe();
    }

    @Test
    public void fluxFromRange() {
        Flux.range(10,5)
                .log().subscribe();
    }

    /**
     * This does not print out because it is ran on a separate thread.
     */
    @Test
    public void fluxFromInterval() {
        Flux.interval(Duration.ofSeconds(3))
                .log()
                .subscribe();
    }
    /**
     * This will print out with a Thread.sleep call.
     *
     * Sleep needs to be longer than the duration of seconds.
     */
    @Test
    public void fluxFromIntervalThreadSleep() throws InterruptedException {
        Flux.interval(Duration.ofSeconds(2))
                .log()
                .subscribe();
        Thread.sleep(5000);
    }

    /**
     * Take forces the subscription to be cancelled after n values returned.
     * @throws InterruptedException
     */
    @Test
    public void fluxFromIntervalTake() throws InterruptedException {
        Flux.interval(Duration.ofSeconds(1))
                .log()
                .take(2)
                .subscribe();
        Thread.sleep(5000);
    }

    @Test
    public void fluxRequest() {
        Flux.range(1,5)
                .log()
                .subscribe(null,
                        null,
                        null,
                        s -> s.request(3));
    }

    @Test
    public void fluxCustomSubscriber() {
        Flux.range(1,10)
                .log()
                .subscribe(new BaseSubscriber<Integer>() {
                    int elementsToProcess = 3;
                    int counter = 0;


                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        System.out.println("Subscribed!");
                        request(elementsToProcess);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        counter++;
                        if (counter == elementsToProcess) {
                           counter = 0;
                           Random r = new Random();
                           elementsToProcess = r.ints(1,4)
                                   .findFirst().getAsInt();
                           request(elementsToProcess);
                        }
                    }
                });
    }

    @Test
    public void fluxLimitRate() {
        Flux.range(1,5)
                .log()
                .limitRate(3)
                .subscribe();
    }
}
