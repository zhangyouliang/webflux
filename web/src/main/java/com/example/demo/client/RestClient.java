package com.example.demo.client;

import com.example.demo.domain.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Random;

/**
 * @author youliangzhang
 * @date 2018/5/26  下午6:17
 **/
public class RestClient {

    public static void main(final String[] args) {
        final User user = new User();
        user.setName("Test");
        user.setId(new Random(1000).nextInt());
        final WebClient client = WebClient.create("http://localhost:8080/user/createOne");
        final Mono<User> createdUser = client.post()
                .uri("")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(user), User.class)
                .exchange()
                .flatMap(response -> response.bodyToMono(User.class));
        System.out.println(createdUser.block());


    }


}