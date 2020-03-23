import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.just;

public class PresentationTest {


    @Test
    public void empty() {
        Mono<String> emptyMono = Mono.empty();
        StepVerifier.create(emptyMono).verifyComplete();
        Flux<String> emptyFlux = Flux.empty();
        StepVerifier.create(emptyFlux).verifyComplete();
    }


    @Test
    public void initialized() {
        Mono<String> nonEmptyMono = just("V");
        StepVerifier.create(nonEmptyMono).expectNext("V").verifyComplete();
        Flux<String> nonEmptyFlux = Flux.just("MC", "V", "AMEX");
        StepVerifier.create(nonEmptyFlux).expectNext("MC", "V", "AMEX").verifyComplete();
        Flux<String> fluxFromIterable = Flux.fromIterable(Arrays.asList("MC", "V", "AMEX"));
        StepVerifier.create(fluxFromIterable).expectNext("MC", "V", "AMEX").verifyComplete();
    }

    @Test
    public void callWithMock() {
        Presentation presentation = mock(Presentation.class);

        when(presentation.doService(any(Function.class))).thenCallRealMethod();

        Mono<Processed> result = presentation.doService(s -> Optional.of("result"));

        verify(presentation).doService(any(Function.class));

//        Processed processed = result.subscribe();
    }

    @Test
    public void callWithResult() {
        Processed processed = new Processed("result");
        Presentation presentation = new Presentation();
        StepVerifier.create(presentation.doService(s -> Optional.of("result")))
                .expectNext(processed).verifyComplete();
    }

    @Test
    public void callWithEmptyResult() {
        Presentation presentation = new Presentation();
        StepVerifier.create(presentation.doService(s -> Optional.empty()))
                .expectErrorMatches(throwable -> throwable instanceof Exception &&
                        throwable.getMessage().equals("Not Found"))
                .verify();

    }
}


