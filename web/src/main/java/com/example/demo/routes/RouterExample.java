package com.example.demo.routes;

import com.example.demo.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author youliangzhang
 * @date 2018/5/26  下午10:11
 **/
@Configuration
public class RouterExample {
    // todo http://localhost:8080/routeExample
    @Bean
    public RouterFunction<ServerResponse> reoutes() {
        return RouterFunctions.route(
                GET("/routeExample"), serverRequest -> {
                    User u = new User();
                    u.setId(1);
                    u.setName("Hello");
                    Mono<User> user = Mono.just(u);
                    return ok().body(fromPublisher(user, User.class));
                }
        );
    }
}
