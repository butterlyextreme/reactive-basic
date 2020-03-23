import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Function;

import static reactor.core.publisher.Mono.*;

public class Presentation {


    public Mono<Processed> doService(Function<Void, Optional<String>> execute) {
        return justOrEmpty(execute.apply(null))
                .doOnSubscribe(__ -> System.err.println("Subscribed to doService"))
                .doOnSuccess(__ -> System.err.println("Completed doService"))
                .flatMap(response -> {
                    if (response.equals("500")) {
                        return error(new Exception("Internal"));
                    }
                    return just(new Processed(response));
                })
                .switchIfEmpty(defer(() -> {
                    System.err.println("do something here");
                    return error(new Exception("Not Found"));
                }));

    }


}
